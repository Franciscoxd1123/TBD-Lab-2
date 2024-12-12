<template>
    <div class="login-page">
      <Login />
      <div class="links">
        <router-link to="/" class="link">Volver al inicio</router-link>
        <router-link to="/register" class="link">¿No tienes cuenta? Regístrate</router-link>
      </div>
    </div>
  </template>
  
  <script setup>
  import Login from '../components/Login.vue'  // Asegúrate de que la ruta sea correcta
  </script>
  
  <style scoped>
  .login-page {
    min-height: 100vh;
    padding: 2rem;
    background: #2d3748;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }
  
  .links {
    margin-top: 1rem;
  }
  
  .link {
    display: block;
    color: #48bb78;
    text-decoration: none;
    font-size: 0.9rem;
    text-align: center;
    margin-top: 0.5rem;
  }
  
  .link:hover {
    text-decoration: underline;
  }
  </style>