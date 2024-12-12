<template>
  <div class="register">
    <h1>Registro</h1>

    <div v-if="error" class="error-message">
      {{ error }}
    </div>

    <div class="form-field">
      <div class="label">Nombre</div>
      <input 
        v-model="clientData.nombre" 
        type="text" 
        required
        placeholder=" "
      >
    </div>

    <div class="form-field">
      <div class="label">Dirección</div>
      <input 
        v-model="clientData.direccion"
        type="text" 
        required
        placeholder=" "
      >
    </div>

    <div class="form-field">
      <div class="label">Email</div>
      <input 
        v-model="clientData.email"
        type="email" 
        required
        placeholder=" "
      >
    </div>

    <div class="form-field">
      <div class="label">Teléfono</div>
      <input 
        v-model="clientData.telefono"
        type="tel" 
        required
        placeholder=" "
      >
    </div>

    <div class="form-field">
      <div class="label">Contraseña</div>
      <input 
        v-model="clientData.password"
        type="password" 
        required
        placeholder=" "
      >
    </div>

    <button type="submit" @click.prevent="handleSubmit">
      Registrarse
    </button>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import clienteService from '../services/clienteService'

const router = useRouter()
const error = ref('')

const clientData = reactive({
  nombre: '',
  direccion: '',
  email: '',
  telefono: '',
  password: ''
})

const handleSubmit = async () => {
  try {
    error.value = ''

    if (!clientData.nombre || clientData.nombre.trim() === '') {
      error.value = 'El nombre es obligatorio'
      return
    }

    if (!clientData.direccion || clientData.direccion.trim() === '') {
      error.value = 'La dirección es obligatoria'
      return
    }

    if (!clientData.email || clientData.email.trim() === '') {
      error.value = 'El email es obligatorio'
      return
    }

    if (!clientData.telefono || clientData.telefono.trim() === '') {
      error.value = 'El teléfono es obligatorio'
      return
    }

    if (!clientData.password || clientData.password.trim() === '') {
      error.value = 'La contraseña es obligatoria'
      return
    }

    const response = await clienteService.createCliente(clientData)
    if (response.data) {
      console.log('Cliente registrado:', response.data)
      router.push('/login')
    }
  } catch (err) {
    console.error('Error de registro:', err)
    error.value = err.response?.data || 'Error al registrar cliente'
  }
}
</script>

<style scoped>
.register {
  background: #2c3e50;
  padding: 2rem;
  border-radius: 0.75rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 280px;
}

h1 {
  color: orange;
  text-align: center;
  font-size: 1.8rem;
  margin-bottom: 2rem;
}

.form-field {
  margin-bottom: 1.5rem;
}

.label {
  color: #42b983;
  text-align: center;
  margin-bottom: 0.5rem;
  font-size: 1rem;
}

input {
  width: 90%;
  padding: 0.75rem;
  border: 1px solid orange;
  border-radius: 0.5rem;
  font-size: 0.9rem;
  background: black;
  outline: none;
  color: #42b983;
}

button {
  width: 100%;
  padding: 0.75rem;
  background: #48bb78;
  color: black;
  border: none;
  border-radius: 0.5rem;
  font-size: 1rem;
  cursor: pointer;
  margin-top: 0.5rem;
}

button:hover {
  background: #38a169;
}

.error-message {
  color: #dc2626;
  background: #fee2e2;
  padding: 0.75rem;
  border-radius: 0.5rem;
  margin-bottom: 1rem;
  font-size: 0.875rem;
  text-align: center;
}
</style>
