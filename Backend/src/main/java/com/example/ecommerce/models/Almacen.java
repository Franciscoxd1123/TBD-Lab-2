package com.example.ecommerce.models;

import lombok.Data;
import org.locationtech.jts.geom.Point;

@Data
public class Almacen {

    private Long idAlmacen;
    private String nombre;
    private String direccion;
    private Double latitud;
    private Double longitud;
    private Point location;
}