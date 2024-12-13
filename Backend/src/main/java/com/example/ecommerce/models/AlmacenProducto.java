package com.example.ecommerce.models;

import lombok.Data;

import java.util.Date;

@Data
public class AlmacenProducto {
    private Long idAlmacenProducto;
    private int idAlmacen;
    private int idProducto;
    private int cantidad;
    private Date fechaEntrada;
    private Date fechaSalida;
}