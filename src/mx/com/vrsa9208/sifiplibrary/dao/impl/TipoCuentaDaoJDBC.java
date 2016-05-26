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
import mx.com.vrsa9208.sifiplibrary.dao.SifipDB;
import mx.com.vrsa9208.sifiplibrary.dao.TipoCuentaDao;
import mx.com.vrsa9208.sifiplibrary.model.TipoCuenta;
import mx.com.vrsa9208.sifiplibrary.util.Log;

/**
 *
 * @author Administrador
 */
public class TipoCuentaDaoJDBC extends SifipDB implements TipoCuentaDao{
    
    private static TipoCuentaDaoJDBC instance;
    
    private TipoCuentaDaoJDBC(){}
    
    public static TipoCuentaDaoJDBC getInstance(){
        if(instance == null) instance = new TipoCuentaDaoJDBC();
        return instance;
    }

    @Override
    public TipoCuenta add(TipoCuenta tipoCuenta) {
        String query = "INSERT INTO Tipo_Cuenta(descripcion) " +
                        "VALUES(?);";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
           connection = dataSource.getConnection();
           preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
           preparedStatement.setString(1, tipoCuenta.getDescripcion());
           preparedStatement.execute();
           resultSet = preparedStatement.getGeneratedKeys();
           resultSet.next();
           tipoCuenta.setId(resultSet.getInt(1));
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try{ if(resultSet != null) resultSet.close();} catch(Exception ex){}
            try{ if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try{ if(connection != null) connection.close();} catch(Exception ex){}
        }
        
        return tipoCuenta;
    }

    @Override
    public TipoCuenta update(TipoCuenta tipoCuenta) {
        String query = "UPDATE Tipo_Cuenta " +
                        "SET descripcion = ?, " +
                        "activo = ? " +
                        "WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean operacionExitosa = false;
        
        try{
            connection = super.dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tipoCuenta.getDescripcion());
            preparedStatement.setBoolean(2, tipoCuenta.isActivo());
            preparedStatement.setInt(3, tipoCuenta.getId());
            if(preparedStatement.executeUpdate() == 1) operacionExitosa = true;
            
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try { if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try { if(connection != null) connection.close();} catch(Exception ex){}
        }
        
        return operacionExitosa ? tipoCuenta : null;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM Tipo_Cuenta WHERE id = ?";
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
    public List<TipoCuenta> get() {
        ArrayList<TipoCuenta> lista = new ArrayList<TipoCuenta>();
        String query = "SELECT * FROM Tipo_Cuenta ORDER BY id";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                TipoCuenta temporal = this.parseTipoCuenta(resultSet);
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
    public TipoCuenta getById(int id) {
        String query = "SELECT * FROM Tipo_Cuenta WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        TipoCuenta tipoCuenta = null;
        
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                tipoCuenta = this.parseTipoCuenta(resultSet);
            }
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try{ if(resultSet != null) resultSet.close();} catch(Exception ex){}
            try{ if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try{ if(connection != null) connection.close();} catch(Exception ex){}
        }
        return tipoCuenta;
    }

    @Override
    public List<TipoCuenta> getByActivo(boolean activo) {
        ArrayList<TipoCuenta> lista = new ArrayList<TipoCuenta>();
        String query = "SELECT * FROM Tipo_Cuenta " +
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
                TipoCuenta temporal = this.parseTipoCuenta(resultSet);
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
    
    private TipoCuenta parseTipoCuenta(ResultSet resultSet) throws SQLException{
        TipoCuenta tipoCuenta = new TipoCuenta();
        tipoCuenta.setId(resultSet.getInt("id"));
        tipoCuenta.setDescripcion(resultSet.getString("descripcion"));
        tipoCuenta.setActivo(resultSet.getBoolean("activo"));
        
        return tipoCuenta;
    }
    
}
