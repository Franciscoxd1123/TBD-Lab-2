<template>
  <div class="user-menu">
    <!-- Sidebar -->
    <aside class="sidebar">
      <h2>Opciones</h2>
      <ul>
        <li><router-link to="/lista-ordenes">Lista de órdenes</router-link></li>
        <li><router-link to="/devolucion-productos">Devolución de productos</router-link></li>
        <li><router-link to="/historial-compras">Historial de compras</router-link></li>
        <li class="logout-item">
          <button @click="handleLogout" class="logout-button">
            Cerrar sesión
          </button>
        </li>
      </ul>
    </aside>

    <!-- Contenido Principal -->
    <main class="content">
      <div class="create-tarea">
        <h1>Bienvenido a E-commerce</h1>

        <!-- Mensaje del carrito -->
        <div v-if="cartMessage" class="cart-message">
          {{ cartMessage }}
        </div>

        <!-- Buscador y Filtros -->
        <div class="search-filters">
          <div class="form-field">
            <div class="label">Buscar Producto</div>
            <input 
              v-model="searchTerm"
              type="text"
              placeholder="Buscar por nombre..."
              @input="filterProducts"
            >
          </div>

          <div class="form-field">
            <div class="label">Categoría</div>
            <select 
              v-model="selectedCategory"
              @change="filterProducts"
              class="select-field"
            >
              <option value="">Todas las categorías</option>
              <option 
                v-for="categoria in categorias" 
                :key="categoria.idCategoria"
                :value="categoria.idCategoria"
              >
                {{ categoria.nombre }}
              </option>
            </select>
          </div>
        </div>

        <!-- Lista de Productos -->
        <div class="products-grid">
          <div 
            v-for="producto in filteredProducts" 
            :key="producto.idProducto"
            class="product-card"
          >
            <!-- Modificar la sección de producto-content en el template -->
      <div class="product-content">
        <h3 class="product-title">{{ producto.nombre }}</h3>
        <p class="product-description">{{ producto.descripcion }}</p>
        <div class="product-details">
          <span class="product-price">${{ producto.precio.toFixed(2) }}</span>
          <span :class="['product-stock', { 'low-stock': producto.stock < 10 }]">
            Stock: {{ producto.stock }}
          </span>
        </div>

        <!-- Nuevo control de cantidad -->
        <div class="quantity-control">
          <button 
            class="quantity-btn"
            @click="decrementQuantity(producto)"
            :disabled="getProductQuantity(producto.idProducto) <= 1"
          >
            -
          </button>
          <span class="quantity-display">{{ getProductQuantity(producto.idProducto) }}</span>
          <button 
            class="quantity-btn"
            @click="incrementQuantity(producto)"
            :disabled="getProductQuantity(producto.idProducto) >= producto.stock"
          >
            +
          </button>
        </div>

        <button 
          class="add-to-cart-btn"
          @click="addToCart(producto)"
          :disabled="producto.stock === 0"
        >
          {{ producto.stock === 0 ? 'Sin Stock' : 'Agregar a la Orden' }}
        </button>
      </div>
      </div>
      </div>

        
      </div>

      <!-- Modal de selección de orden -->
      <div v-if="showOrderModal" class="modal-overlay" @click="closeModal">
        <div class="modal-content" @click.stop>
          <h2 class="modal-title">Seleccionar Orden</h2>
          
          <div class="modal-options">
            <div class="orden-option new-order" @click="crearNuevaOrden(selectedProduct)">
              <div class="option-text">
                <h3>Crear Nueva Orden</h3>
                <p>Iniciar una nueva orden con este producto</p>
              </div>
            </div>

            <div v-if="ordenesPendientes.length > 0">
              <h3 class="existing-orders-title">Órdenes Pendientes</h3>
              <div v-for="orden in ordenesPendientes" 
                   :key="orden.idOrden" 
                   class="orden-option existing-order"
                   @click="agregarAOrdenExistente(orden, selectedProduct)">
                <div class="option-content">
                  <h3>Orden #{{ orden.idOrden }}</h3>
                  <p>Fecha: {{ formatearFecha(orden.fechaOrden) }}</p>
                  <p>Total actual: ${{ orden.total.toFixed(2) }}</p>
                </div>
              </div>
            </div>
          </div>

          <button class="modal-close" @click="closeModal">Cancelar</button>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import productoService from '../services/productoService';
