package server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;
/**
 * The main class of the TCP Chess game
 * @author Zach, Kyle, and Steve
 *
 */
public class ChessServer {

	private static final int PORT = 9999;
	public static HashMap<String, PlayerInfo> players= new HashMap<String, PlayerInfo>();
	public static GameState test;
    /**
     * Runs the application. Pairs up clients that connect.
     * Creates a new thread for each player that connects.
     */
    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(PORT);
        test = new GameState();
        //test.test();
        System.out.println("Chess Server is Running");
        try {
            while (true) {
                new Player(listener.accept()).start();
                System.out.println("New client added");
            }
        } finally {
            listener.close();
        }
    }
    /**
     * Each client that connects to TCP chess gets its own listener thread.
     * @author Zach, Kyle, and Steve
     *
     */
    private static class Player extends Thread{
      Socket socket;
      BufferedReader input;
      PrintWriter output;

      /**
       * Constructs a handler thread for a given socket. Initializes
       * the input and output stream feeds.
       */
      public Player(Socket socket) {
          this.socket = socket;
          try {
              input = new BufferedReader(
                  new InputStreamReader(socket.getInputStream()));
              output = new PrintWriter(socket.getOutputStream(), true);
          } catch (IOException e) {
              System.out.println("Player lost: " + e);
          }
      }
      /**
       * This is the main game thread for Player. It loops through
       * username creation, opponent selection, gameplay between opponents.
       * At the end of the loop, a user can choose to play another game. 
       * The loop resets with username creation. 
       */
      public void run() {
    	  String username = "";
    	  String opponentUsername = "";
    	  PrintWriter opponentOutput = output; // Initialized to output to allow for compilation.
          try {
        	  // Gets username. Loops until a unique username is inputted by the client. 
              while (true) {
            	  output.println("GETUSERNAME");
                  String recUsername = input.readLine();
                  if(recUsername.startsWith("USERNAME") && recUsername.length() >= 10){
                	  username = recUsername.substring(8);
	                  System.out.println("username chosen: " + username);
	                  synchronized (players) {
	                      if (!players.containsKey(username) || username != null || 
	                    		  username.charAt(0) != ' ') {
	                          players.put(username, new PlayerInfo(output, input));
	                          
	                          output.println("NAMEACCEPTED");
	                          System.out.println("NAMEACCEPTED");
	                          break;
	                      }
	                  }
                  }
              }
              System.out.println("Completed Username Selection Stage");

              // Start player selection loop. Loops until players are paired and colors selected.
              while(true){
            	  String playerSelection = input.readLine();
            	  
            	  // Sends the list of all connected players or a message saying that only one player has registered with the server
            	  if(playerSelection.equals("GET_PLAYERLIST")){
            	  
		              if(players.size() == 1){
		            	  output.println("MESSAGE You are the only registered player. Please wait.");
		              }
		              else if(players.size() > 1){
		            	  StringBuilder str = new StringBuilder();
		            	  str.append("PLAYERLIST");
		            	  
		            	  // Gets all players names
		            	  for(String key: players.keySet()){
		            		  str.append(" ");
		            		  // If player doesn't have an opponent. The key is appended to the string buffer.
		            		  if(!players.get(key).haveOpponent){
		            			  str.append(key);
		            		  }
		            	  } 
		            	  // Sends current players to all registered users
		            	  for(String key: players.keySet()){
		            		  // Sends string of current players logged in who do not have opponents. 
		            		  if(!players.get(key).haveOpponent){
		            			  players.get(key).getOutput().println(str.toString());
		            		  }
		            	  }
		            	  System.out.println(str.toString());
		              }
            	  }
            	  
	              // Receives a chosen player from the client. Then sends challenge message to opponent.
            	  else if(playerSelection.startsWith("CHOOSEN_PLAYER")){
            		  System.out.println("playerSelection: " + playerSelection);
            		  String potentialOpponent = playerSelection.substring(15);
		              if(players.containsKey(potentialOpponent)){
		            	  System.out.println("playerSelection: " + potentialOpponent);
		            	  opponentUsername = potentialOpponent;
		            	  opponentOutput = players.get(opponentUsername).getOutput();
		            	  players.get(potentialOpponent).getOutput().println("CHALLENGE " + username);
		              }
		              else{
		            	  System.out.println("ERROR - potentialOpponent: " + potentialOpponent);
		              }
            	  }
            	  // 
            	  else if(playerSelection.startsWith("ACCEPT_OR_REJECT_GAME")){
            		  char acceptOrReject = playerSelection.charAt(22);
            		  // If user responded with 0, Accept. set opponent name to received valueS
            		  if(acceptOrReject == '0'){
            			  String acceptedOpponent = playerSelection.substring(24);
            			  if(players.containsKey(acceptedOpponent)){
            				  opponentUsername = acceptedOpponent;
    		            	  System.out.println("playerSelection: " + opponentUsername);
    		            	  opponentOutput = players.get(opponentUsername).getOutput();
    		            	  
    		            	  // removes both players names from players HashMap
    		            	  players.get(username).setOpponent();;
    		            	  players.get(opponentUsername).setOpponent();;
    		            	  
    		            	  // Message both players telling them to choose colors
    		            	  opponentOutput.println("CHOOSE_COLOR");
    		            	  output.println("CHOOSE_COLOR");
    		              }
            			  else{
            				  System.out.println("Opponent not in HashSet");
            			  }
            		  }
                 // If user responded with 1, Reject

            		  else if(acceptOrReject == '1') {
            			  System.out.println("MESSAGE " + username + " rejected your request");
                    System.out.println("opponentUsername: " + opponentUsername);
            			  opponentOutput.println("MESSAGE " + username + " rejected your request");
            		  }
            		  
            		  else System.out.println("ERROR: Unrecognized acceptOrReject Value");
            		  
            		  
            	  }
            	  else if(playerSelection.startsWith("COLOR")){

            		  //Gets color from client. Sets player's color to 
            		  char myColor = playerSelection.charAt(6);
            		  char oppColor = players.get(opponentUsername).getColor();
            		  System.out.println("oppColor: " + oppColor + " & myColor: " + myColor);
            		  
        			  //If the current player is the first to log in 
        			  if(oppColor == ' '){
        				  System.out.println("First opponent submit color");
        			  }
        			  //If the opponent chose not to pick a color
        			  else if(oppColor == 'n'){
        				  System.out.println("Opponent didn't choose a color");
        				  //If current player's color is white
        				  if(myColor == 'w'){
        					  oppColor = 'b';
        				  }
        				  //If current player's color is black
        				  else if(myColor == 'b'){
        					  oppColor = 'w';
        				  }
        				  //If current player also hasn't chose a color. Randomly assign a color
        				  else{
        					  Random r = new Random();
        					  int randInt = r.nextInt(2);
        					  if(randInt == 1){
        						  myColor = 'b';
        						  oppColor = 'w';
        					  }
        					  else{
        						  myColor = 'w';
        						  oppColor = 'b';
        					  }
        				  }
        				  
        			  }
        			  else if(oppColor == 'w'){
        				  System.out.println("Opponent choose w");
        				  //If current player's color is white
        				  if(myColor == 'w'){
        					  Random r = new Random();
        					  int randInt = r.nextInt(2);
        					  if(randInt == 1){
        						  myColor = 'b';
        						  oppColor = 'w';
        					  }
        					  else{
        						  myColor = 'w';
        						  oppColor = 'b';
        					  }            
        				  }
        				  //If current player did not choose color
        				  else if(myColor == 'n'){
        					  myColor = 'b';
        				  }
        			  }
        			  else if(oppColor == 'b'){
        				  if(myColor == 'n'){
        					  myColor = 'w';
        				  }
        				  //If current player's color is also black
        				  if(myColor == 'b'){
        					  Random r = new Random();
        					  int randInt = r.nextInt(2);
        					  if(randInt == 1){
        						  myColor = 'b';
        						  oppColor = 'w';
        					  }
        					  else{
        						  myColor = 'w';
        						  oppColor = 'b';
        					  }            
        				  }
        				  
        			  }
        			  // Synchronized area to prevent multiple threads from updating players
        			  // Sets both colors in the players HashSet
        			  synchronized(players){
        				  players.get(username).setColor(myColor);
        				  players.get(opponentUsername).setColor(oppColor);
	                      System.out.println("Completed Player Selection Stage: " + username);
	              		  System.out.println("MyColor: " + players.get(username).getColor());
	            		  System.out.println("OppColor: " + players.get(opponentUsername).getColor());
        			  }
        			  // If first player to log in, then sleep.
        			  if(players.get(opponentUsername).getColor() == ' '){
        				  try {
							Thread.sleep(420000000);
							System.out.println("Snorlax woke up! It attacked in a grumpy rage.");
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							System.out.println("Snorlax did not sleep");
							e.printStackTrace();
						}
        			  }
        			  // Else, then all colors have been set and negotiation is complete. Start game.
        			  //Starts new class of GameState. Then creates new thread
        			  //Sends start game message to both players
        			  else{
        				  output.println("START_GAME " + myColor);
        				  players.get(opponentUsername).getOutput().println("START_GAME " + oppColor);
	        			  GameState game = new GameState();
	        			  if(players.get(username).getColor() == 'w') game.run(output, input, players.get(opponentUsername).getOutput(), players.get(opponentUsername).getInput());
	        			  else  game.run(players.get(opponentUsername).getOutput(), players.get(opponentUsername).getInput(), output, input);
        			  }
        			  break;
            	  }
            	  
            	  else{
            		  System.out.println("Misunderstood communication: " + playerSelection);
            	  }
              }
              System.out.println("Exiting while loop: " + username);
                       
	      } catch (IOException e) {
	          System.out.println(e);
	      } finally {
	          // This client is going down!  Remove its name and its print
	          // writer from the sets, and close its socket.
	          try {
	        	  System.out.println("Closing");
	        	  input.close();
	        	  output.close();
	            socket.close();
	            players.remove(username);
	          } catch (IOException e) {
	          }
	      }
          System.out.println("Completed Game");

      }
    /**
     * Checks if a stalemate exists.
     * @return true if a stalemate exists and false if one does not
     */
	private boolean checkStalemate() {
		// TODO Auto-generated method stub
		return true;
	}
	/**
	 * Checks if checkMate exists
	 * @return true if check mate exists and false if it does not
	 */
	private boolean checkMate() {
		// TODO Auto-generated method stub
		return true;
	}
	/**
	 * Checks if check exists
	 * @return true if check  exists and false if it does not
	 */
	private boolean checkCheck() {
		// TODO Auto-generated method stub
		return true;
	}
	/**
	 * Checks if a move is valid
	 * @param fromX
	 * @param fromY
	 * @param toX
	 * @param toY
	 * @return
	 */
	private boolean checkMove(char fromX, char fromY, char toX, char toY) {
		// Should use checkCheck if the king is in check
		checkCheck();
		return true;
	}
    }
    /**
     * This is used as the value in the key-value pair of the players
     * HashMap. It contains information about the current user and if
     * the current user has been paired with an opponent.
     * @author Zach, Kyle, and Steve
     *
     */
    private static class PlayerInfo{
        private PrintWriter output;
        private BufferedReader input;
        private char color = ' ';
        private boolean haveOpponent = false;
        
        public PlayerInfo(PrintWriter output, BufferedReader input){
        	this.input = input;
        	this.output = output;
        }
        public void setColor(char c){
        	color = c;
        }
        public PrintWriter getOutput(){
        	return output;
        }
        public char getColor(){
        	return color;
        }
        public void setOpponent(){
        	haveOpponent = true;
        }
        public BufferedReader getInput(){
        	return input;
        }

    }
}