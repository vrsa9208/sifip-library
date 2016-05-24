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
import mx.com.vrsa9208.sifiplibrary.dao.MovimientoDao;
import mx.com.vrsa9208.sifiplibrary.dao.SifipDB;
import mx.com.vrsa9208.sifiplibrary.model.Movimiento;
import mx.com.vrsa9208.sifiplibrary.util.DateHelper;
import mx.com.vrsa9208.sifiplibrary.util.Log;

/**
 *
 * @author vrsa9208
 */
public class MovimientoDaoJDBC extends SifipDB implements MovimientoDao{

    @Override
    public Movimiento add(Movimiento movimiento) {
        String query = "INSERT INTO Movimiento(descripcion, fecha_creacion, cantidad, id_tipo_movimiento, id_categoria) " +
                        "VALUES(?, ?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
           connection = dataSource.getConnection();
           preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
           preparedStatement.setString(1, movimiento.getDescripcion());
           preparedStatement.setString(2, DateHelper.getStringDateNow());
           preparedStatement.setDouble(3, movimiento.getCantidad());
           preparedStatement.setInt(4, movimiento.getIdTipoMovimiento());
           preparedStatement.setInt(5, movimiento.getIdCategoria());
           
           preparedStatement.execute();
           resultSet = preparedStatement.getGeneratedKeys();
           resultSet.next();
           movimiento.setId(resultSet.getInt(1));
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try{ if(resultSet != null) resultSet.close();} catch(Exception ex){}
            try{ if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try{ if(connection != null) connection.close();} catch(Exception ex){}
        }
        
        return movimiento;
    }

    @Override
    public Movimiento update(Movimiento movimiento) {
        String query = "UPDATE Movimiento " +
                        "SET descripcion = ?, " +
                        "cantidad = ?, " +
                        "id_tipo_movimiento = ?, " +
                        "id_categoria = ? " +
                        "WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean operacionExitosa = false;
        
        try{
            connection = super.dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, movimiento.getDescripcion());
            preparedStatement.setDouble(2, movimiento.getCantidad());
            preparedStatement.setInt(3, movimiento.getIdTipoMovimiento());
            preparedStatement.setInt(4, movimiento.getIdCategoria());
            preparedStatement.setInt(5, movimiento.getId());
            if(preparedStatement.executeUpdate() == 1) operacionExitosa = true;
            
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try { if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try { if(connection != null) connection.close();} catch(Exception ex){}
        }
        
        return operacionExitosa ? movimiento : null;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM Movimiento WHERE id = ?";
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
    public List<Movimiento> get() {
        ArrayList<Movimiento> lista = new ArrayList<Movimiento>();
        String query = "SELECT * FROM Movimiento ORDER BY id";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Movimiento temporal = this.parseMovimiento(resultSet);
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
    public Movimiento getById(int id) {
        String query = "SELECT * FROM Movimiento WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Movimiento movimiento = null;
        
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                movimiento = this.parseMovimiento(resultSet);
            }
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try{ if(resultSet != null) resultSet.close();} catch(Exception ex){}
            try{ if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try{ if(connection != null) connection.close();} catch(Exception ex){}
        }
        return movimiento;
    }
    
    private Movimiento parseMovimiento(ResultSet resultSet) throws SQLException{
        Movimiento movimiento = new Movimiento();
        movimiento.setId(resultSet.getInt("id"));
        movimiento.setDescripcion(resultSet.getString("descripcion"));
        movimiento.setFechaCreacion(DateHelper.dateToGregorianCalendar(resultSet.getDate("fecha_creacion")));
        movimiento.setCantidad(resultSet.getFloat("cantidad"));
        movimiento.setIdTipoMovimiento(resultSet.getInt("id_tipo_movimiento"));
        movimiento.setIdCategoria(resultSet.getInt("id_categoria"));
        
        return movimiento;
    }
}
