/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.vrsa9208.sifiplibrary.dao;

import java.util.List;
import mx.com.vrsa9208.sifiplibrary.model.Categoria;

/**
 *
 * @author vrsa9208
 */
public interface CategoriaDao {
    
    Categoria add(Categoria categoria);
    Categoria update(Categoria categoria);
    boolean delete(int id);
    List<Categoria> get();
    List<Categoria> getByActivo(boolean activo);
    Categoria getById(int id);
}
