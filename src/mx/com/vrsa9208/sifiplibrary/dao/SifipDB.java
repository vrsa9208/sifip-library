/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.vrsa9208.sifiplibrary.dao;

import java.util.ResourceBundle;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author Administrador
 */
public class SifipDB {
    static{
        ResourceBundle resourceBundle = ResourceBundle.getBundle("properties.database");
        SifipDB.driverClassName = resourceBundle.getString("driverClassName");
        SifipDB.userName = resourceBundle.getString("userName");
        SifipDB.password = resourceBundle.getString("password");
        SifipDB.connectionUrl = resourceBundle.getString("connectionUrl");
        
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(SifipDB.driverClassName);
        basicDataSource.setUsername(SifipDB.userName);
        basicDataSource.setPassword(SifipDB.password);
        basicDataSource.setUrl(SifipDB.connectionUrl);
        SifipDB.dataSource = basicDataSource;
    }
    
    private static String driverClassName;
    private static String userName;
    private static String password;
    private static String connectionUrl;
    
    protected static DataSource dataSource;
}
