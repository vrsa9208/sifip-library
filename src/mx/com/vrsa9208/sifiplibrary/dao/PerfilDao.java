/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.vrsa9208.sifiplibrary.dao;

import java.util.List;
import mx.com.vrsa9208.sifiplibrary.model.Perfil;

/**
 *
 * @author vrsa9208
 */
public interface PerfilDao {
    Perfil add(Perfil perfil);
    Perfil update(Perfil perfil);
    boolean delete(int id);
    List<Perfil> get();
    Perfil getById(int id);
}
