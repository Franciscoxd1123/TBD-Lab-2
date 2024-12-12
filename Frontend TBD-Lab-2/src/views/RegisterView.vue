<template>
  <div class="register-page">
    <Register />
    <router-link to="/" class="link">Volver al inicio</router-link>
  </div>
</template>

<script setup>
import Register from '../components/Register.vue'
</script>

<style scoped>
.register-page {
  min-height: 100vh;
  padding: 1rem;
  background: #2d3748;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.link {
  color: #48bb78;
  text-decoration: none;
  font-size: 0.9rem;
  margin-top: 1rem;
}

.link:hover {
  text-decoration: underline;
}
</style>