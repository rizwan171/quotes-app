<template>
  <div class="saved-quotes-list">
    <div class="saved-quote" v-for="quote in savedQuotes" :key="quote.id">
      <span>{{ quote.quoteText }}</span>
      <br />
      <span class="saved-quote-author">- {{ quote.author }}</span>
    </div>
  </div>
</template>

<script lang="ts">
import { getAllUserSavedQuotes } from "@/services/modules/QuoteService";
import type { Quote } from "@/types/Quote";
import { notify } from "@kyvg/vue3-notification";
import { onMounted, ref } from "vue";

export default {
  setup() {
    const savedQuotes = ref<Quote[]>([]);

    onMounted(async () => {
      try {
        const userSavedQuotes = await getAllUserSavedQuotes();
        if (userSavedQuotes == null) {
          notify({ text: "There was a problem loading saved quotes.", type: "warn" });
        } else {
          savedQuotes.value = userSavedQuotes.map((quote) => {
            return {
              ...quote,
              author: quote.author ? quote.author : "Unknown",
              origin: quote.origin ? quote.origin : "Unknown"
            };
          });
        }
      } catch (error) {
        notify({ text: "There was a problem loading saved quotes.", type: "warn" });
      }
    });

    return {
      savedQuotes
    };
  }
};
</script>

<style scoped>
.saved-quotes-list {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 32rem;
  overflow-x: hidden;
  overflow-y: auto;
  scrollbar-width: thin;
}

.saved-quote {
  background-color: #bbbbbb;
  opacity: 80%;
  padding: 0.5rem;
  margin-bottom: 0.5rem;
  border-radius: 5px;
  width: 42rem;
  max-width: 42rem;
  font-weight: 600;
}

.saved-quote-author {
  margin-left: 0.5rem;
}
</style>
