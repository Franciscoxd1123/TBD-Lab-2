package com.example.ecommerce.services;

import com.example.ecommerce.models.Almacen;
import com.example.ecommerce.repositories.AlmacenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AlmacenService {

    @Autowired
    private AlmacenRepository almacenRepository;

    // Metodo para obtener latitud, longitud y location utilizando OpenStreetMap Nominatim
    private void obtenerGeolocalizacion(Almacen almacen) {
        String direccion = almacen.getDireccion();
        String url = "https://nominatim.openstreetmap.org/search?format=json&q=" + direccion;

        RestTemplate restTemplate = new RestTemplate();
        try {
            // Llamada a la API de Nominatim
            String response = restTemplate.getForObject(url, String.class);
            // Parsear el resultado de la API (esto puede cambiar dependiendo del formato de respuesta)
            if (response != null) {
                // Suponemos que el API retorna un JSON, parseamos
                // Esto debería ser más robusto con un JSON parser como Jackson o Gson
                String lat = response.substring(response.indexOf("lat") + 5, response.indexOf(","));
                String lon = response.substring(response.indexOf("lon") + 5, response.indexOf("}", response.indexOf("lon")));
                almacen.setLatitud(Double.parseDouble(lat));
                almacen.setLongitud(Double.parseDouble(lon));
                almacen.setLocation(direccion);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener la geolocalización: " + e.getMessage());
        }
    }

    public Almacen createAlmacen(Almacen almacen){
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