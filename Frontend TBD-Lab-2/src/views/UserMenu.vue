<template>
  <div class="user-view">
    <UserMenu :msg="welcomeMessage" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import UserMenu from '../components/UserMenu.vue';

const router = useRouter();
const welcomeMessage = ref('Bienvenido al Gestor de Tareas');

onMounted(() => {
  const usuario = JSON.parse(localStorage.getItem('usuario'));
  if (!usuario) {
    router.push('/user-menu');
    return;
  }
  welcomeMessage.value = `Bienvenido ${usuario.nombre} al Gestor de Tareas`;
});
</script>