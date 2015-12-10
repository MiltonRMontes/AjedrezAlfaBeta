/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgAgentes;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import pkgLogica.clsMovimientos;
import pkgVista.pnlUserInterface;

/**
 *
 * @author Milton R. Montes
 */
public class clsAgenteGrafico extends GuiAgent {

    /**
     * Frm que crea el agente.
     */
    protected JFrame frm;
    /**
     * Agente gráfico.
     */
    protected GuiAgent agente;
    /**
     * Panel donde se gráfica el ajedrez.
     */
    protected pnlUserInterface ui;

    /**
     * Método que se ejecuta al iniciar el agente, donde se llama el método inicializar, 
     * y se añade un comportamiento.
     */
    @Override
    public void setup() {
        this.Inicializar();
        this.addBehaviour(new Comportamiento());
    }
    /**
     * Inicializa el agente gráfico para poder referenciarlo en el Comportamiento.
     */
    private void Inicializar() {
        agente = this;
    }

    /**
     * Método que está revisando constantemente el evento que llega desde la vista.
     * @param ge Variable evento que se generá en la vista.
     */
    @Override
    protected void onGuiEvent(GuiEvent ge) {
        String movimiento = ui.MoverFichas((MouseEvent) ge.getSource());
        if (!movimiento.equals("")) {
            this.EnviarMovimiento(movimiento);
        }
    }

    /**
     * Se envia al agenteMovimiento el movimiento que se recibe desde la vista.
     * @param movimiento Variable que es el movimiento que se recibe desde la vista.
     */
    protected void EnviarMovimiento(String movimiento) {
        ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);
        mensaje.setContent(movimiento);
        mensaje.addReceiver(new AID("agenteMovimiento", false));
        send(mensaje);
        this.RecibirRespuestaMovimiento();
    }

    /**
     * Recibe la respuesta del agenteMovimiento y luego la envía a la vista.
     */
    protected void RecibirRespuestaMovimiento() {
        ACLMessage mensaje = blockingReceive();
        String respueta_movimiento = mensaje.getContent();
        respueta_movimiento = Cambiar_Mensaje(respueta_movimiento);
        ui.Decir_Movimiento(respueta_movimiento);
    }

    /**
     * Recibe un char que es una letra del movimiento para cambiarlo.
     * @param letra Variable que es la letra del movimiento.
     * @return Devuelve la letra modificada.
     */
    protected char Cambiar_Letra(char letra) {
        switch (letra) {
            case '0':
                letra = '8';
                break;
            case '1':
                letra = '7';
                break;
            case '2':
                letra = '6';
                break;
            case '3':
                letra = '5';
                break;
            case '4':
                letra = '4';
                break;
            case '5':
                letra = '3';
                break;
            case '6':
                letra = '2';
                break;
            case '7':
                letra = '1';
                break;

        }
        return letra;
    }

    /**
     * Cambia las posiciones que tiene el movimiento recibido para que se pueda entender mejor.
     * @param respuesta_movimiento Movimiento que se recibió desde el agenteMovimiento.
     * @return Devuelve el movimiento normalizado para su mejor comprensión.
     */
    protected String Cambiar_Mensaje(String respuesta_movimiento) {
        String cambio_respuesta_movimiento = "";
        cambio_respuesta_movimiento += Cambiar_Letra(respuesta_movimiento.charAt(1));
        cambio_respuesta_movimiento += ",";
        cambio_respuesta_movimiento += Cambiar_Letra(respuesta_movimiento.charAt(0));
        cambio_respuesta_movimiento += " - ";
        cambio_respuesta_movimiento += Cambiar_Letra(respuesta_movimiento.charAt(3));
        cambio_respuesta_movimiento += ",";
        cambio_respuesta_movimiento += Cambiar_Letra(respuesta_movimiento.charAt(2));
        return cambio_respuesta_movimiento;
    }

    public class Comportamiento extends SimpleBehaviour {

        /**
         * Variable que determina quién hace el primero movimiento.
         */
        int humanAsWhite = -1;

        /**
         * Es el método que dice lo que va a realizar el comportamiento.
         * Se inicia el Frame y el Panel donde se pinta la interfaz.
         * Se determina quién inicia la partida.
         */
        @Override
        public void action() {
            frm = new JFrame("Ajedrez");
            frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Object[] option = {"Computador", "Persona"};
            humanAsWhite = JOptionPane.showOptionDialog(null, "Quien va a iniciar el juego?", "Opciones", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
            ui = new pnlUserInterface(agente, humanAsWhite);
            clsMovimientos.setTurno(humanAsWhite);
            frm.add(ui);
            frm.setResizable(false);
            frm.setSize(600, 350);
            frm.setVisible(true);//         
        }
        
        /**
         * Determina cuando debe terminarse la acción del comportamiento.
         * @return Retorna para finalizar.
         */
        @Override
        public boolean done() {
            return true;
        }

    }

}
