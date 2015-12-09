package pkgLogica;

import jade.util.leap.Serializable;
import static pkgLogica.clsMovimientos.*;

public class clsAlphaBetaChess implements Serializable{
    
    public static int  profundidad = 4;
    public static int beta = 1000000;
    public static int alfa = -1000000;
    public static String movimiento1  = "";
    public static int jugador;
    public static int turno;
    public static String movimiento_respuesta;
    
    public static String AlfaBetaGeneral(String movimiento) {
        clsMovimientos.makeMove(movimiento);
        clsMovimientos.flipBoard();
        clsMovimientos.CambiarTurno();
        movimiento_respuesta = alphaBeta(profundidad, beta, alfa, movimiento1, jugador);
        clsMovimientos.makeMove(movimiento_respuesta);
        clsMovimientos.flipBoard();
        clsMovimientos.CambiarTurno();
        return movimiento_respuesta;
    }

    public static String alphaBeta(int depth, int beta, int alpha, String move, int player) {
        //return in the form of 1234b##########
        String list = posibleMoves();
        if (depth == 0 || list.length() == 0) {
            return move + (clsRating.rating(list.length(), depth) * (player * 2 - 1));
        }
        list = sortMoves(list);
        player = 1 - player;//either 1 or 0
        for (int i = 0; i < list.length(); i += 5) {
            clsMovimientos.makeMove(list.substring(i, i + 5));
            clsMovimientos.flipBoard();
            String returnString = alphaBeta(depth - 1, beta, alpha, list.substring(i, i + 5), player);
            int value = Integer.valueOf(returnString.substring(5));
            clsMovimientos.flipBoard();
            clsMovimientos.undoMove(list.substring(i, i + 5));
            if (player == 0) {
                if (value <= beta) {
                    beta = value;
                    if (depth == 4) {
                        move = returnString.substring(0, 5);
                    }
                }
            } else {
                if (value > alpha) {
                    alpha = value;
                    if (depth == 4) {
                        move = returnString.substring(0, 5);
                    }
                }
            }
            if (alpha >= beta) {
                if (player == 0) {
                    return move + beta;
                } else {
                    return move + alpha;
                }
            }
        }
        if (player == 0) {
            return move + beta;
        } else {
            return move + alpha;
        }
    }    
}