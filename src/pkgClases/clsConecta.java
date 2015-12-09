/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgClases;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Milton R. Montes
 */
public class clsConecta {
    
    java.sql.Connection conexion;
    java.sql.Statement statement;
    java.sql.ResultSet resulset;

    public clsConecta() {
        try {
            // Cargar el Driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error cargando el Driver");
        } finally {
            // Que hace despues de capturar un error
        }
        try {
            // Establer la conexión con el motor
            conexion = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/bdAjedrezAlfabeta", "postgres", "admin");
            System.out.println("Si conectó");
        } catch (SQLException ex) {
            System.out.println("Error conectandose con el motor");
        }
    }
    
     public java.sql.ResultSet Procesar(String sql) {
        try {
            statement = conexion.createStatement();
            resulset = statement.executeQuery(sql);
        } catch (SQLException e) {
            return null;
        }
        return resulset;
    }
    
    
}
