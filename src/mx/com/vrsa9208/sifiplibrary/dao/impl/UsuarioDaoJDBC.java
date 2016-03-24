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
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
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
        String query = "INSERT INTO usuario(nombre, primer_apellido, segundo_apellido, " +
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Usuario> get() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
