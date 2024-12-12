// src/services/ordenService.js
import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8090/ordenes',
    headers: {
        'Content-Type': 'application/json',
    },
});

export default {
    // Crear una nueva orden
    createOrden(data) {
        return apiClient.post('/', data);
    },

    // Obtener todas las órdenes
    listOrdenes() {
        return apiClient.get('/');
    },

    // Obtener una orden por id
    getOrden(id) {
        return apiClient.get(`/${id}`);
    },

    // Obtener órdenes por cliente
    getOrdenesPorCliente(idCliente) {
        return apiClient.get(`/cliente/${idCliente}`);
    },

    // Actualizar una orden
    updateOrden(id, data) {
        return apiClient.put(`/${id}`, data);
    },

    // Eliminar una orden
    deleteOrden(id) {
        return apiClient.delete(`/${id}`);
    },

    getDetallesOrden(idOrden) {
        return apiClient.get(`/detalles/${idOrden}`);
    }
};