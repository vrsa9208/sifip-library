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
import mx.com.vrsa9208.sifiplibrary.dao.PerfilDao;
import mx.com.vrsa9208.sifiplibrary.dao.SifipDB;
import mx.com.vrsa9208.sifiplibrary.model.Perfil;
import mx.com.vrsa9208.sifiplibrary.util.Log;

/**
 *
 * @author vrsa9208
 */
public class PerfilDaoJDBC extends SifipDB implements PerfilDao{
    
    private static PerfilDaoJDBC instance;
    
    private PerfilDaoJDBC(){}
    
    public static PerfilDaoJDBC getInstance(){
        if(instance == null) instance = new PerfilDaoJDBC();
        return instance;
    }

    @Override
    public Perfil add(Perfil perfil) {
        String query = "INSERT INTO Perfil(descripcion) " +
                        "VALUES(?);";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
           connection = dataSource.getConnection();
           preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
           preparedStatement.setString(1, perfil.getDescripcion());
           preparedStatement.execute();
           resultSet = preparedStatement.getGeneratedKeys();
           resultSet.next();
           perfil.setId(resultSet.getInt(1));
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try{ if(resultSet != null) resultSet.close();} catch(Exception ex){}
            try{ if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try{ if(connection != null) connection.close();} catch(Exception ex){}
        }
        
        return perfil;
    }

    @Override
    public Perfil update(Perfil perfil) {
        String query = "UPDATE Perfil " +
                        "SET descripcion = ? " +
                        "WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean operacionExitosa = false;
        
        try{
            connection = super.dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, perfil.getDescripcion());
            preparedStatement.setInt(2, perfil.getId());
            if(preparedStatement.executeUpdate() == 1) operacionExitosa = true;
            
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try { if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try { if(connection != null) connection.close();} catch(Exception ex){}
        }
        
        return operacionExitosa ? perfil : null;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM Perfil WHERE id = ?";
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
    public List<Perfil> get() {
        ArrayList<Perfil> lista = new ArrayList<Perfil>();
        String query = "SELECT * FROM Perfil ORDER BY id";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Perfil temporal = this.parsePerfil(resultSet);
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
    public Perfil getById(int id) {
        String query = "SELECT * FROM Perfil WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Perfil perfil = null;
        
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                perfil = this.parsePerfil(resultSet);
            }
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try{ if(resultSet != null) resultSet.close();} catch(Exception ex){}
            try{ if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try{ if(connection != null) connection.close();} catch(Exception ex){}
        }
        return perfil;
    }
    
    private Perfil parsePerfil(ResultSet resultSet) throws SQLException{
        Perfil perfil = new Perfil();
        perfil.setId(resultSet.getInt("id"));
        perfil.setDescripcion(resultSet.getString("descripcion"));
        
        return perfil;
    }
}
