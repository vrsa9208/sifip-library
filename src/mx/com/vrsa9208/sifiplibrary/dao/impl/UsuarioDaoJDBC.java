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
import mx.com.vrsa9208.sifiplibrary.dao.UsuarioDao;
import mx.com.vrsa9208.sifiplibrary.model.Usuario;
import mx.com.vrsa9208.sifiplibrary.util.DateHelper;
import mx.com.vrsa9208.sifiplibrary.util.Log;

/**
 *
 * @author Administrador
 */
public class UsuarioDaoJDBC extends SifipDB implements UsuarioDao{
    
    private static UsuarioDaoJDBC instance;
    
    private UsuarioDaoJDBC(){ }
    
    public static UsuarioDaoJDBC getInstance(){
        if(instance == null) instance = new UsuarioDaoJDBC();
        return instance;
    }

    @Override
    public Usuario add(Usuario usuario) {
        String query = "INSERT INTO Usuario(nombre, primer_apellido, segundo_apellido, " +
                        "email, password, fecha_creacion) " +
                        "VALUES(?, ?, ?, ?, SHA(?), ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
           connection = dataSource.getConnection();
           preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
           preparedStatement.setString(1, usuario.getNombre());
           preparedStatement.setString(2, usuario.getPrimerApellido());
           preparedStatement.setString(3, usuario.getSegundoApellido());
           preparedStatement.setString(4, usuario.getEmail());
           preparedStatement.setString(5, usuario.getPassword());
           preparedStatement.setString(6, DateHelper.getStringDateNow());
           preparedStatement.execute();
           resultSet = preparedStatement.getGeneratedKeys();
           resultSet.next();
           usuario.setId(resultSet.getInt(1));
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try{ if(resultSet != null) resultSet.close();} catch(Exception ex){}
            try{ if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try{ if(connection != null) connection.close();} catch(Exception ex){}
        }
        
        return usuario;
    }

    @Override
    public Usuario update(Usuario usuario) {
        String query = "UPDATE Usuario " +
                        "SET nombre = ?, " +
                        "primer_apellido = ?, " +
                        "segundo_apellido = ?, " +
                        "email = ?, " +
                        "activo = ? " +
                        "WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean operacionExitosa = false;
        
        try{
            connection = super.dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getPrimerApellido());
            preparedStatement.setString(3, usuario.getSegundoApellido());
            preparedStatement.setString(4, usuario.getEmail());
            preparedStatement.setBoolean(5, usuario.isActivo());
            preparedStatement.setInt(6, usuario.getId());
            if(preparedStatement.executeUpdate() == 1) operacionExitosa = true;
            
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try { if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try { if(connection != null) connection.close();} catch(Exception ex){}
        }
        
        return operacionExitosa ? usuario : null;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM Usuario WHERE id = ?";
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
    public List<Usuario> get() {
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        String query = "SELECT * FROM Usuario ORDER BY id";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Usuario temporal = this.parseUsuario(resultSet);
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
    public Usuario getById(int id) {
        String query = "SELECT * FROM Usuario WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Usuario usuario = null;
        
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                usuario = this.parseUsuario(resultSet);
            }
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try{ if(resultSet != null) resultSet.close();} catch(Exception ex){}
            try{ if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try{ if(connection != null) connection.close();} catch(Exception ex){}
        }
        return usuario;
    }
    
    private Usuario parseUsuario(ResultSet resultSet) throws SQLException{
        Usuario usuario = new Usuario();
        usuario.setId(resultSet.getInt("id"));
        usuario.setNombre(resultSet.getString("nombre"));
        usuario.setPrimerApellido(resultSet.getString("primer_apellido"));
        usuario.setSegundoApellido(resultSet.getString("segundo_apellido"));
        usuario.setEmail(resultSet.getString("email"));
        usuario.setPassword(null);
        usuario.setFechaCreacion(DateHelper.dateToGregorianCalendar(resultSet.getDate("fecha_creacion")));
        usuario.setActivo(resultSet.getBoolean("activo"));
        return usuario;
    }

    @Override
    public boolean updatePassword(int id, String password) {
        String query = "UPDATE Usuario " +
                        "SET password = SHA(?) " +
                        "WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean operacionExitosa = false;
        
        try{
            connection = super.dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, id);
            if(preparedStatement.executeUpdate() == 1) operacionExitosa = true;
            
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return false;
        } finally{
            try { if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try { if(connection != null) connection.close();} catch(Exception ex){}
        }
        
        return operacionExitosa;
    }

    @Override
    public Usuario getByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM Usuario " +
                        "WHERE email = ? " +
                        "AND password = SHA(?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Usuario usuario = null;
        
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                usuario = this.parseUsuario(resultSet);
            }
        } catch(SQLException ex){
            Log.error(ex.getMessage());
            return null;
        } finally{
            try{ if(resultSet != null) resultSet.close();} catch(Exception ex){}
            try{ if(preparedStatement != null) preparedStatement.close();} catch(Exception ex){}
            try{ if(connection != null) connection.close();} catch(Exception ex){}
        }
        return usuario;
    }
    
}
