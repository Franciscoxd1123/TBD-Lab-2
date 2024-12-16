<template>
    <div class="almacen-cercano">
        <h2>Almacén más Cercano</h2>
        
        <!-- Información del Cliente -->
        <div v-if="userData" class="client-info">
            <h3>Cliente</h3>
            <p>ID: {{ userData.idCliente }}</p>
            <p>Nombre: {{ userData.nombre }}</p>
            <p>Dirección: {{ userData.direccion }}</p>
        </div>

        <div v-for="almacen in almacenInfo" class="almacen-info">
            <h3>{{ almacen.nombre }}</h3>
            <p>Dirección: {{ almacen.direccion }}</p>
            <p>Ruta mas corta: {{ almacen.distancia }} km</p>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import almacenService from '../services/almacenService';
import clienteService from '../services/clienteService';

const almacenInfo = ref([]);
const busquedaRealizada = ref(false);
const userData = ref(null);

onMounted(async () => {
    try {
        const userDataFromStorage = JSON.parse(localStorage.getItem('userData'));
        if (userDataFromStorage?.idCliente) {
            userData.value = userDataFromStorage;
            const almacenes = await almacenService.listAlmacenes();
            for (let i = 0; i < almacenes.length; i++) {
                const distancia = await clienteService.shortestRoute({ idAlmacen: almacenes[i].idAlmacen, idCliente: userDataFromStorage.idCliente})
                almacenInfo.value.append({ 
                    distancia,
                    nombre: almacenes[i].nombre,
                    direccion: almacenes[i].direccion,
                })
                console.log(almacenInfo.value)
            }
        }
        busquedaRealizada.value = true;
    } catch (error) {
        console.error('Error al obtener almacén cercano:', error);
        busquedaRealizada.value = true;
    }
});
</script>

<style scoped>
.almacen-cercano {
    padding: 2rem;
    background: #2c3e50;
    border-radius: 0.75rem;
    margin: 2rem;
    color: #fff;
}

.client-info {
    background: #1a2634;
    border: 1px solid #42b983;
    border-radius: 0.5rem;
    padding: 1rem;
    margin-bottom: 1rem;
}

.client-info h3 {
    color: #42b983;
    margin-bottom: 0.5rem;
}

.client-info p {
    margin: 0.5rem 0;
}

.almacen-info {
    background: #1a2634;
    border: 1px solid orange;
    border-radius: 0.5rem;
    padding: 1rem;
    margin-top: 1rem;
}

.almacen-info h3 {
    color: #42b983;
    margin-bottom: 1rem;
}

.almacen-info p {
    margin: 0.5rem 0;
}

.no-results {
    color: #e74c3c;
    text-align: center;
    font-weight: bold;
}
</style>