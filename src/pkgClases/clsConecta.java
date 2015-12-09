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

    /**
     * Inicializa la conexión con la base de datos.
     */
    public clsConecta() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error cargando el Driver");
        } finally {
        }
        try {
            conexion = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/bdAjedrezAlfabeta", "postgres", "admin");
        } catch (SQLException ex) {
            System.out.println("Error conectandose con el motor");
        }
    }
    
    /**
     * Ejecuta la instrucciones SQL en la base de datos.
     * @param sql Variable que es la instrucción SQL que se recibe desde la clase DAO.
     * @return Devuelve la información traída desde la base de datos.
     */
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
