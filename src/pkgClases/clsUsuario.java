/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgClases;

/**
 *
 * @author Milton R. Montes
 */
public class clsUsuario {
    private String id;
    private String nombre;
    private int juegos_ganados;
    private int juegos_empatados;
    private int juegos_perdidos;

    public clsUsuario() {
    }

    public clsUsuario(String id, String nombre, int juegos_ganados, int juegos_empatados, int juegos_perdidos) {
        this.id = id;
        this.nombre = nombre;
        this.juegos_ganados = juegos_ganados;
        this.juegos_empatados = juegos_empatados;
        this.juegos_perdidos = juegos_perdidos;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the juegos_ganados
     */
    public int getJuegos_ganados() {
        return juegos_ganados;
    }

    /**
     * @param juegos_ganados the juegos_ganados to set
     */
    public void setJuegos_ganados(int juegos_ganados) {
        this.juegos_ganados = juegos_ganados;
    }

    /**
     * @return the juegos_empatados
     */
    public int getJuegos_empatados() {
        return juegos_empatados;
    }

    /**
     * @param juegos_empatados the juegos_empatados to set
     */
    public void setJuegos_empatados(int juegos_empatados) {
        this.juegos_empatados = juegos_empatados;
    }

    /**
     * @return the juegos_perdidos
     */
    public int getJuegos_perdidos() {
        return juegos_perdidos;
    }

    /**
     * @param juegos_perdidos the juegos_perdidos to set
     */
    public void setJuegos_perdidos(int juegos_perdidos) {
        this.juegos_perdidos = juegos_perdidos;
    }
    
}
