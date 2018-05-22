package checkers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Minmax {
	static int boardSize=8;
	static BoardSquare[][] board;
	static GUI CheckerBoard;
	final int SIZE = 500; 
	static TreeStructure<BoardSquare[][]> root;
	
	public Minmax(){
		board = new BoardSquare[boardSize][boardSize];
		initalizeBoard();
		CheckerBoard = new GUI(board);
		CheckerBoard.setSize(SIZE, SIZE); 
		CheckerBoard.drawMen(board);
		CheckerBoard.setVisible(true); 
	}

	/**
	 * Setup board to start position with 12 white and 12 black pawns.
	 */

	private void initalizeBoard() {
		for(int i=0; i<boardSize; i++){
			for(int j=0; j<boardSize; j++){
				board[i][j] = new BoardSquare();
				
				if((i+j)%2!=0 && i<3){
					board[i][j].setPawnColor(Color.BLACK);
					System.out.print("B ");
				}
				
				else if((i+j)%2!=0 && i>4){
					board[i][j].setPawnColor(Color.WHITE);
					System.out.print("W ");
				}
				
				else{
					System.out.print("- ");
				}
			}
			System.out.println();
		}
		
	}
	
	static void AIminmax(BoardSquare[][] b) {
		/**
		 * depth=3
		 */
		if(CheckerBoard.checkGameOver(b)){
			
		}
		
		int max=(int) Double.NEGATIVE_INFINITY;
		ArrayList<BoardSquare[][]> bestMove = new ArrayList<>();
		checkAvaliableMoves(b);
				
		
		for (TreeStructure<BoardSquare[][]> boardSquares1 : root.getChildren()) {
			for (TreeStructure<BoardSquare[][]> boardSquares2 : boardSquares1.getChildren()) {
				for (TreeStructure<BoardSquare[][]> boardSquares3 : boardSquares2.getChildren()) {
					
					possibleMoves move = new possibleMoves(boardSquares3.getData(), boardSize);
					move.calculateResult2();
					if(move.getResult()>max){
						max=move.getResult();
						bestMove.clear();
						bestMove.add(boardSquares1.getData());
						
					}
					else if(move.getResult()==max && !bestMove.contains(boardSquares1.getData())){
						//System.out.println(bestMove.size());
						bestMove.add(boardSquares1.getData());
					}
				}
			}
		}
		
		/**
		 * Chose random move from list of all possible moves (with the same result)
		 */
		Random random = new Random();
		int i = random.nextInt(bestMove.size());
		board = copyArray(bestMove.get(i), board);
		System.out.println();
		printBoard(board);
		CheckerBoard.updateBoard(board);
		max=(int) Double.NEGATIVE_INFINITY;
		bestMove=null;
	}
	
	static void printBoard(BoardSquare[][] boardSquare) {
		for(int i=0; i<boardSize; i++){
			for(int j=0; j<boardSize; j++){
				
				if(boardSquare[i][j].getPawnColor()==Color.BLACK){
					System.out.print("B ");
				}
				
				else if(boardSquare[i][j].getPawnColor()==Color.WHITE){
					System.out.print("W ");
				}
				
				else if(boardSquare[i][j].getPawnColor()==Color.BLUE){
					System.out.print("B^ ");
				}
				
				else if(boardSquare[i][j].getPawnColor()==Color.RED){
					System.out.print("W^ ");
				}
				
				else{
					System.out.print("- ");
				}
			}
			System.out.println();
		}
	}

	private static void checkAvaliableMoves(BoardSquare[][] b) {		
		root = new TreeStructure<>(b);
		
		
		ArrayList<BoardSquare[][]> temp = new ArrayList<>();
		for(int i=0; i<boardSize; i++){
			for(int j=0; j<boardSize; j++){
				if(b[i][j].getPawnColor()==Color.BLACK || b[i][j].getPawnColor()==Color.BLUE){					
					temp=getAvaliableMovesForGivenPawn(b, i, j);
					if(!temp.isEmpty()){
						for (BoardSquare[][] boardSquares : temp) {
							root.addChild(new TreeStructure<BoardSquare[][]>(boardSquares));
						}
					}					
				}
			}
		}
		
		BoardSquare[][] boardSquares;
		for (TreeStructure<BoardSquare[][]> b1 : root.getChildren()) {
			boardSquares = b1.getData();
			for(int i=0; i<boardSize; i++){
				for(int j=0; j<boardSize; j++){
					if(boardSquares[i][j].getPawnColor()==Color.WHITE || boardSquares[i][j].getPawnColor()==Color.RED){					
						temp=getAvaliableMovesForOpponentPawn(boardSquares, i, j);
						if(!temp.isEmpty()){
							for (BoardSquare[][] square : temp) {
								b1.addChild(new TreeStructure<BoardSquare[][]>(square));
							}
						}					
					}
				}
			}
		}		
		
		for (TreeStructure<BoardSquare[][]> b1 : root.getChildren()) {
			for (TreeStructure<BoardSquare[][]> b2 : b1.getChildren()) {
				boardSquares = b2.getData();
				for(int i=0; i<boardSize; i++){
					for(int j=0; j<boardSize; j++){
						if(boardSquares[i][j].getPawnColor()==Color.BLACK || boardSquares[i][j].getPawnColor()==Color.BLUE){					
							temp=getAvaliableMovesForGivenPawn(boardSquares, i, j);
							if(!temp.isEmpty()){
								for (BoardSquare[][] square : temp) {
									b2.addChild(new TreeStructure<BoardSquare[][]>(square));
								}
								
							}					
						}
					}
				}
			}
		}
	}

	private static ArrayList<BoardSquare[][]> getAvaliableMovesForOpponentPawn(BoardSquare[][] board, int i, int j) {
		ArrayList<BoardSquare[][]> temp = new ArrayList<>();
		if(board[i][j].pawnColor==Color.WHITE){
			if(i>0 && j>0 && board[i-1][j-1].pawnColor==null){
				BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
				copyArray(board, b1);
				b1[i][j].setPawnColor(null);
				b1[i-1][j-1].setPawnColor(Color.WHITE);
				temp.add(b1);
			}
			if(i>0 && j<7 && board[i-1][j+1].pawnColor==null){
				BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
				copyArray(board, b1);
				b1[i][j].setPawnColor(null);
				b1[i-1][j+1].setPawnColor(Color.WHITE);
				temp.add(b1);
			}
			if(i>1 && j>1 && board[i-2][j-2].pawnColor==null && board[i-1][j-1].pawnColor==Color.BLACK){
				BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
				copyArray(board, b1);
				b1[i][j].setPawnColor(null);
				b1[i-1][j-1].setPawnColor(null);
				b1[i-2][j-2].setPawnColor(Color.WHITE);
				temp.add(b1);
			}
			if(i>1 && j<6 && board[i-2][j+2].pawnColor==null && board[i-1][j+1].pawnColor==Color.BLACK){
				BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
				copyArray(board, b1);
				b1[i][j].setPawnColor(null);
				b1[i-1][j+1].setPawnColor(null);
				b1[i-2][j+2].setPawnColor(Color.WHITE);
				temp.add(b1);
			}
			if(i<6 && j>1 && board[i+2][j-2].pawnColor==null && board[i+1][j-1].pawnColor==Color.BLACK){
				BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
				copyArray(board, b1);
				b1[i][j].setPawnColor(null);
				b1[i+1][j-1].setPawnColor(null);
				b1[i+2][j-2].setPawnColor(Color.WHITE);
				temp.add(b1);
			}
			if(i<6 && j<6 && board[i+2][j+2].pawnColor==null && board[i+1][j+1].pawnColor==Color.BLACK){
				BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
				copyArray(board, b1);
				b1[i][j].setPawnColor(null);
				b1[i+1][j+1].setPawnColor(null);
				b1[i+2][j+2].setPawnColor(Color.WHITE);
				temp.add(b1);    		
	    	}
		}
		else if(board[i][j].pawnColor==Color.RED){
			if(i<7 && j<7){
    			int b=j+1;
    			for(int a=i+1; a<8 && b<8; a++){
					if(board[a][b].getPawnColor()==null){
						BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
    					copyArray(board, b1);
    					b1[i][j].setPawnColor(null);
    					b1[a][b].setPawnColor(Color.RED);
    					temp.add(b1);
    				}
					else{
						break;
					}
					b++;
				}
    			b=j+1;
    			for(int a=i+1; a<7 && b<7; a++){
					if((board[a][b].getPawnColor()==Color.BLACK || board[a][b].getPawnColor()==Color.BLUE) && board[a+1][b+1].getPawnColor()==null){
						BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
        				copyArray(board, b1);
        				b1[i][j].setPawnColor(null);
        				b1[a][b].setPawnColor(null);
        				b1[a+1][b+1].setPawnColor(Color.RED);
        				temp.add(b1);
        			}
					b++;
	    		}
    		}
    		
    		if(i>0 && j>0){
    			int b = j-1;
    			for(int a=i-1; a>-1 && b>-1; a--){
					if(board[a][b].getPawnColor()==null){
						BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
    					copyArray(board, b1);
    					b1[i][j].setPawnColor(null);
    					b1[a][b].setPawnColor(Color.RED);
    					temp.add(b1);
    				}
					else{
						break;
					}
					b--;
				}
    			b=j-1;
    			for(int a=i-1; a>0 && b>0; a--){
    				if((board[a][b].getPawnColor()==Color.BLACK || board[a][b].getPawnColor()==Color.BLUE) && board[a-1][b-1].getPawnColor()==null){
    					BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
        				copyArray(board, b1);
        				b1[i][j].setPawnColor(null);
        				b1[a][b].setPawnColor(null);
        				b1[a-1][b-1].setPawnColor(Color.RED);
        				temp.add(b1);
    				}
    				b--;
    			}
    		}
    		
    		if(i<7 && j>0){
    			int b=j-1;
    			for(int a=i+1; a<8 && b>-1; a++){
    				if(board[a][b].getPawnColor()==null){
    					BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
    					copyArray(board, b1);
    					b1[i][j].setPawnColor(null);
    					b1[a][b].setPawnColor(Color.RED);
    					temp.add(b1);
    				}
    				else{
						break;
					}
					b--;
				}
    			b=j-1;
    			for(int a=i+1; a<7 && b>0; a++){
        			if((board[a][b].getPawnColor()==Color.BLACK || board[a][b].getPawnColor()==Color.BLUE) && board[a+1][b-1].getPawnColor()==null){
        				BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
        				copyArray(board, b1);
        				b1[i][j].setPawnColor(null);
        				b1[a][b].setPawnColor(null);
        				b1[a+1][b-1].setPawnColor(Color.RED);
        				temp.add(b1);
        			}
        			else{
						break;
					}
					b--;
	    		}
    		}
    		
    		if(i>0 && j<7){
    			int b=j+1;
    			for(int a=i-1; a>-1 && b<8; a--){
   				 
    				if(board[a][b].getPawnColor()==null){
    					BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
    					copyArray(board, b1);
    					b1[i][j].setPawnColor(null);
    					b1[a][b].setPawnColor(Color.RED);
    					temp.add(b1);
	    			}
				b++;
    			}    			
    			b=j+1;
    			for(int a=i-1; a>0 && b<7; a--){
    				if((board[a][b].getPawnColor()==Color.BLACK || board[a][b].getPawnColor()==Color.BLUE) && board[a-1][b+1].getPawnColor()==null){
    					BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
        				copyArray(board, b1);
        				b1[i][j].setPawnColor(null);
        				b1[a][b].setPawnColor(null);
        				b1[a-1][b+1].setPawnColor(Color.RED);
        				temp.add(b1);
    				}
					b++;
				}
    		}
		}
		return temp;
	}

	private static ArrayList<BoardSquare[][]> getAvaliableMovesForGivenPawn(BoardSquare[][] board, int i, int j) {
		ArrayList<BoardSquare[][]> grey = new ArrayList<>();
		ArrayList<BoardSquare[][]> red = new ArrayList<>();
		if(board[i][j].getPawnColor()==Color.BLACK){
			if(i<7 && j<7 && board[i+1][j+1].pawnColor==null){
				BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
				copyArray(board, b1);
				b1[i][j].setPawnColor(null);
				if(i==6){
					b1[i+1][j+1].setPawnColor(Color.BLUE);
				}
				else{
					b1[i+1][j+1].setPawnColor(Color.BLACK);
				}
				grey.add(b1);
			}
			if(i<7 && j>0 && board[i+1][j-1].pawnColor==null){
				BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
				copyArray(board, b1);
				b1[i][j].setPawnColor(null);
				if(i==6){
					b1[i+1][j-1].setPawnColor(Color.BLUE);
				}
				else{
					b1[i+1][j-1].setPawnColor(Color.BLACK);
				}
				grey.add(b1);
			}
			if(i<6 && j<6 && board[i+2][j+2].pawnColor==null && board[i+1][j+1].pawnColor==Color.WHITE){
				BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
				copyArray(board, b1);
				b1[i][j].setPawnColor(null);
				b1[i+1][j+1].setPawnColor(null);
				if(i==5){
					b1[i+2][j+2].setPawnColor(Color.BLUE);
				}
				else{
					b1[i+2][j+2].setPawnColor(Color.BLACK);
				}
				red.add(b1);
			}
			if(i<6 && j>1 && board[i+2][j-2].pawnColor==null && board[i+1][j-1].pawnColor==Color.WHITE){
				BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
				copyArray(board, b1);
				b1[i][j].setPawnColor(null);
				b1[i+1][j-1].setPawnColor(null);
				if(i==5){
					b1[i+2][j-2].setPawnColor(Color.BLUE);
				}
				else{
					b1[i+2][j-2].setPawnColor(Color.BLACK);
				}
				red.add(b1);
			}
			if(i>1 && j<6 && board[i-2][j+2].pawnColor==null && board[i-1][j+1].pawnColor==Color.WHITE){
				BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
				copyArray(board, b1);
				b1[i][j].setPawnColor(null);
				b1[i-1][j+1].setPawnColor(null);
				b1[i-2][j+2].setPawnColor(Color.BLACK);
				red.add(b1);
			}
			if(i>1 && j>1 && board[i-2][j-2].pawnColor==null && board[i-1][j-1].pawnColor==Color.WHITE){
				BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
				copyArray(board, b1);
				b1[i][j].setPawnColor(null);
				b1[i-1][j-1].setPawnColor(null);
				b1[i-2][j-2].setPawnColor(Color.BLACK);
				red.add(b1);
			}
		}
		else if(board[i][j].pawnColor==Color.BLUE){
			if(i<7 && j<7){
    			int b=j+1;
    			for(int a=i+1; a<8 && b<8; a++){
					if(board[a][b].getPawnColor()==null){
						BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
    					copyArray(board, b1);
    					b1[i][j].setPawnColor(null);
    					b1[a][b].setPawnColor(Color.BLUE);
    					grey.add(b1);
    				}
					else{
						break;
					}
					b++;
				}
    			b=j+1;
    			for(int a=i+1; a<7 && b<7; a++){
					if((board[a][b].getPawnColor()==Color.WHITE || board[a][b].getPawnColor()==Color.RED) && board[a+1][b+1].getPawnColor()==null){
						BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
        				copyArray(board, b1);
        				b1[i][j].setPawnColor(null);
        				b1[a][b].setPawnColor(null);
        				b1[a+1][b+1].setPawnColor(Color.BLUE);
        				red.add(b1);
        			}
					b++;
	    		}
    		}
    		
    		
    		if(i>0 && j>0){
    			int b = j-1;
    			for(int a=i-1; a>-1 && b>-1; a--){
					if(board[a][b].getPawnColor()==null){
						BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
    					copyArray(board, b1);
    					b1[i][j].setPawnColor(null);
    					b1[a][b].setPawnColor(Color.BLUE);
    					grey.add(b1);
    				}
					else{
						break;
					}
					b--;
				}
    			b=j-1;
    			for(int a=i-1; a>0 && b>0; a--){
    				if((board[a][b].getPawnColor()==Color.WHITE || board[a][b].getPawnColor()==Color.RED) && board[a-1][b-1].getPawnColor()==null){
    					BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
        				copyArray(board, b1);
        				b1[i][j].setPawnColor(null);
        				b1[a][b].setPawnColor(null);
        				b1[a-1][b-1].setPawnColor(Color.BLUE);
        				red.add(b1);
    				}
    				b--;
    			}
    		}
    		
    		if(i<7 && j>0){
    			int b=j-1;
    			for(int a=i+1; a<8 && b>-1; a++){
    				if(board[a][b].getPawnColor()==null){
    					BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
    					copyArray(board, b1);
    					b1[i][j].setPawnColor(null);
    					b1[a][b].setPawnColor(Color.BLUE);
    					grey.add(b1);
    				}
    				else{
						break;
					}
					b--;
				}
    			b=j-1;
    			for(int a=i+1; a<7 && b>0; a++){
        			if((board[a][b].getPawnColor()==Color.WHITE || board[a][b].getPawnColor()==Color.RED) && board[a+1][b-1].getPawnColor()==null){
        				BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
        				copyArray(board, b1);
        				b1[i][j].setPawnColor(null);
        				b1[a][b].setPawnColor(null);
        				b1[a+1][b-1].setPawnColor(Color.BLUE);
        				red.add(b1);
        			}
        			else{
						break;
					}
					b--;
	    		}
    		}
    		
    		if(i>0 && j<7){
    			int b=j+1;
    			for(int a=i-1; a>-1 && b<8; a--){
   				 
    				if(board[a][b].getPawnColor()==null){
    					BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
    					copyArray(board, b1);
    					b1[i][j].setPawnColor(null);
    					b1[a][b].setPawnColor(Color.BLUE);
    					grey.add(b1);
	    			}
				b++;
    			}    			
    			b=j+1;
    			for(int a=i-1; a>0 && b<7; a--){
    				if((board[a][b].getPawnColor()==Color.WHITE || board[a][b].getPawnColor()==Color.RED) && board[a-1][b+1].getPawnColor()==null){
    					BoardSquare[][] b1 = new BoardSquare[boardSize][boardSize];
        				copyArray(board, b1);
        				b1[i][j].setPawnColor(null);
        				b1[a][b].setPawnColor(null);
        				b1[a-1][b+1].setPawnColor(Color.BLUE);
        				red.add(b1);
    				}
					b++;
				}
    		}
		}
		if(!red.isEmpty()){
			return red;
		}
		return grey;
	}
	static BoardSquare[][] copyArray(BoardSquare[][] board, BoardSquare[][] b1){
		for(int i=0; i<board.length; i++){
			  for(int j=0; j<board[i].length; j++){
				  b1[i][j]=new BoardSquare();
				  b1[i][j].setPawnColor(board[i][j].getPawnColor());
			  }
		}
		return b1;
	}

	private static void updateGUI() {
		CheckerBoard.setVisible(false); 
		CheckerBoard.setVisible(true); 
		CheckerBoard.repaint();
	}

}
