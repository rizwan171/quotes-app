<template>
  <div class="generate-quote-container">
    <div class="generated-quote-info">
      <span class="no-generated-quote" v-if="!quote.quoteText">Click the button below to get a quote...</span>
      <span class="generated-quote-text">{{ quote.quoteText }}</span>
      <span class="generated-quote-author" v-if="quote.author">- By {{ quote.author }}</span>
      <span class="generated-quote-origin" v-if="quote.origin">- From {{ quote.origin }}</span>
    </div>
    <div class="generated-quote-actions">
      <button class="btn-generate-quote" @click="handleGenerate">
        <font-awesome-icon class="spinner-icon fa-spin" icon="circle-notch" size="lg" v-if="loading" />
        <span>Generate Quote</span>
      </button>
      <!-- TODO instead of @, register listener when save clicked and deregister when save completed -->
      <button
        class="btn-save-generated-quote"
        :class="{ 'saving-failed': failedSave, 'saving-complete': savingComplete }"
        :disabled="savingComplete"
        v-if="quote.quoteText"
        @click="saveGeneratedQuote"
        @mouseover="handleMouseEnter"
        @mouseout="handleMouseLeave"
      >
        <!-- TODO tidy up the v-if logic here by encappsulating into one var -->
        <font-awesome-icon icon="floppy-disk" size="lg" v-if="!failedSave && !saving && !savingComplete" />
        <font-awesome-icon class="fa-spin" icon="circle-notch" size="lg" v-if="saving" />
        <font-awesome-icon :icon="failedSaveIcon" size="lg" v-if="failedSave && !saving" />
        <font-awesome-icon icon="check" size="lg" v-if="!failedSave && savingComplete" />
      </button>
    </div>
  </div>
</template>

<script lang="ts">
import { CreationType } from "@/enums/CreationType";
import { generateQuote } from "@/services";
import { saveQuote } from "@/services/modules/QuoteService";
import type { Quote } from "@/types/Quote";
import { notify } from "@kyvg/vue3-notification";
import { defineComponent, ref } from "vue";

export default defineComponent({
  setup() {
    let quote = ref<Quote>({
      quoteText: "",
      author: "",
      origin: "",
      creationType: CreationType.GENERATED
    });

    let loading = ref(false);
    let saving = ref(false);
    let failedSave = ref(false);
    let savingComplete = ref(false);
    let failedSaveIcon = ref("triangle-exclamation");

    const handleGenerate = async () => {
      loading.value = true;
      failedSave.value = false;

      const generatedQuote = await generateQuote();
      if (generatedQuote) {
        quote.value = generatedQuote;
      }

      loading.value = false;
    };

    const saveGeneratedQuote = async () => {
      saving.value = true;

      const quoteToSave = { ...quote.value, creationType: CreationType.SAVED };
      try {
        const savedQuote = await saveQuote(quoteToSave);

        if (savedQuote === null) {
          failedSave.value = true;
          notify({ text: "Saving failed.", type: "warn" });
        } else {
          failedSave.value = false;
          savingComplete.value = true;
          quote.value = savedQuote;
          notify({ text: "Quote saved successfully.", type: "success" });
        }
      } catch (error) {
        failedSave.value = true;
      } finally {
        saving.value = false;
      }
    };

    const handleMouseEnter = () => {
      console.log("hit1");

      if (failedSave.value && !saving.value) {
        failedSaveIcon.value = "rotate-left";
      }
    };

    const handleMouseLeave = () => {
      console.log("hit2");
      if (failedSave.value && !saving.value) {
        failedSaveIcon.value = "triangle-exclamation";
      }
    };

    return {
      quote,
      loading,
      saving,
      failedSave,
      failedSaveIcon,
      savingComplete,
      handleGenerate,
      saveGeneratedQuote,
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

.no-generated-quote {
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

.saving-failed {
  background-color: #e6a610;
}

.saving-complete {
  cursor: default;
}

.saving-complete:hover {
  filter: none;
}
</style>
