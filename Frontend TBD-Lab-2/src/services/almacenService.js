import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8090/almacenes',
    headers: {
        'Content-Type': 'application/json',
    },
});

export default {
    // Obtener la lista de todos los almacenes
    listAlmacenes() {
        return apiClient.get('/');
    },

    // Obtener un almacén por id
    getAlmacen(id) {
        return apiClient.get(`/${id}`);
    },

    // Obtener almacén más cercano a un cliente
    getAlmacenCercano(idCliente) {
        return apiClient.get(`/cercano/cliente/${idCliente}`);
    },
};