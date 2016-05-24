/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.vrsa9208.sifiplibrary.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.com.vrsa9208.sifiplibrary.dao.CategoriaDao;
import mx.com.vrsa9208.sifiplibrary.dao.SifipDB;
import mx.com.vrsa9208.sifiplibrary.model.Categoria;
import mx.com.vrsa9208.sifiplibrary.util.Log;

/**
 *
 * @author vrsa9208
 */
public class CategoriaDaoJDBC extends SifipDB implements CategoriaDao{
    
    private static CategoriaDaoJDBC instance;
    
    private CategoriaDaoJDBC(){}
    
    public static CategoriaDaoJDBC getInstance(){
        if(instance == null) instance = new CategoriaDaoJDBC();
        return instance;
    }

    @Override
    public Categoria add(Categoria categoria) {
        String query = "INSERT INTO Categoria(descripcion) " +
                        "VALUES(?);";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
           connection = dataSource.getConnection();
           preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
           preparedStatement.setString(1, categoria.getDescripcion());
           preparedStatement.execute();
           resultSet = preparedStatement.getGeneratedKeys();
           resultSet.next();
           categoria.setId(resultSet.getInt(1));
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try{ if(resultSet != null) resultSet.close();} catch(Exception ex){}
            try{ if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try{ if(connection != null) connection.close();} catch(Exception ex){}
        }
        
        return categoria;
    }

    @Override
    public Categoria update(Categoria categoria) {
        String query = "UPDATE Categoria " +
                        "SET descripcion = ?, " +
                        "activo = ? " +
                        "WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean operacionExitosa = false;
        
        try{
            connection = super.dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, categoria.getDescripcion());
            preparedStatement.setBoolean(2, categoria.isActivo());
            preparedStatement.setInt(3, categoria.getId());
            if(preparedStatement.executeUpdate() == 1) operacionExitosa = true;
            
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try { if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try { if(connection != null) connection.close();} catch(Exception ex){}
        }
        
        return operacionExitosa ? categoria : null;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM Categoria WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean ret = false;
        
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ret = preparedStatement.executeUpdate() == 1;
            
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return false;
        } finally{
            try { if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try { if(connection != null) connection.close();} catch(Exception ex){}
        }
        
        return ret;
    }

    @Override
    public List<Categoria> get() {
        ArrayList<Categoria> lista = new ArrayList<Categoria>();
        String query = "SELECT * FROM Categoria ORDER BY id";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Categoria temporal = this.parseCategoria(resultSet);
                lista.add(temporal);
            }
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try{ if(resultSet != null)resultSet.close();} catch(Exception ex){}
            try{ if(preparedStatement != null)preparedStatement.close();} catch(Exception ex){}
            try{ if(connection != null)connection.close();} catch(Exception ex){}
        }
        return lista;
    }

    @Override
    public List<Categoria> getByActivo(boolean activo) {
        ArrayList<Categoria> lista = new ArrayList<Categoria>();
        String query = "SELECT * FROM Categoria " +
                        "WHERE activo = ? ORDER BY id";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, activo);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Categoria temporal = this.parseCategoria(resultSet);
                lista.add(temporal);
            }
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try{ if(resultSet != null)resultSet.close();} catch(Exception ex){}
            try{ if(preparedStatement != null)preparedStatement.close();} catch(Exception ex){}
            try{ if(connection != null)connection.close();} catch(Exception ex){}
        }
        return lista;
    }

    @Override
    public Categoria getById(int id) {
        String query = "SELECT * FROM Categoria WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Categoria categoria = null;
        
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                categoria = this.parseCategoria(resultSet);
            }
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try{ if(resultSet != null) resultSet.close();} catch(Exception ex){}
            try{ if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try{ if(connection != null) connection.close();} catch(Exception ex){}
        }
        return categoria;
    }
    
    private Categoria parseCategoria(ResultSet resultSet) throws SQLException{
        Categoria categoria = new Categoria();
        categoria.setId(resultSet.getInt("id"));
        categoria.setDescripcion(resultSet.getString("descripcion"));
        categoria.setActivo(resultSet.getBoolean("activo"));
        
        return categoria;
    }
    
}
