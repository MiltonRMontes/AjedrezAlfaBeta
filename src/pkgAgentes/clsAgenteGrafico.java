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
import pkgVista.pnlUserInterface;

/**
 *
 * @author Milton R. Montes
 */
public class clsAgenteGrafico extends GuiAgent {

    JFrame frm;
    GuiAgent agente;
    pnlUserInterface ui;

    @Override
    public void setup() {
        this.Inicializar();
        this.addBehaviour(new Comportamiento());
    }

    private void Inicializar() {
        agente = this;
    }

    @Override
    protected void onGuiEvent(GuiEvent ge) {
        String movimiento = ui.MoverFichas((MouseEvent) ge.getSource());
        if (!movimiento.equals("")) {
            this.EnviarMovimiento(movimiento);
        }
    }

    private void EnviarMovimiento(String movimiento) {
        ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);
        mensaje.setContent(movimiento);
        mensaje.addReceiver(new AID("agenteMovimiento", false));
        send(mensaje);
        this.RecibirRespuestaMovimiento();
    }

    private void RecibirRespuestaMovimiento() {
        ACLMessage mensaje = blockingReceive();
        String respueta_movimiento = mensaje.getContent();
        respueta_movimiento = Cambiar_Mensaje(respueta_movimiento);
        ui.Decir_Movimiento(respueta_movimiento);
    }

    private String Cambiar_Mensaje(String respuesta_movimiento) {
        respuesta_movimiento = respuesta_movimiento.replace('7', '0');
        respuesta_movimiento = respuesta_movimiento.replace('6', '1');
        respuesta_movimiento = respuesta_movimiento.replace('5', '2');
        respuesta_movimiento = respuesta_movimiento.replace('4', '3');
        respuesta_movimiento = respuesta_movimiento.replace('3', '4');
        respuesta_movimiento = respuesta_movimiento.replace('2', '5');
        respuesta_movimiento = respuesta_movimiento.replace('1', '6');
        respuesta_movimiento = respuesta_movimiento.replace('0', '7');
        return respuesta_movimiento;

    }

    public class Comportamiento extends SimpleBehaviour {

        int humanAsWhite = -1;//1=human as white, 0=human as black

        @Override
        public void action() {
            frm = new JFrame("Ajedrez");
            frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Object[] option = {"Computador", "Persona"};
            humanAsWhite = JOptionPane.showOptionDialog(null, "Quien va a iniciar el juego?", "Opciones", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
            ui = new pnlUserInterface(agente, humanAsWhite);
            frm.add(ui);
            frm.setSize(600, 350);
            frm.setVisible(true);//         
        }

        @Override
        public boolean done() {
            return true;
        }

    }

}
