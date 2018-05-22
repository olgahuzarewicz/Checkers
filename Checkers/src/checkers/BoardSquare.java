package checkers;

import java.awt.Color;

public class BoardSquare {
	Color pawnColor;
	
	public BoardSquare(){
		this.pawnColor = null;
	}
	
	public Color getPawnColor() {
		return pawnColor;
	}

	public void setPawnColor(Color pawnColor) {
		this.pawnColor = pawnColor;
	}
}
