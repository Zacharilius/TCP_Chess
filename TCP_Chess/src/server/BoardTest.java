package server;

public class BoardTest {
	public BoardTest(){}
	
	public void test(){
		Board test = new Board();
        test.printBoard();
        boolean[][] validMoves = new boolean[8][8];
        boolean successfulMove = false;
        int x, y, newX, newY;
        String coord, strValidMoves, strNewState;
        
        /* GENERAL TURN STRUCTURE */
        
        /* -- Step 1: User sends x and y value to the server that they want to check for valid moves.
         * This makes the assumption that it's sending a string with two values, x and y. 
         * This also makes the assumption that x & y is only sent up if the value is a valid piece.
         */
        successfulMove = false;
        coord = "11"; //this is a test, this would actually get the values.
        x = coord.charAt(0) - 48;
        y = coord.charAt(1) - 48;
        
        /* -- Step 2: Server would retrieve the valid moves of said piece.
         */
        validMoves = test.validMoves(x, y);
        test.printValidMoves(validMoves);
        
        /* -- Step 3: Server would send the valid moves to the client in a String
         * of 1's and 0's. If the piece has no valid moves, the server should either
         * tell the user that there are no valid moves for that piece or do nothing
         * and prompt them to enter another move.
         */
        
        strValidMoves = test.validMovesToString(validMoves);
        // !-- THIS IS WHERE YOU WOULD SEND THE MESSAGE --! \\
        
        /* -- Step 4: Client would send the server another x and y to move the
         * piece. If this is an invalid move (only case is if it endangers the 
         * king), the function will return false. If this returns false, let the
         * user know and restart at Step 1. If it returns true, send the message
         * to both clients on the new state of the system.
         * 
         * Server makes the assumption that the user must send up a vaild move. 
         * Client will have a list of the valid moves already sent to them so
         * they can regulate this and send the proper ones to the server.
         */
        
        coord = "12";
        newX = coord.charAt(0) - 48;
        newY = coord.charAt(1) - 48;
        successfulMove = test.movePiece(x, y, newX, newY, validMoves);
        if(!successfulMove){
            System.out.println("This move endangers the king! Try another move!");
            //! -- RESET THEM AT STEP 1 -- ! \\
        }
        else{
            strNewState = test.boardToString();
            // !-- THIS IS WHERE YOU WOULD SEND THE MESSAGE --! \\
            if(test.isCheckMate()){
                // !-- SEND MESSAGE, END GAME --\\
                System.out.println("Checkmate! [INSERT TEAM NAME] WINS!");
            }
        }
        test.printBoard();
        
        /* Step 5: Switch the turn order and repeat at step 1!
         * 
         */
        
        
        
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
        /*
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
        */
        
        
	}
}
