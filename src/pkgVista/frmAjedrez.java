/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgVista;

import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JOptionPane;
import pkgClases.clsObjetoMensaje;

/**
 *
 * @author Milton R. Montes
 */
public class frmAjedrez extends javax.swing.JFrame implements MouseListener, MouseMotionListener {

    GuiAgent agentePropietario;
//    
//    /**
//     * Creates new form frmAjedrez
//     */

    public frmAjedrez(GuiAgent agente) {
        //userInterface1 = new UserInterface();
        this.agentePropietario = agente;
        initComponents();
    }
//    
//    @Override
//    public void mouseClicked(MouseEvent e) {
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//        if (e.getX() < 8 * userInterface1.squareSize && e.getY() < 8 * userInterface1.squareSize) {
//            //if inside the board
//            userInterface1.mouseX = e.getX();
//            userInterface1.mouseY = e.getY();
//            userInterface1.una_vez = 1;
//        }
//        userInterface1.Repintar();
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {
//        GuiEvent evento = new GuiEvent(e, 0);
//        this.agentePropietario.postGuiEvent(evento);
//        userInterface1.Repintar();
//    }
//    
//    public clsObjetoMensaje MoverFichas(MouseEvent e){
//        return userInterface1.MoverFichas(e);
//    }
//
//    @Override
//    public void mouseEntered(MouseEvent e) {}
//
//    @Override
//    public void mouseExited(MouseEvent e) {}
//
//    @Override
//    public void mouseDragged(MouseEvent e) {}
//
//    @Override
//    public void mouseMoved(MouseEvent e) {}
//    
    //public 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addContainerGap(109, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(17, 17, 17)
                .addComponent(jButton1)
                .addContainerGap(193, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jLabel1.setText("Hola");
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
        JOptionPane.showMessageDialog(rootPane, "Hola mundo");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        JOptionPane.showMessageDialog(rootPane, "Hola mundo");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        JOptionPane.showMessageDialog(rootPane, "Hola mundo");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JOptionPane.showMessageDialog(rootPane, "Hola mundo");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JOptionPane.showMessageDialog(rootPane, "Hola mundo");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        JOptionPane.showMessageDialog(rootPane, "Hola mundo");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        JOptionPane.showMessageDialog(rootPane, "Hola mundo");
    }

}
