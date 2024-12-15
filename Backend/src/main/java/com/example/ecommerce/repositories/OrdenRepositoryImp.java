package com.example.ecommerce.repositories;

import com.example.ecommerce.models.Orden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import java.util.List;
import java.util.Map;

@Repository
public class OrdenRepositoryImp implements OrdenRepository {

    @Autowired
    private Sql2o sql2o;

    private static final double RADIO_KM = 10.0; // Radio fijo de 10 km
    private static final double METROS_POR_KM = 1000.0;

    @Override
    public Orden create(Orden orden) {
        String sql = "INSERT INTO Orden (fecha_orden, estado, id_cliente, total) " +
                "VALUES (:fechaOrden, :estado, :idCliente, :total) " +
                "RETURNING id_orden";

        try (Connection con = sql2o.open()) {
            Integer id = con.createQuery(sql, true)
                    .addParameter("fechaOrden", orden.getFechaOrden())
                    .addParameter("estado", orden.getEstado())
                    .addParameter("idCliente", orden.getIdCliente())
                    .addParameter("total", orden.getTotal())
                    .executeUpdate()
                    .getKey(Integer.class);

            orden.setIdOrden(Long.valueOf(id));
            return orden;
        } catch (Exception e) {
            System.out.println("Error al crear la orden: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Orden> getAll() {
        String sql = "SELECT id_orden AS idOrden, fecha_orden AS fechaOrden, estado, id_cliente AS idCliente, total FROM Orden";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Orden.class);
        } catch (Exception e) {
            System.out.println("Error al consultar las órdenes: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Orden getOrdenById(int id) {
        String sql = "SELECT id_orden AS idOrden, fecha_orden AS fechaOrden, estado, id_cliente AS idCliente, total FROM Orden WHERE id_orden = :id";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Orden.class);
        } catch (Exception e) {
            System.out.println("Error al consultar la orden: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Orden update(Orden orden, int id) {
        String sql = "UPDATE Orden SET estado = :estado, total = :total " +
                "WHERE id_orden = :id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("estado", orden.getEstado())
                    .addParameter("total", orden.getTotal())
                    .executeUpdate();
            return orden;
        } catch (Exception e) {
            System.out.println("Error al actualizar la orden: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Orden WHERE id_orden = :id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al eliminar la orden: " + e.getMessage());
        }
    }

    @Override
    public List<Orden> getOrdenesCliente(int idCliente) {
        String sql = "SELECT id_orden AS idOrden, fecha_orden AS fechaOrden, estado, id_cliente AS idCliente, total FROM Orden WHERE id_cliente = :idCliente ORDER BY fecha_orden DESC";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("idCliente", idCliente)
                    .executeAndFetch(Orden.class);
        } catch (Exception e) {
            System.out.println("Error al consultar las órdenes del cliente: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> findOrdenesEnviadasCercaAlmacen(int idAlmacen) {
        String sql = "SELECT DISTINCT o.id_orden AS idOrden, " +
                "o.fecha_orden AS fechaOrden, " +
                "o.estado, o.id_cliente AS idCliente, " +
                "o.total, " +
                "ROUND(CAST(ST_Distance(c.location::geography, a.location::geography) / 1000 AS numeric), 2) AS distanciaKm " +
                "FROM Orden o " +
                "INNER JOIN Cliente c ON o.id_cliente = c.id_cliente " +
                "INNER JOIN Almacen a ON a.id_almacen = :idAlmacen " +
                "WHERE o.estado = 'Enviada' " +
                "AND ST_DWithin(c.location::geography, a.location::geography, :radioMetros) " +
                "ORDER BY distanciaKm";

        try (Connection con = sql2o.open()) {
            Query query = con.createQuery(sql)
                    .addParameter("idAlmacen", idAlmacen)
                    .addParameter("radioMetros", 10000);
            return query.executeAndFetchTable().asList();
        } catch (Exception e) {
            System.out.println("Error al consultar órdenes cercanas: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}