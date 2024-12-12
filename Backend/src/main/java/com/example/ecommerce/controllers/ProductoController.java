package com.example.ecommerce.controllers;

import com.example.ecommerce.models.Producto;
import com.example.ecommerce.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductoController{

    @Autowired
    private ProductoService productoService;

    @PostMapping("/")
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        Producto productoNew = productoService.createProducto(producto);
        return ResponseEntity.ok(productoNew);
    }

    @GetMapping("/")
    public ResponseEntity<List<Producto>> listProductos() {
        List<Producto> productos = productoService.getProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProducto(@PathVariable int id) {
        Producto producto = productoService.getProducto(id);
        return ResponseEntity.ok(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@RequestBody Producto producto, @PathVariable int id){
        Producto productoUpdated = productoService.updateProducto(producto, id);
        return ResponseEntity.ok(productoUpdated);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteProducto(@PathVariable int id) {
       productoService.deleteProducto(id);
    }

    @GetMapping("/categoria/{categoria}")
    public List<Producto> getProductosByCategory(@PathVariable int categoria) {
        return productoService.getProductosByCategory(categoria);
    }
}