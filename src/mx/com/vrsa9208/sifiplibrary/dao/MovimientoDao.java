/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.vrsa9208.sifiplibrary.dao;

import java.util.List;
import mx.com.vrsa9208.sifiplibrary.model.Movimiento;

/**
 *
 * @author vrsa9208
 */
public interface MovimientoDao {
    
    Movimiento add(Movimiento movimiento);
    Movimiento update(Movimiento movimiento);
    boolean delete(int id);
    List<Movimiento> get();
    Movimiento getById(int id);
    
}
