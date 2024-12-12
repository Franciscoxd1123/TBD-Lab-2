package com.example.ecommerce.repositories;

import com.example.ecommerce.models.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.List;

@Repository
public class CategoriaRepositoryImp implements CategoriaRepository{

    @Autowired
    private Sql2o sql2o;

    @Override
    public Categoria create(Categoria categoria){
        String sql = "INSERT INTO Categoria (nombre) " + "VALUES (:nombre) " + "RETURNING id_categoria";

        try (Connection con = sql2o.open()) {
            Integer id = con.createQuery(sql, true)
                    .addParameter("nombre", categoria.getNombre())
                    .executeUpdate()
                    .getKey(Integer.class);

            categoria.setIdCategoria(Long.valueOf(id));
            return categoria;
        }

        catch (Exception e) {
            System.out.println("Error al crear la categoria: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Categoria> getAll() {
        String sql = "SELECT id_categoria AS idCategoria, nombre FROM Categoria";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Categoria.class);
        }
        catch (Exception e) {
            System.out.println("Error al consultar las categorias: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Categoria getCategoriaId(int id){
        String sql = "SELECT id_categoria AS idCategoria, nombre FROM Categoria WHERE id_categoria = :id";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).addParameter("id",id).executeAndFetchFirst(Categoria.class);
        }
        catch (Exception e) {
            System.out.println("Error al consultar la categoria: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Categoria update(Categoria categoria, int id){
        String sql = "UPDATE Categoria SET Nombre = :Nombre WHERE id_categoria = :id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("Nombre", categoria.getNombre())
                    .executeUpdate();
            return categoria;
        }
        catch (Exception e) {
            System.out.println("Error al actualizar la categoria: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(int id){
        String sql = "DELETE FROM Categoria WHERE id_categoria = :id";

        try (Connection con = sql2o.open()){
            con.createQuery(sql).addParameter("id",id).executeUpdate();
        }
        catch (Exception e) {
            System.out.println("Error al eliminar la categoria: " + e.getMessage());
        }
    }
}
