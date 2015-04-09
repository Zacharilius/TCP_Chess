package server;

public class Board {
    private Piece[][] pieces; //first column = x, second = y;
    private int whiteKingX, whiteKingY, blackKingX, blackKingY;
    private boolean whiteKingCheck, blackKingCheck;
    private String moveMSG;
    
    public Board(){
        initialize();        
    }
    
    private void initialize(){
        pieces = new Piece[8][8];
        whiteKingCheck = blackKingCheck = false;
        moveMSG = " ";
        
        //Initialize Rooks (4 Corners)
        pieces[0][0] = new Piece("Rook", true);
        pieces[7][0] = new Piece("Rook", true);
        
        pieces[0][7] = new Piece("Rook", false);
        pieces[7][7] = new Piece("Rook", false);
        
        //Initialize Knights (Next to the Rooks)
        pieces[1][0] = new Piece("Knight", true);
        pieces[6][0] = new Piece("Knight", true);
        
        pieces[1][7] = new Piece("Knight", false);
        pieces[6][7] = new Piece("Knight", false);
        
        //Initialize Bishops (Next to the Knights)
        pieces[2][0] = new Piece("Bishop", true);
        pieces[7][4] = new Piece("Bishop", true); // 5,0
        
        pieces[2][7] = new Piece("Bishop", false);
        pieces[5][7] = new Piece("Bishop", false);
        
        //Initialize King & Queen
        whiteKingX = 4; whiteKingY = 0; blackKingX = 4; blackKingY = 7;
        
        pieces[3][0] = new Piece("Queen", true);
        pieces[whiteKingX][whiteKingY] = new Piece("King", true);
        
        pieces[3][7] = new Piece("Queen", false);
        pieces[blackKingX][blackKingY] = new Piece("King", false);
        
        //Initialize Pawns
        for(int i = 0; i < 8; i++){
            pieces[i][1] = new Piece("Pawn", true);
        }
        
        for(int j = 0; j < 8; j++){
            pieces[j][6] = new Piece("Pawn", false);
        }
    }
    
    
    public void printBoard(){
        Piece temp;
        char team;
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                temp = pieces[x][y];
                if(temp == null){
                    System.out.print(String.format("|%s", "--"));
                }
                else{
                    if(temp.getTeam()){
                        team = 'W';
                    }
                    else{
                        team = 'B';
                    }
                    System.out.print(String.format("|%s%c", temp.getType().substring(0, 1), team));
                }
            }
            System.out.println("|");
        }
        System.out.println("=========== ========== =========");
    }
    
    public boolean[][] validMoves(int x, int y){
        boolean[][] moves = new boolean[8][8];
        if(pieces[x][y] == null){
            return null;
        }
        String type = pieces[x][y].getType();
        switch(type){
            case "Rook":
                moves = getValidRook(x,y); break;
            case "Pawn":
                moves = getValidPawn(x,y); break;
            case "Knight":
                moves = getValidKnight(x,y); break;
            case "Bishop":
                moves = getValidBishop(x,y); break;
            case "Queen":
                boolean[][] horizontal = getValidRook(x,y);
                boolean[][] diagonal = getValidBishop(x,y);
                moves = getValidQueen(horizontal, diagonal);
                break;
            case "King":
                moves = getValidKing(x,y); break;
            default:
                moves = null;
                break;
        }
        //printValidMoves(moves);
        return moves;
    }
    
    private boolean placesInCheck(int x, int y){
        boolean[][] moves = validMoves(x,y);
        boolean team = pieces[x][y].getTeam();
        int kingX, kingY;
        String oppTeamName; //unnecessary, but good for printing
        if(team){ //team player is white, so get black king's location
            kingX = blackKingX;
            kingY = blackKingY;
            oppTeamName = "Black";
        }
        else{ //team player is black, so get white king's location
            kingX = whiteKingX;
            kingY = whiteKingY;
            oppTeamName = "White";
        }
        
        if(moves[kingX][kingY]){
            System.out.println("!!! " + oppTeamName + "'s King is in danger !!!");
            return true;
        }
        return false;
    }
    
    private boolean placesInCheckmate(boolean team){
        boolean[][] moves;
        boolean canEscape = false;
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(pieces[j][i] != null && pieces[j][i].getTeam() != team){
                    moves = validMoves(j,i);
                    if(moves != null){
                        canEscape = canEscapeCheck(j,i,moves);
                        if(canEscape){
                            return false;
                        }
                    }
                }
            }
        }
        
        System.out.println("CHECKMATE!");
        return true;
    }
    
    private boolean canEscapeCheck(int x, int y, boolean[][] moves){
        Piece temp;
        boolean check, isKing;
        isKing = false;
        boolean team = pieces[x][y].getTeam();
        String piece = pieces[x][y].getType();
        int oldKingX, oldKingY;
        oldKingX = oldKingY = 0;
        if("King".equals(piece)){
            
            isKing = true;
            if(team){ //whiteKing
                oldKingX = whiteKingX;
                oldKingY = whiteKingY;
            }
            else{
                oldKingX = blackKingX;
                oldKingY = blackKingY;
            }
        }
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(moves[j][i]){
                    temp = pieces[j][i];
                    pieces[j][i] = pieces[x][y];
                    pieces[x][y] = null;
                    if(isKing){
                        if(team){
                            whiteKingX = j;
                            whiteKingY = i;
                        }
                        else{
                            blackKingX = j;
                            blackKingY = i;
                        }
                    }
                    check = endangersKing(team);
                    pieces[x][y] = pieces[j][i];
                    pieces[j][i] = temp;
                    
                    if(isKing){
                        if(team){
                            whiteKingX = oldKingX;
                            whiteKingY = oldKingY;
                        }
                        else{
                            blackKingX = oldKingX;
                            blackKingY = oldKingY;
                        }
                    }
                    
                    if(!check){ //meaning this move makes it so the king is no longer in danger
                        //Just a simple check
                        System.out.println("Escape still possible! Piece at (" + x + ", " + 
                                y + ") [" + piece + "] can save it!");
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    private boolean endangersKing(boolean team){
        boolean[][] moves = new boolean[8][8];
        int kingX, kingY;
        if(team){ //white
            kingX = whiteKingX;
            kingY = whiteKingY;
        }
        else{ //black
            kingX = blackKingX;
            kingY = blackKingY;
        }
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(pieces[j][i] != null && pieces[j][i].getTeam() != team){
                    moves = validMoves(j,i);
                    if(moves != null && moves[kingX][kingY]){
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    //--- STEVEN J MOON, THIS IS THE CODE FOR THE HIGHLIGHTS! -- \\
    public void printValidMoves(boolean[][] validMoves){
        boolean temp;
        if(validMoves == null){
            System.out.println("No valid moves");
        }
        else{
            for(int y = 0; y < 8; y++){
                for(int x = 0; x < 8; x++){
                    temp = validMoves[x][y];
                    if(temp){
                        System.out.print(String.format("|%2s", "O"));
                    }
                    else{
                        System.out.print(String.format("|%2s", "X"));
                    }
                }
                System.out.println("|");
            }
        }
        System.out.println("=========== ========== =========");
    }
    
    private boolean[][] getValidRook(int x, int y){
        boolean[][] moves = new boolean[8][8];
        boolean hasMoves = false; //boolean used to check if any valid moves
        
        //checking left
        for(int a = x-1; a >= 0; a--){
            if(isEmptySpace(a,y)){
                hasMoves = true;
                moves[a][y] = true;
            }
            else{
                if(isEnemyPiece(x,y,a,y)){ 
                    hasMoves = true;
                    moves[a][y] = true;
                }
                break;
            }
        }
        
        //checking right
        for(int b = x+1; b < 8; b++){
            if(isEmptySpace(b,y)){
                hasMoves = true;
                moves[b][y] = true;
            }
            else{
                if(isEnemyPiece(x,y,b,y)){
                    hasMoves = true;
                    moves[b][y] = true;
                }
                break;
            }
        }
        
        //checking up
        for(int d = y-1; d >= 0; d--){
            if(isEmptySpace(x,d)){
                hasMoves = true;
                moves[x][d] = true;
                //System.out.println("x:")
            }
            else{
                if(isEnemyPiece(x,d,x,y)){
                    hasMoves = true;
                    moves[x][d] = true;
                }
                break;
            }
        }
        
        //checking down
        for(int c = y+1; c < 8; c++){
            if(isEmptySpace(x,c)){
                hasMoves = true;
                moves[x][c] = true;
            }
            else{
                if(isEnemyPiece(x,c,x,y)){
                    hasMoves = true;
                    moves[x][c] = true;
                }

                break;
            }
        }
        
        
        if(!hasMoves) {
            return null;
        }
        return moves;
    }
    
    private boolean[][] getValidPawn(int x, int y){
        boolean[][] moves = new boolean[8][8];
        boolean hasMoves = false; //boolean used to check if any valid moves
        boolean team = pieces[x][y].getTeam();
        int inc;
        if(team){
            inc = 1;
        }
        else{
            inc = -1;
        }
        
        if(isEmptySpace(x,y+inc)){
            moves[x][y+inc] = true;
            hasMoves = true;
            if(pieces[x][y].isFirstTurn() && isEmptySpace(x,y+inc*2)){
                moves[x][y+inc*2] = true;
            }
        }
        
        if(x + 1 < 8 && !isEmptySpace(x+1,y+inc) && isEnemyPiece(x,y,x+1,y+inc)){
            moves[x+1][y+inc] = true;
            hasMoves = true;
        }
            
        if(x - 1 >= 0 && !isEmptySpace(x-1,y+inc) && isEnemyPiece(x,y,x-1,y+inc)){
            moves[x-1][y+inc] = true;
            hasMoves = true;
        }

        if(!hasMoves){
            return null;
        }
        return moves;
    }
    
    private boolean[][] getValidKnight(int x, int y){
        boolean[][] moves = new boolean[8][8];
        boolean hasMoves = false;
        int xInc, yInc;
        
        //up 2, left 1
        for(int i = 0; i < 8; i++){
            switch(i){
                case 0: //up 2, left 1
                    xInc = -1; yInc = -2; break;
                case 1: //up 1, left 2
                    xInc = -2; yInc = -1; break;
                case 2: //up 2, right 1
                    xInc = 1; yInc = -2; break;
                case 3: //up 1, right 2
                    xInc = 2; yInc = -1; break;
                case 4: //down 2, left 1
                    xInc = -1; yInc = 2; break;
                case 5: //down 1, left 2
                    xInc = -2; yInc = 1; break;
                case 6: //down 2, right 1
                    xInc = 1; yInc = 2; break;
                case 7: //down 1, right 2
                    xInc = 2; yInc = 1; break;
                default: //never happens
                    xInc = yInc = 0; break;
            }
            if(isInBoard(x+xInc,y+yInc)){
                if(isEmptySpace(x+xInc,y+yInc) || isEnemyPiece(x,y,x+xInc,y+yInc)){
                    moves[x+xInc][y+yInc] = true;
                    hasMoves = true;
                }
            }
        }
        
        if(!hasMoves){
            return null;
        }
        return moves;
    }
    
    private boolean[][] getValidBishop(int x, int y){
        boolean[][] moves = new boolean[8][8];
        boolean hasMoves = false;
        
        int xLoc, yLoc, xInc, yInc;
        
         for(int i = 0; i < 4; i++){
            switch(i){
                case 0: // up-left
                    xLoc = x-1; yLoc = y-1;
                    xInc = yInc = -1; break;
                case 1: // up-right
                    xLoc = x+1; yLoc = y-1; 
                    xInc = 1; yInc = -1; break;
                case 2: // down-left
                    xLoc = x-1; yLoc = y+1; 
                    xInc = -1; yInc = 1; break;
                case 3: // down-right
                    xLoc = x+1; yLoc = y+1; 
                    xInc = yInc = 1; break;
                default:
                    xLoc = yLoc = xInc = yInc = 0; break;
            }
            
            while(isInBoard(xLoc,yLoc)){
                if(isEmptySpace(xLoc,yLoc)){
                    moves[xLoc][yLoc] = true;
                    hasMoves = true;
                    xLoc += xInc;
                    yLoc += yInc;
                    
                }
                else{
                    if(isEnemyPiece(x,y,xLoc,yLoc)){
                        moves[xLoc][yLoc] = true;
                        hasMoves = true;
                    }
                    break;
                }
            }
        }
        
        
        if(!hasMoves){
            return null;
        }
        return moves;
    }
    
    private boolean[][] getValidQueen(boolean[][] horizontal, boolean[][] diagonal){
        boolean[][] moves = new boolean[8][8];
        if(horizontal != null && diagonal != null){
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    moves[i][j] = horizontal[i][j] || diagonal[i][j];
                }
            }
            return moves;
        }
        else if(diagonal == null && horizontal != null){
            return horizontal;
        }
        else if(horizontal == null && diagonal != null){
            return diagonal;
        }
        else{
            return null;
        }
    }
    
    private boolean[][] getValidKing(int x, int y){
        boolean[][] moves = new boolean[8][8];
        boolean hasMoves = false;
        int xLoc = x-1;
        int yLoc = y-1;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(isInBoard(xLoc+j,yLoc+i)){
                    if(isEmptySpace(xLoc+j,yLoc+i) || isEnemyPiece(xLoc+j,yLoc+i,x,y)){
                        moves[xLoc+j][yLoc+i] = true;
                        hasMoves = true;
                    }
                }
            }
        }
        
        if(!hasMoves){
            return null;
        }
        return moves;
        
    }
    
    private boolean isEmptySpace(int x, int y){
        return pieces[x][y] == null;
    }
    
    private boolean isEnemyPiece(int x1, int y1, int x2, int y2){
        return pieces[x1][y1].getTeam() != pieces[x2][y2].getTeam();
    }
    
    private boolean isInBoard(int x, int y){
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }
    
    public boolean movePiece(int x, int y, int newX, int newY, boolean[][] moves){
        if(moves != null && moves[newX][newY]){
            swap(x, y, newX, newY);
            moveMSG = "Move: " + x + ", " + y + " to " + newX + ", " + newY;
            boolean team = pieces[newX][newY].getTeam();
            if(endangersKing(team)){
                System.out.println("THIS ENDANGERS THE KING!");
                
                swap(x, y, newX, newY);
                return false;
            }
            
            pieces[x][y] = null;
            if(placesInCheck(newX,newY)){
                boolean checkMate = placesInCheckmate(team); //if true, this needs to return up somehow
                if(team){ //team is white
                    whiteKingCheck = true;
                }
                else{
                    blackKingCheck = true;
                }
            }
            if(x == whiteKingX && y == whiteKingY){
                whiteKingX = newX;
                whiteKingY = newY;
            }
            else if(x == blackKingX && y == whiteKingY){
                blackKingX = newX;
                blackKingY = newY;
            }
            pieces[newX][newY].setTurn();
            return true;
        }
        else{
            System.out.println("--- INVALID MOVE ---");
            return false;
        }
    }
    
    private void swap(int x1, int y1, int x2, int y2){
        Piece temp = pieces[x1][y1];
        pieces[x1][y1] = pieces[x2][y2];
        pieces[x2][y2] = temp;
    }
    
    public void printKing(boolean team){
        String teamName = team ? "White" : "Black";
        int x = team ? whiteKingX : blackKingX;
        int y = team ? whiteKingY : blackKingY;
        System.out.println(teamName + "'s King is at location (" + x + "," + y + ").");
    }
    
    public String getMoveMSG(){
        return moveMSG;
    }
    
    public String validMovesToString(boolean[][] moves){
        String temp = "";
        if(moves == null){
            return null;
        }
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(moves[j][i]){ //valid move
                    temp += "1";
                }
                else{
                    temp += "0";
                }
            }
        }
        return temp;
    }
    
    public String boardToString(){
        String temp = "";
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                temp += pieceConversion(pieces[j][i]);
            }
        }
                
        return temp;
    }
    
    private String pieceConversion(Piece current){
        String letter = "";
        if(current == null){
            return "_";
        }
        boolean team = current.getTeam();
        String type = current.getType();
        if("Knight".equals(type)){
           letter = "N"; 
        }
        else{
           letter = type.substring(0, 1);
        }
        
        if(team){ //white team, automatically uppercase so the assignment for black is unnecessary
            letter = letter.toLowerCase();
        }
        
        return letter;
    }
    
} //end of class

