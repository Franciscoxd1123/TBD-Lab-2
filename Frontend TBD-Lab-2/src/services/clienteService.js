import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8090/clientes',
    headers: {
        'Content-Type': 'application/json',
    },
});

export default {
    // Crear un nuevo cliente
    createCliente(data) {
        return apiClient.post('/', data);
    },

    // Obtener la lista de todos los clientes
    listClientes() {
        return apiClient.get('/');
    },

    // Obtener un cliente por id
    getCliente(id) {
        return apiClient.get(`/${id}`);
    },

    loginCliente(email, password) {
        return apiClient.post('/login', { 
            email: email,
            password: password 
        });
    },

    shortestRoute(payload) {
        return apiClient.post('/shortestRoute', payload)
    }

    // Actualizar un cliente
    updateCliente(id, data) {
        return apiClient.put(`/${id}`, data);
    },

    // Eliminar un cliente
    deleteCliente(id) {
        return apiClient.delete(`/${id}`);
    }
};