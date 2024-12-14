package com.example.ecommerce.services;

import com.example.ecommerce.models.AlmacenProducto;
import com.example.ecommerce.repositories.AlmacenProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlmacenProductoService {

    @Autowired
    private AlmacenProductoRepository almacenProductoRepository;

    public AlmacenProducto createAlmacenProducto(AlmacenProducto almacenProducto){
        //Esta como NOT NULL pero no se puede usar esta funcion para eso
        if (almacenProducto.getCantidad() == null || almacenProducto.getCantidad().trim().isEmpty()) {
            throw new IllegalArgumentException("La cantidad es obligatorio");
        }

        return almacenProductoRepository.create(almacenProducto);
    }

    public List<AlmacenProducto> getAlmacenProductos(){
        return almacenProductoRepository.getAll();
    }

    public AlmacenProducto getAlmacenProducto(int id){
        return almacenProductoRepository.getAlmacenProductoId(id);
    }

    public AlmacenProducto updateAlmacenProducto(AlmacenProducto almacenProducto, int id){
        return almacenProductoRepository.update(almacenProducto, id);
    }

    public void deleteAlmacenProducto(int id){
        almacenProductoRepository.delete(id);
    }
}
