package com.example.ecommerce.repositories;

import com.example.ecommerce.models.Almacen;
import com.example.ecommerce.models.Cliente;
import java.util.List;


public interface ClienteRepository {

    Cliente create(Cliente cliente);

    List<Cliente> getAll();

    Cliente getClienteId(int id);

    Cliente update(Cliente cliente, int id);

    void delete(int id);

    boolean existeEmail(String email);

    Cliente findByEmail(String email);

    Double shortestRoute(Almacen almacen, Cliente cliente);

}