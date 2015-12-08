/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgAgentes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pkgClases.clsObjetoMensaje;
import pkgLogica.*;

/**
 *
 * @author Milton R. Montes
 */
public class clsAgenteMovimientos extends Agent {

    @Override
    public void setup() {
        Comportamiento comp = new Comportamiento();
        this.addBehaviour(comp);
    }

    public class Comportamiento extends CyclicBehaviour {

        //clsAlphaBetaChess alfabeta = new clsAlphaBetaChess();

        @Override
        public void action() {
            ACLMessage mensaje = blockingReceive();
            String movimiento = mensaje.getContent();
            String objetomensaje = this.GenerarMovimiento(movimiento);
            this.EnviarObjeto(objetomensaje);

        }

        private void EnviarObjeto(String objetomensaje) {
            ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
            mensaje.setContent(objetomensaje);
            mensaje.addReceiver(new AID("agenteGrafico", false));
            send(mensaje);
        }

        private String GenerarMovimiento(String movimiento) {
            return clsAlphaBetaChess.AlfaBetaGeneral(movimiento);
        }

    }
}