import categoriaService from '../services/categoriaService';
import clienteService from '../services/clienteService';
import ordenService from '../services/ordenService';
import detalleOrdenService from '../services/detalleOrdenService';

const router = useRouter();
const productos = ref([]);
const categorias = ref([]);
const searchTerm = ref('');
const selectedCategory = ref('');
const filteredProducts = ref([]);
const cartItemCount = ref(0);
const cartMessage = ref('');
const showOrderModal = ref(false);
const selectedProduct = ref(null);
const ordenesPendientes = ref([]);
const productQuantities = ref({});

// Cargar productos y categorías
const loadData = async () => {
  try {
    const [productosResponse, categoriasResponse] = await Promise.all([
      productoService.listProductos(),
      categoriaService.listCategorias()
    ]);
    
    productos.value = productosResponse.data;
    categorias.value = categoriasResponse.data;
    filterProducts();
  } catch (error) {
    console.error('Error al cargar datos:', error);
  }
};

// Filtrar productos
const filterProducts = () => {
  filteredProducts.value = productos.value.filter(producto => {
    const matchesSearch = producto.nombre.toLowerCase().includes(searchTerm.value.toLowerCase()) ||
                         producto.descripcion.toLowerCase().includes(searchTerm.value.toLowerCase());
    const matchesCategory = !selectedCategory.value || producto.idCategoria === selectedCategory.value;
    
    return matchesSearch && matchesCategory;
  });
};

// Función para cerrar sesión
const handleLogout = async () => {
  try {
    const userData = JSON.parse(localStorage.getItem('userData'));
    if (userData?.idCliente) {
      await clienteService.logoutCliente(userData.idCliente);
    }
  } catch (error) {
    console.error('Error al cerrar sesión:', error);
  } finally {
    localStorage.clear();
    router.push('/');
  }
};

// Función para agregar al carrito modificada
const addToCart = async (producto) => {
  const userData = JSON.parse(localStorage.getItem('userData'));
  if (!userData?.idCliente) {
    cartMessage.value = 'Debe iniciar sesión para realizar una orden';
    setTimeout(() => cartMessage.value = '', 3000);
    return;
  }

  try {
    const response = await ordenService.getOrdenesPorCliente(userData.idCliente);
    ordenesPendientes.value = response.data.filter(orden => orden.estado === 'Pendiente');
    selectedProduct.value = producto;
    showOrderModal.value = true;
  } catch (error) {
    console.error('Error al cargar órdenes:', error);
    cartMessage.value = 'Error al cargar órdenes pendientes';
    setTimeout(() => cartMessage.value = '', 3000);
  }
};

const crearNuevaOrden = async (producto) => {
  try {
    const userData = JSON.parse(localStorage.getItem('userData'));
    const cantidad = getProductQuantity(producto.idProducto);
    
    const nuevaOrden = {
      idCliente: userData.idCliente,
      fechaOrden: new Date().toISOString(),
      estado: 'Pendiente',
      total: producto.precio * cantidad
    };

    const ordenResponse = await ordenService.createOrden(nuevaOrden);
    
    const nuevoDetalle = {
      idOrden: ordenResponse.data.idOrden,
      idProducto: producto.idProducto,
      cantidad: cantidad,
      precioUnitario: producto.precio
    };

    await detalleOrdenService.createDetalleOrden(nuevoDetalle);
    cartItemCount.value += cantidad;
    
    // Resetear la cantidad después de agregar
    productQuantities.value[producto.idProducto] = 1;
    
    cartMessage.value = 'Nueva orden creada con éxito';
    setTimeout(() => cartMessage.value = '', 3000);
    
    closeModal();
    router.push('/lista-ordenes');
  } catch (error) {
    console.error('Error al crear orden:', error);
    cartMessage.value = 'Error al crear la orden';
    setTimeout(() => cartMessage.value = '', 3000);
  }
};

