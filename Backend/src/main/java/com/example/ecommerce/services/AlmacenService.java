package com.example.ecommerce.services;

import com.example.ecommerce.models.Almacen;
import com.example.ecommerce.repositories.AlmacenRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AlmacenService {

    @Autowired
    private AlmacenRepository almacenRepository;

    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();
    private GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    // Metodo para obtener latitud, longitud y location utilizando OpenStreetMap Nominatim
    private void obtenerGeolocalizacion(Almacen almacen) {
        String direccion = almacen.getDireccion();
        String url = "https://nominatim.openstreetmap.org/search?format=json&q=" + direccion;

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode jsonArray = objectMapper.readTree(response);

            if (jsonArray.isArray() && jsonArray.size() > 0) {
                JsonNode firstResult = jsonArray.get(0);

                double lat = firstResult.get("lat").asDouble();
                double lon = firstResult.get("lon").asDouble();

                // Validaciones de rango
                if (lat < -90 || lat > 90 || lon < -180 || lon > 180) {
                    throw new IllegalArgumentException("Coordenadas inválidas");
                }

                almacen.setLatitud(lat);
                almacen.setLongitud(lon);

                // Crear punto geométrico
                Point point = geometryFactory.createPoint(new Coordinate(lon, lat));
                almacen.setLocation(point);
            } else {
                throw new IllegalArgumentException("No se encontró la dirección");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener la geolocalización: " + e.getMessage());
            throw new IllegalArgumentException("No se pudo obtener la geolocalización");
        }
    }

    public Almacen createAlmacen(Almacen almacen){
        if (almacen.getNombre() == null || almacen.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (almacen.getDireccion() == null || almacen.getDireccion().trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección es obligatoria");
        }

        // Obtener latitud, longitud y location a partir de la dirección
        obtenerGeolocalizacion(almacen);

        return almacenRepository.create(almacen);
    }

    public List<Almacen> getAlmacenes(){
        return almacenRepository.getAll();
    }

    public Almacen getAlmacen(int id){
        return almacenRepository.getAlmacenId(id);
    }

    public Almacen updateAlmacen(Almacen almacen, int id){
        // Obtener latitud, longitud y location si hay cambios en la dirección
        if (almacen.getDireccion() != null) {
            obtenerGeolocalizacion(almacen);
        }

        return almacenRepository.update(almacen, id);
    }

    public void deleteAlmacen(int id){
        almacenRepository.delete(id);
    }
}