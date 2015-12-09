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

    clsConecta conexion;

    public clsDAOUsuario() {
        conexion = new clsConecta();
    }

    public clsUsuario Consultar(String id) {
        clsUsuario usuario = new clsUsuario();
        StringBuffer sb = new StringBuffer();
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

    public boolean Actualizar(clsUsuario usuario) {
        String sql = "UPDATE \"tblUsuario\" SET juegos_ganados= '" + usuario.getJuegos_ganados() + "', juegos_empatados='" + usuario.getJuegos_empatados() + "',"
                + "juegos_perdidos='" + usuario.getJuegos_perdidos() + "' WHERE id = '" + usuario.getId() + "'";
        try {
            conexion.Procesar(sql);
            System.out.println("Modificó bien");
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar usuario");
            return false;
        }
    }

    public boolean Insertar(clsUsuario usuario) {
        String sql = "INSERT INTO \"tblUsuario\"(id,nombre,juegos_ganados, juegos_empatados, juegos_perdidos)"
                + " VALUES ('" + usuario.getId() + "', '" + usuario.getNombre() + "',"
                + usuario.getJuegos_ganados() + ", " + usuario.getJuegos_perdidos() + ", " + usuario.getJuegos_empatados() + ");";
        try {
            conexion.Procesar(sql);
            System.out.println("Insertó bien");
            return true;
        } catch (Exception e) {
            System.out.println("Error al crear usuario");
            return false;
        }
    }

}
