package com.example.ecommerce.controllers;

import com.example.ecommerce.models.Orden;
import com.example.ecommerce.services.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenes")
@CrossOrigin(origins = "http://localhost:5173")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    @PostMapping("/")
    public ResponseEntity<Orden> createOrden(@RequestBody Orden orden) {
        Orden ordenNew = ordenService.createOrden(orden);
        return ResponseEntity.ok(ordenNew);
    }

    @GetMapping("/")
    public ResponseEntity<List<Orden>> listOrdenes() {
        List<Orden> ordenes = ordenService.getOrdenes();
        return ResponseEntity.ok(ordenes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orden> getOrden(@PathVariable int id) {
        Orden orden = ordenService.getOrden(id);
        return ResponseEntity.ok(orden);
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Orden>> getOrdenesPorCliente(@PathVariable int idCliente) {
        List<Orden> ordenes = ordenService.getOrdenesPorCliente(idCliente);
        return ResponseEntity.ok(ordenes);
    }

    @GetMapping("/almacen/{idAlmacen}/cercanas")
    public ResponseEntity<?> getOrdenesEnviadasCercaAlmacen(@PathVariable int idAlmacen) {
        try {
            List<Orden> ordenes = ordenService.getOrdenesEnviadasCercaAlmacen(idAlmacen);
            return ResponseEntity.ok(ordenes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al procesar la solicitud: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orden> updateOrden(@RequestBody Orden orden, @PathVariable int id) {
        Orden ordenUpdated = ordenService.updateOrden(orden, id);
        return ResponseEntity.ok(ordenUpdated);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteOrden(@PathVariable int id) {
        ordenService.deleteOrden(id);
    }
}
