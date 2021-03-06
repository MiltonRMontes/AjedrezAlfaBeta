/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgLogica;

import jade.util.leap.Serializable;

/**
 *
 * @author Milton R. Montes
 */
public class clsMovimientos implements Serializable{
    /**
     * Tablero inicial.
     */
    public static String chessBoard[][] = {
        {"r", "k", "b", "q", "a", "b", "k", "r"},
        {"p", "p", "p", "p", "p", "p", "p", "p"},
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {" ", " ", " ", " ", " ", " ", " ", " "},
        {"P", "P", "P", "P", "P", "P", "P", "P"},
        {"R", "K", "B", "Q", "A", "B", "K", "R"}};
    /**
     * Posición del rey de la máquina y del usuario, turno 
     */
    public static int kingPositionC, kingPositionL, turno;
    
    /**
     * Voltea el tablero para el buen funcionamiento del programa.
     */
    public static void flipBoard() {
        String temp;
        for (int i = 0; i < 32; i++) {
            int r = i / 8, c = i % 8;
            if (Character.isUpperCase(chessBoard[r][c].charAt(0))) {
                temp = chessBoard[r][c].toLowerCase();
            } else {
                temp = chessBoard[r][c].toUpperCase();
            }
            if (Character.isUpperCase(chessBoard[7 - r][7 - c].charAt(0))) {
                chessBoard[r][c] = chessBoard[7 - r][7 - c].toLowerCase();
            } else {
                chessBoard[r][c] = chessBoard[7 - r][7 - c].toUpperCase();
            }
            chessBoard[7 - r][7 - c] = temp;
        }
        int kingTemp = kingPositionC;
        kingPositionC = 63 - kingPositionL;
        kingPositionL = 63 - kingTemp;
    }

