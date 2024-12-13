package com.example.ecommerce.repositories;

import com.example.ecommerce.models.Almacen;
import java.util.List;

public interface AlmacenRepository {
    Almacen create(Almacen almacen);

    List<Almacen> getAll();

    Almacen getAlmacenId(int id);

    Almacen update(Almacen almacen, int id);

    void delete(int id);
}