package com.example.ecommerce.services;

import com.example.ecommerce.models.Categoria;
import com.example.ecommerce.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria createCategoria(Categoria categoria){
        return categoriaRepository.create(categoria);
    }

    public List<Categoria> getCategorias(){
        return categoriaRepository.getAll();
    }

    public Categoria getCategoria(int id){
        return categoriaRepository.getCategoriaId(id);
    }

    public Categoria updateCategoria(Categoria categoria, int id){
        return categoriaRepository.update(categoria, id);
    }

    public void deleteCategoria(int id){
        categoriaRepository.delete(id);
    }
}
