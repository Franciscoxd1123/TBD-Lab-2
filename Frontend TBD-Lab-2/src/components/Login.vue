<template>
  <div class="login">
    <h1>Iniciar Sesión</h1>

    <div v-if="error" class="error-message">
      {{ error }}
    </div>

    <div class="form-field">
      <div class="label">Email</div>
      <input 
        v-model="loginData.email"
        type="email" 
        required
        placeholder=" "
      >
    </div>

    <div class="form-field">
      <div class="label">Contraseña</div>
      <input 
        v-model="loginData.password"
        type="password" 
        required
        placeholder=" "
      >
    </div>

    <button 
      type="submit" 
      @click.prevent="handleSubmit"
      :disabled="loading"
    >
      {{ loading ? 'Iniciando sesión...' : 'Iniciar Sesión' }}
    </button>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import clienteService from '../services/clienteService'

const router = useRouter()
const error = ref('')
const loading = ref(false)

const loginData = reactive({
  email: '',
  password: ''
})

const handleSubmit = async () => {
  try {
    if (!loginData.email || !loginData.password) {
      error.value = 'Por favor, complete todos los campos'
      return
    }

    loading.value = true
    error.value = ''

    const response = await clienteService.loginCliente(
      loginData.email,
      loginData.password
    )
    
    if (response.data) {
      const userData = {
        idCliente: response.data.idCliente,
        email: response.data.email,
        nombre: response.data.nombre,
        direccion: response.data.direccion,
        telefono: response.data.telefono,
        password: response.data.password
      }
      
      localStorage.setItem('userData', JSON.stringify(userData))
      
      localStorage.setItem('email', response.data.email)
      
      console.log('Usuario logueado:', userData) 
      
      router.push('/user-menu')
    }
  } catch (err) {
    console.error('Error de login:', err)
    if (err.response?.status === 401) {
      error.value = 'Email o contraseña incorrectos'
    } else if (err.response?.data) {
      error.value = err.response.data
    } else {
      error.value = 'Error al iniciar sesión. Por favor, intente nuevamente.'
    }
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login {
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
  transition: background-color 0.2s;
}

button:hover:not(:disabled) {
  background: #38a169;
}

button:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.error-message {
  background: #fee2e2;
  color: #dc2626;
  padding: 0.75rem;
  border-radius: 0.5rem;
  margin-bottom: 1rem;
  font-size: 0.875rem;
  text-align: center;
  border: 1px solid #fca5a5;
}
</style>