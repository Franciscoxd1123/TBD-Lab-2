<template>
  <div class="create-tarea">
    <h1>Historial de Compras</h1>

    <!-- Mensaje de error -->
    <div v-if="error" class="error-message">
      {{ error }}
    </div>

    <!-- Estado de carga -->
    <div v-if="loading" class="loading-message">
      Cargando historial...
    </div>

    <!-- Sin órdenes -->
    <div v-else-if="ordenes.length === 0" class="info-message">
      No tienes órdenes en tu historial
    </div>

    <!-- Lista de órdenes -->
    <div v-else class="ordenes-list">
      <div v-for="orden in ordenes" 
           :key="orden.idOrden" 
           class="orden-item"
           @click="toggleDetalles(orden.idOrden)">
        
        <!-- Cabecera de la orden -->
        <div class="orden-header">
          <div class="orden-title">Orden #{{ orden.idOrden }}</div>
          <div class="orden-date">{{ formatearFecha(orden.fechaOrden) }}</div>
        </div>

        <div class="orden-summary">
          <div :class="['orden-estado', getEstadoClase(orden.estado)]">
            {{ orden.estado }}
          </div>
          <div class="orden-total">${{ orden.total.toFixed(2) }}</div>
        </div>

        <!-- Detalles de la orden -->
        <div v-if="detallesExpandidos[orden.idOrden]" class="orden-detalles">
          <div class="detalles-header">
            <div class="detalle-col">Producto</div>
            <div class="detalle-col">Cantidad</div>
            <div class="detalle-col">Precio</div>
            <div class="detalle-col">Total</div>
          </div>
          <div v-for="detalle in detallesOrden[orden.idOrden]" 
               :key="detalle.idDetalle" 
               class="detalle-row">
               <div class="detalle-col">{{ productosCache[detalle.idProducto]?.nombre || 'Cargando...' }}</div>
              <div class="detalle-col">{{ detalle.cantidad }}</div>
              <div class="detalle-col">${{ detalle.precioUnitario.toFixed(2) }}</div>
              <div class="detalle-col">${{ (detalle.cantidad * detalle.precioUnitario).toFixed(2) }}</div>
            </div>
        </div>
      </div>
    </div>

    <button @click="goBack" class="go-back-btn">
      Volver
    </button>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import ordenService from '../services/ordenService';
import detalleOrdenService from '../services/detalleOrdenService';
import productoService from '../services/productoService';

const router = useRouter();
const ordenes = ref([]);
const detallesExpandidos = ref({});
const detallesOrden = ref({});
const loading = ref(true);
const error = ref(null);
const ESTADOS_VALIDOS = ['PAGADA', 'ENVIADA'];
const productosCache = ref({});

const obtenerClienteId = () => {
  const userData = JSON.parse(localStorage.getItem('userData'));
  return userData?.idCliente;
};

// Modificar la función cargarOrdenes
const cargarOrdenes = async () => {
  try {
    loading.value = true;
    error.value = null;
    const clienteId = obtenerClienteId();

    if (!clienteId) {
      throw new Error('No se encontró información del cliente');
    }

    const response = await ordenService.getOrdenesPorCliente(clienteId);
    
    // Filtrar solo las órdenes pagadas y enviadas
    ordenes.value = response.data.filter(orden => 
      ESTADOS_VALIDOS.includes(orden.estado.toUpperCase())
    );

    // Ordenar por fecha más reciente
    ordenes.value.sort((a, b) => 
      new Date(b.fechaOrden) - new Date(a.fechaOrden)
    );
  } catch (err) {
    console.error('Error:', err);
    error.value = 'Error al cargar las órdenes. Por favor, intente nuevamente.';
  } finally {
    loading.value = false;
  }
};

const cargarDetallesOrden = async (idOrden) => {
  try {
    error.value = null;
    const response = await detalleOrdenService.getDetallesByOrden(idOrden);
    detallesOrden.value[idOrden] = response.data;
    
    // Cargar información de productos para los detalles
    for (const detalle of response.data) {
      if (!productosCache.value[detalle.idProducto]) {
        try {
          const productoResponse = await productoService.getProducto(detalle.idProducto);
          productosCache.value[detalle.idProducto] = productoResponse.data;
        } catch (err) {
          console.error(`Error al cargar el producto ${detalle.idProducto}:`, err);
          productosCache.value[detalle.idProducto] = { nombre: 'Producto no encontrado' };
        }
      }
    }
  } catch (err) {
    console.error('Error:', err);
    error.value = 'Error al cargar los detalles de la orden';
  }
};

const toggleDetalles = async (idOrden) => {
  if (!detallesExpandidos.value[idOrden]) {
    detallesExpandidos.value[idOrden] = true;
    if (!detallesOrden.value[idOrden]) {
      await cargarDetallesOrden(idOrden);
    }
  } else {
    detallesExpandidos.value[idOrden] = false;
  }
};

const formatearFecha = (fecha) => {
  return new Date(fecha).toLocaleDateString('es-ES', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  });
};

// Modificar la función getEstadoClase para mejorar la visualización
const getEstadoClase = (estado) => {
  const estadoNormalizado = estado.toUpperCase();
  switch (estadoNormalizado) {
    case 'PAGADA':
      return 'pagada';
    case 'ENVIADA':
      return 'enviada';
    default:
      return 'otro';
  }
};

const goBack = () => {
  router.push({ name: 'UserMenu' });
};

onMounted(() => {
  cargarOrdenes();
});
</script>

<style scoped>
.create-tarea {
  background: #2c3e50;
  padding: 2rem;
  border-radius: 0.75rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 500px;
  margin: 0 auto;
}

h1 {
  color: orange;
  text-align: center;
  font-size: 1.8rem;
  margin-bottom: 2rem;
}

.loading-message {
  color: #42b983;
  text-align: center;
  margin: 1rem 0;
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

.info-message {
  color: #42b983;
  text-align: center;
  margin: 1rem 0;
}

.ordenes-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.orden-item {
  background: black;
  border: 1px solid orange;
  border-radius: 0.5rem;
  padding: 1rem;
  cursor: pointer;
  transition: all 0.2s;
}

.orden-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.orden-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.5rem;
}

.orden-title {
  color: #42b983;
  font-size: 1.1rem;
  font-weight: 500;
}

.orden-date {
  color: orange;
  font-size: 0.9rem;
}

.orden-summary {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #42b983;
}

.orden-estado {
  font-size: 0.9rem;
}

.orden-estado.pendiente {
  color: #fbbf24;
}

.orden-estado.completado {
  color: #34d399;
}

.orden-estado.cancelado {
  color: #ef4444;
}

.orden-total {
  font-weight: 500;
  font-size: 1.1rem;
}

.orden-detalles {
  margin-top: 1rem;
  padding-top: 1rem;
  border-top: 1px solid #42b983;
}

.detalles-header {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 1fr;
  gap: 1rem;
  padding: 0.5rem 0;
  color: orange;
  font-weight: 500;
}

.detalle-row {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 1fr;
  gap: 1rem;
  padding: 0.5rem 0;
  color: #42b983;
  border-bottom: 1px solid rgba(66, 185, 131, 0.2);
}

.go-back-btn {
  width: 100%;
  padding: 0.75rem;
  background: #e74c3c;
  color: black;
  border: none;
  border-radius: 0.5rem;
  font-size: 1rem;
  cursor: pointer;
  margin-top: 1rem;
  transition: background-color 0.2s;
}

.go-back-btn:hover {
  background: #c0392b;
}
</style>