<template>
  <div class="generate-quote-container">
    <div class="generated-quote-info">
      <span class="italic" v-if="!quote.quoteText && !generating && !generationFailed"
        >Click the button below to get a quote...</span
      >
      <span class="italic" v-if="generating">Generating quote...</span>
      <span v-if="!generating && generationFailed"
        >There was a problem generating a quote. If this issue persists, please try again later.</span
      >
      <span class="generated-quote-text">{{ quote.quoteText }}</span>
      <span class="generated-quote-author" v-if="quote.author">- By {{ quote.author }}</span>
      <span class="generated-quote-origin" v-if="quote.origin">- From {{ quote.origin }}</span>
    </div>
    <div class="generated-quote-actions">
      <button class="btn-generate-quote" @click="handleGenerate">
        <font-awesome-icon class="spinner-icon fa-spin" icon="circle-notch" size="lg" v-if="generating" />
        <span>Generate Quote</span>
      </button>
      <button
        ref="saveButton"
        class="btn-save-generated-quote"
        :class="{ 'save-failed': saveFailed, 'save-complete': saveComplete }"
        :disabled="saveComplete"
        v-if="quote.quoteText && !generating"
        @click="save"
      >
        <font-awesome-icon icon="floppy-disk" size="lg" v-if="showSaveIcon" />
        <font-awesome-icon class="fa-spin" icon="circle-notch" size="lg" v-if="saving" />
        <font-awesome-icon :icon="saveFailedIcon" size="lg" v-if="showSaveFailedIcon" />
        <font-awesome-icon icon="check" size="lg" v-if="showSaveCompleteIcon" />
      </button>
    </div>
  </div>
</template>

<script lang="ts">
import { CreationType } from "@/enums/CreationType";
import { generateQuote } from "@/services";
import { saveGeneratedQuote, saveQuote } from "@/services/modules/QuoteService";
import type { Quote } from "@/types/Quote";
import { notify } from "@kyvg/vue3-notification";
import { computed, defineComponent, ref, type Ref } from "vue";

export default defineComponent({
  setup() {
    let quote = ref<Quote>({
      quoteText: "",
      author: "",
      origin: "",
      creationType: CreationType.GENERATED
    });

    const generating = ref(false);
    const generationFailed = ref(false);
    const saveButton: Ref<HTMLButtonElement | null> = ref(null);
    const saving = ref(false);
    const saveFailed = ref(false);
    const saveComplete = ref(false);
    const saveFailedIcon = ref("triangle-exclamation");

    const showSaveIcon = computed(() => {
      return !saveFailed.value && !saving.value && !saveComplete.value;
    });

    const showSaveFailedIcon = computed(() => {
      return saveFailed.value && !saving.value;
    });

    const showSaveCompleteIcon = computed(() => {
      return !saveFailed.value && saveComplete.value;
    });

    const handleGenerate = async () => {
      generating.value = true;
      generationFailed.value = false;
      saving.value = false;
      saveFailed.value = false;
      saveComplete.value = false;

      const generatedQuote = await generateQuote();
      if (generatedQuote) {
        quote.value = generatedQuote;
      } else {
        generationFailed.value = true;
      }

      generating.value = false;
    };

    const save = async () => {
      saving.value = true;

      const quoteToSave = { ...quote.value, creationType: CreationType.SAVED };

      try {
        const savedQuote = await saveGeneratedQuote(quoteToSave);

        if (savedQuote === null || savedQuote == false) {
          saveFailed.value = true;
          notify({ text: "Saving failed.", type: "warn" });
        } else {
          saveFailed.value = false;
          saveComplete.value = true;
          if (typeof savedQuote === "object") {
            quote.value = savedQuote;
          }
          notify({ text: "Quote saved successfully.", type: "success" });
        }
      } catch (error) {
        saveFailed.value = true;
      } finally {
        saving.value = false;

        if (saveButton.value) {
          if (saveFailed.value) {
            saveButton.value.addEventListener("mouseover", handleMouseEnter);
            saveButton.value.addEventListener("mouseout", handleMouseLeave);
          }

          if (saveComplete.value) {
            saveButton.value.removeEventListener("mouseover", handleMouseEnter);
            saveButton.value.removeEventListener("mouseout", handleMouseLeave);
          }
        }
      }
    };

    const handleMouseEnter = () => {
      if (saveFailed.value && !saving.value) {
        saveFailedIcon.value = "rotate-left";
      }
    };

    const handleMouseLeave = () => {
      if (saveFailed.value && !saving.value) {
        saveFailedIcon.value = "triangle-exclamation";
      }
    };

    return {
      quote,
      generating,
      generationFailed,
      saveButton,
      saving,
      saveFailed,
      saveFailedIcon,
      saveComplete,
      showSaveIcon,
      showSaveFailedIcon,
      showSaveCompleteIcon,
      handleGenerate,
      save,
      handleMouseEnter,
      handleMouseLeave
    };
  }
});
</script>

<style scoped>
.generate-quote-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.generated-quote-info {
  display: flex;
  flex-direction: column;
  background-color: black;
  color: white;
  padding: 2em;
  width: 32rem;
  border-radius: 5px;
}

.italic {
  font-style: italic;
}

.generated-quote-text {
  font-weight: bold;
  font-size: 1.2rem;
}

.generated-quote-author {
  margin-top: 0.5rem;
  font-style: italic;
  font-size: 0.8rem;
}

.generated-quote-origin {
  font-style: italic;
  font-size: 0.8rem;
}

.generated-quote-actions {
  display: flex;
  align-self: center;
  column-gap: 0.5rem;
  margin-top: 1rem;
  max-height: fit-content;
}

button {
  cursor: pointer;
}

.btn-generate-quote {
  padding: 1rem;
  color: white;
  font-weight: 900;
  background-color: #106bc0;
}

.spinner-icon {
  margin-right: 0.25rem;
}

.btn-save-generated-quote {
  padding: 1rem;
  color: white;
  font-weight: 900;
  background-color: #2ca930;
}

.save-failed {
  background-color: #e6a610;
}

.save-complete {
  cursor: default;
}

.save-complete:hover {
  filter: none;
}
</style>
