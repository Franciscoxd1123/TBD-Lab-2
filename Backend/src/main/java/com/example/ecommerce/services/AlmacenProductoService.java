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
        // Si cantidad es un int, se necesita verificar si el objeto es null
        if (almacenProducto == null) {
            throw new IllegalArgumentException("El almacenProducto no puede ser null");
        }

        // Si se quiere validar que la cantidad sea mayor a 0 (o algún otro valor)
        if (almacenProducto.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
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
