package com.example.ecommerce.repositories;

import com.example.ecommerce.models.Almacen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;
import java.util.Map;

@Repository
public class AlmacenRepositoryImp implements AlmacenRepository{

    @Autowired
    private Sql2o sql2o;

    @Override
    public Almacen create(Almacen almacen) {
        String sql = "INSERT INTO Almacen (nombre, direccion, latitud, longitud, location) " +
                "VALUES (:nombre, :direccion, :latitud, :longitud, " +
                "ST_GeomFromText('POINT(' || :longitud || ' ' || :latitud || ')', 4326)) " +
                "RETURNING id_almacen";

        try (Connection con = sql2o.open()) {
            Integer id = con.createQuery(sql, true)
                    .addParameter("nombre", almacen.getNombre())
                    .addParameter("direccion", almacen.getDireccion())
                    .addParameter("latitud", almacen.getLatitud())
                    .addParameter("longitud", almacen.getLongitud())
                    .executeUpdate()
                    .getKey(Integer.class);

            almacen.setIdAlmacen(Long.valueOf(id));
            return almacen;
        } catch (Exception e) {
            System.out.println("Error al crear el almacén: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Almacen> getAll() {
        String sql = "SELECT id_almacen AS idAlmacen, nombre, direccion, latitud, longitud FROM Almacen";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Almacen.class);
        } catch (Exception e) {
            System.out.println("Error al consultar los almacenes: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Almacen getAlmacenId(int id) {
        String sql = "SELECT id_almacen AS idAlmacen, nombre, direccion, latitud, longitud FROM Almacen WHERE id_almacen = :id";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Almacen.class);
        } catch (Exception e) {
            System.out.println("Error al consultar el almacén: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Almacen update(Almacen almacen, int id) {
        String sql = "UPDATE Almacen SET nombre = :nombre WHERE id_almacen = :id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("nombre", almacen.getNombre())
                    .executeUpdate();
            return almacen;
        } catch (Exception e) {
            System.out.println("Error al actualizar el almacén: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Almacen WHERE id_almacen = :id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql).addParameter("id", id).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al eliminar el almacén: " + e.getMessage());
        }
    }

    @Override
    public Map<String, Object> findAlmacenMasCercano(int idCliente) {
        String sql = "SELECT a.id_almacen AS idAlmacen, " +
                "a.nombre, a.direccion, " +
                "ROUND(CAST(ST_Distance(a.location::geography, c.location::geography) / 1000 AS numeric), 2) AS distanciaKm " +
                "FROM Almacen a, Cliente c " +
                "WHERE c.id_cliente = :idCliente " +
                "ORDER BY ST_Distance(a.location::geography, c.location::geography) ASC " +
                "LIMIT 1";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("idCliente", idCliente)
                    .executeAndFetchTable()
                    .asList()
                    .get(0);
        } catch (Exception e) {
            System.out.println("Error al buscar almacén cercano: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}