package Shared;

public class ChessBoard {
	private static String[][] chessBoard;
	
	/**
	 * Creates a chess board based on the inputted color
	 * @param color 
	 */
	public ChessBoard(char color){
		if(color == 'w'){
			chessBoard = new String[][]{
			    {"bR", "bKN","bB","bQ","bKI","bB","bKN","bR"},
			   	{"bP","bP","bP","bP","bP","bP","bP","bP"},
			   	{"0","0","0","0","0","0","0","0"},
			   	{"0","0","0","0","0","0","0","0"},
			   	{"0","0","0","0","0","0","0","0"},
		    	{"0","0","0","0","0","0","0","0"},
		    	{"wP","wP","wP","wP","wP","wP","wP","wP"},
		    	{"wR", "wKN","wB","wQ","wKI","wB","wKN","wR"}
			};
		}
		else if(color == 'b'){
			chessBoard = new String[][]{
			    {"wR", "wKN","wB","wQ","wKI","wB","wKN","wR"},
			   	{"wP","wP","wP","wP","wP","wP","wP","wP"},
			   	{"0","0","0","0","0","0","0","0"},
			   	{"0","0","0","0","0","0","0","0"},
			   	{"0","0","0","0","0","0","0","0"},
		    	{"0","0","0","0","0","0","0","0"},
		    	{"bP","bP","bP","bP","bP","bP","bP","bP"},
		    	{"bR", "bKN","bB","bQ","bKI","bB","bKN","bR"}
			};
		}
	}
	public ChessBoard(){
		chessBoard = new String[][]{
		    {"bR", "bKN","bB","bQ","bKI","bB","bKN","bR"},
		   	{"bP","bP","bP","bP","bP","bP","bP","bP"},
		   	{"0","0","0","0","0","0","0","0"},
		   	{"0","0","0","0","0","0","0","0"},
		   	{"0","0","0","0","0","0","0","0"},
	    	{"0","0","0","0","0","0","0","0"},
	    	{"wP","wP","wP","wP","wP","wP","wP","wP"},
	    	{"wR", "wKN","wB","wQ","wKI","wB","wKN","wR"}
		};
	}
	/**
	 * Returns either the white chess board setup or the black 
	 * chess board setup depending on the inputted paramater.
	 * If a 'w' or 'b' is not inputed, an empty chess board is
	 * outputted. 
	 * 
	 * @param color The color of the chess board
	 * @return A 2d String array representation of a chess board
	 * @throws Exception 
	 */
	public String[][] getChessBoard(){
		return chessBoard;
	}
	public void move(int fromX, int fromY, int toX, int toY){
		chessBoard[toX][toY] = chessBoard[fromX][fromY];
		chessBoard[fromX][fromY] = "0";
	}
}
