<template>
  <div class="app-container">
    <header>
      <h1>Quotes App</h1>
    </header>

    <main>
      <generated-quote />
    </main>

    <section class="saved-quotes">
      <h2>Saved Quotes</h2>
      <saved-quotes />
    </section>
  </div>
</template>

<script lang="ts" setup>
import GeneratedQuote from "./components/GeneratedQuote.vue";
import SavedQuotes from "./components/SavedQuotes.vue";
</script>

<style scoped>
.app-container {
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100%;
  min-height: 100%;
}

.saved-quotes {
  margin-top: 8rem;
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
