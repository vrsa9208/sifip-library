/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.vrsa9208.sifiplibrary.dao;

import java.util.List;
import mx.com.vrsa9208.sifiplibrary.model.Cuenta;

/**
 *
 * @author vrsa9208
 */
public interface CuentaDao {
    
    Cuenta add(Cuenta cuenta);
    Cuenta update(Cuenta cuenta);
    boolean delete(int id);
    List<Cuenta> get();
    Cuenta getById(int id);
    
}
