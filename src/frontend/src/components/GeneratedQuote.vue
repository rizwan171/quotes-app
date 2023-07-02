<template>
  <div class="quote-container">
    <div class="quote-info">
      <span class="quote-text"
        >"I would rather be ashes than dust. I would rather that my spark should burn out in a
        brilliant blaze than it should be stifled by dry-rot. I would rather be a superb meteor,
        every atom of me in magnificent glow, than a sleepy and permanent planet. The function of
        man is to live, not to exist. I shall not waste my days trying to prolong them. I shall use
        my time"</span
      >
      <span class="quote-author">- By</span>
      <span class="quote-origin">- From</span>
    </div>
    <button class="btn-generate-quote">Generate Quote</button>
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
