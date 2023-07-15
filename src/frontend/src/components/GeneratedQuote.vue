<template>
  <div class="generate-quote-container">
    <div class="generated-quote-info">
      <span class="no-generated-quote" v-if="!quote.quoteText">Click the button below to get a quote...</span>
      <span class="generated-quote-text">{{ quote.quoteText }}</span>
      <span class="generated-quote-author" v-if="quote.author">- By {{ quote.author }}</span>
      <span class="generated-quote-origin" v-if="quote.origin">- From {{ quote.origin }}</span>
    </div>
    <div class="generated-quote-actions">
      <button class="btn-generate-quote" @click="handleGenerate">Generate Quote</button>
      <button class="btn-save-generated-quote" v-if="quote.quoteText">
        <font-awesome-icon icon="floppy-disk" size="lg" />
      </button>
    </div>
  </div>
</template>

<script lang="ts">
import { generateQuote } from "@/services";
import type { GeneratedQuote } from "@/types/GeneratedQuote";
import { ref } from "vue";

export default {
  setup() {
    let quote = ref<GeneratedQuote>({
      quoteText: "Sanity is a madness put to good use.",
      author: "George Santayana",
      origin: "QuotationsPage.com"
    });

    const handleGenerate = async () => {
      const generatedQuote = await generateQuote();
      if (generatedQuote) {
        quote.value = generatedQuote;
      }
    };

    return { quote, handleGenerate };
  }
};
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

.btn-save-generated-quote {
  padding: 1rem;
  color: white;
  font-weight: 900;
  background-color: #2ca930;
}
</style>
