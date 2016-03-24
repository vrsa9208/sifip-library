/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.vrsa9208.sifiplibrary.test;

import mx.com.vrsa9208.sifiplibrary.dao.UsuarioDao;
import mx.com.vrsa9208.sifiplibrary.dao.impl.UsuarioDaoJDBC;
import mx.com.vrsa9208.sifiplibrary.model.Usuario;
import mx.com.vrsa9208.sifiplibrary.util.DateHelper;

/**
 *
 * @author Administrador
 */
public class UsuarioTest {
    
    private UsuarioDao dao;

    public UsuarioTest() {
        this.dao = UsuarioDaoJDBC.getInstance();
    }
    
    public static void main(String[] args) {
        System.out.println("Usuario Test");
        UsuarioTest test = new UsuarioTest();
        
        //test.add();
        //test.getById(6);
        test.delete(1);
    }
    
    public void add(){
        Usuario usuario = new Usuario();
        usuario.setNombre("Omar");
        usuario.setPrimerApellido("Santiago");
        usuario.setSegundoApellido("Sánchez");
        usuario.setEmail("olss@gmail.com");
        usuario.setPassword("12345");
        this.dao.add(usuario);
    }
    
    public void getById(int id){
        Usuario usuario = this.dao.getById(id);
        if(usuario != null){
            System.out.println(usuario.getId());
            System.out.println(usuario.getNombre());
            System.out.println(usuario.getPrimerApellido());
            System.out.println(usuario.getSegundoApellido());
            System.out.println(usuario.getEmail());
            System.out.println(DateHelper.CalendarToString(usuario.getFechaCreacion()));
            System.out.println(usuario.isActivo());
        }
        else{
            System.out.println("No hay usuario con el id " + id);
        }
    }
    
    public void delete(int id){
        System.out.println(this.dao.delete(id));
    }
}
