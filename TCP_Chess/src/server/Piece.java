package server;

public class Piece {
    private String type;
    private boolean team; //white = true, black = false | No that's not racist.
    private boolean firstTurn; //only applicable to pawns
    
    public Piece(){
        type = null;
        team = true;
        firstTurn = true;
    }
    
    public Piece(String t, boolean color){
        type = t;
        team = color;
        firstTurn = true;
    }
    
    public String getType(){
        return type;
    }
    
    public boolean getTeam(){
        return team;
    }
    
    public boolean isFirstTurn(){
        return firstTurn;
    }
    
    /**
     * Only applies to pawns. Sets the boolean to "false" to ensure that the
     * pawn's first turn is no longer false.
     */
    public void setTurn(){
        firstTurn = false;
    }
}
