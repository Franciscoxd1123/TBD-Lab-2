<script setup>
defineProps({
  msg: String,
});
</script>

<template>
  <div class="hello">
    <h1>Ecommerce</h1>
    <div class="content">
      <p>Bienvenido a nuestra aplicación</p>
      <p>Por favor, selecciona una opción:</p>
    </div>
  </div>
</template>

<style scoped>
.hello {
  text-align: center;
  padding: 2rem;
}

h1 {
  font-size: 2.5rem;
  margin-bottom: 2rem;
  color: orange;
}

.content {
  font-size: 1.2rem;
  color: #dddddd;
  line-height: 1.6;
}

.buttons {
  margin-top: 2rem;
}

button {
  font-size: 1rem;
  padding: 0.8rem 1.5rem;
  margin: 0 0.5rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  background-color: #3498db;
  color: white;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #2980b9;
}
</style>
