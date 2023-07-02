<template>
  <div class="saved-quotes-list">
    <div class="saved-quote">
      "Nobody is trying to fix the root problems we have in this country. Everyone is trying to make
      enough money so that the problems don't apply to them anymore" - Jack London
    </div>
    <div class="saved-quote">
      "The secret to happiness is freedom, and the secret to freedom is courage" - Unknown
    </div>
    <div class="saved-quote">
      "It may seem difficult at first, but everything seems difficult first" - Miyamoto Musashi
    </div>
  </div>
</template>

<script lang="ts">
export default {
  setup() {
    return {};
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
  background-color: #eee;
  padding: 0.5rem;
  margin-bottom: 0.5rem;
  border-radius: 5px;
  width: 42rem;
  max-width: 42rem;
  font-weight: 600;
}
</style>
