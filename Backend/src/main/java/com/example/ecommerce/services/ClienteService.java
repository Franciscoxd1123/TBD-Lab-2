package com.example.ecommerce.services;

import com.example.ecommerce.models.Almacen;
import com.example.ecommerce.models.Cliente;
import com.example.ecommerce.repositories.AlmacenRepositoryImp;
import com.example.ecommerce.repositories.ClienteRepository;
import com.example.ecommerce.repositories.ClienteRepositoryImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();
    private GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    @Autowired
    private AlmacenRepositoryImp almacenRepositoryImp;
    @Autowired
    private ClienteRepositoryImp clienteRepositoryImp;

    // Metodo para obtener latitud, longitud y location utilizando OpenStreetMap Nominatim
    private void obtenerGeolocalizacion(Cliente cliente) {
        String direccion = cliente.getDireccion();
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

                cliente.setLatitud(lat);
                cliente.setLongitud(lon);

                // Crear punto geométrico
                Point point = geometryFactory.createPoint(new Coordinate(lon, lat));
                cliente.setLocation(point);
            } else {
                throw new IllegalArgumentException("No se encontró la dirección");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener la geolocalización: " + e.getMessage());
            throw new IllegalArgumentException("No se pudo obtener la geolocalización");
        }
    }

    public Cliente createCliente(Cliente cliente){
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (cliente.getDireccion() == null || cliente.getDireccion().trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección es obligatoria");
        }
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (cliente.getTelefono() == null || cliente.getTelefono().trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es obligatorio");
        }
        if (cliente.getPassword() == null || cliente.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        // Obtener latitud, longitud y location a partir de la dirección
        obtenerGeolocalizacion(cliente);

        // Codificar la contraseña (BCrypt)
        String encodedPassword = passwordEncoder.encode(cliente.getPassword());
        cliente.setPassword(encodedPassword);

        return clienteRepository.create(cliente);
    }

    public List<Cliente> getClientes(){
        return clienteRepository.getAll();
    }

    public Cliente getCliente(int id){
        return clienteRepository.getClienteId(id);
    }

    public Cliente updateCliente(Cliente cliente, int id){
        // Obtener latitud, longitud y location si hay cambios en la dirección
        if (cliente.getDireccion() != null) {
            obtenerGeolocalizacion(cliente);
        }
        return clienteRepository.update(cliente, id);
    }

    public Double shortestRoute(int almacenId, int clienteId) {
        return clienteRepository.shortestRoute(almacenId, clienteId);
    }

    public void deleteCliente(int id){
        clienteRepository.delete(id);
    }

    public Cliente login(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es requerido");
        }
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente == null) {
            throw new IllegalArgumentException("Email o contraseña incorrectos");
        }
        if (passwordEncoder.matches(password, cliente.getPassword())) {
            return cliente;
        } else {
            throw new IllegalArgumentException("Email o contraseña incorrectos");
        }
    }
}