const agregarAOrdenExistente = async (orden, producto) => {
  try {
    const cantidad = getProductQuantity(producto.idProducto);
    const nuevoDetalle = {
      idOrden: orden.idOrden,
      idProducto: producto.idProducto,
      cantidad: cantidad,
      precioUnitario: producto.precio
    };

    await detalleOrdenService.createDetalleOrden(nuevoDetalle);
    orden.total += producto.precio * cantidad;
    await ordenService.updateOrden(orden.idOrden, orden);
    
    cartItemCount.value += cantidad;
    // Resetear la cantidad después de agregar
    productQuantities.value[producto.idProducto] = 1;
    
    cartMessage.value = 'Producto agregado a la orden existente';
    setTimeout(() => cartMessage.value = '', 3000);
    
    closeModal();
    router.push('/lista-ordenes');
  } catch (error) {
    console.error('Error al agregar a orden:', error);
    cartMessage.value = 'Error al agregar el producto';
    setTimeout(() => cartMessage.value = '', 3000);
  }
};

const formatearFecha = (fecha) => {
  return new Date(fecha).toLocaleDateString('es-ES', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  });
};

const closeModal = () => {
  showOrderModal.value = false;
  selectedProduct.value = null;
};

// Funciones para manejar la cantidad
const getProductQuantity = (productId) => {
  return productQuantities.value[productId] || 1;
};

const incrementQuantity = (producto) => {
  const currentQty = getProductQuantity(producto.idProducto);
  if (currentQty < producto.stock) {
    productQuantities.value[producto.idProducto] = currentQty + 1;
  }
};

const decrementQuantity = (producto) => {
  const currentQty = getProductQuantity(producto.idProducto);
  if (currentQty > 1) {
    productQuantities.value[producto.idProducto] = currentQty - 1;
  }
};

onMounted(() => {
  loadData();
});
</script>


<style scoped>
.user-menu {
  display: flex;
  min-height: 100vh;
  background: #1a2634;
}

.sidebar {
  width: 250px;
  background-color: #2c3e50;
  color: orange;
  padding: 1rem;
  position: fixed;
  height: 100vh;
  overflow-y: auto;
}

.sidebar h2 {
  margin: 0 0 1rem;
  font-size: 1.5rem;
}

.sidebar ul {
  list-style: none;
  padding: 0;
}

.sidebar li {
  margin: 1rem 0;
}

.sidebar a {
  color: #42b983;
  text-decoration: none;
  transition: color 0.3s;
}

.sidebar a:hover {
  color: #1abc9c;
}

.logout-button {
  background-color: #e74c3c;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
  width: 100%;
  transition: background-color 0.3s;
}

.logout-button:hover {
  background-color: #c0392b;
}

.logout-item {
  margin-top: 2rem;
}

.content {
  flex: 1;
  margin-left: 250px;
  padding: 2rem;
}

