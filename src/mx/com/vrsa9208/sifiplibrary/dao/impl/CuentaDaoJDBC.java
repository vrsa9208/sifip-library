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
import mx.com.vrsa9208.sifiplibrary.dao.CuentaDao;
import mx.com.vrsa9208.sifiplibrary.dao.SifipDB;
import mx.com.vrsa9208.sifiplibrary.model.Cuenta;
import mx.com.vrsa9208.sifiplibrary.util.DateHelper;
import mx.com.vrsa9208.sifiplibrary.util.Log;

/**
 *
 * @author vrsa9208
 */
public class CuentaDaoJDBC extends SifipDB implements CuentaDao{

    @Override
    public Cuenta add(Cuenta cuenta) {
        String query = "INSERT INTO Cuenta(descripcion, fecha_creacion, id_usuario, id_tipo_cuenta) " +
                        "VALUES(?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
           connection = dataSource.getConnection();
           preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
           preparedStatement.setString(1, cuenta.getDescripcion());
           preparedStatement.setString(2, DateHelper.getStringDateNow());
           preparedStatement.setInt(3, cuenta.getIdUsuario());
           preparedStatement.setInt(4, cuenta.getIdTipoCuenta());
           preparedStatement.execute();
           resultSet = preparedStatement.getGeneratedKeys();
           resultSet.next();
           cuenta.setId(resultSet.getInt(1));
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try{ if(resultSet != null) resultSet.close();} catch(Exception ex){}
            try{ if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try{ if(connection != null) connection.close();} catch(Exception ex){}
        }
        
        return cuenta;
    }

    @Override
    public Cuenta update(Cuenta cuenta) {
        String query = "UPDATE Cuenta " +
                        "SET descripcion = ?, " +
                        "activo = ?, " +
                        "id_tipo_cuenta = ? " +
                        "WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean operacionExitosa = false;
        
        try{
            connection = super.dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cuenta.getDescripcion());
            preparedStatement.setBoolean(2, cuenta.isActivo());
            preparedStatement.setInt(3, cuenta.getIdTipoCuenta());
            preparedStatement.setInt(4, cuenta.getId());
            if(preparedStatement.executeUpdate() == 1) operacionExitosa = true;
            
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try { if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try { if(connection != null) connection.close();} catch(Exception ex){}
        }
        
        return operacionExitosa ? cuenta : null;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM Cuenta WHERE id = ?";
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
    public List<Cuenta> get() {
        ArrayList<Cuenta> lista = new ArrayList<Cuenta>();
        String query = "SELECT * FROM Cuenta ORDER BY id";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Cuenta temporal = this.parseCuenta(resultSet);
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
    public Cuenta getById(int id) {
        String query = "SELECT * FROM Cuenta WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Cuenta cuenta = null;
        
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                cuenta = this.parseCuenta(resultSet);
            }
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try{ if(resultSet != null) resultSet.close();} catch(Exception ex){}
            try{ if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try{ if(connection != null) connection.close();} catch(Exception ex){}
        }
        return cuenta;
    }
    
    private Cuenta parseCuenta(ResultSet resultSet) throws SQLException{
        Cuenta cuenta = new Cuenta();
        cuenta.setId(resultSet.getInt("id"));
        cuenta.setDescripcion(resultSet.getString("descripcion"));
        cuenta.setActivo(resultSet.getBoolean("activo"));
        cuenta.setFechaCreacion(DateHelper.dateToGregorianCalendar(resultSet.getDate("fecha_creacion")));
        cuenta.setIdTipoCuenta(resultSet.getInt("id_tipo_cuenta"));
        cuenta.setIdUsuario(resultSet.getInt("id_usuario"));
        
        return cuenta;
    }
    
}
