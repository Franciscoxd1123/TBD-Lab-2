// src/services/productoService.js
import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8090/productos',
    headers: {
        'Content-Type': 'application/json',
    },
});

export default {
    // Crear un nuevo producto
    createProducto(data) {
        return apiClient.post('/', data);
    },

    // Obtener todos los productos
    listProductos() {
        return apiClient.get('/');
    },

    // Obtener un producto por id
    getProducto(id) {
        return apiClient.get(`/${id}`);
    },

    // Actualizar un producto
    updateProducto(id, data) {
        return apiClient.put(`/${id}`, data);
    },

    // Eliminar un producto
    deleteProducto(id) {
        return apiClient.delete(`/${id}`);
    },

    // Buscar productos por nombre y categoría
    searchProductosByCategory(nombre, categoria) {
        return apiClient.get('/', {
            params: {
                nombre: nombre,
                categoria: categoria
            }
        });
    }
};
