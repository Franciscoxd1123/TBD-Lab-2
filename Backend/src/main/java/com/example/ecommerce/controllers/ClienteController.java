package com.example.ecommerce.controllers;

import com.example.ecommerce.models.Almacen;
import com.example.ecommerce.models.Cliente;
import com.example.ecommerce.services.ClienteService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:5173")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/")
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        Cliente clienteNew = clienteService.createCliente(cliente);
        return ResponseEntity.ok(clienteNew);
    }

    @GetMapping("/")
    public ResponseEntity<List<Cliente>> listClientes() {
        List<Cliente> clientes = clienteService.getClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getCliente(@PathVariable int id) {
        Cliente cliente = clienteService.getCliente(id);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@RequestBody Cliente cliente, @PathVariable int id){
        Cliente clienteUpdated = clienteService.updateCliente(cliente, id);
        return ResponseEntity.ok(clienteUpdated);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteCliente(@PathVariable int id) {
        clienteService.deleteCliente(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        try {
            String email = credenciales.get("email");
            String password = credenciales.get("password");

            if (email == null) {
                return ResponseEntity
                        .badRequest()
                        .body("Correo requerido");
            }
            if (password == null) {
                return ResponseEntity
                        .badRequest()
                        .body("Contraseña requerida");
            }

            Cliente cliente = clienteService.login(email, password);
            return ResponseEntity.ok(cliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/shortestRoute")
    public ResponseEntity<?> shortestRoute(@RequestBody shortestRouteRequest request) {
        try {
            return ResponseEntity.ok(clienteService.shortestRoute(request.getIdAlmacen(), request.getIdCliente()));
        } catch (IllegalArgumentException e) {
            return  ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }

    @Data
    public static class shortestRouteRequest {
        private int idAlmacen;
        private int idCliente;
    }

    @GetMapping("/reporte-general")
    public ResponseEntity<?> reporteGeneral() {
        return ResponseEntity.ok(clienteService.getReporteGeneral());
    }

}