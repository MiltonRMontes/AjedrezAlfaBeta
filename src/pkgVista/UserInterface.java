package pkgVista;

import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import pkgClases.clsObjetoMensaje;
import pkgLogica.clsMovimientos;

public class UserInterface extends JPanel implements MouseListener, MouseMotionListener{

    int mouseX, mouseY, newMouseX, newMouseY;
    static int squareSize = 32;
    int una_vez = 0;
    public clsObjetoMensaje objeto = new clsObjetoMensaje();
    GuiAgent agentePropietario;
    
    public UserInterface(GuiAgent agente){
        this.agentePropietario = agente;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < 64; i += 2) {
            g.setColor(new Color(255, 200, 100));
            g.fillRect((i % 8 + (i / 8) % 2) * squareSize, (i / 8) * squareSize, squareSize, squareSize);
            g.setColor(new Color(150, 50, 30));
            g.fillRect(((i + 1) % 8 - ((i + 1) / 8) % 2) * squareSize, ((i + 1) / 8) * squareSize, squareSize, squareSize);
        }
        objeto.setMovimientos(new clsMovimientos());
        Image chessPiecesImage;
        chessPiecesImage = new ImageIcon("ChessPieces.png").getImage();
        for (int i = 0; i < 64; i++) {
            int j = -1, k = -1;
            switch (pkgLogica.clsMovimientos.chessBoard[i / 8][i % 8]) {
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
                    if (newMouseY / squareSize == 0 && mouseY / squareSize == 1 && "P".equals(pkgLogica.clsMovimientos.chessBoard[mouseY / squareSize][mouseX / squareSize])) {
                        //pawn promotion
                        dragMove = "" + mouseX / squareSize + newMouseX / squareSize + pkgLogica.clsMovimientos.chessBoard[newMouseY / squareSize][newMouseX / squareSize] + "QP";
                    } else {
                        //regular move
                        dragMove = "" + mouseY / squareSize + mouseX / squareSize + newMouseY / squareSize + newMouseX / squareSize + pkgLogica.clsMovimientos.chessBoard[newMouseY / squareSize][newMouseX / squareSize];
                    }
                    String userPosibilities = pkgLogica.clsMovimientos.posibleMoves();

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
        objeto.setMovimiento(dragMove);
        return objeto;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() < 8 * this.squareSize && e.getY() < 8 * this.squareSize) {
            //if inside the board
            this.mouseX = e.getX();
            this.mouseY = e.getY();
            this.una_vez = 1;
            JOptionPane.showMessageDialog(this, "Si entra");
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
