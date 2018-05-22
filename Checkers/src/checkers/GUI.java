package checkers;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*; 
public class GUI extends JFrame{ 
      private final int ROWS = Minmax.boardSize; 
      private final int COLS = Minmax.boardSize; 
      private int x; 
      private int y; 
      Color currentTurn=Color.WHITE;
      Point oldPawnPosition;
      Color currentPawnColor=null;
      int avaliableMoves=0;
      BoardSquare[][] board;
      private JPanel pnl = new JPanel 
      (new GridLayout(Minmax.boardSize,Minmax.boardSize,2,2)); 
      private JButton[][] pnl1 = new JButton[ROWS][COLS]; 
      public GUI(BoardSquare[][] board){ 
    	  
    	 /***
    	  * Initializing GUI for checkerboard size 8x8.
    	  */
         super("Checkers"); 
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
         this.board=board;
         add(pnl); 
         Insets buttonMargin = new Insets(0,0,0,0);
         for(x=0; x<ROWS; x++){ 
        	 for(y=0; y<COLS; y++){
        		 pnl1[x][y] = new JButton(); 
        		 pnl1[x][y].setMargin(buttonMargin);
        		 pnl1[x][y].setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
                 pnl.add(pnl1[x][y]); 
                 
                 if((x+y)%2==0) {
                	 pnl1[x][y].setBackground(Color.WHITE); 
                 }
                 else{
                	 pnl1[x][y].setBackground(Color.BLACK); 
                 }
                 
                 pnl1[x][y].addActionListener(new ActionListener(){
	          		  @Override
	          		  public void actionPerformed(ActionEvent e){
	          			  for(int i=0; i<ROWS; i++){ 
	          	        	 for(int j=0; j<COLS; j++){
	          	        		if(currentPawnColor==null){
		          	        		if(e.getSource()==pnl1[i][j] && (board[i][j].getPawnColor()==Color.WHITE || board[i][j].getPawnColor()==Color.RED)){
		  	          					showOptions(board, i, j);
		  	          					if(avaliableMoves>0){
			  	          					currentPawnColor = board[i][j].getPawnColor();
			  	          					board[i][j].setPawnColor(null);
			  	          					oldPawnPosition=new Point(i,j);
		  	          					}
		  	          				            				  
		          	        		} 
	          	        		}
	          	        		else if(avaliableMoves==0){
	          	        			currentPawnColor=null;
	      	        			}
	          	        		else{
	          	        			if(e.getSource()==pnl1[i][j] && pnl1[i][j].getBackground()==Color.GRAY || pnl1[i][j].getBackground()==Color.RED){
	          	        				
	          	        				if(pnl1[i][j].getBackground()==Color.RED){
	          	        					if(currentPawnColor==Color.RED){
	          	        						if(i>oldPawnPosition.getX() && j>oldPawnPosition.getY()){
	          	        							board[i-1][j-1].setPawnColor(null);
	          	        						}
	          	        						else if(i<oldPawnPosition.getX() && j>oldPawnPosition.getY()){
	          	        							board[i+1][j-1].setPawnColor(null);
	          	        						}
	          	        						else if(i<oldPawnPosition.getX() && j<oldPawnPosition.getY()){
	          	        							board[i+1][j+1].setPawnColor(null);
	          	        						}
	          	        						else if(i>oldPawnPosition.getX() && j<oldPawnPosition.getY()){
	          	        							board[i-1][j+1].setPawnColor(null);
	          	        						}
	          	        					}
	          	        					else{
	          	        						x=(int) oldPawnPosition.getX();
		          	        					y=(int) oldPawnPosition.getY();
		          	        					int deletedPawnX = (Math.abs(x)+Math.abs(i))/2;
		          	        					int deletedPawnY = (Math.abs(y)+Math.abs(j))/2;
		          	        					board[deletedPawnX][deletedPawnY].setPawnColor(null);
	          	        					}
	          	        				}
	          	        				if(i==0){
	          	        					if(currentPawnColor==Color.WHITE || currentPawnColor==Color.RED){
	          	        						board[i][j].setPawnColor(Color.RED);
	          	        					}
	          	        				}
	          	        				else{
		          	        				board[i][j].setPawnColor(currentPawnColor);
		          	        				currentTurn(Color.WHITE);
		          	        				currentPawnColor=null;
	          	        				}
	              	        			updateBoard(board);
	              	        			checkGameOver(board);
	              	        			avaliableMoves=0;
	          	        			}    
	    	          			}	
	          			  }
	          			}
	          		  }
	          		});                 
        	 }
         } 
         
      } 
      
