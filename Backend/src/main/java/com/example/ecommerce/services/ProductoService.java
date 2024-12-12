package com.example.ecommerce.services;

import com.example.ecommerce.models.Producto;
import com.example.ecommerce.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Producto createProducto(Producto producto){
        return productoRepository.create(producto);
    }

    public List<Producto> getProductos(){
        return productoRepository.getAll();
    }

    public Producto getProducto(int id){
        return productoRepository.getProductoId(id);
    }

    public Producto updateProducto(Producto producto, int id){
        return productoRepository.update(producto, id);
    }

    public void deleteProducto(int id){
        productoRepository.delete(id);
    }

    public List<Producto> getProductosByCategory(int categoria) {
        return productoRepository.findByCategory(categoria);
    }
}