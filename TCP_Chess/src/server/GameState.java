package server;

public class GameState {
	// !-- Need two Buffers here, un-initialized --!\\
	
	public GameState(){
		//!-- Initialize buffers here, add parameters --!\\
	}
	
	public void run(){
		Board test = new Board();
        test.printBoard();
        boolean[][] validMoves = new boolean[8][8];
        boolean successfulMove = false;
        boolean isWhite = true;
        boolean runGame = true;
        boolean switchTurn = false;
        int x, y, newX, newY;
        String coord, strValidMoves, strNewState;
        
        //!-- Create a local buffer to use throughout here --!\\
        //!-- We will reference this throughout the loop --! \\
        
        while(runGame){
            while(!switchTurn){
            	if(isWhite){
            		//!-- make the local buffer equal to the player that's white --!\\
            	}
            	else{
            		//!-- make the local buffer equal to the player that's white --!\\
            	}
            	
            	
                switchTurn = false;
                
                /* -- Step 1: User sends x and y value to the server that they want to check for valid moves.
                 * This makes the assumption that it's sending a string with two values, x and y. 
                 * This also makes the assumption the piece clicked could be null.
                 */
                successfulMove = false;
                coord = "11"; //-- ! GET THE VALUES FROM THE CLIENT ! --\\
                x = coord.charAt(0) - 48;
                y = coord.charAt(1) - 48;

                /* -- Step 2: Server would retrieve the valid moves of said piece. If NULL, loop them back
                 * to Step 1 so they can choose another piece.
                 */
                validMoves = test.validMoves(isWhite, x, y);
                test.printValidMoves(validMoves);

                /* -- Step 3: Server would send the valid moves to the client in a String
                 * of 1's and 0's. If the piece has no valid moves, it will return null,
                 * telling the client to choose another piece. 
                 */

                strValidMoves = test.validMovesToString(validMoves);
                // !-- Send "strValidMoves" to the client --! \\
                if(validMoves == null) {break;} //meaning this loop starts to repeat again

                /* -- Step 4: Client would send the server another x and y to move the
                 * piece. If this is an invalid move (invalid move OR endangers king), 
                 * the function will return false. If this returns false, let the
                 * user know why (Invalid Input OR Endangered King) and loop back to step1. 
                 * If it returns true, send the message to both clients on the new state 
                 * of the system.
                 * 
                 * Server makes the assumption that the user must send up a vaild move. 
                 * Client will have a list of the valid moves already sent to them so
                 * they can regulate this and send the proper ones to the server.
                 */

                coord = "12"; //-- ! GET THE VALUES FROM THE CLIENT --\\
                newX = coord.charAt(0) - 48;
                newY = coord.charAt(1) - 48;
                successfulMove = test.movePiece(x, y, newX, newY, validMoves);

                // If the move was successful, sends the new state, otherwise sends why it was a fail.
                strNewState = successfulMove ? test.boardToString() : test.whyInvalid() ? "KING" : "INVALID";
                // !-- Send "strNewState" to both clients --! \\
                test.printBoard();
                
                if(test.isCheckMate()){
                    // !-- Send end game message to both clients --\\
                    runGame = false;
                }

                /* Step 5: Switch the turn order if the move was successful. Otherwise
                 * 
                 */
                
                runGame = false;
                if(successfulMove){
                    isWhite = !isWhite;
                    switchTurn = true;
                }
                
            } //end of one turn, turn switches if their move was successful.
                            
        } //end of entire loop, ends if checkMate was achieved
        
        
	}
}
