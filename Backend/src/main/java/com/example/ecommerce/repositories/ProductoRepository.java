package com.example.ecommerce.repositories;

import com.example.ecommerce.models.Producto;
import java.util.List;

public interface ProductoRepository {
    Producto create(Producto producto);

    List<Producto> getAll();

    Producto getProductoId(int id);

    Producto update(Producto producto, int id);

    void delete(int id);

    List<Producto> findByCategory(int categoria);
}