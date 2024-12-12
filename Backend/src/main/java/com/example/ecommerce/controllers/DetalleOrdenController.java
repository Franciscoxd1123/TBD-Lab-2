package com.example.ecommerce.controllers;

import com.example.ecommerce.models.DetalleOrden;
import com.example.ecommerce.services.DetalleOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalles")
@CrossOrigin(origins = "http://localhost:5173")
public class DetalleOrdenController{

    @Autowired
    private DetalleOrdenService detalleOrdenService;

    @PostMapping("/")
    public ResponseEntity<DetalleOrden> createProducto(@RequestBody DetalleOrden detalleOrden) {
        DetalleOrden detalleNew = detalleOrdenService.createDetalleOrden(detalleOrden);
        return ResponseEntity.ok(detalleNew);
    }

    @GetMapping("/")
    public ResponseEntity<List<DetalleOrden>> listDetalleOrden() {
        List<DetalleOrden> detalleOrden = detalleOrdenService.getAll();
        return ResponseEntity.ok(detalleOrden);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleOrden> getDetalleOrden(@PathVariable int id) {
        DetalleOrden detalleOrden = detalleOrdenService.getDetalleOrden(id);
        return ResponseEntity.ok(detalleOrden);
    }

    @GetMapping("/orden/{idOrden}")
    public ResponseEntity<List<DetalleOrden>> getDetallesByOrden(@PathVariable int idOrden) {
        List<DetalleOrden> detalles = detalleOrdenService.getDetallesByOrden(idOrden);
        return ResponseEntity.ok(detalles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleOrden> updateProducto(@RequestBody DetalleOrden detalleOrden, @PathVariable int id){
        DetalleOrden detalleOrdenUptadted = detalleOrdenService.updateDetalleOrden(detalleOrden, id);
        return ResponseEntity.ok(detalleOrdenUptadted);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteDetalleOrden(@PathVariable int id) {
        detalleOrdenService.deleteDetalleOrden(id);
    }
}