      protected void currentTurn(Color currentPawnColor) {
		if(currentPawnColor==Color.BLACK || currentPawnColor==Color.BLUE){
			currentTurn = Color.WHITE;
		}
		else{
			currentTurn = Color.BLACK;
			Minmax.AIminmax(board);
			//Alphabeta.AIalphabeta(board);
		}
	}

	protected boolean checkGameOver(BoardSquare[][] board) {
		int whitePawns=0;
		int blackPawns=0;
		
		 for(int i=0; i<ROWS; i++){ 
	        	 for(int j=0; j<COLS; j++){
	        	 
	        	 if(board[i][j].getPawnColor()==Color.WHITE || board[i][j].getPawnColor()==Color.RED){
	        		 whitePawns++;
	        	 }
	        	 else if(board[i][j].getPawnColor()==Color.BLACK || board[i][j].getPawnColor()==Color.BLUE){
	        		 blackPawns++;
	        	 }
	        }
		 }
		 
		
		 JFrame frame = new JFrame("");
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		 if(blackPawns==0){
			 JLabel label = new JLabel("White WIN") ;
			 frame.getContentPane().add(label);
			 frame.pack();
			 frame.setVisible(true);
			 return true;
		 }
		 else if(whitePawns==0){
			 JLabel label = new JLabel("Black WIN") ;
			 frame.getContentPane().add(label);
			 frame.pack();
			 frame.setVisible(true);
			 return true;
		 }	
		 return false;
	}

	protected void showOptions(BoardSquare[][] board, int i, int j) {
    	if(board[i][j].pawnColor==Color.WHITE){
    		if(i>0 && j>0 && board[i-1][j-1].pawnColor==null){
    			pnl1[i-1][j-1].setBackground(Color.GRAY); 
    			avaliableMoves++;
    		}
    		if(i>0 && j<7 && board[i-1][j+1].pawnColor==null){
    			pnl1[i-1][j+1].setBackground(Color.GRAY); 
    			avaliableMoves++;
    		}
    		if(i>1 && j>1 && board[i-2][j-2].pawnColor==null && board[i-1][j-1].pawnColor==Color.BLACK){
    			pnl1[i-2][j-2].setBackground(Color.RED); 
    			avaliableMoves++;
    		}
    		if(i>1 && j<6 && board[i-2][j+2].pawnColor==null && board[i-1][j+1].pawnColor==Color.BLACK){
    			pnl1[i-2][j+2].setBackground(Color.RED); 
    			avaliableMoves++;
    		}
    		if(i<6 && j>1 && board[i+2][j-2].pawnColor==null && board[i+1][j-1].pawnColor==Color.BLACK){
    			pnl1[i+2][j-2].setBackground(Color.RED); 
    			avaliableMoves++;
    		}
    		if(i<6 && j<6 && board[i+2][j+2].pawnColor==null && board[i+1][j+1].pawnColor==Color.BLACK){
    			pnl1[i+2][j+2].setBackground(Color.RED); 
    			avaliableMoves++;
    		}
    	}
    	
    	else if(board[i][j].pawnColor==Color.RED){
    		if(i<7 && j<7){
    			int b=j+1;
    			for(int a=i+1; a<8 && b<8; a++){
					if(board[a][b].getPawnColor()==null){
						pnl1[a][b].setBackground(Color.GRAY); 
            			avaliableMoves++;	
    				}
					else{
						break;
					}
					b++;
				}
    			b=j+1;
    			for(int a=i+1; a<7 && b<7; a++){
					if((board[a][b].getPawnColor()==Color.BLACK || board[a][b].getPawnColor()==Color.BLUE) && board[a+1][b+1].getPawnColor()==null){
						pnl1[a+1][b+1].setBackground(Color.RED); 
            			avaliableMoves++;
        			}
					b++;
	    		}
    		}
    		
    		
    		if(i>0 && j>0){
    			int b = j-1;
    			for(int a=i-1; a>-1 && b>-1; a--){
					if(board[a][b].getPawnColor()==null){
						pnl1[a][b].setBackground(Color.GRAY); 
            			avaliableMoves++;
    				}
					else{
						break;
					}
					b--;
				}
    			b=j-1;
    			for(int a=i-1; a>0 && b>0; a--){
    				if((board[a][b].getPawnColor()==Color.BLACK || board[a][b].getPawnColor()==Color.BLUE) && board[a-1][b-1].getPawnColor()==null){
    					pnl1[a-1][b-1].setBackground(Color.RED); 
            			avaliableMoves++;
    				}
    				b--;
    			}
    		}
    		
    		if(i<7 && j>0){
    			int b=j-1;
    			for(int a=i+1; a<8 && b>-1; a++){
    				if(board[a][b].getPawnColor()==null){
    					pnl1[a][b].setBackground(Color.GRAY); 
            			avaliableMoves++;
    				}
    				else{
						break;
					}
					b--;
				}
    			b=j-1;
    			for(int a=i+1; a<7 && b>0; a++){
        			if((board[a][b].getPawnColor()==Color.BLACK || board[a][b].getPawnColor()==Color.BLUE) && board[a+1][b-1].getPawnColor()==null){
        				pnl1[a+1][b-1].setBackground(Color.RED); 
            			avaliableMoves++;
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
    					pnl1[a][b].setBackground(Color.GRAY); 
            			avaliableMoves++;
	    			}
				b++;
    			}    			
    			b=j+1;
    			for(int a=i-1; a>0 && b<7; a--){
    				if((board[a][b].getPawnColor()==Color.BLACK || board[a][b].getPawnColor()==Color.BLUE) && board[a-1][b+1].getPawnColor()==null){
    					pnl1[a-1][b+1].setBackground(Color.RED); 
            			avaliableMoves++;
    				}
					b++;
				}
    		}
    	}		
	}

