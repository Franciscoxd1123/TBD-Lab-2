package com.example.ecommerce.models;

import lombok.Data;

@Data
public class Almacen {
    private Long idAlmacen;
    private String nombre;
    private String direccion;
    private Double latitud;
    private Double longitud;
    private String location;
}