/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgAgentes;

import jade.core.AID;
import pkgVista.frmAjedrez;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import pkgClases.clsObjetoMensaje;
import pkgVista.pnlUserInterface;

/**
 *
 * @author Milton R. Montes
 */
public class clsAgenteGrafico extends GuiAgent {

    frmAjedrez frm2;
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
        String objetoMensaje = ui.MoverFichas((MouseEvent) ge.getSource());
        //String dragMove = "";
        if (!objetoMensaje.equals("")) {
            this.EnviarMovimiento(objetoMensaje);
        }
    }
    
    private void EnviarMovimiento(String objetoMensaje){
            ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);
            mensaje.setContent(objetoMensaje);
            mensaje.addReceiver(new AID("agenteMovimiento",false));
            send(mensaje);
            this.RecibirRespuestaMovimiento();
    }
    
    private void RecibirRespuestaMovimiento(){
            ACLMessage mensaje = blockingReceive();
            String movimiento = mensaje.getContent();
    }

    public class Comportamiento extends SimpleBehaviour {

        int humanAsWhite = -1;//1=human as white, 0=human as black

        @Override
        public void action() {
            frm = new JFrame("Ajedrez");
            frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Object[] option = {"Computador", "Persona"};
            humanAsWhite = JOptionPane.showOptionDialog(null, "Quien va a jugar con las ficha blancas?", "Opciones", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, option, option[1]);            
            ui = new pnlUserInterface(agente, humanAsWhite);
            frm.add(ui);
            frm.setSize(500, 500);
            frm.setVisible(true);
//         
        }
        
        @Override
        public boolean done() {
            return true;
        }

    }

}