	void updateBoard(BoardSquare[][] bestMove) {
			
			for(int i=0; i<ROWS; i++){
				for(int j=0; j<COLS; j++){
					board[i][j].setPawnColor(bestMove[i][j].getPawnColor());
				}
			}
			drawMen(board);
			currentPawnColor=Color.BLACK;
			pnl.validate();
			pnl.repaint();
		}
   
      void drawMen(BoardSquare[][] board){
    	  ImageIcon icon = createImageIcon("manblack.png","");
    	  ImageIcon icon2 = createImageIcon("manwhite.png","");
    	  ImageIcon icon3 = createImageIcon("kingblack.png","");
    	  ImageIcon icon4 = createImageIcon("kingwhite.png","");
    	  
    	  for(x=0; x<ROWS; x++){ 
         	 for(y=0; y<COLS; y++){
         		 
         		if(board[x][y].getPawnColor()!=null){
         			pnl1[x][y].removeAll();
         			if(board[x][y].getPawnColor().equals(Color.BLACK)){
         				JLabel label1 = new JLabel("", icon, JLabel.CENTER);
    	         		pnl1[x][y].add(label1);
         			}
         			else if(board[x][y].getPawnColor().equals(Color.WHITE)){
         				JLabel label1 = new JLabel("", icon2, JLabel.CENTER);
    	         		pnl1[x][y].add(label1);
         			}
         			
         			/**
         			 * Black king = Color blue
         			 * White king = Color red
         			 */
         			else if(board[x][y].getPawnColor().equals(Color.BLUE)){
         				JLabel label1 = new JLabel("", icon3, JLabel.CENTER);
    	         		pnl1[x][y].add(label1);
         			}
         			else if(board[x][y].getPawnColor().equals(Color.RED)){
         				JLabel label1 = new JLabel("", icon4, JLabel.CENTER);
    	         		pnl1[x][y].add(label1);
         			}
         		}
         		else{
     				pnl1[x][y].removeAll();
     			}
         		
         		if((x+y)%2==0) {
                  	 pnl1[x][y].setBackground(Color.WHITE); 
                }
                else{
                	pnl1[x][y].setBackground(Color.BLACK); 
                }
         	 }
          } 
      }
      
      /** Returns an ImageIcon, or null if the path was invalid. */
      protected ImageIcon createImageIcon(String path, String description) {
          java.net.URL imgURL = getClass().getResource(path);
          if (imgURL != null) {
              return new ImageIcon(imgURL, description);
          } else {
              System.err.println("Couldn't find file: " + path);
              return null;
          }
      }
  } 


