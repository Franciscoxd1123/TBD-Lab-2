package com.example.ecommerce.controllers;

import com.example.ecommerce.models.AlmacenProducto;
import com.example.ecommerce.services.AlmacenProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/almacen-productos")
@CrossOrigin(origins = "http://localhost:5173")
public class AlmacenProductoController {

    @Autowired
    private AlmacenProductoService almacenProductoService;

    @PostMapping("/")
    public ResponseEntity<AlmacenProducto> createAlmacenProducto(@RequestBody AlmacenProducto almacenProducto) {
        AlmacenProducto almacenProductoNew = almacenProductoService.createAlmacenProducto(almacenProducto);
        return ResponseEntity.ok(almacenProductoNew);
    }

    @GetMapping("/")
    public ResponseEntity<List<AlmacenProducto>> listAlmacenProductos() {
        List<AlmacenProducto> almacenProductos = almacenProductoService.getAlmacenProductos();
        return ResponseEntity.ok(almacenProductos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlmacenProducto> getAlmacenProducto(@PathVariable int id) {
        AlmacenProducto almacenProducto = almacenProductoService.getAlmacenProducto(id);
        return ResponseEntity.ok(almacenProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlmacenProducto> updateAlmacenProducto(@RequestBody AlmacenProducto almacenProducto, @PathVariable int id) {
        AlmacenProducto almacenProductoActualizado = almacenProductoService.updateAlmacenProducto(almacenProducto, id);
        return ResponseEntity.ok(almacenProductoActualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteAlmacenProducto(@PathVariable int id) {
        almacenProductoService.deleteAlmacenProducto(id);
    }
}
