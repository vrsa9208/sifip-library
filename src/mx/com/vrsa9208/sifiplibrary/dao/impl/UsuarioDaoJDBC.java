/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.vrsa9208.sifiplibrary.dao.impl;

import java.util.List;
import mx.com.vrsa9208.sifiplibrary.dao.SifipDB;
import mx.com.vrsa9208.sifiplibrary.dao.UsuarioDao;
import mx.com.vrsa9208.sifiplibrary.model.Usuario;
import sun.security.jca.GetInstance;

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
        return null;
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
