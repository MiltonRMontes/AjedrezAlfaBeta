/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgVista;

import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import pkgClases.clsObjetoMensaje;
import pkgLogica.clsAlphaBetaChess;
import pkgLogica.clsMovimientos;

/**
 *
 * @author Milton R. Montes
 */
public class pnlUserInterface extends javax.swing.JPanel implements MouseListener, MouseMotionListener{

    /**
     * Creates new form pnlUserInterface
     */
    
    int mouseX, mouseY, newMouseX, newMouseY;
    static int squareSize = 32;
    int una_vez = 0;
    public clsMovimientos movimientos;
    public clsObjetoMensaje objeto;
    GuiAgent agentePropietario;
    
    public pnlUserInterface(GuiAgent agente, int humanAsWhite) {
        initComponents();
        movimientos = new clsMovimientos();
        objeto = new clsObjetoMensaje();
        objeto.setMovimientos(movimientos);
        this.agentePropietario = agente;
        
        if (humanAsWhite==0) {
            long startTime=System.currentTimeMillis();
            clsAlphaBetaChess alfa = new clsAlphaBetaChess();
            movimientos.makeMove(alfa.alphaBeta(objeto.getMovimientos(), 4, 1000000, -1000000, "", 0));
            long endTime=System.currentTimeMillis();
            System.out.println("That took "+(endTime-startTime)+" milliseconds");
            objeto.getMovimientos().flipBoard();
            repaint();
        }
        objeto.getMovimientos().makeMove("7655 ");
        objeto.getMovimientos().undoMove("7655 ");        
        
        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        for (int i = 0; i < 64; i += 2) {
            g.setColor(new Color(255, 200, 100));
            g.fillRect((i % 8 + (i / 8) % 2) * squareSize, (i / 8) * squareSize, squareSize, squareSize);
            g.setColor(new Color(150, 50, 30));
            g.fillRect(((i + 1) % 8 - ((i + 1) / 8) % 2) * squareSize, ((i + 1) / 8) * squareSize, squareSize, squareSize);
        }
        Image chessPiecesImage;
        chessPiecesImage = new ImageIcon("ChessPieces.png").getImage();
        for (int i = 0; i < 64; i++) {
            int j = -1, k = -1;
            switch (movimientos.chessBoard[i / 8][i % 8]) {
                case "P":
                    j = 5;
                    k = 0;
                    break;
                case "p":
                    j = 5;
                    k = 1;
                    break;
                case "R":
                    j = 2;
                    k = 0;
                    break;
                case "r":
                    j = 2;
                    k = 1;
                    break;
                case "K":
                    j = 4;
                    k = 0;
                    break;
                case "k":
                    j = 4;
                    k = 1;
                    break;
                case "B":
                    j = 3;
                    k = 0;
                    break;
                case "b":
                    j = 3;
                    k = 1;
                    break;
                case "Q":
                    j = 1;
                    k = 0;
                    break;
                case "q":
                    j = 1;
                    k = 1;
                    break;
                case "A":
                    j = 0;
                    k = 0;
                    break;
                case "a":
                    j = 0;
                    k = 1;
                    break;
            }
            if (j != -1 && k != -1) {
                g.drawImage(chessPiecesImage, (i % 8) * squareSize, (i / 8) * squareSize, (i % 8 + 1) * squareSize, (i / 8 + 1) * squareSize, j * 64, k * 64, (j + 1) * 64, (k + 1) * 64, this);
            }
        }
    }

    public void Igualar(clsObjetoMensaje objetomensaje){
        this.objeto = objetomensaje;
        this.Repintar();
    }
    
    public void Repintar() {
        repaint();
    }

    public clsObjetoMensaje MoverFichas(MouseEvent e) {
        String dragMove = "";
        if (una_vez == 1) {
            if (e.getX() < 8 * squareSize && e.getY() < 8 * squareSize) {
                //if inside the board
                newMouseX = e.getX();
                newMouseY = e.getY();
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (newMouseY / squareSize == 0 && mouseY / squareSize == 1 && "P".equals(movimientos.chessBoard[mouseY / squareSize][mouseX / squareSize])) {
                        //pawn promotion
                        dragMove = "" + mouseX / squareSize + newMouseX / squareSize + movimientos.chessBoard[newMouseY / squareSize][newMouseX / squareSize] + "QP";
                    } else {
                        //regular move
                        dragMove = "" + mouseY / squareSize + mouseX / squareSize + newMouseY / squareSize + newMouseX / squareSize + movimientos.chessBoard[newMouseY / squareSize][newMouseX / squareSize];
                    }
                    String userPosibilities = movimientos.posibleMoves();

                    if (userPosibilities.replaceAll(dragMove, "").length() < userPosibilities.length()) {
                        //if valid move
                    } else {
                        JOptionPane.showMessageDialog(this, "Movimiento inválido", "No se puede mover", 2);
                        dragMove = "";
                    }
                }
            }
            una_vez = 0;
        }
        this.Repintar();
        objeto.setMovimiento(dragMove);
        return objeto;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() < 8 * this.squareSize && e.getY() < 8 * this.squareSize) {
            //if inside the board
            this.mouseX = e.getX();
            this.mouseY = e.getY();
            this.una_vez = 1;
        }
        this.Repintar();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        GuiEvent evento = new GuiEvent(e, 0);
        this.agentePropietario.postGuiEvent(evento);
        Repintar();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