    /**
     * Realiza el movimiento recibido en el tablero.
     * @param move Variable que es el movimiento que se va a realizar.
     */
    public static void makeMove(String move) {
        if (move.charAt(4) != 'P') {
            chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] = chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))];
            chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))] = " ";
            if ("A".equals(chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))])) {
                kingPositionC = 8 * Character.getNumericValue(move.charAt(2)) + Character.getNumericValue(move.charAt(3));
            }
        } else {
            //if pawn promotion
            chessBoard[1][Character.getNumericValue(move.charAt(0))] = " ";
            chessBoard[0][Character.getNumericValue(move.charAt(1))] = String.valueOf(move.charAt(3));
        }
    }

    /**
     * Deshace el movimiento recibido en el tablero.
     * @param move Variable que es el movimiento que se va a realizar.
     */
    public static void undoMove(String move) {
        if (move.charAt(4) != 'P') {
            chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))] = chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))];
            chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] = String.valueOf(move.charAt(4));
            if ("A".equals(chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))])) {
                kingPositionC = 8 * Character.getNumericValue(move.charAt(0)) + Character.getNumericValue(move.charAt(1));
            }
        } else {
            //if pawn promotion
            chessBoard[1][Character.getNumericValue(move.charAt(0))] = "P";
            chessBoard[0][Character.getNumericValue(move.charAt(1))] = String.valueOf(move.charAt(2));
        }
    }

    /**
     * Método que devuelve un string que tiene de los posibles movimientos.
     * @return Devuelve la lista de los posibles movimientos.
     */
    public static String posibleMoves() {
        String list = "";
        for (int i = 0; i < 64; i++) {
            switch (chessBoard[i / 8][i % 8]) {
                case "P":
                    list += posibleP(i);
                    break;
                case "R":
                    list += posibleR(i);
                    break;
                case "K":
                    list += posibleK(i);
                    break;
                case "B":
                    list += posibleB(i);
                    break;
                case "Q":
                    list += posibleQ(i);
                    break;
                case "A":
                    list += posibleA(i);
                    break;
            }
        }
        return list;//x1,y1,x2,y2,captured piece
    }

    /**
     * Método que devuelve un string que tiene de los posibles movimientos del Peón. 
     * @param i Variable que dice la posición del tablero.
     * @return Devuelve los posibles movimientos del Peón.
     */
    public static String posibleP(int i) {
        String list = "", oldPiece;
        int r = i / 8, c = i % 8;
        for (int j = -1; j <= 1; j += 2) {
            try {//capture
                if (Character.isLowerCase(chessBoard[r - 1][c + j].charAt(0)) && i >= 16) {
                    oldPiece = chessBoard[r - 1][c + j];
                    chessBoard[r][c] = " ";
                    chessBoard[r - 1][c + j] = "P";
                    if (kingSafe()) {
                        list = list + r + c + (r - 1) + (c + j) + oldPiece;
                    }
                    chessBoard[r][c] = "P";
                    chessBoard[r - 1][c + j] = oldPiece;
                }
            } catch (Exception e) {
            }
            try {//promotion && capture
                if (Character.isLowerCase(chessBoard[r - 1][c + j].charAt(0)) && i < 16) {
                    String[] temp = {"Q", "R", "B", "K"};
                    for (int k = 0; k < 4; k++) {
                        oldPiece = chessBoard[r - 1][c + j];
                        chessBoard[r][c] = " ";
                        chessBoard[r - 1][c + j] = temp[k];
                        if (kingSafe()) {
                            //column1,column2,captured-piece,new-piece,P
                            list = list + c + (c + j) + oldPiece + temp[k] + "P";
                        }
                        chessBoard[r][c] = "P";
                        chessBoard[r - 1][c + j] = oldPiece;
                    }
                }
            } catch (Exception e) {
            }
        }
        try {//move one up
            if (" ".equals(chessBoard[r - 1][c]) && i >= 16) {
                oldPiece = chessBoard[r - 1][c];
                chessBoard[r][c] = " ";
                chessBoard[r - 1][c] = "P";
                if (kingSafe()) {
                    list = list + r + c + (r - 1) + c + oldPiece;
                }
                chessBoard[r][c] = "P";
                chessBoard[r - 1][c] = oldPiece;
            }
        } catch (Exception e) {
        }
        try {//promotion && no capture
            if (" ".equals(chessBoard[r - 1][c]) && i < 16) {
                String[] temp = {"Q", "R", "B", "K"};
                for (int k = 0; k < 4; k++) {
                    oldPiece = chessBoard[r - 1][c];
                    chessBoard[r][c] = " ";
                    chessBoard[r - 1][c] = temp[k];
                    if (kingSafe()) {
                        //column1,column2,captured-piece,new-piece,P
                        list = list + c + c + oldPiece + temp[k] + "P";
                    }
                    chessBoard[r][c] = "P";
                    chessBoard[r - 1][c] = oldPiece;
                }
            }
        } catch (Exception e) {
        }
        try {//move two up
            if (" ".equals(chessBoard[r - 1][c]) && " ".equals(chessBoard[r - 2][c]) && i >= 48) {
                oldPiece = chessBoard[r - 2][c];
                chessBoard[r][c] = " ";
                chessBoard[r - 2][c] = "P";
                if (kingSafe()) {
                    list = list + r + c + (r - 2) + c + oldPiece;
                }
                chessBoard[r][c] = "P";
                chessBoard[r - 2][c] = oldPiece;
            }
        } catch (Exception e) {
        }
        return list;
    }

    /**
     * Método que devuelve un string que tiene de los posibles movimientos de la Torre. 
     * @param i Variable que dice la posición del tablero.
     * @return Devuelve los posibles movimientos de la Torre.
     */
    public static String posibleR(int i) {
        String list = "", oldPiece;
        int r = i / 8, c = i % 8;
        int temp = 1;
        for (int j = -1; j <= 1; j += 2) {
            try {
                while (" ".equals(chessBoard[r][c + temp * j])) {
                    oldPiece = chessBoard[r][c + temp * j];
                    chessBoard[r][c] = " ";
                    chessBoard[r][c + temp * j] = "R";
                    if (kingSafe()) {
                        list = list + r + c + r + (c + temp * j) + oldPiece;
                    }
                    chessBoard[r][c] = "R";
                    chessBoard[r][c + temp * j] = oldPiece;
                    temp++;
                }
                if (Character.isLowerCase(chessBoard[r][c + temp * j].charAt(0))) {
                    oldPiece = chessBoard[r][c + temp * j];
                    chessBoard[r][c] = " ";
                    chessBoard[r][c + temp * j] = "R";
                    if (kingSafe()) {
                        list = list + r + c + r + (c + temp * j) + oldPiece;
                    }
                    chessBoard[r][c] = "R";
                    chessBoard[r][c + temp * j] = oldPiece;
                }
            } catch (Exception e) {
            }
            temp = 1;
            try {
                while (" ".equals(chessBoard[r + temp * j][c])) {
                    oldPiece = chessBoard[r + temp * j][c];
                    chessBoard[r][c] = " ";
                    chessBoard[r + temp * j][c] = "R";
                    if (kingSafe()) {
                        list = list + r + c + (r + temp * j) + c + oldPiece;
                    }
                    chessBoard[r][c] = "R";
                    chessBoard[r + temp * j][c] = oldPiece;
                    temp++;
                }
                if (Character.isLowerCase(chessBoard[r + temp * j][c].charAt(0))) {
                    oldPiece = chessBoard[r + temp * j][c];
                    chessBoard[r][c] = " ";
                    chessBoard[r + temp * j][c] = "R";
                    if (kingSafe()) {
                        list = list + r + c + (r + temp * j) + c + oldPiece;
                    }
                    chessBoard[r][c] = "R";
                    chessBoard[r + temp * j][c] = oldPiece;
                }
            } catch (Exception e) {
            }
            temp = 1;
        }
        return list;
    }

    /**
     * Método que devuelve un string que tiene de los posibles movimientos del Caballo. 
     * @param i Variable que dice la posición del tablero.
     * @return Devuelve los posibles movimientos del Caballo.
     */
    public static String posibleK(int i) {
        String list = "", oldPiece;
        int r = i / 8, c = i % 8;
        for (int j = -1; j <= 1; j += 2) {
            for (int k = -1; k <= 1; k += 2) {
                try {
                    if (Character.isLowerCase(chessBoard[r + j][c + k * 2].charAt(0)) || " ".equals(chessBoard[r + j][c + k * 2])) {
                        oldPiece = chessBoard[r + j][c + k * 2];
                        chessBoard[r][c] = " ";
                        if (kingSafe()) {
                            list = list + r + c + (r + j) + (c + k * 2) + oldPiece;
                        }
                        chessBoard[r][c] = "K";
                        chessBoard[r + j][c + k * 2] = oldPiece;
                    }
                } catch (Exception e) {
                }
                try {
                    if (Character.isLowerCase(chessBoard[r + j * 2][c + k].charAt(0)) || " ".equals(chessBoard[r + j * 2][c + k])) {
                        oldPiece = chessBoard[r + j * 2][c + k];
                        chessBoard[r][c] = " ";
                        if (kingSafe()) {
                            list = list + r + c + (r + j * 2) + (c + k) + oldPiece;
                        }
                        chessBoard[r][c] = "K";
                        chessBoard[r + j * 2][c + k] = oldPiece;
                    }
                } catch (Exception e) {
                }
            }
        }
        return list;
    }

    /**
     * Método que devuelve un string que tiene de los posibles movimientos del Alfil. 
     * @param i Variable que dice la posición del tablero.
     * @return Devuelve los posibles movimientos del Alfil.
     */
    public static String posibleB(int i) {
        String list = "", oldPiece;
        int r = i / 8, c = i % 8;
        int temp = 1;
        for (int j = -1; j <= 1; j += 2) {
            for (int k = -1; k <= 1; k += 2) {
                try {
                    while (" ".equals(chessBoard[r + temp * j][c + temp * k])) {
                        oldPiece = chessBoard[r + temp * j][c + temp * k];
                        chessBoard[r][c] = " ";
                        chessBoard[r + temp * j][c + temp * k] = "B";
                        if (kingSafe()) {
                            list = list + r + c + (r + temp * j) + (c + temp * k) + oldPiece;
                        }
                        chessBoard[r][c] = "B";
                        chessBoard[r + temp * j][c + temp * k] = oldPiece;
                        temp++;
                    }
                    if (Character.isLowerCase(chessBoard[r + temp * j][c + temp * k].charAt(0))) {
                        oldPiece = chessBoard[r + temp * j][c + temp * k];
                        chessBoard[r][c] = " ";
                        chessBoard[r + temp * j][c + temp * k] = "B";
                        if (kingSafe()) {
                            list = list + r + c + (r + temp * j) + (c + temp * k) + oldPiece;
                        }
                        chessBoard[r][c] = "B";
                        chessBoard[r + temp * j][c + temp * k] = oldPiece;
                    }
                } catch (Exception e) {
                }
                temp = 1;
            }
        }
        return list;
    }
    
    /**
     * Método que devuelve un string que tiene de los posibles movimientos de la Reina. 
     * @param i Variable que dice la posición del tablero.
     * @return Devuelve los posibles movimientos de la Reina. 
     */
    public static String posibleQ(int i) {
        String list = "", oldPiece;
        int r = i / 8, c = i % 8;
        int temp = 1;
        for (int j = -1; j <= 1; j++) {
            for (int k = -1; k <= 1; k++) {
                if (j != 0 || k != 0) {
                    try {
                        while (" ".equals(chessBoard[r + temp * j][c + temp * k])) {
                            oldPiece = chessBoard[r + temp * j][c + temp * k];
                            chessBoard[r][c] = " ";
                            chessBoard[r + temp * j][c + temp * k] = "Q";
                            if (kingSafe()) {
                                list = list + r + c + (r + temp * j) + (c + temp * k) + oldPiece;
                            }
                            chessBoard[r][c] = "Q";
                            chessBoard[r + temp * j][c + temp * k] = oldPiece;
                            temp++;
                        }
                        if (Character.isLowerCase(chessBoard[r + temp * j][c + temp * k].charAt(0))) {
                            oldPiece = chessBoard[r + temp * j][c + temp * k];
                            chessBoard[r][c] = " ";
                            chessBoard[r + temp * j][c + temp * k] = "Q";
                            if (kingSafe()) {
                                list = list + r + c + (r + temp * j) + (c + temp * k) + oldPiece;
                            }
                            chessBoard[r][c] = "Q";
                            chessBoard[r + temp * j][c + temp * k] = oldPiece;
                        }
                    } catch (Exception e) {
                    }
                    temp = 1;
                }
            }
        }
        return list;
    }

    /**
     * Método que devuelve un string que tiene de los posibles movimientos del Rey. 
     * @param i Variable que dice la posición del tablero.
     * @return Devuelve los posibles movimientos del Rey.
     */
    public static String posibleA(int i) {
        String list = "", oldPiece;
        int r = i / 8, c = i % 8;
        for (int j = 0; j < 9; j++) {
            if (j != 4) {
                try {
                    if (Character.isLowerCase(chessBoard[r - 1 + j / 3][c - 1 + j % 3].charAt(0)) || " ".equals(chessBoard[r - 1 + j / 3][c - 1 + j % 3])) {
                        oldPiece = chessBoard[r - 1 + j / 3][c - 1 + j % 3];
                        chessBoard[r][c] = " ";
                        chessBoard[r - 1 + j / 3][c - 1 + j % 3] = "A";
                        int kingTemp = kingPositionC;
                        kingPositionC = i + (j / 3) * 8 + j % 3 - 9;
                        if (kingSafe()) {
                            list = list + r + c + (r - 1 + j / 3) + (c - 1 + j % 3) + oldPiece;
                        }
                        chessBoard[r][c] = "A";
                        chessBoard[r - 1 + j / 3][c - 1 + j % 3] = oldPiece;
                        kingPositionC = kingTemp;
                    }
                } catch (Exception e) {
                }
            }
        }
        //need to add casting later
        return list;
    }

    public static String sortMoves(String list) {
        int[] score = new int[list.length() / 5];
        for (int i = 0; i < list.length(); i += 5) {
            makeMove(list.substring(i, i + 5));
            score[i / 5] = -clsRating.rating(-1, 0);
            undoMove(list.substring(i, i + 5));
        }
        String newListA = "", newListB = list;
        for (int i = 0; i < Math.min(6, list.length() / 5); i++) {//first few moves only
            int max = -1000000, maxLocation = 0;
            for (int j = 0; j < list.length() / 5; j++) {
                if (score[j] > max) {
                    max = score[j];
                    maxLocation = j;
                }
            }
            score[maxLocation] = -1000000;
            newListA += list.substring(maxLocation * 5, maxLocation * 5 + 5);
            newListB = newListB.replace(list.substring(maxLocation * 5, maxLocation * 5 + 5), "");
        }
        return newListA + newListB;
    }

    /**
     * Metodo que determina si el rey está en jaque o no.
     * @return Devuelve un boolena que determina si el rey está a salvo o no.
     */
    public static boolean kingSafe() {
        //bishop/queen
        int temp = 1;
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                try {
                    while (" ".equals(chessBoard[kingPositionC / 8 + temp * i][kingPositionC % 8 + temp * j])) {
                        temp++;
                    }
                    if ("b".equals(chessBoard[kingPositionC / 8 + temp * i][kingPositionC % 8 + temp * j])
                            || "q".equals(chessBoard[kingPositionC / 8 + temp * i][kingPositionC % 8 + temp * j])) {
                        return false;
                    }
                } catch (Exception e) {
                }
                temp = 1;
            }
        }
        //rook/queen
        for (int i = -1; i <= 1; i += 2) {
            try {
                while (" ".equals(chessBoard[kingPositionC / 8][kingPositionC % 8 + temp * i])) {
                    temp++;
                }
                if ("r".equals(chessBoard[kingPositionC / 8][kingPositionC % 8 + temp * i])
                        || "q".equals(chessBoard[kingPositionC / 8][kingPositionC % 8 + temp * i])) {
                    return false;
                }
            } catch (Exception e) {
            }
            temp = 1;
            try {
                while (" ".equals(chessBoard[kingPositionC / 8 + temp * i][kingPositionC % 8])) {
                    temp++;
                }
                if ("r".equals(chessBoard[kingPositionC / 8 + temp * i][kingPositionC % 8])
                        || "q".equals(chessBoard[kingPositionC / 8 + temp * i][kingPositionC % 8])) {
                    return false;
                }
            } catch (Exception e) {
            }
            temp = 1;
        }
        //knight
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                try {
                    if ("k".equals(chessBoard[kingPositionC / 8 + i][kingPositionC % 8 + j * 2])) {
                        return false;
                    }
                } catch (Exception e) {
                }
                try {
                    if ("k".equals(chessBoard[kingPositionC / 8 + i * 2][kingPositionC % 8 + j])) {
                        return false;
                    }
                } catch (Exception e) {
                }
            }
        }
        //pawn
        if (kingPositionC >= 16) {
            try {
                if ("p".equals(chessBoard[kingPositionC / 8 - 1][kingPositionC % 8 - 1])) {
                    return false;
                }
            } catch (Exception e) {
            }
            try {
                if ("p".equals(chessBoard[kingPositionC / 8 - 1][kingPositionC % 8 + 1])) {
                    return false;
                }
            } catch (Exception e) {
            }
            //king
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i != 0 || j != 0) {
                        try {
                            if ("a".equals(chessBoard[kingPositionC / 8 + i][kingPositionC % 8 + j])) {
                                return false;
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * Cambia el jugador a turno cada vez que se realiza un movimiento.
     */
    public static void CambiarTurno(){
        if (turno == 0) {
            turno = 1;
        }
        else{
            turno = 0;
        }
    }
    
    /**
     * Modifica el jugador que inicia el juego.
     * @param turno1 Jugador que inicia el juego.
     */
    public static void setTurno(int turno1){
        turno = turno1;
    }
}
