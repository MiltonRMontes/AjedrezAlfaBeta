/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgClases;

import jade.util.leap.Serializable;
import pkgLogica.clsMovimientos;

/**
 *
 * @author Milton R. Montes
 */
public class clsObjetoMensaje implements Serializable{

    private clsMovimientos movimientos;
    private String movimiento;

    public clsObjetoMensaje() {
    }

    public clsObjetoMensaje(clsMovimientos movimientos, String movimiento) {
        this.movimientos = movimientos;
        this.movimiento = movimiento;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public clsMovimientos getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(clsMovimientos movimientos) {
        this.movimientos = movimientos;
    }
}
