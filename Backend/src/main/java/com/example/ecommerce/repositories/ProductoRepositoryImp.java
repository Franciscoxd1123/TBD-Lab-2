package com.example.ecommerce.repositories;

import com.example.ecommerce.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

@Repository
public class ProductoRepositoryImp implements ProductoRepository{

    @Autowired
    private Sql2o sql2o;

    @Override
    public Producto create(Producto producto) {
        String sql = "INSERT INTO Producto (nombre, descripcion, precio, stock, estado, id_categoria) " +
                "VALUES (:nombre, :descripcion, :precio, :stock, :estado, :idCategoria) " +
                "RETURNING id_producto";

        try (Connection con = sql2o.open()) {
            Integer id = con.createQuery(sql, true)
                    .addParameter("nombre", producto.getNombre())
                    .addParameter("descripcion", producto.getDescripcion())
                    .addParameter("precio", producto.getPrecio())
                    .addParameter("stock", producto.getStock())
                    .addParameter("estado", producto.getEstado())
                    .addParameter("idCategoria", producto.getIdCategoria())
                    .executeUpdate()
                    .getKey(Integer.class);

            producto.setIdProducto(Long.valueOf(id));
            return producto;
        }
        catch (Exception e) {
            System.out.println("Error al crear el producto: " + e.getMessage());
            return null;
        }        
    }

    @Override
    public List<Producto> getAll() {
        String sql = "SELECT id_producto AS idProducto, nombre, descripcion, precio, stock, estado, id_categoria AS idCategoria FROM Producto";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Producto.class);
        }
        catch (Exception e) {
            System.out.println("Error al consultar los productos: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Producto getProductoId(int id){
        String sql = "SELECT id_producto AS idProducto, nombre, descripcion, precio, stock, estado, id_categoria AS idCategoria FROM Producto WHERE id_producto = :id";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).addParameter("id",id).executeAndFetchFirst(Producto.class);
        }
        catch (Exception e) {
            System.out.println("Error al consultar el producto: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Producto update(Producto producto, int id){
        String sql = "UPDATE Producto SET Precio = :Precio, Stock = :Stock, Estado = :Estado WHERE id_producto = :id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql) 
                    .addParameter("id", id)
                    .addParameter("Precio", producto.getPrecio())
                    .addParameter("Stock", producto.getStock())
                    .addParameter("Estado", producto.getEstado())
                    .executeUpdate(); 
            return producto; 
        }
        catch (Exception e) {
            System.out.println("Error al actualizar el producto: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(int id){
        String sql = "DELETE FROM Producto WHERE id_producto = :id";

        try (Connection con = sql2o.open()){
            con.createQuery(sql).addParameter("id",id).executeUpdate();
        }
        catch (Exception e) {
            System.out.println("Error al eliminar el producto: " + e.getMessage());
        }
    }

    @Override
    public List<Producto> findByCategory(int categoria) {
        String sql = "SELECT id_producto AS idProducto, nombre, descripcion, precio, stock, estado, id_categoria AS idCategoria FROM Producto WHERE id_categoria = :categoria";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("categoria", categoria)
                    .executeAndFetch(Producto.class); // Devuelve una lista de productos
        }
    }
}