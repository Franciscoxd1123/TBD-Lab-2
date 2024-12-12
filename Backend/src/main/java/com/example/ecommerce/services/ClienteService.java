package com.example.ecommerce.services;

import com.example.ecommerce.models.Cliente;
import com.example.ecommerce.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Cliente createCliente(Cliente cliente){
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (cliente.getDireccion() == null || cliente.getDireccion().trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección es obligatoria");
        }
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (cliente.getTelefono() == null || cliente.getTelefono().trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es obligatorio");
        }
        if (cliente.getPassword() == null || cliente.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        String encodedPassword = passwordEncoder.encode(cliente.getPassword());
        cliente.setPassword(encodedPassword);
        return clienteRepository.create(cliente);
    }

    public List<Cliente> getClientes(){
        return clienteRepository.getAll();
    }

    public Cliente getCliente(int id){
        return clienteRepository.getClienteId(id);
    }

    public Cliente updateCliente(Cliente cliente, int id){
        return clienteRepository.update(cliente, id);
    }

    public void deleteCliente(int id){
        clienteRepository.delete(id);
    }

    public Cliente login(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es requerido");
        }
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente == null) {
            throw new IllegalArgumentException("Email o contraseña incorrectos");
        }
        if (passwordEncoder.matches(password, cliente.getPassword())) {
            return cliente;
        } else {
            throw new IllegalArgumentException("Email o contraseña incorrectos");
        }
    }
}
