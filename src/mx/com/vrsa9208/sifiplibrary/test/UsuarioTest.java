/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.vrsa9208.sifiplibrary.test;

import mx.com.vrsa9208.sifiplibrary.dao.UsuarioDao;
import mx.com.vrsa9208.sifiplibrary.dao.impl.UsuarioDaoJDBC;

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
        
        test.add();
    }
    
    public void add(){
        this.dao.add(null);
    }
}
