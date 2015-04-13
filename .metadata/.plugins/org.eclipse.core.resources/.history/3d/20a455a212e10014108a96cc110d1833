package Shared;

public class Message {
// FIELDS
	private int type; // Message type for easy accessing the message type
	private String username; // The username of the client
	private boolean acceptGame; // True => Accept, False => Reject
	private String moveFromX; // The lowercase letter of the chess piece before moving
	private int moveFromY; // The number of the chess piece before moving
	private String moveToX; // The lowercase letter of the chess piece after moving
	private int moveToY; // The number of the chess piece after moving
	private boolean acceptMove; // True => Accept, False => Reject
	
	
// EMPTY CONSTRUCTOR
	public Message(){
	}
	/**
	 * connectMessage class constructor
	 */

// METHODS
	
// METHODS THAT CALL CONSTRUCTORS AND CREATE THE APPROPRIATE MESSAGES
	public static Message connectMessage(String username){
		Message m = new Message();
		m.setType(0);
		m.setUsername(username);
		return m;
	}

	public static Message chooseStartMessage(){
		
		return new Message();
	}
	public static Message moveMessage(){
		
		return new Message();
	}
	public static Message connectToOtherPlayerMessage(){
		
		return new Message();
	}
	public static Message chooseGoFirstMessage(){
		
		return new Message();
	}
	public static Message restartGameMessage(){
		
		return new Message();
	}
	public static Message startGameMessage(){
		
		return new Message();
	}
	public static Message notValidMoveMessage(){
		
		return new Message();
	}
	public static Message validMoveMessage(){
		
		return new Message();
	}
	public static Message winMessage(){
		
		return new Message();
	}
	
	
	
//METHODS - GETTER AND SETTERS	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isAcceptGame() {
		return acceptGame;
	}
	public void setAcceptGame(boolean acceptGame) {
		this.acceptGame = acceptGame;
	}
	public String getMoveFromX() {
		return moveFromX;
	}
	public void setMoveFromX(String moveFromX) {
		this.moveFromX = moveFromX;
	}
	public int getMoveFromY() {
		return moveFromY;
	}
	public void setMoveFromY(int moveFromY) {
		this.moveFromY = moveFromY;
	}
	public String getMoveToX() {
		return moveToX;
	}
	public void setMoveToX(String moveToX) {
		this.moveToX = moveToX;
	}
	public int getMoveToY() {
		return moveToY;
	}
	public void setMoveToY(int moveToY) {
		this.moveToY = moveToY;
	}
	public boolean isAcceptMove() {
		return acceptMove;
	}
	public void setAcceptMove(boolean acceptMove) {
		this.acceptMove = acceptMove;
	}	
	
	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append("\ttype: " + type);
		str.append("\n\tusername: " + username);
		str.append("\n\tacceptGame: " + acceptGame);
		str.append("\n\tmoveFromX: " + moveFromX);
		str.append("\n\tmoveFromY: " + moveFromY);

		return str.toString();
	}
}
