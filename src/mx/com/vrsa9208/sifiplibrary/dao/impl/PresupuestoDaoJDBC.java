/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.vrsa9208.sifiplibrary.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.com.vrsa9208.sifiplibrary.dao.PresupuestoDao;
import mx.com.vrsa9208.sifiplibrary.dao.SifipDB;
import mx.com.vrsa9208.sifiplibrary.model.Categoria;
import mx.com.vrsa9208.sifiplibrary.model.Presupuesto;
import mx.com.vrsa9208.sifiplibrary.util.DateHelper;
import mx.com.vrsa9208.sifiplibrary.util.Log;

/**
 *
 * @author vrsa9208
 */
public class PresupuestoDaoJDBC extends SifipDB implements PresupuestoDao{
    
    private static PresupuestoDaoJDBC instance;
    
    private PresupuestoDaoJDBC(){}
    
    public static PresupuestoDaoJDBC getInstance(){
        if(instance == null) instance = new PresupuestoDaoJDBC();
        return instance;
    }

    @Override
    public Presupuesto add(Presupuesto presupuesto) {
        String query = "INSERT INTO Presupuesto(descripcion, fecha_inicio, fecha_fin, id_usuario) " +
                        "VALUES(?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
           connection = dataSource.getConnection();
           preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
           preparedStatement.setString(1, presupuesto.getDescripcion());
           preparedStatement.setString(2, DateHelper.CalendarToString(presupuesto.getFechaInicio()));
           preparedStatement.setString(3, DateHelper.CalendarToString(presupuesto.getFechaFin()));
           preparedStatement.setInt(4, presupuesto.getIdUsuario());
           preparedStatement.execute();
           resultSet = preparedStatement.getGeneratedKeys();
           resultSet.next();
           presupuesto.setId(resultSet.getInt(1));
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try{ if(resultSet != null) resultSet.close();} catch(Exception ex){}
            try{ if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try{ if(connection != null) connection.close();} catch(Exception ex){}
        }
        
        return presupuesto;
    }

    @Override
    public Presupuesto update(Presupuesto presupuesto) {
        String query = "UPDATE Presupuesto " +
                        "SET descripcion = ?, " +
                        "fecha_inicio = ?, " +
                        "fecha_fin = ? " +
                        "WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean operacionExitosa = false;
        
        try{
            connection = super.dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, presupuesto.getDescripcion());
            preparedStatement.setString(2, DateHelper.CalendarToString(presupuesto.getFechaInicio()));
            preparedStatement.setString(3, DateHelper.CalendarToString(presupuesto.getFechaFin()));
            preparedStatement.setInt(4, presupuesto.getId());
            if(preparedStatement.executeUpdate() == 1) operacionExitosa = true;
            
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try { if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try { if(connection != null) connection.close();} catch(Exception ex){}
        }
        
        return operacionExitosa ? presupuesto : null;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM Presupuesto WHERE id = ?";
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
    public List<Presupuesto> get() {
        ArrayList<Presupuesto> lista = new ArrayList<Presupuesto>();
        String query = "SELECT * FROM Presupuesto ORDER BY id";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Presupuesto temporal = this.parsePresupuesto(resultSet);
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
    
    private Presupuesto parsePresupuesto(ResultSet resultSet) throws SQLException{
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setId(resultSet.getInt("id"));
        presupuesto.setDescripcion(resultSet.getString("descripcion"));
        presupuesto.setIdUsuario(resultSet.getInt("id_usuario"));
        presupuesto.setFechaInicio(DateHelper.dateToGregorianCalendar(resultSet.getDate("fecha_inicio")));
        presupuesto.setFechaFin(DateHelper.dateToGregorianCalendar(resultSet.getDate("fecha_fin")));
        return presupuesto;
    }

    @Override
    public Presupuesto getById(int id) {
        String query = "SELECT * FROM Presupuesto WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Presupuesto presupuesto = null;
        
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                presupuesto = this.parsePresupuesto(resultSet);
            }
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try{ if(resultSet != null) resultSet.close();} catch(Exception ex){}
            try{ if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try{ if(connection != null) connection.close();} catch(Exception ex){}
        }
        return presupuesto;
    }

    @Override
    public List<Presupuesto> getByIdUsuario(int id) {
        ArrayList<Presupuesto> lista = new ArrayList<Presupuesto>();
        String query = "SELECT * FROM Presupuesto WHERE id_usuario = ? ORDER BY id";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Presupuesto temporal = this.parsePresupuesto(resultSet);
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
}
