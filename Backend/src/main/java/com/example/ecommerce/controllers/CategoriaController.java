package com.example.ecommerce.controllers;

import com.example.ecommerce.models.Categoria;
import com.example.ecommerce.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping("/")
    public ResponseEntity<Categoria> createCategoria(@RequestBody Categoria categoria) {
        Categoria categoriaNew = categoriaService.createCategoria(categoria);
        return ResponseEntity.ok(categoriaNew);
    }

    @GetMapping("/")
    public ResponseEntity<List<Categoria>> listCategorias() {
        List<Categoria> categorias = categoriaService.getCategorias();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoria(@PathVariable int id) {
        Categoria categoria = categoriaService.getCategoria(id);
        return ResponseEntity.ok(categoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateCategoria(@RequestBody Categoria categoria, @PathVariable int id){
        Categoria categoriaUpdated = categoriaService.updateCategoria(categoria, id);
        return ResponseEntity.ok(categoriaUpdated);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteCategoria(@PathVariable int id) {
        categoriaService.deleteCategoria(id);
    }
}
