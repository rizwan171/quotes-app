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
      <button class="btn-save-generated-quote" v-if="quote.quoteText">
        <font-awesome-icon icon="floppy-disk" size="lg" />
      </button>
    </div>
  </div>
</template>

<script lang="ts">
import { generateQuote } from "@/services";
import type { GeneratedQuote } from "@/types/GeneratedQuote";
import { defineComponent, ref } from "vue";

export default defineComponent({
  setup() {
    let quote = ref<GeneratedQuote>({
      quoteText: "Kinder beeno and flelo losh.",
      author: "Qas Khan",
      origin: "Chaiwali in Bladford"
    });

    let loading = ref(false);

    const handleGenerate = async () => {
      loading.value = true;

      const generatedQuote = await generateQuote();
      if (generatedQuote) {
        quote.value = generatedQuote;
      }

      loading.value = false;
    };

    return { quote, loading, handleGenerate };
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
</style>
