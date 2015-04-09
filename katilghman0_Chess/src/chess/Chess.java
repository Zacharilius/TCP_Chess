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
        int x = 5;
        int y = 6;
        boolean[][] validMoves = new boolean[8][8];
        /* //GENERAL TESTING
        int newX = 1;
        int newY = 3;
        
        System.out.println("=========== ========== =========");
        validMoves = test.validMoves(x, y);
        test.printValidMoves(validMoves);
        System.out.println("=========== ========== =========");
        test.printKing(true);
        test.printKing(false);
        test.movePiece(x, y, newX, newY, validMoves);
        test.printBoard();
        System.out.println("=========== ========== =========");
        
        validMoves = test.validMoves(newX, newY);
        test.printValidMoves(validMoves);
        System.out.println("=========== ========== =========");
        test.movePiece(newX, newY, newX, newY+1, validMoves);
        test.printBoard();
        
        
        validMoves = test.validMoves(newX, newY);
        test.printValidMoves(validMoves);
        System.out.println("=========== ========== =========");
        //END GENERAL TESTING */
        
        /* CHECK TESTING */
        validMoves = test.validMoves(x, y);
        test.printValidMoves(validMoves);
        System.out.println(test.validMovesToString(validMoves));
        if(test.movePiece(x, y, x, y-1, validMoves)){ //so send only if it's a valid move, otherwise loop (loop not implemented)
            System.out.println(test.getMoveMSG()); 
        }
        test.printBoard();
        System.out.println(test.boardToString());
        
        validMoves = test.validMoves(7, 4);
        test.printValidMoves(validMoves);
        if(test.movePiece(7, 4, 5, 6, validMoves)){
            System.out.println(test.getMoveMSG());
        }
            
        test.printBoard();
        
        validMoves = test.validMoves(4,7);
        test.printValidMoves(validMoves);
        test.movePiece(4, 7, 5, 6, validMoves);
        test.printBoard();
        
        validMoves = test.validMoves(5, 2);
        test.printValidMoves(validMoves);
        
    }
}
