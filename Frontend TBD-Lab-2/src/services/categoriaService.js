import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8090/categorias',
    headers: {
        'Content-Type': 'application/json',
    },
});

export default {
    // Crear una nueva categoría
    createCategoria(data) {
        return apiClient.post('/', data);
    },

    // Obtener la lista de todas las categorías
    listCategorias() {
        return apiClient.get('/');
    },

    // Obtener una categoría por id
    getCategoria(id) {
        return apiClient.get(`/${id}`);
    },

    // Actualizar una categoría
    updateCategoria(id, data) {
        return apiClient.put(`/${id}`, data);
    },

    // Eliminar una categoría
    deleteCategoria(id) {
        return apiClient.delete(`/${id}`);
    }
};