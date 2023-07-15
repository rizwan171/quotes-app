<template>
  <div class="quote-container">
    <div class="quote-info">
      <span class="quote-text">{{ quote.quoteText }}</span>
      <span class="quote-author" v-if="quote.author">- By {{ quote.author }}</span>
      <span class="quote-origin" v-if="quote.origin">- From {{ quote.origin }}</span>
    </div>
    <button class="btn-generate-quote" @click="handleGenerate">Generate Quote</button>
  </div>
</template>

<script lang="ts">
import { generateQuote } from "@/services";
import type { GeneratedQuote } from "@/types/GeneratedQuote";
import { ref } from "vue";

export default {
  setup() {
    let quote = ref<GeneratedQuote>({
      quoteText: "",
      author: "",
      origin: ""
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
.quote-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.quote-info {
  display: flex;
  flex-direction: column;
  background-color: black;
  color: white;
  padding: 2em;
  width: 32rem;
  border-radius: 5px;
}

.quote-text {
  font-weight: bold;
}

.quote-author {
  margin-top: 0.5rem;
  margin-left: 1rem;
  font-style: italic;
}

.quote-origin {
  margin-left: 1rem;
  font-style: italic;
}

.btn-generate-quote {
  padding: 0.5rem;
  padding-right: 2rem;
  padding-left: 2rem;
  margin-top: 1rem;
}
</style>
