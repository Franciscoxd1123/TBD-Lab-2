package com.example.ecommerce.repositories;

import com.example.ecommerce.models.DetalleOrden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DetalleOrdenRepositoryImp implements DetalleOrdenRepository {
    @Autowired
    private Sql2o sql2o;

    public DetalleOrden create(DetalleOrden detalleOrden) {
        String sql = "INSERT INTO detalle_orden (id_orden, id_producto, cantidad, precio_unitario) " +
                "VALUES (:idOrden, :idProducto, :cantidad, :precioUnitario) " +
                "RETURNING id_detalle";

        try (Connection con = sql2o.open()) {
            Integer id = con.createQuery(sql, true)
                    .addParameter("idOrden", detalleOrden.getIdOrden())
                    .addParameter("idProducto", detalleOrden.getIdProducto())
                    .addParameter("cantidad", detalleOrden.getCantidad())
                    .addParameter("precioUnitario", detalleOrden.getPrecioUnitario())
                    .executeUpdate()
                    .getKey(Integer.class);

            detalleOrden.setIdDetalle(Long.valueOf(id));
            return detalleOrden;
        }
    }

    public List<DetalleOrden> getAll() {
        String sql = "SELECT id_detalle AS idDetalle, id_orden AS idOrden, id_producto AS idProducto, cantidad, precio_unitario AS precioUnitario FROM detalle_orden";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(DetalleOrden.class);
        } catch (Exception e) {
            System.out.println("Error al consultar los detalles de orden: " + e.getMessage());
            return null;
        }
    }

    public DetalleOrden getDetalleById(int id) {
        String sql = "SELECT id_detalle AS idDetalle, id_orden AS idOrden, id_producto AS idProducto, cantidad, precio_unitario AS precioUnitario FROM detalle_orden WHERE id_detalle = :id";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).addParameter("id",id).executeAndFetchFirst(DetalleOrden.class);
        } catch (Exception e) {
            System.out.println("Error al consultar el detalle de orden: " + e.getMessage());
            return null;
        }
    }

    public DetalleOrden update(DetalleOrden detalleOrden, int id) {
        String sql = "UPDATE detalle_orden SET id_orden = :idOrden, id_producto = :idProducto, cantidad = :cantidad, precio_unitario = :precioUnitario WHERE id_detalle = :id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("idOrden", detalleOrden.getIdOrden())
                    .addParameter("idProducto", detalleOrden.getIdProducto())
                    .addParameter("cantidad", detalleOrden.getCantidad())
                    .addParameter("precioUnitario", detalleOrden.getPrecioUnitario())
                    .executeUpdate();
            return detalleOrden;
        } catch (Exception e) {
            System.out.println("Error al actualizar el detalle de orden: " + e.getMessage());
            return null;
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM detalle_orden WHERE id_detalle = :id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql).addParameter("id",id).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al eliminar el detalle de orden: " + e.getMessage());
        }
    }

    public List<DetalleOrden> getDetallesByOrden(int idOrden) {
        String sql = "SELECT id_detalle AS idDetalle, id_orden AS idOrden, " +
                "id_producto AS idProducto, cantidad, precio_unitario AS precioUnitario " +
                "FROM detalle_orden WHERE id_orden = :idOrden";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("idOrden", idOrden)
                    .executeAndFetch(DetalleOrden.class);
        } catch (Exception e) {
            System.out.println("Error al consultar los detalles de la orden: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
