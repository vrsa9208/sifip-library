/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.vrsa9208.sifiplibrary.test;

import java.util.GregorianCalendar;
import mx.com.vrsa9208.sifiplibrary.dao.UsuarioDao;
import mx.com.vrsa9208.sifiplibrary.dao.impl.UsuarioDaoJDBC;
import mx.com.vrsa9208.sifiplibrary.model.Usuario;


/**
 *
 * @author Administrador
 */
public class Test {
    
    public static void main(String[] args) {
        System.out.println("Pruebas de la libreria");
        
        Usuario usuario = new Usuario();
        usuario.setNombre("Víctor Orlando");
        usuario.setPrimerApellido("Santiago");
        usuario.setSegundoApellido("Sánchez");
        usuario.setPassword("12345");
        usuario.setFechaCreacion(new GregorianCalendar());
        usuario.setActivo(true);
        usuario.setId_perfil(2);
        
        UsuarioDao dao = UsuarioDaoJDBC.getInstance();
        dao.add(usuario);
        
    }
}