.create-tarea {
  background: #2c3e50;
  padding: 2rem;
  border-radius: 0.75rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.search-filters {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
  margin-bottom: 2rem;
}

h1 {
  color: orange;
  text-align: center;
  font-size: 1.8rem;
  margin-bottom: 2rem;
}

.search-filters {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
  margin-bottom: 2rem;
}

.form-field {
  margin-bottom: 1rem;
  padding-right: 1rem;
}

.label {
  color: #42b983;
  text-align: center;
  margin-bottom: 0.5rem;
  font-size: 1rem;
}

input,
.select-field {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid orange;
  border-radius: 0.5rem;
  font-size: 0.9rem;
  background: black;
  color: #42b983;
}

.products-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.product-card {
  background: black;
  border: 1px solid orange;
  border-radius: 0.5rem;
  overflow: hidden;
  transition: transform 0.2s;
}

.product-card:hover {
  transform: translateY(-4px);
}

.product-content {
  padding: 1rem;
}

.product-title {
  color: #42b983;
  font-size: 1.1rem;
  margin-bottom: 0.5rem;
}

.product-description {
  color: #8f9ca8;
  font-size: 0.9rem;
  margin-bottom: 1rem;
  min-height: 2.7rem;
}

.product-details {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.product-price {
  color: orange;
  font-size: 1.2rem;
  font-weight: bold;
}

.product-stock {
  color: #42b983;
  font-size: 0.9rem;
}

.low-stock {
  color: #e74c3c;
}

.add-to-cart-btn {
  width: 100%;
  padding: 0.75rem;
  background: #42b983;
  color: black;
  border: none;
  border-radius: 0.5rem;
  font-size: 0.9rem;
  cursor: pointer;
  transition: background-color 0.2s;
}

.add-to-cart-btn:hover:not(:disabled) {
  background: #3aa876;
}

.add-to-cart-btn:disabled {
  background: #767676;
  cursor: not-allowed;
}

.menu-buttons {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 1rem;
  margin-top: 2rem;
}

.menu-btn {
  padding: 0.75rem;
  border: none;
  border-radius: 0.5rem;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.2s;
  position: relative;
}

.cart-btn {
  background: #42b983;
  color: black;
}

.cart-btn:hover {
  background: #3aa876;
}

.cart-count {
  position: absolute;
  top: -8px;
  right: -8px;
  background: orange;
  color: black;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.8rem;
  font-weight: bold;
}

.logout-btn {
  background: #e74c3c;
  color: black;
}

.logout-btn:hover {
  background: #c0392b;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.8);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999; 
  padding: 1rem;
}

.modal-content {
  background: #2c3e50;
  padding: 2rem;
  border-radius: 0.75rem;
  width: 90%;
  max-width: 500px;
  border: 1px solid orange;
  max-height: 90vh; 
  overflow-y: auto; 
  position: relative; 
  animation: modalAppear 0.3s ease;
}

.modal-title {
  color: orange;
  text-align: center;
  font-size: 1.5rem;
  margin-bottom: 1.5rem;
  padding: 0 1rem;
}

.modal-options {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-bottom: 1rem;
}

.orden-option {
  background: #1a2634;
  border: 1px solid #42b983;
  border-radius: 0.5rem;
  padding: 1rem;
  cursor: pointer;
  transition: all 0.2s ease;
}

.orden-option:hover {
  transform: translateY(-2px);
  border-color: orange;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.new-order {
  border-color: orange;
  background: rgba(255, 165, 0, 0.1);
}

.existing-orders-title {
  color: orange;
  margin: 1rem 0;
  font-size: 1.2rem;
  padding: 0 1rem;
}

.option-text h3,
.option-content h3 {
  color: #42b983;
  margin-bottom: 0.5rem;
  font-size: 1.1rem;
}

.option-text p,
.option-content p {
  color: #8f9ca8;
  font-size: 0.9rem;
  line-height: 1.4;
}

.modal-close {
  width: 100%;
  padding: 0.75rem;
  background: #e74c3c;
  color: white;
  border: none;
  border-radius: 0.5rem;
  margin-top: 1rem;
  cursor: pointer;
  transition: background-color 0.2s;
  font-size: 1rem;
  font-weight: 500;
}

.modal-close:hover {
  background: #c0392b;
}

@keyframes modalAppear {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@media (max-width: 640px) {
  .modal-content {
    padding: 1rem;
    width: 95%;
  }

  .modal-title {
    font-size: 1.2rem;
  }

  .orden-option {
    padding: 0.75rem;
  }

  .option-text h3,
  .option-content h3 {
    font-size: 1rem;
  }
}

body.modal-open {
  overflow: hidden;
}

.quantity-control {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 1rem;
  margin: 1rem 0;
  background: rgba(66, 185, 131, 0.1);
  padding: 0.5rem;
  border-radius: 0.5rem;
}

.quantity-btn {
  background: #42b983;
  color: black;
  border: none;
  border-radius: 0.25rem;
  width: 30px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.2rem;
  cursor: pointer;
  transition: all 0.2s ease;
}

.quantity-btn:disabled {
  background: #767676;
  cursor: not-allowed;
}

.quantity-btn:hover:not(:disabled) {
  background: #3aa876;
  transform: scale(1.1);
}

.quantity-display {
  color: #42b983;
  font-size: 1.1rem;
  font-weight: bold;
  min-width: 30px;
  text-align: center;
}
</style>