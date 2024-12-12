package com.example.ecommerce.services;

import com.example.ecommerce.models.Orden;
import com.example.ecommerce.repositories.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Date;

@Service
public class OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    public Orden createOrden(Orden orden) {
        // Establecer la fecha actual al crear la orden
        orden.setFechaOrden(new Date());
        // Asumimos que el estado inicial es "PENDIENTE"
        if (orden.getEstado() == null || orden.getEstado().isEmpty()) {
            orden.setEstado("PENDIENTE");
        }
        return ordenRepository.create(orden);
    }

    public List<Orden> getOrdenes() {
        return ordenRepository.getAll();
    }

    public Orden getOrden(int id) {
        return ordenRepository.getOrdenById(id);
    }

    public Orden updateOrden(Orden orden, int id) {
        Orden ordenExistente = ordenRepository.getOrdenById(id);
        if (ordenExistente == null) {
            return null;
        }
        return ordenRepository.update(orden, id);
    }

    public void deleteOrden(int id) {
        ordenRepository.delete(id);
    }

    public List<Orden> getOrdenesPorCliente(int idCliente) {
        return ordenRepository.getOrdenesCliente(idCliente);
    }

}
