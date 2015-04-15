package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class GameState {
	// !-- Need two Buffers here, un-initialized --!\\
	
	public GameState(){
		//!-- Initialize buffers here, add parameters --!\\
	}
	// The input and output parameters provide communications to clients through input and output.  
	public void run(PrintWriter whiteOutput, BufferedReader whiteInput, PrintWriter blackOutput, BufferedReader blackInput) throws IOException{
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
        
        
        // PrintWriters send messages to the respective client
        // BufferedREaders accept inputs from the respective clients
        PrintWriter output;
        BufferedReader input;
        
        while(runGame){
            while(!switchTurn){
            	if(isWhite){
            		//!-- make the local buffer equal to the player that's white --!\\
            		output = whiteOutput;
            		input = whiteInput;
            	}
            	else{
            		//!-- make the local buffer equal to the player that's white --!\\
            		output = blackOutput;
            		input = blackInput;
            	}
            	//Sends message to current player saying that it is their turn. Waits for a message from them
            	output.println("YOUR_MOVE");            	
                switchTurn = false;
                
                /* -- Step 1: User sends x and y value to the server that they want to check for valid moves.
                 * This makes the assumption that it's sending a string with two values, x and y. 
                 * This also makes the assumption the piece clicked could be null.
                 */
                // Waits for response from the user
// Receives message <CHECK_MOVE ##>
            	String step1Response = input.readLine();
            	
                successfulMove = false;
                //       coord = "11"; //-- ! GET THE VALUES FROM THE CLIENT ! --\\
                coord = step1Response.substring(11);
                if(coord.equals("99"))break;
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
                output.println("VALID_MOVES " + strValidMoves);
                if(validMoves == null) break; //meaning this loop starts to repeat again
                
                
                
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
// Receives message of "MOVE ##"
                String step4Response = input.readLine();
            	coord = step4Response.substring(5);
                //coord = "12"; //-- ! GET THE VALUES FROM THE CLIENT --\\
                newX = coord.charAt(0) - 48;
                newY = coord.charAt(1) - 48;
                successfulMove = test.movePiece(x, y, newX, newY, validMoves);

                // If the move was successful, sends the new state, otherwise sends why it was a fail.
                strNewState = successfulMove ? test.boardToString() : test.whyInvalid() ? "KING" : "INVALID";
                // !-- Send "strNewState" to both clients --! \\
               whiteOutput.println("NEW_BOARD" + strNewState);
               blackOutput.println("NEW_BOARD" + strNewState);

                test.printBoard();
                
                if(test.isCheckMate()){
                    // !-- Send end game message to both clients --\\
                	if(isWhite) {
                		whiteOutput.println("WIN");
                    	blackOutput.println("LOSE");
                	}
                	else{
                    	blackOutput.println("WIN");
                		whiteOutput.println("LOSE");
                	}
                	
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
