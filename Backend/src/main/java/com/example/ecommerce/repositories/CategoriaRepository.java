package com.example.ecommerce.repositories;

import com.example.ecommerce.models.Categoria;
import java.util.List;

public interface CategoriaRepository {

    Categoria create(Categoria categoria);

    List<Categoria> getAll();

    Categoria getCategoriaId(int id);

    Categoria update(Categoria categoria, int id);

    void delete(int id);
}