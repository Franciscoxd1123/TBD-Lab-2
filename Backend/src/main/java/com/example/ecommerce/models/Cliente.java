package com.example.ecommerce.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Cliente{

    private Long idCliente;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;
    @Getter @Setter
    private String password;
    private Double latitud;
    private Double longitud;
    private String location;
}