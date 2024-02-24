<template>
  <div class="saved-quotes-list">
    <div class="saved-quote" v-for="quote in savedQuotes" :key="quote.id">
      {{ quote.quoteText }} - {{ quote.author }}
    </div>
    <!-- TODO save these -->
    <div class="saved-quote">
      "Nobody is trying to fix the root problems we have in this country. Everyone is trying to make enough money so
      that the problems don't apply to them anymore" - Jack London
    </div>
    <div class="saved-quote">"The secret to happiness is freedom, and the secret to freedom is courage" - Unknown</div>
    <div class="saved-quote">
      "It may seem difficult at first, but everything seems difficult first" - Miyamoto Musashi
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
</style>
