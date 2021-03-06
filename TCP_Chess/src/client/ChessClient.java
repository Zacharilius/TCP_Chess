package client;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BoxLayout;


/**
 * A client for TCP Chess. The 
 * @author Zach, Kyle, and Steve
 *
 * Steve's attempt to add a gui
 * 2015.03.30
 *
 */
public class ChessClient {

    private static final int PORT = 9999;

    private static JFrame frame = new JFrame("TCP Chess");
    private JLabel messageLabel = new JLabel("");
    private static ImageIcon icon;
    //private ImageIcon opponentIcon;

    private MySquare[][] board = new MySquare[8][8];
    private MyTimer timepiece=new MyTimer();
    //private MySquare currentSquare;
    private boolean playerIsBlack=false; //Change this value upon selection of color. DO NOT HARDCODE!
    private boolean isYourTurn=true; //Change this value upon start(true)/end(false) of your turn. DO NOT HARDCODE!
    private boolean readyToMove=false; //Change this value to true upon reception of valid moves. false when turn starts, and false when invalid move is chosen DO NOT HARDCODE!
    private JPanel boardPanel = new JPanel();
    private JPanel timerPanel = new JPanel();

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    
    private static String username;

    /**
     * Constructs the client by connecting to a server, laying out the
     * GUI and registering GUI listeners.
     */
    public ChessClient(String serverAddress) throws Exception {

        // Setup networking
        socket = new Socket(serverAddress, PORT);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

      
        // Layout GUI
        messageLabel.setBackground(Color.lightGray);
        //frame.getContentPane().add(messageLabel, "South");
        //frame.getContentPane().add(messageLabel);
        timerPanel.setBackground(Color.lightGray);
        timerPanel.setLayout(new BoxLayout(timerPanel,BoxLayout.Y_AXIS));
        timerPanel.add(messageLabel);
        timerPanel.add(timepiece);

        boardPanel.setBackground(Color.black);
        boardPanel.setLayout(new GridLayout(board.length, board[0].length, 2, 2));
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                final int fi =i;
                final int fj =j;
                board[i][j] = new MySquare(i,j);
                board[i][j].addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        //currentSquare = board[fi][fj];
                    	if(isYourTurn){
                    		if(readyToMove){
                    			if(board[fi][fj].isEnabled()){
                        			out.println("MOVE "+fj+""+fi);
                    			}
                    			else{
                    				unhighlightAll();
                    				//out.println("MOVE 99");
                    			}
                    		}
                    		else{
                    			if(board[fi][fj].isEnabled()){
                        			out.println("CHECK_MOVE "+fj+""+fi);
                    			}
                    			else{
                    				//DO NOTHING!!!!
                    			}
                    		}
                    	}
                    	else{
                    		out.println("Hey! It's not your turn "+(playerIsBlack?"Black":"White")+"y!");
                    	}
                    }});
                boardPanel.add(board[i][j]);
            }
        }
        frame.getContentPane().add(boardPanel, "Center");
        frame.getContentPane().add(timerPanel, "South");
        

        //highlightSpaces("1000000000000000000000000000001110011010110110101011100110110011");
        //updateBoardState("kKkKkKkpQqQqQqQP_b_B_rRp_______p_______P___N___p_______R_______n");
        //updateBoardState("rnbqkbnrpppppppp________________________________PPPPPPPPRNBQKBNR");
    }
    /**
     * Runs ChessClient. If args is empty, it asks the user
     * to input a server address. If args contains a value, 
     * then that value is used for the server address. 
     * It Initiates and starts the main frame. Currently a 
     * tic-tac-toe game is displayed. This needs to be changed. 
     * This also controls the continue game loop.
     */
    @SuppressWarnings("static-access")
    public static void main(String[] args) throws Exception {
        while (true) {
            // Get server address
            String serverAddress;
            if(args.length == 0) serverAddress = getServerAddress();
            else serverAddress = args[0];       
            
            // Starts the ChessClient
            ChessClient client = new ChessClient(serverAddress);
            client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            client.frame.setSize(500, 500);
            client.frame.setVisible(true);
            client.frame.setResizable(false);
            client.play();
            if (!client.wantsToPlayAgain()) {
                break;
            }
        }
    }
    /**
     * Prompts for and return the address of the server.
     */
    private static String getServerAddress() {
        return JOptionPane.showInputDialog(
            frame,
            "Enter Server IP Address:",
            "Welcome to TCP Chess",
            JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Prompt for and return the desired screen name.
     */
    private static String getUsername() {
        return JOptionPane.showInputDialog(
            frame,
            "Choose a screen name:",
            "Username selection",
            JOptionPane.PLAIN_MESSAGE);
    }   
    /**
     * Accepts a player string. For example, it could accept
     * "player1 player2 player3" Adds each player into an object
     * array. Then Opens a JOptionPane with a drop down menu for
     * player1, player2, player3. Returns the string value for
     * the selected player. 
     * @param players A string value received from ChessServer
     * of all available players.
     * @return A string of the selected player name.
     */
    private static String choosePlayer(String players){
        Object[] allPlayers = players.split(" ");
        
        Object[] allPlayersMinusClient = new Object[allPlayers.length - 1];
        // Copies into the new array all values except the current username
        
        int j = 0;
        for(int i = 0; i < allPlayers.length; i++){
            if(!((String)allPlayers[i]).equals(username)){
                allPlayersMinusClient[j++] = allPlayers[i];
            }
        }
        if(allPlayersMinusClient.length != 0){
            return (String)JOptionPane.showInputDialog(
                    frame,
                    "Choose a player or select cancel to wait for another player to choose you:",
                    "Player Selection",
                    JOptionPane.PLAIN_MESSAGE,
                    icon,
                    allPlayersMinusClient,
                    allPlayersMinusClient[0]);
        }
        return "";
    }
    /**
     * A prompt with do you want to play the challenger?
     * @param challengeString The challenger's name
     * @return The selection of hte client. 0 for yes and 1 for no.
     */
    private static int challenge(String challengeString){
        String challenger = challengeString.substring(10);
        return (JOptionPane.showConfirmDialog(
                frame,
                "Would you like to play " + challenger,
                null,
                JOptionPane.YES_NO_OPTION));
    }
    
    /**
     * A prompt to choose a color for chess.
     * @return The char of the selected color. The
     * char 'b' for black and 'w' for white.
     */
    private static char chooseColor(){
        Object[] options = {"Black", "White", "I don't want to choose"};
        int n = JOptionPane.showOptionDialog(frame,
                "Please Choose a Starting Color",
                "Color Selection",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button title
        if(n == 0) return 'b';
        else if(n == 1) return 'w';
        return 'n';
    }
        
    /**
     * The main thread of the client will listen for messages
     * and provides a GUI for responses. This class has three
     * loop for entering user information. First, the username
     * loop continues until a correct username has been entered.
     * Second, the Playerchoice loop continues until two clients
     * have connected, and they have chosen colors. Third, the
     * game loop continues until the game has completed. 
     */
    public void play() throws Exception {
        String response;
        char color = ' ';
        messageLabel.setText("Welcome to TCP Chess");
        
        //Get Username loop
        while (true) {
            response = in.readLine();
            if (response.startsWith("GETUSERNAME")) {
                messageLabel.setText("Please enter a username");
                username = getUsername();
                out.println("USERNAME" + username);
            } else if (response.startsWith("NAMEACCEPTED")) {
                messageLabel.setText("Username Accepted");
                break;
            } else if (response.startsWith("MESSAGE")) {
                messageLabel.setText(response.substring(8));
            }
        }
        
        // Get PlayerChoice Loop
        out.println("GET_PLAYERLIST");
        while(true){
            response = in.readLine();
            if(response.startsWith("PLAYERLIST")){
                messageLabel.setText("Opponent Selection");
                String players = response.substring(10);

                // Display GUI of listed Players and respond with chosen player
                if(players.length() > 0){
                    String playerChoice = choosePlayer(players);
                    if(playerChoice != null){
                        out.println("CHOOSEN_PLAYER " + playerChoice);
                    }
                }
            }
            else if(response.startsWith("MESSAGE")){
                messageLabel.setText(response.substring(8));
            }
            else if(response.startsWith("CHALLENGE")){
                String challengerUsername = response.substring(10);
                int choice = challenge(response);
                out.println("ACCEPT_OR_REJECT_GAME " + choice + " " + challengerUsername);

            }

            // Get Color Loop
            else if(response.startsWith("CHOOSE_COLOR")){
                System.out.println("CHOOSE_COLOR");
                color = chooseColor();
                System.out.println("CHOOSE_COLOR: " + color);
                out.println("COLOR " + color);
            }

            else if(response.startsWith("START_GAME")){
                color = response.charAt(11);
                messageLabel.setText("Let's Play! You are playing as " + color);
               
                // Create both boards according to the received color
                if(color == 'w') playerIsBlack = false;
                else playerIsBlack = true;
                
                if(!playerIsBlack){//Move this logic to place where color gets chosen AFTER playerIsBlack has been set
                    flipBoard();//When this is called, white is put on the bottom. Default: black is on bottom
                }
                timepiece.startTimer();
                break;
            }
        }


        // Game Loop
        while (true) {
            response = in.readLine();
            System.out.println("response: " + response);
            if(response.startsWith("YOUR_MOVE")){//MUST BE CALLED AFTER NEW_BOARD to set isYourTurn to true. NEW_BOARD sets isYourTurn to false
            	//Activate the player's board and allow move
            	
            	
                messageLabel.setText("Your move");
                isYourTurn=true;
                timepiece.setTurn(isYourTurn);
            	
            }else if(response.startsWith("VALID_MOVES")){
                if(response.length() <= 20){
                    System.out.println("No valid moves for that piece.");
                    readyToMove=false;
                }
                else{

                	String validMoves = response.substring(12);
                	System.out.println(response);
                	System.out.println(validMoves);
                	// parse the validMoves string and highlight the valid moves on the board
                    if(validMoves!=null){
                    	highlightSpaces(validMoves.trim());
                        readyToMove=true;
                    }
                    else{
                    	System.out.println("No valid moves for that piece.");
                        readyToMove=false;
                    }
            	}

        	}else if(response.startsWith("NEW_BOARD")) {
                messageLabel.setText("Board updated");
                String newBoard = response.substring(10);
                System.out.println(response);
                System.out.println(newBoard);
                // Deactivate board
                // Update display to show updated board. 
                isYourTurn=false;
                readyToMove=false;
                timepiece.setTurn(isYourTurn);
                updateBoardState(newBoard.trim());
                unhighlightAll();
                
            }else if (response.startsWith("WIN")) {
                messageLabel.setText("You win!!!");
                timepiece.stopTimer();
                break;
            } else if (response.startsWith("LOSE")) {
                messageLabel.setText("You lose!!!");
                timepiece.stopTimer();
                break;
            } else if (response.startsWith("STALEMATE")) {
                messageLabel.setText("Stalemate");
                break;
            } else if (response.startsWith("MESSAGE")) {
                messageLabel.setText(response.substring(8));
            }
        }
    }

    /**
     * Prompts the user if they want to continue playing
     * @return 0 for yes and 1 for no.
     */
    private boolean wantsToPlayAgain() {
        int response = JOptionPane.showConfirmDialog(frame,
            "Want to play again?",
            null,
            JOptionPane.YES_NO_OPTION);
        frame.dispose();
        return response == JOptionPane.YES_OPTION;
    }
    
   //v~~~~~~~~~~~~~~~~~~~~~~~~~~~v~~~~~~~~~~~~~~~~~~~~~~~~~v~GUI FUNCTIONS!~v~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~v~~~~~~~~~~~~~~~~~~~~v~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~v
    
    /**
     * Highlight and enable valid squares received from the server.  Disable invalid squares
     * @param spaces: String representation of spaces. 1=potential move. 0=not potential move
     */
    private void highlightSpaces(String spaces){
    	if(spaces.length()!=64){
    		System.err.println("Error: highlightSpaces. String not long enough");
    		return;
    	}
    	if(!playerIsBlack){
            spaces=new StringBuilder(spaces).reverse().toString();
    	}
    	for(int i=0;i<spaces.length();++i){
    		if(spaces.charAt(i)=='1'){
        		board[i/board.length][i%board.length].highlightMe();
        		board[i/board.length][i%board.length].enable();
    		}
    		else{
    			board[i/board.length][i%board.length].disable();
    		}
    	}
    }
    
    /**
     * Unhighlight all board squares.
     */
    private void unhighlightAll(){
    	for(int i=0;i<board.length;i++){
    		for(int j=0;j<board[0].length;++j){
    			board[i][j].unhighlightMe();
    		}
    	}
    }
    
    private void updateBoardState(String state){
    	if(state.length()!=64){
    		System.err.println("Error: updateBoardState. String not long enough");
    		return;
    	}
    	if(!playerIsBlack){
            state=new StringBuilder(state).reverse().toString();
    	}
    	for(int i=0;i<state.length();++i){
    		board[i/board.length][i%board.length].changePiece(state.charAt(i));
    		if(Character.isLowerCase(state.charAt(i))){//white piece
    			if(!playerIsBlack){
        			board[i/board.length][i%board.length].enable();
    			}
    			else{
        			board[i/board.length][i%board.length].disable();
    			}
    		}
    		else if(Character.isUpperCase(state.charAt(i))){//black piece
    			if(!playerIsBlack){
        			board[i/board.length][i%board.length].disable();
    			}
    			else{
        			board[i/board.length][i%board.length].enable();
    			}
    		}
    		else{//vacant spot
    			board[i/board.length][i%board.length].disable();
    		}
    	}
    }
    
    private void flipBoard(){
    	for(int i=0;i<board.length;i++){
    		for(int j=0;j<board[i].length/2;++j){
    			MySquare temp= board[i][j];
    			board[i][j]=board[board.length-i-1][board.length-j-1];
    			board[board.length-i-1][board.length-j-1]=temp;
    		}
    	}
    	boardPanel.removeAll();
    	messageLabel.setBackground(Color.lightGray);
        //frame.getContentPane().add(messageLabel, "South");
        timerPanel.setBackground(Color.lightGray);
        timerPanel.setLayout(new BoxLayout(timerPanel,BoxLayout.Y_AXIS));
        timerPanel.add(messageLabel);
        timerPanel.add(timepiece);

        boardPanel.setBackground(Color.black);
        boardPanel.setLayout(new GridLayout(board.length, board[0].length, 2, 2));
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                final int fi =i;
                final int fj =j;
                board[i][j].removeMouseListener(board[i][j].getMouseListeners()[0]);
                board[i][j].addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        //currentSquare = board[fi][fj];
                    	if(isYourTurn){
                    		if(readyToMove){
                    			if(board[fi][fj].isEnabled()){
                        			out.println("MOVE "+(8-fj-1)+""+(8-fi-1));
                    			}
                    			else{
                    				unhighlightAll();
                    				//out.println("MOVE99");
                    			}
                    		}
                    		else{
                    			if(board[fi][fj].isEnabled()){
                        			out.println("CHECK_MOVE "+(8-fj-1)+""+(8-fi-1));
                    			}
                    			else{
                    				//DO NOTHING!!!!
                    			}
                    		}
                    	}
                    	else{
                    		out.println("Hey! It's not your turn "+(playerIsBlack?"Black":"White")+"y!");
                    	}
                    	/*
                        if(board[fi][fj].isEnabled()){
                            out.println("Clicked ON REVERSE" + (8-fi-1)+","+(8-fj-1));
                        }
                        */
                    }});
                boardPanel.add(board[i][j]);
            }
        }
        frame.getContentPane().add(boardPanel, "Center");
        frame.getContentPane().add(timerPanel, "South");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}