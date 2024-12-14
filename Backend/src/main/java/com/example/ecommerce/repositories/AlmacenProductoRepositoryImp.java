package com.example.ecommerce.repositories;

import com.example.ecommerce.models.AlmacenProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class AlmacenProductoRepositoryImp implements AlmacenProductoRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public AlmacenProducto create(AlmacenProducto almacenProducto) {
        String sql = "INSERT INTO Almacen_Producto (id_almacen, id_producto, cantidad, fecha_entrada, fecha_salida) " +
                "VALUES (:idAlmacen, :idProducto, :cantidad, :fechaEntrada, :fechaSalida) " +
                "RETURNING id_almacen_producto";

        try (Connection con = sql2o.open()) {
            Integer id = con.createQuery(sql, true)
                    .addParameter("idAlmacen", almacenProducto.getIdAlmacen())
                    .addParameter("idProducto", almacenProducto.getIdProducto())
                    .addParameter("cantidad", almacenProducto.getCantidad())
                    .addParameter("fechaEntrada", almacenProducto.getFechaEntrada())
                    .addParameter("fechaSalida", almacenProducto.getFechaSalida())
                    .executeUpdate()
                    .getKey(Integer.class);

            almacenProducto.setIdAlmacenProducto(Long.valueOf(id));
            return almacenProducto;
        } catch (Exception e) {
            System.out.println("Error al crear el registro de AlmacenProducto: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<AlmacenProducto> getAll() {
        String sql = "SELECT id_almacen_producto AS idAlmacenProducto, id_almacen AS idAlmacen, id_producto AS idProducto, " +
                "cantidad, fecha_entrada AS fechaEntrada, fecha_salida AS fechaSalida " +
                "FROM Almacen_Producto";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(AlmacenProducto.class);
        } catch (Exception e) {
            System.out.println("Error al consultar los registros de AlmacenProducto: " + e.getMessage());
            return null;
        }
    }

    @Override
    public AlmacenProducto getAlmacenProductoId(int id) {
        String sql = "SELECT id_almacen_producto AS idAlmacenProducto, id_almacen AS idAlmacen, id_producto AS idProducto, " +
                "cantidad, fecha_entrada AS fechaEntrada, fecha_salida AS fechaSalida " +
                "FROM Almacen_Producto WHERE id_almacen_producto = :id";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(AlmacenProducto.class);
        } catch (Exception e) {
            System.out.println("Error al consultar el registro de AlmacenProducto por ID: " + e.getMessage());
            return null;
        }
    }

    @Override
    public AlmacenProducto update(AlmacenProducto almacenProducto, int id) {
        String sql = "UPDATE Almacen_Producto SET id_almacen = :idAlmacen, id_producto = :idProducto, cantidad = :cantidad, " +
                "fecha_entrada = :fechaEntrada, fecha_salida = :fechaSalida " +
                "WHERE id_almacen_producto = :id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("idAlmacen", almacenProducto.getIdAlmacen())
                    .addParameter("idProducto", almacenProducto.getIdProducto())
                    .addParameter("cantidad", almacenProducto.getCantidad())
                    .addParameter("fechaEntrada", almacenProducto.getFechaEntrada())
                    .addParameter("fechaSalida", almacenProducto.getFechaSalida())
                    .executeUpdate();
            return almacenProducto;
        } catch (Exception e) {
            System.out.println("Error al actualizar el registro de AlmacenProducto: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Almacen_Producto WHERE id_almacen_producto = :id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al eliminar el registro de AlmacenProducto: " + e.getMessage());
        }
    }
}
