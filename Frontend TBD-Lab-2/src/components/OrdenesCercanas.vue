<template>
    <div class="ordenes-cercanas">
        <h2>Órdenes en Radio de 10km</h2>
        <div class="form-field">
            <div class="label">Seleccionar Almacén</div>
            <select v-model="selectedAlmacen" class="select-field">
                <option value="">Seleccione un almacén</option>
                <option v-for="almacen in almacenes" 
                        :key="almacen.idAlmacen" 
                        :value="almacen.idAlmacen">
                    {{ almacen.nombre }}
                </option>
            </select>
            <button @click="buscarOrdenes" 
                    class="search-btn" 
                    :disabled="!selectedAlmacen">
                Buscar Órdenes
            </button>
        </div>

        <div v-if="ordenes.length > 0" class="orders-list">
            <div v-for="orden in ordenes" :key="orden.idorden" class="order-card">
                <h3>Orden #{{ orden.idorden }}</h3>
                <p>Total: ${{ Number(orden.total).toFixed(2) }}</p>
                <p>Distancia: {{ orden.distanciakm }} km</p>
                <p>Estado: {{ orden.estado }}</p>
                <p>Fecha: {{ formatearFecha(orden.fechaorden) }}</p>
            </div>
        </div>
        <p v-else-if="busquedaRealizada" class="no-results">
            No se encontraron órdenes en un radio de 10km
        </p>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import ordenService from '../services/ordenService';
import almacenService from '../services/almacenService';

const selectedAlmacen = ref('');
const ordenes = ref([]);
const almacenes = ref([]);
const busquedaRealizada = ref(false);

onMounted(async () => {
    try {
        const response = await almacenService.listAlmacenes();
        almacenes.value = response.data;
    } catch (error) {
        console.error('Error al cargar almacenes:', error);
    }
});

const buscarOrdenes = async () => {
    try {
        busquedaRealizada.value = true;
        const response = await ordenService.getOrdenesCercanas(selectedAlmacen.value);
        console.log('Datos recibidos:', response.data);
        ordenes.value = response.data;
    } catch (error) {
        console.error('Error al buscar órdenes:', error);
        ordenes.value = [];
    }
};

const formatearFecha = (fecha) => {
    if (!fecha) return 'Fecha no disponible';
    try {
        return new Date(fecha.replace(' ', 'T')).toLocaleDateString('es-ES', {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    } catch (e) {
        return 'Fecha inválida';
    }
};
</script>

<style scoped>
.ordenes-cercanas {
    padding: 2rem;
    background: #2c3e50;
    border-radius: 0.75rem;
    margin: 2rem;
    color: #fff;
}

.form-field {
    margin-bottom: 2rem;
}

.label {
    color: #42b983;
    margin-bottom: 0.5rem;
}

.select-field {
    width: 100%;
    padding: 0.75rem;
    border: 1px solid #42b983;
    border-radius: 0.5rem;
    background: #1a2634;
    color: #fff;
    margin-bottom: 1rem;
}

.search-btn {
    width: 100%;
    padding: 0.75rem;
    background: #42b983;
    color: black;
    border: none;
    border-radius: 0.5rem;
    cursor: pointer;
    font-weight: bold;
}

.search-btn:disabled {
    background: #767676;
    cursor: not-allowed;
}

.order-card {
    background: #1a2634;
    border: 1px solid #42b983;
    border-radius: 0.5rem;
    padding: 1rem;
    margin-bottom: 1rem;
}

.order-card h3 {
    color: #42b983;
    margin-bottom: 0.5rem;
}

.no-results {
    color: #e74c3c;
    text-align: center;
    font-weight: bold;
}
</style>