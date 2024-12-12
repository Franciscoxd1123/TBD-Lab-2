package com.example.ecommerce.models;

import lombok.Data;
import java.util.Date;

@Data
public class Orden{

    private Long idOrden;
    private Date fechaOrden;
    private String estado;
    private int idCliente;
    private float total;
}