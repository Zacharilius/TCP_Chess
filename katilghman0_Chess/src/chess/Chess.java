/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

/**
 *
 * @author Afrohawk
 */
public class Chess {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Board test = new Board();
        test.printBoard();
        int x = 1;
        int y = 1;
        int newX = 1;
        int newY = 3;
        boolean[][] validMoves = new boolean[8][8];
        System.out.println("=========== ========== =========");
        validMoves = test.validMoves(x, y);
        test.printKing(true);
        test.printKing(false);
        test.movePiece(x, y, newX, newY, validMoves);
        test.printBoard();
        validMoves = test.validMoves(newX, newY);
        test.movePiece(newX, newY, newX, newY+1, validMoves);
        test.printBoard();
        
    }
}
