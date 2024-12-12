package com.example.ecommerce.services;

import com.example.ecommerce.models.DetalleOrden;
import com.example.ecommerce.repositories.DetalleOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleOrdenService {

    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;

    public DetalleOrden createDetalleOrden(DetalleOrden detalleOrden){
        return detalleOrdenRepository.create(detalleOrden);
    }

    public List<DetalleOrden> getAll(){
        return detalleOrdenRepository.getAll();
    }

    public DetalleOrden getDetalleOrden(int id){
        return detalleOrdenRepository.getDetalleById(id);
    }

    public DetalleOrden updateDetalleOrden(DetalleOrden detalleOrden, int id){
        return detalleOrdenRepository.update(detalleOrden, id);
    }

    public void deleteDetalleOrden(int id){
        detalleOrdenRepository.delete(id);
    }

    public List<DetalleOrden> getDetallesByOrden(int idOrden) {
        return detalleOrdenRepository.getDetallesByOrden(idOrden);
    }
}