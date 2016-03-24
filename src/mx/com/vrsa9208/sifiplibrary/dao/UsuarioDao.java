/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.vrsa9208.sifiplibrary.dao;

import java.util.List;
import mx.com.vrsa9208.sifiplibrary.model.Usuario;

/**
 *
 * @author Administrador
 */
public interface UsuarioDao {
    
    Usuario add(Usuario usuario);
    Usuario update(Usuario usuario);
    boolean delete(int id);
    List<Usuario> get();
    Usuario getById(int id);
    boolean updatePassword(int id, String password);
    Usuario getByEmailAndPassword(String email, String password);
    
}
