/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgClases;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Milton R. Montes
 */
public class clsDAOUsuario {

    /**
     * Conexión a la base de datos.
     */
    clsConecta conexion;

    /**
     * Constructor en donde se instancia una nueva conexión a la base de datos.
     */
    public clsDAOUsuario() {
        conexion = new clsConecta();
    }

    /**
     * Método que devuelve un usuario con respecto al id recibido.
     *
     * @param id Variable que indica que usuario se va a buscar en la base de
     * datos.
     * @return Devuelve el Usuario encontrado.
     */
    public clsUsuario Consultar(String id) {
        clsUsuario usuario = new clsUsuario();
        String sql = "Select * from \"tblUsuario\" where id = '" + id + "'";
        try {
            java.sql.ResultSet rs = null;
            rs = conexion.Procesar(sql);
            if (rs.next()) {
                usuario.setId(rs.getString(1));
                usuario.setNombre(rs.getString(2));
                usuario.setJuegos_ganados(rs.getInt(3));
                usuario.setJuegos_empatados(rs.getInt(4));
                usuario.setJuegos_perdidos(rs.getInt(5));
            }
            return usuario;
        } catch (SQLException ex) {
            Logger.getLogger(clsDAOUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Actualiza la información en la base de datos de los juegos del usuario
     * que se recibe.
     *
     * @param usuario Variable que es el usuario que está jugando contra la
     * máquina.
     * @return Devuelve un booleano que indica si se ejecutó la instrucción SQL.
     */
    public boolean Actualizar(clsUsuario usuario) {
        String sql = "UPDATE \"tblUsuario\" SET juegos_ganados= '" + usuario.getJuegos_ganados() + "', juegos_empatados='" + usuario.getJuegos_empatados() + "',"
                + "juegos_perdidos='" + usuario.getJuegos_perdidos() + "' WHERE id = '" + usuario.getId() + "'";
        try {
            return conexion.Insertar(sql);
        } catch (Exception e) {
            System.out.println("Error al actualizar usuario");
            return false;
        }
    }

    /**
     * Inserta en la base de datos el usuario que se recibe.
     *
     * @param usuario Usuario que se va a insertar en la base de datos.
     * @return Devuelve un booleano que indica si se ejecutó la instrucción SQL.
     */
    public boolean Insertar(clsUsuario usuario) {
        String sql = "INSERT INTO \"tblUsuario\"(id,nombre,juegos_ganados, juegos_empatados, juegos_perdidos)"
                + " VALUES ('" + usuario.getId() + "', '" + usuario.getNombre() + "',"
                + usuario.getJuegos_ganados() + ", " + usuario.getJuegos_perdidos() + ", " + usuario.getJuegos_empatados() + ");";
        try {
            return conexion.Insertar(sql);
        } catch (Exception e) {
            System.out.println("Error al crear usuario");
            return false;
        }
    }

}
