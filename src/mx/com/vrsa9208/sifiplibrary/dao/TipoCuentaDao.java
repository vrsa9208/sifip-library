/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.vrsa9208.sifiplibrary.dao;

import java.util.List;
import mx.com.vrsa9208.sifiplibrary.model.TipoCuenta;

/**
 *
 * @author Administrador
 */
public interface TipoCuentaDao {
    
    TipoCuenta add(TipoCuenta tipoCuenta);
    TipoCuenta update(TipoCuenta tipoCuenta);
    boolean delete(int id);
    List<TipoCuenta> get();
    TipoCuenta getById(int id);
    List<TipoCuenta> getByActivo(boolean activo);
    
}
