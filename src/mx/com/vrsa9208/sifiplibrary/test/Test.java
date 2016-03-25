/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.vrsa9208.sifiplibrary.test;

import java.util.List;
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
        UsuarioDao dao = UsuarioDaoJDBC.getInstance();
        Usuario nuevo = new Usuario();
        nuevo.setNombre("Víctor Orlando");
        nuevo.setPrimerApellido("Santiago");
        nuevo.setSegundoApellido("Sánchez");
        nuevo.setEmail("vrsa9208@gmail.com");
        nuevo.setPassword("12345");
        dao.add(nuevo);
        List<Usuario> lista = dao.get();
    }
}
