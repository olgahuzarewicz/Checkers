package checkers;

import java.awt.Color;

public class possibleMoves {

	int result;
	BoardSquare[][] b;
	int boardSize;
	int whitePawns;
	int blackPawns;
	
	public possibleMoves(BoardSquare[][] board, int boardSize){
		this.b=board;
		this.boardSize=boardSize;
		this.whitePawns=0;
		this.blackPawns=0;
		this.result=0;
	}

	void calculateResult(){
		
		 for(int i=0; i<boardSize; i++){ 
	        	 for(int j=0; j<boardSize; j++){
	        	 
	        	 if(b[i][j].getPawnColor()==Color.WHITE){
	        		 whitePawns++;
	        	 }
	        	 else if(b[i][j].getPawnColor()==Color.RED){
	        		 whitePawns=+5;
	        	 }
	        	 else if(b[i][j].getPawnColor()==Color.BLACK){
	        		 blackPawns++;
	        	 }
	        	 else if(b[i][j].getPawnColor()==Color.BLUE){
	        		 blackPawns=+5;
	        	 }
	        }
		 }
		 this.result=(blackPawns-whitePawns);
	}
	
	void calculateResult2(){

		 for(int i=0; i<boardSize/2; i++){ 
	        	 for(int j=0; j<boardSize/2; j++){
	        	 if(b[i][j].getPawnColor()==Color.WHITE){
	        		 whitePawns=whitePawns+i*j;
	        	 }
	        	 else if(b[i][j].getPawnColor()==Color.RED){
	        		 whitePawns=whitePawns+5+i*j;
	        	 }
	        	 else if(b[i][j].getPawnColor()==Color.BLACK){
	        		 blackPawns=blackPawns+i*j;
	        	 }
	        	 else if(b[i][j].getPawnColor()==Color.BLUE){
	        		 blackPawns=blackPawns+5+i*j;
	        	 }
	        }
		 }
		 for(int i=boardSize/2; i<boardSize; i++){ 
        	 for(int j=boardSize/2; j<boardSize; j++){
        	 int valueX=boardSize-i;
        	 int valueY=boardSize-j;
        	 if(b[i][j].getPawnColor()==Color.WHITE){
        		 whitePawns=whitePawns+valueX*valueY;
        	 }
        	 else if(b[i][j].getPawnColor()==Color.RED){
        		 whitePawns=whitePawns+5+valueX*valueY;
        	 }
        	 else if(b[i][j].getPawnColor()==Color.BLACK){
        		 blackPawns=blackPawns+valueX*valueY;
        	 }
        	 else if(b[i][j].getPawnColor()==Color.BLUE){
        		 blackPawns=blackPawns+5+valueX*valueY;
        	 }
        }
	 }
		 this.result=(blackPawns-whitePawns);
	}
	
	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public BoardSquare[][] getB() {
		return b;
	}

	public void setB(BoardSquare[][] b) {
		this.b = b;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	public int getWhitePawns() {
		return whitePawns;
	}

	public void setWhitePawns(int whitePawns) {
		this.whitePawns = whitePawns;
	}

	public int getBlackPawns() {
		return blackPawns;
	}

	public void setBlackPawns(int blackPawns) {
		this.blackPawns = blackPawns;
	}
}
