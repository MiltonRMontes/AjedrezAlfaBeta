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
import pkgClases.clsDAOUsuario;
import pkgClases.clsUsuario;
import pkgLogica.clsAlphaBetaChess;
import pkgLogica.clsMovimientos;

/**
 *
 * @author Milton R. Montes
 */
public class pnlUserInterface extends javax.swing.JPanel implements MouseListener, MouseMotionListener {

    /**
     * Creates new form pnlUserInterface
     */
    int mouseX, mouseY, newMouseX, newMouseY;
    static int squareSize = 32;
    int una_vez = 0;
    String id_usuario;
    GuiAgent agentePropietario;

    public pnlUserInterface(GuiAgent agente, int humanAsWhite) {
        initComponents();
        this.agentePropietario = agente;
        while (!"A".equals(clsMovimientos.chessBoard[clsMovimientos.kingPositionC / 8][clsMovimientos.kingPositionC % 8])) {
            clsMovimientos.kingPositionC++;
        }//get King's location
        while (!"a".equals(clsMovimientos.chessBoard[clsMovimientos.kingPositionL / 8][clsMovimientos.kingPositionL % 8])) {
            clsMovimientos.kingPositionL++;
        }//get king's location
        if (humanAsWhite == 0) {
            jtaUsuario.setBackground(Color.white);
            jtaMaquina.setBackground(Color.black);
            jtaActual.setBackground(Color.black);
            long startTime = System.currentTimeMillis();
            clsMovimientos.makeMove(clsAlphaBetaChess.alphaBeta(4, 1000000, -1000000, "", 0));
            long endTime = System.currentTimeMillis();
            System.out.println("That took " + (endTime - startTime) + " milliseconds");
            clsMovimientos.flipBoard();
            repaint();
        } else {
            jtaUsuario.setBackground(Color.white);
            jtaMaquina.setBackground(Color.black);
            jtaActual.setBackground(Color.white);
        }
        clsMovimientos.makeMove("7655 ");
        clsMovimientos.undoMove("7655 ");

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
            switch (clsMovimientos.chessBoard[i / 8][i % 8]) {
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
    
    public void Decir_Movimiento(String movimiento){
        lblMostrarRespuesta.setText(movimiento);
    }

    public void Repintar() {
        if (clsMovimientos.turno == 0) {
            jtaActual.setBackground(Color.BLACK);
        } else {
            jtaActual.setBackground(Color.white);
        }
        repaint();
    }

    public String MoverFichas(MouseEvent e) {
        String dragMove = "";
        if (una_vez == 1) {
            if (e.getX() < 8 * squareSize && e.getY() < 8 * squareSize) {
                //if inside the board
                newMouseX = e.getX();
                newMouseY = e.getY();
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (newMouseY / squareSize == 0 && mouseY / squareSize == 1 && "P".equals(clsMovimientos.chessBoard[mouseY / squareSize][mouseX / squareSize])) {
                        //pawn promotion
                        dragMove = "" + mouseX / squareSize + newMouseX / squareSize + clsMovimientos.chessBoard[newMouseY / squareSize][newMouseX / squareSize] + "QP";
                    } else {
                        //regular move
                        dragMove = "" + mouseY / squareSize + mouseX / squareSize + newMouseY / squareSize + newMouseX / squareSize + clsMovimientos.chessBoard[newMouseY / squareSize][newMouseX / squareSize];
                    }
                    String userPosibilities = clsMovimientos.posibleMoves();

                    if (userPosibilities.replaceAll(dragMove, "").length() < userPosibilities.length()) {
                        //if valid move
                    } else {
                        if (userPosibilities.length() < 1) {
                            JOptionPane.showMessageDialog(this, "JaqueMate", "Mate", 3);
                            if (!lblMostrarUsuario.getText().equals("-")) {
                                clsUsuario usuario = new clsUsuario(id_usuario, lblMostrarUsuario.getText(), Integer.parseInt(lblMostrarGanados.getText()),
                                        Integer.parseInt(lblMostrarEmpatados.getText()), Integer.parseInt(lblMostrarPerdidos.getText()) + 1);
                                ModificarLabels(usuario);
                            }

                        } else if (!clsMovimientos.kingSafe()) {
                            JOptionPane.showMessageDialog(this, "Jaque", "Jaque", 1);
                        }
                        JOptionPane.showMessageDialog(this, "Movimiento inválido", "No se puede mover", 2);
                        dragMove = "";
                    }
                    if (clsAlphaBetaChess.alphaBeta(4, 1000000, -1000000, "", 0).length() < 1) {
                        JOptionPane.showMessageDialog(this, "Ganó", "Mate", 3);
                        if (!lblMostrarUsuario.getText().equals("-")) {
                            clsUsuario usuario = new clsUsuario(id_usuario, lblMostrarUsuario.getText(), Integer.parseInt(lblMostrarGanados.getText()) + 1,
                                    Integer.parseInt(lblMostrarEmpatados.getText()), Integer.parseInt(lblMostrarPerdidos.getText()));
                            ModificarLabels(usuario);
                        }
                    }
                    int contador = 0;
                    for (int i = 0; i < clsMovimientos.chessBoard.length; i++) {
                        for (int j = 0; j < clsMovimientos.chessBoard.length; j++) {
                            if (!clsMovimientos.chessBoard[i][j].equals(" ")) {
                                contador++;
                            }
                        }
                    }
                    if (contador <= 2) {
                        if (!lblMostrarUsuario.getText().equals("-")) {
                            clsUsuario usuario = new clsUsuario(id_usuario, lblMostrarUsuario.getText(), Integer.parseInt(lblMostrarGanados.getText()),
                                    Integer.parseInt(lblMostrarEmpatados.getText()) + 1, Integer.parseInt(lblMostrarPerdidos.getText()));
                            ModificarLabels(usuario);
                        }
                    }
                }
            }
            una_vez = 0;
        }
        Repintar();
        return dragMove;
    }

    private void Actualizar_Jugador(clsUsuario usuario) {
        clsDAOUsuario dao = new clsDAOUsuario();
        dao.Actualizar(usuario);
    }

    private void ModificarLabels(clsUsuario usuario) {
        id_usuario = usuario.getId();
        lblMostrarUsuario.setText(usuario.getNombre());
        lblMostrarGanados.setText(usuario.getJuegos_ganados() + "");
        lblMostrarEmpatados.setText(usuario.getJuegos_empatados() + "");
        lblMostrarPerdidos.setText(usuario.getJuegos_perdidos() + "");
        Actualizar_Jugador(usuario);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() < 8 * squareSize && e.getY() < 8 * squareSize) {
            //if inside the board
            this.mouseX = e.getX();
            this.mouseY = e.getY();
            this.una_vez = 1;
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        GuiEvent evento = new GuiEvent(e, 0);
        this.agentePropietario.postGuiEvent(evento);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblUsuario = new javax.swing.JLabel();
        lblMostrarUsuario = new javax.swing.JLabel();
        lblMostrarGanados = new javax.swing.JLabel();
        lblGanados = new javax.swing.JLabel();
        lblMostrarEmpatados = new javax.swing.JLabel();
        lblEmpatados = new javax.swing.JLabel();
        lblMostrarPerdidos = new javax.swing.JLabel();
        lblPerdidos = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        btnCrearUsuario = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaActual = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtaUsuario = new javax.swing.JTextArea();
        lblColorUsuario = new javax.swing.JLabel();
        lblColorMaquina = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtaMaquina = new javax.swing.JTextArea();
        lblColorActual = new javax.swing.JLabel();
        lblRespuesta = new javax.swing.JLabel();
        lblMostrarRespuesta = new javax.swing.JLabel();

        lblUsuario.setText("Usuario: ");

        lblMostrarUsuario.setText("-");

        lblMostrarGanados.setText("-");

        lblGanados.setText("Juegos Ganados:");

        lblMostrarEmpatados.setText("-");

        lblEmpatados.setText("Juegos Empatados:");

        lblMostrarPerdidos.setText("-");

        lblPerdidos.setText("Juegos Perdidos:");

        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnCrearUsuario.setText("Sing Up");
        btnCrearUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearUsuarioActionPerformed(evt);
            }
        });

        jtaActual.setEditable(false);
        jtaActual.setTabSize(0);
        jtaActual.setPreferredSize(new java.awt.Dimension(50, 50));
        jScrollPane2.setViewportView(jtaActual);

        jtaUsuario.setEditable(false);
        jtaUsuario.setTabSize(0);
        jtaUsuario.setPreferredSize(new java.awt.Dimension(50, 50));
        jScrollPane3.setViewportView(jtaUsuario);

        lblColorUsuario.setText("Usuario");

        lblColorMaquina.setText("Máquina");

        jtaMaquina.setEditable(false);
        jtaMaquina.setTabSize(0);
        jtaMaquina.setPreferredSize(new java.awt.Dimension(50, 50));
        jScrollPane4.setViewportView(jtaMaquina);

        lblColorActual.setText("Turno Actual");

        lblRespuesta.setText("Movimiento Máquina");

        lblMostrarRespuesta.setText("-");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(274, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblPerdidos)
                                .addGap(12, 12, 12))
                            .addComponent(lblEmpatados, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblGanados, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsuario, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblMostrarEmpatados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMostrarPerdidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMostrarGanados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMostrarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnLogin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCrearUsuario)
                        .addGap(64, 64, 64))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblColorUsuario)
                                .addGap(27, 27, 27)
                                .addComponent(lblColorMaquina)
                                .addGap(18, 18, 18)
                                .addComponent(lblColorActual))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblRespuesta)
                                .addGap(18, 18, 18)
                                .addComponent(lblMostrarRespuesta, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(56, 56, 56))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario)
                    .addComponent(lblMostrarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGanados)
                    .addComponent(lblMostrarGanados, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmpatados)
                    .addComponent(lblMostrarEmpatados, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPerdidos, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblMostrarPerdidos, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin)
                    .addComponent(btnCrearUsuario))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblColorUsuario)
                    .addComponent(lblColorMaquina, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblColorActual))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRespuesta)
                    .addComponent(lblMostrarRespuesta))
                .addContainerGap(32, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        clsDAOUsuario daoUsuario = new clsDAOUsuario();
        clsUsuario usuario = daoUsuario.Consultar(JOptionPane.showInputDialog(this, "Digite su número de usuario", "Login", 1));
        if (usuario.getId() != null) {
            ModificarLabels(usuario);
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnCrearUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearUsuarioActionPerformed
        frmCrearUsuario frm = new frmCrearUsuario();
        frm.setVisible(true);
    }//GEN-LAST:event_btnCrearUsuarioActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCrearUsuario;
    private javax.swing.JButton btnLogin;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jtaActual;
    private javax.swing.JTextArea jtaMaquina;
    private javax.swing.JTextArea jtaUsuario;
    private javax.swing.JLabel lblColorActual;
    private javax.swing.JLabel lblColorMaquina;
    private javax.swing.JLabel lblColorUsuario;
    private javax.swing.JLabel lblEmpatados;
    private javax.swing.JLabel lblGanados;
    private javax.swing.JLabel lblMostrarEmpatados;
    private javax.swing.JLabel lblMostrarGanados;
    private javax.swing.JLabel lblMostrarPerdidos;
    private javax.swing.JLabel lblMostrarRespuesta;
    private javax.swing.JLabel lblMostrarUsuario;
    private javax.swing.JLabel lblPerdidos;
    private javax.swing.JLabel lblRespuesta;
    private javax.swing.JLabel lblUsuario;
    // End of variables declaration//GEN-END:variables

}
