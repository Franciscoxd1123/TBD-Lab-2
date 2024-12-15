package com.example.ecommerce.repositories;

import com.example.ecommerce.models.Orden;
import java.util.List;
import java.util.Map;

public interface OrdenRepository {
    Orden create(Orden orden);

    List<Orden> getAll();

    Orden getOrdenById(int id);

    Orden update(Orden orden, int id);

    void delete(int id);

    List<Orden> getOrdenesCliente(int idCliente);

    List<Map<String, Object>> findOrdenesEnviadasCercaAlmacen(int idAlmacen);
}
