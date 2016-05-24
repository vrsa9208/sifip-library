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
import mx.com.vrsa9208.sifiplibrary.dao.TipoMovimientoDao;
import mx.com.vrsa9208.sifiplibrary.model.TipoMovimiento;
import mx.com.vrsa9208.sifiplibrary.util.Log;

/**
 *
 * @author vrsa9208
 */
public class TipoMovimientoDaoJDBC extends SifipDB implements TipoMovimientoDao{

    @Override
    public TipoMovimiento add(TipoMovimiento tipoMovimiento) {
        String query = "INSERT INTO Tipo_Movimiento(descripcion) " +
                        "VALUES(?);";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
           connection = dataSource.getConnection();
           preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
           preparedStatement.setString(1, tipoMovimiento.getDescripcion());
           preparedStatement.execute();
           resultSet = preparedStatement.getGeneratedKeys();
           resultSet.next();
           tipoMovimiento.setId(resultSet.getInt(1));
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try{ if(resultSet != null) resultSet.close();} catch(Exception ex){}
            try{ if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try{ if(connection != null) connection.close();} catch(Exception ex){}
        }
        
        return tipoMovimiento;
    }

    @Override
    public TipoMovimiento update(TipoMovimiento tipoMovimiento) {
        String query = "UPDATE Tipo_Movimiento " +
                        "SET descripcion = ?, " +
                        "activo = ?" +
                        "WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean operacionExitosa = false;
        
        try{
            connection = super.dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tipoMovimiento.getDescripcion());
            preparedStatement.setBoolean(2, tipoMovimiento.isActivo());
            preparedStatement.setInt(3, tipoMovimiento.getId());
            if(preparedStatement.executeUpdate() == 1) operacionExitosa = true;
            
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try { if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try { if(connection != null) connection.close();} catch(Exception ex){}
        }
        
        return operacionExitosa ? tipoMovimiento : null;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM Tipo_Movimiento WHERE id = ?";
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
    public List<TipoMovimiento> get() {
        ArrayList<TipoMovimiento> lista = new ArrayList<TipoMovimiento>();
        String query = "SELECT * FROM Tipo_Movimiento ORDER BY id";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                TipoMovimiento temporal = this.parseTipoMovimiento(resultSet);
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
    public List<TipoMovimiento> getByActivo(boolean activo) {
        ArrayList<TipoMovimiento> lista = new ArrayList<TipoMovimiento>();
        String query = "SELECT * FROM Tipo_Movimiento " +
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
                TipoMovimiento temporal = this.parseTipoMovimiento(resultSet);
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
    public TipoMovimiento getById(int id) {
        String query = "SELECT * FROM Tipo_Movimiento WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        TipoMovimiento tipoMovimiento = null;
        
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                tipoMovimiento = this.parseTipoMovimiento(resultSet);
            }
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try{ if(resultSet != null) resultSet.close();} catch(Exception ex){}
            try{ if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try{ if(connection != null) connection.close();} catch(Exception ex){}
        }
        return tipoMovimiento;
    }
    
    private TipoMovimiento parseTipoMovimiento(ResultSet resultSet) throws SQLException{
        TipoMovimiento tipoMovimiento = new TipoMovimiento();
        tipoMovimiento.setId(resultSet.getInt("id"));
        tipoMovimiento.setDescripcion(resultSet.getString("descripcion"));
        tipoMovimiento.setActivo(resultSet.getBoolean("activo"));
        
        return tipoMovimiento;
    }
    
}
