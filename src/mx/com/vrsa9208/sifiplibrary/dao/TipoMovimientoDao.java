/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.vrsa9208.sifiplibrary.dao;

import java.util.List;
import mx.com.vrsa9208.sifiplibrary.model.TipoMovimiento;

/**
 *
 * @author vrsa9208
 */
public interface TipoMovimientoDao {
    
    TipoMovimiento add(TipoMovimiento tipoMovimiento);
    TipoMovimiento update(TipoMovimiento tipoMovimiento);
    boolean delete(int id);
    List<TipoMovimiento> get();
    List<TipoMovimiento> getByActivo(boolean activo);
    TipoMovimiento getById(int id);
}
