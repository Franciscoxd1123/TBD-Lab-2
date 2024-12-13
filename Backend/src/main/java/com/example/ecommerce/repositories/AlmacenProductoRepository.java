package com.example.ecommerce.repositories;

import com.example.ecommerce.models.AlmacenProducto;
import java.util.List;

public interface AlmacenProductoRepository {
    AlmacenProducto create(AlmacenProducto almacenProducto);

    List<AlmacenProducto> getAll();

    AlmacenProducto getAlmacenProductoId(int id);

    AlmacenProducto update(AlmacenProducto almacenProducto, int id);

    void delete(int id);
}