package com.example.ecommerce.models;

import lombok.Data;

@Data
public class Producto{

    private Long idProducto;
    private String nombre;
    private String descripcion;
    private float precio;
    private int stock;
    private String estado;
    private int idCategoria;

}