<template>
  <div class="home">
    <HelloWorld msg="Vite + Vue" />
    <div class="nav">
      <router-link to="/register" class="nav-link">Registrarse</router-link>
      <router-link to="/login" class="nav-link">Iniciar Sesión</router-link>
    </div>
  </div>
</template>

<script setup>
import HelloWorld from '../components/HelloWorld.vue'
</script>

<style scoped>
.home {
  min-height: 100vh;
  padding: 2rem;
}

.nav {
  display: flex;
  gap: 1rem;
  justify-content: center;
  margin-top: 2em;
}

.nav-link {
  color: #42b983;
  text-decoration: none;
  padding: 0.5em 1em;
  border: 1px solid #42b983;
  border-radius: 4px;
}

.nav-link:hover {
  background: #42b983;
  color: white;
}
</style>