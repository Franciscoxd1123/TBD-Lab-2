package com.example.ecommerce.models;

import lombok.Data;

@Data
public class DetalleOrden{

    private Long idDetalle;
    private int idOrden;
    private int idProducto;
    private int cantidad;
    private float precioUnitario;
}