import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8090/tareas',
  headers: {
    'Content-Type': 'application/json',
  },
});

export default {
    // Crear una nueva tarea
    createTarea(data) {
        return apiClient.post('/', data);
    },

    // Obtener la lista de todas las tareas
    listTareas() {
        return apiClient.get('/');
    },

    // Obtener una tarea por id
    getTarea(id) {
        return apiClient.get(`/${id}`);
    },

    // Buscar tareas por estado o palabra clave
    buscarTareas(estado, palabraClave) {
        return apiClient.get('/buscar', {
            params: { estado, palabraClave }
        });
    },

    // Obtener tareas por idUsuario
    getTareasByUsuario(idUsuario) {
        return apiClient.get(`/buscar/${idUsuario}`);
    },

    // Obtener notificaciones para un usuario específico
    obtenerNotificaciones(idUsuario) {
        return apiClient.get(`/notificaciones/${idUsuario}`);
    },

    // Actualizar una tarea por id
    updateTarea(id, data) {
        return apiClient.put(`/${id}`, data);
    },

    // Marcar una tarea como completada
    completarTarea(id) {
        return apiClient.put(`/${id}/completar`);
    },

    // Eliminar una tarea por id
    deleteTarea(id) {
        return apiClient.delete(`/${id}`);
    },
};