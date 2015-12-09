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
import pkgLogica.*;
/**
 *
 * @author Milton R. Montes
 */
public class clsAgenteMovimientos extends Agent {

    /**
     * Método que se ejecuta al iniciar el agente, donde se añade un comportamiento.
     */
    @Override
    public void setup() {
        Comportamiento comp = new Comportamiento();
        this.addBehaviour(comp);
    }

    public class Comportamiento extends CyclicBehaviour {

        /**
         * Es el método que dice lo que va a realizar el comportamiento.
         * Recibe el mensaje del agenteGráfico y luego llama a los métodos GenerarMovimientos y EnviarObjeto.
         */
        @Override
        public void action() {
            ACLMessage mensaje = blockingReceive();
            String movimiento = mensaje.getContent();
            String respuesta_movimiento = this.GenerarMovimiento(movimiento);
            this.EnviarObjeto(respuesta_movimiento);

        }

        /**
         * Recibe el movimiento que genera la máquina para luego enviarlo al agenteGráfico.
         * @param respueta_movimiento Variable que es el movimiento que genera la máquina.
         */
        protected void EnviarObjeto(String respueta_movimiento) {
            ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
            mensaje.setContent(respueta_movimiento);
            mensaje.addReceiver(new AID("agenteGrafico", false));
            send(mensaje);
        }

        /**
         * Genera el movimiento que realiza la máquina a partir del movimiento del usuario.
         * @param movimiento Variable que es el movimiento del usuario recibido desde el agenteGráfico.
         * @return Devuelve el movimiento que generó la máquina.
         */
        protected String GenerarMovimiento(String movimiento) {
            return clsAlphaBetaChess.AlfaBetaGeneral(movimiento);
        }

    }
}
