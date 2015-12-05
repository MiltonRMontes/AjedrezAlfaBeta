package pkgLogica;

import jade.util.leap.Serializable;
import pkgClases.clsObjetoMensaje;
import static pkgLogica.clsMovimientos.*;

public class clsAlphaBetaChess implements Serializable{
    
    public clsObjetoMensaje AlfaBetaGeneral(clsObjetoMensaje objetomensaje) {
        objetomensaje.getMovimientos().makeMove(objetomensaje.getMovimiento());
        objetomensaje.getMovimientos().flipBoard();
        objetomensaje.getMovimientos().makeMove(this.alphaBeta(objetomensaje.getMovimientos(), 4, 1000000, -1000000, "", 0));
        objetomensaje.getMovimientos().flipBoard();
        return objetomensaje;
    }

    public String alphaBeta(clsMovimientos objetomensaje, int depth, int beta, int alpha, String move, int player) {
        //return in the form of 1234b##########
        String list = posibleMoves();
        if (depth == 0 || list.length() == 0) {
            return move + (clsRating.rating(list.length(), depth) * (player * 2 - 1));
        }
        list = sortMoves(list);
        player = 1 - player;//either 1 or 0
        for (int i = 0; i < list.length(); i += 5) {
            objetomensaje.makeMove(list.substring(i, i + 5));
            objetomensaje.flipBoard();
            String returnString = alphaBeta(objetomensaje, depth - 1, beta, alpha, list.substring(i, i + 5), player);
            int value = Integer.valueOf(returnString.substring(5));
            objetomensaje.flipBoard();
            objetomensaje.undoMove(list.substring(i, i + 5));
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