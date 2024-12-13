package com.example.ecommerce.controllers;

import com.example.ecommerce.models.Almacen;
import com.example.ecommerce.services.AlmacenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/almacenes")
@CrossOrigin(origins = "http://localhost:5173")
public class AlmacenController {
    @Autowired
    private AlmacenService almacenService;

    @PostMapping("/")
    public ResponseEntity<Almacen> createAlmacen(@RequestBody Almacen almacen) {
        Almacen almacenNew = almacenService.createAlmacen(almacen);
        return ResponseEntity.ok(almacenNew);
    }

    @GetMapping("/")
    public ResponseEntity<List<Almacen>> listAlmacenes() {
        List<Almacen> almacenes = almacenService.getAlmacenes();
        return ResponseEntity.ok(almacenes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Almacen> getAlmacen(@PathVariable int id) {
        Almacen almacen = almacenService.getAlmacen(id);
        return ResponseEntity.ok(almacen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Almacen> updateAlmacen(@RequestBody Almacen almacen, @PathVariable int id){
        Almacen almacenUpdated = almacenService.updateAlmacen(almacen, id);
        return ResponseEntity.ok(almacenUpdated);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteAlmacen(@PathVariable int id) {
        almacenService.deleteAlmacen(id);
    }
}