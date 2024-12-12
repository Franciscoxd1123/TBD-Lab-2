// src/services/detalleOrdenService.js
import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8090/detalles',
    headers: {
        'Content-Type': 'application/json',
    },
});

export default {
    // Crear un nuevo detalle de orden
    createDetalleOrden(data) {
        return apiClient.post('/', data);
    },

    // Obtener todos los detalles de orden
    listDetalleOrden() {
        return apiClient.get('/');
    },

    // Obtener un detalle de orden por id
    getDetalleOrden(id) {
        return apiClient.get(`/${id}`);
    },

    // Obtener detalles por orden
    getDetallesByOrden(idOrden) {
        return apiClient.get(`/orden/${idOrden}`);
    },

    // Actualizar un detalle de orden
    updateDetalleOrden(id, data) {
        return apiClient.put(`/${id}`, data);
    },

    // Eliminar un detalle de orden
    deleteDetalleOrden(id) {
        return apiClient.delete(`/${id}`);
    }
};