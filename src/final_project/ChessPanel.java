package final_project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * 
 * @author Aaron Voymas
 * @since December 2015
 * This class handles a Jpanel where chess is played. It handles board creation, filling,
 * and drawing.
 */
public class ChessPanel extends JPanel implements Serializable
{
	// a chessboard is always 8x8, unless you are weird or on Star Trek
	private final int BOARD_ROW = 8;
	private final int BOARD_COL = 8;
	
	//	variables for offsetting what will be drawn
	final int OFFSET = 10;
	int hiWidth;
	int hiHeight;
	
	// position to pass down to the board and piece
	private Position pos;
	private int width; // panel width
	private int height; // panel height
	
	// by row and column
	private ChessBoard[][] chessBoard; // the board with all the pieces
	private ArrayList<Position> boardPos; // all possible positions on the board
	private ArrayList<Position> p1Pos; // all of player 1's positions
	private ArrayList<Position> p2Pos; // all of player 2's positions
	private ArrayList<ChessPiece> p1Pieces; // all of player 1's remaining pieces
	private ArrayList<ChessPiece> p2Pieces; // all of player 2's remaining pieces
	private ArrayList<Position> posList; // current player's remaining pieces, link by reference
	
	// chessPiece variables
	private ChessPiece chessPiece;
	
	// player variables
	private static int turn; // used for incrementing and modular division
	private Color turnColor; // used for comparison to piece color, for limiting which pieces
									// can be selected.
	
	// mouse listener for determining what piece was selected
	public class MousePressListener implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent mouse)
		{
			Position mPos = new Position(mouse.getX(), mouse.getY()); // mouse position
			int row = (int)(mPos.getPosY()/height); // row index of chessboard
			int col = (int)(mPos.getPosX()/width); // col index of chessboard
			
			// link the posList to the current player's, by color
			if(turnColor.equals(GameWindow.getP1Color()))
			{
				posList = p1Pos;
			}
			else if(turnColor.equals(GameWindow.getP2Color()))
			{
				posList = p2Pos;
			}
			
			// if the board square has a chesspiece
			if(chessBoard[row][col].getPiece() != null)
			// if the piece in the square is that player's color
			if(chessBoard[row][col].getPiece().getColor().equals(turnColor))
			{
				chessPiece = chessBoard[row][col].getPiece(); // select the piece
				
				// if the chesspiece has possible moves
				if(chessPiece.calcMoves(boardPos))
				{
					//System.out.println("Moves in chess piece after calculating: " + chessPiece.getMoves().size());
					MessagePanel.getText().append("\nHas moves on list.");
					
					// pare down positions for pawn when enemy is in front, or not to the corner
					if(chessPiece instanceof Pawn)
					{
						for(int p = 0; p < chessPiece.getMoves().size(); p++)
						{
							// if a piece is right in front of it, it can't move there
							if(chessPiece.getPosition().getPosX() == chessPiece.getMoves().get(p).getPosX() &&
									chessBoard[chessPiece.getMoves().get(p).getPosY()][chessPiece.getMoves().get(p).getPosX()].getPiece() != null)
							{
								chessPiece.getMoves().remove(p); // remove the position
							}
							
							// if the diagonal has a piece
							if(chessBoard[chessPiece.getMoves().get(p).getPosY()][chessPiece.getMoves().get(p).getPosX()].getPiece() != null)
							// if a black piece isn't in a diagonal, it can't move there
							if(chessPiece.getPosition().getPosX() != chessPiece.getMoves().get(p).getPosX() &&
									chessPiece.getColor().equals(chessBoard[chessPiece.getMoves().get(p).getPosY()][chessPiece.getMoves().get(p).getPosX()].getPiece().getColor()))
							{
								chessPiece.getMoves().remove(p); // remove the position
							}
						}
					}
					
					// pare down the move list to spaces not occupied by your own piece
					// for each position in the player's position list
					for(int i = 0; i < posList.size(); i++)
					{
						// for each move in the chess piece's move list
						for(int j = 0; j < chessPiece.getMoves().size(); j++)
						{
							MessagePanel.getText().append("\nEntered inner loop: i,j " +
																	i + "," + j);
							MessagePanel.getText().append("\nPosition List x,y: " +
																	posList.get(i).getPosX() + "," +
																	posList.get(i).getPosY() +
																	"\nChess Piece move list x,y: " +
																	chessPiece.getMoves().get(j).getPosX() +
																	"," + chessPiece.getMoves().get(j).getPosY());
							
							// if any of the piece's possible moves is one of their allies...
							if(posList.get(i).equals(chessPiece.getMoves().get(j)))
							{
								// remove the move
								chessPiece.getMoves().remove(j);
								MessagePanel.getText().append("\nPosition removed: " +
																		posList.get(i).getPosX() + "," +
																		posList.get(i).getPosY() +
																		" from " + posList.toString());
							}
						}	
					}
					
				}
				MessagePanel.getText().append("\nPiece position: " + chessPiece.getPosition().getPosX() +
						"," + chessPiece.getPosition().getPosY() + "\n");
			}
			
		   // if the player has a piece selected.
			if(chessPiece != null)
			// for each move in the piece's list
			for(int i = 0; i < chessPiece.getMoves().size(); i++)
			{
				// display the moves in the message window
				for(int m = 0; m < chessPiece.getMoves().size(); m++)
				{
					MessagePanel.getText().append("\nPossible Moves:\n" +
														chessPiece.getMoves().get(m).getPosX() + "," +
														chessPiece.getMoves().get(m).getPosY() + "\n");
				}

				// if the chosen square is in the move list
				if(chessPiece.getMoves().get(i).equals(chessBoard[row][col].getPosition()))
				{
					MessagePanel.getText().append("\nMoving piece...");
					
					// update the position list
					for(int j = 0; i < posList.size(); j++)
					{
						// find the specific index of the position in your position list
						if(posList.get(j).equals(chessPiece.getPosition()))
						{
							MessagePanel.getText().append("\nPosition: " + posList.get(j).getPosX() +
																	"," + posList.get(j).getPosY() +
																	"\nChessBoard: " + chessPiece.getPosition().getPosX()
																	+ "," +	chessPiece.getPosition().getPosY());
							
							posList.remove(j); // the position isn't occupied by the player anymore
							// removed the piece from the old position
							chessBoard[chessPiece.getPosition().getPosY()][chessPiece.getPosition().getPosX()].setPiece(null);
							
							// message to make sure the piece was removed.
							if(chessBoard[chessPiece.getPosition().getPosY()][chessPiece.getPosition().getPosX()].getPiece() == null)
								MessagePanel.getText().append("\nNo longer has a piece");
							
							// add the new position to your position list
							posList.add(chessPiece.getMoves().get(i));
							break;
						}
					}
					
					MessagePanel.getText().append("\nRearranging pieces...");
					
					chessBoard[row][col].setPiece(chessPiece); // the piece is in a new spot
					// tell the piece their new position
					chessBoard[row][col].getPiece().getPosition().setPosX(col); 
					chessBoard[row][col].getPiece().getPosition().setPosY(row);
					
					// if the piece is a pawn, tell it that it moved
					if(chessPiece instanceof Pawn)
					{
						((Pawn) chessPiece).hasMoved();
					}
					
					turn = ++turn % 2; // change the turn
					break;
				}
			}
				
			// switch turns
			switch(turn)
			{
			case 0: turnColor = Color.BLACK; // if it is player 2's turn
			MessagePanel.getText().append("Turn is Black");
			break;
			case 1: turnColor = Color.WHITE; // if it is player 1's turn
			MessagePanel.getText().append("Turn is White");
			break;
			}
			
			// check for win
			checkPawnsWin();
			
			repaint();
			MessagePanel.getText().append(chessPiece.toString() + "\n");
		}

		private void checkPawnsWin()
		{
			// check the player that just moved
			if(GameWindow.getP1Color().equals(turnColor))
			{
				posList = p1Pos; //set access to the player's position list
				
				// find the color
				if(GameWindow.getP1Color().equals(Color.WHITE))
				//for each index in the list
					for(int i = 0; i < posList.size(); i++)
					{
						if(posList.get(i).getPosY() > 0)
						{
							MessagePanel.getText().setText("");
							MessagePanel.getText().append("Player 1 Won!!!");
							break;
						}
					}
				else if(GameWindow.getP1Color().equals(Color.BLACK))
					for(int i = 0; i < posList.size(); i++)
					{
						if(posList.get(i).getPosY() < 7)
						{
							MessagePanel.getText().setText("");
							MessagePanel.getText().append("Player 1 Won!!!");
							break;
						}
					}
			}
			// check the player that just moved
			if(GameWindow.getP2Color().equals(turnColor))
			{
				posList = p2Pos; //set access to the player's position list

				// find the color
				if(GameWindow.getP2Color().equals(Color.WHITE))
					//for each index in the list
					for(int i = 0; i < posList.size(); i++)
					{
						if(posList.get(i).getPosY() > 0)
						{
							MessagePanel.getText().setText("");
							MessagePanel.getText().append("Player 2 Won!!!");
							break;
						}
					}
				else if(GameWindow.getP2Color().equals(Color.BLACK))
					for(int i = 0; i < posList.size(); i++)
					{
						if(posList.get(i).getPosY() < 7)
						{
							MessagePanel.getText().setText("");
							MessagePanel.getText().append("Player 2 Won!!!");
							break;
						}
					}
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}
		
	}
	
	// create listener
	private MouseListener listener = new MousePressListener();
	
	/**
	 * Constructor with no parameters
	 */
	public ChessPanel()
	{
		super();
		this.setSize( 600, 400);
		pos = new Position(0,0);
		
		// create each player's position and piece lists
		p1Pos = new ArrayList<Position>();
		p2Pos = new ArrayList<Position>();
		p1Pieces = new ArrayList<ChessPiece>();
		p2Pieces = new ArrayList<ChessPiece>();
		posList = new ArrayList<Position>(); // list used for linking to the current player's position list

		// create chessboard
		createChessBoard();
		
		this.addMouseListener(listener);
		
		// create and fill all board positions
		boardPos = new ArrayList<Position>();
		for(int row = 0; row < BOARD_ROW; row++)
		{
			for(int col = 0; col < BOARD_COL; col++)
			{
				boardPos.add(new Position(col, row));
			}
		}
				
		// white always goes first
		turn = 1;
		turnColor = Color.WHITE;
	}

	/**
	 * The paintComponent
	 * @param g the graphics component
	 */
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;	
		
	   this.draw(g2);
	}
	
	/**
	 * Create the chessboard and reset the pieces
	 */
	public void createChessBoard()
	{
		int colorSwitch = 0; // controls which color the square will be
		
		width = getWidth()/BOARD_COL; // the width of a board square
		height = getHeight()/BOARD_ROW; // the height of a board sqare
		hiWidth = width - OFFSET; // the width of the pointer dot for a move
		hiHeight = height - OFFSET; // the height of the pointer dot for a move
		
		//System.out.println("Screen width: " + (width * BOARD_COL));
		
		chessBoard = new ChessBoard[BOARD_ROW][BOARD_COL]; // create a new chessBoard
		
		for(int row = 0; row < BOARD_ROW; row++)
		{
			for(int col = 0; col < BOARD_COL; col++)
			{
				// set current position to be passed to current chessboard
				pos.setPosX(col);
				pos.setPosY(row);
				
				// create a boardsquare at the given indices, with alternating color
				switch(colorSwitch)
				{
				case 0: chessBoard[row][col] = new ChessBoard(pos, 
																			width, 
																			height,
																			Color.WHITE);
					break;
				case 1: chessBoard[row][col] = new ChessBoard(pos, 
																			width, 
																			height,
																			Color.BLACK);
					break;
				}
				
				colorSwitch = ++colorSwitch % 2;
			}
			colorSwitch = ++colorSwitch % 2;
		}
		
		// reset all pieces
		resetPieces();
	}

	/**
	 * This method resets the pieces on the board
	 */
	public void resetPieces()
	{	
		//fill each square with the corresponding chesspiece
		for(int row = 0; row < chessBoard.length; row++)
		{
			for(int col = 0; col < chessBoard[row].length; col++)
			{
				// set current position to be passed to current piece
				pos.setPosX(col);
				pos.setPosY(row);
				
				chessBoard[row][col].setPiece(null); // resets the square to empty first
				
				if(row == 1) // the second and 6th rows are all pawns
				{
					// link the position list to the correct player
					if(GameWindow.getP1Color().equals(Color.BLACK))
					{
						posList = p1Pos;
					}
					else
					{
						posList = p2Pos;
					}
					// set the piece to the square
					chessBoard[row][col].setPiece(new Pawn(pos,
																		width,
																		height,
																		"blackPawn.png",
																		Color.BLACK));
					
					posList.add(chessBoard[row][col].getPosition()); // add the piece position to the list
					MessagePanel.getText().append("\nAdding position: " +
							posList.get(col).getPosX() + "," +
							posList.get(col).getPosY() +
							" to " + posList.toString());
				}
				else if(row == chessBoard.length - 2)
				{
					// link the position list to the correct player
					if(GameWindow.getP1Color().equals(Color.WHITE))
					{
						posList = p1Pos;
					}
					else
					{
						posList = p2Pos;
					}
					
					chessBoard[row][col].setPiece(new Pawn(pos,
																		width,
																		height,
																		"whitePawn.png",
																		Color.WHITE));
					
					posList.add(chessBoard[row][col].getPosition()); // add the piece
					MessagePanel.getText().append("\nAdding position: " +
							posList.get(col).getPosX() + "," +
							posList.get(col).getPosY() +
							" to " + posList.toString());
				}
			}
		}
		
	}
	
	/**
	 * Unimplemented Method from original UML
	 * @param piece the piece to move
	 * @param pos the position to move it to
	 */
	public void movePiece(ChessPiece piece, Position pos)
	{
		
	}
	
	/**
	 * Unimplemented Method from original UML
	 * @param piece the piece to show their moves
	 * @return returns the ArrayList of moves to show
	 */
	public ArrayList<Position> showMoves(ChessPiece piece)
	{
		ArrayList<Position> locations = piece.getMoves();
		
		return locations;
	}
	
	/**
	 * Gets the current turn number
	 * @return returns the current turn
	 */
	public int getTurn()
	{
		return turn;
	}
	
	/**
	 * Gets the current state of the chessBoard
	 * @return returns the chessboard object
	 */
	public ChessBoard[][] getChessBoard()
	{
		return chessBoard;
	}
	
	/**
	 * Unimplemented optional method for changing
	 * @param panel
	 */
 	public void changeTurn(JPanel panel)
	{
		turn = ++turn % 2;
		switch(turn)
		{
		case 0: turnColor = Color.BLACK;
				break;
		case 1: turnColor = Color.WHITE;
				break;
		}
		
		((ButtonPanel)panel).getLblTurn().setText("Player " + (2 - turn));
	}
	
 	/**
 	 * draw the board, highlighters, and pieces
 	 * @param g2 the paintbrush
 	 */
	public void draw(Graphics2D g2)
	{
		//System.out.println("Drawing board ...");
		Color currentColor = g2.getColor(); // save the brush color

		g2.setColor(Color.BLACK);
		
		//draw the board outline
		g2.drawRect(0,
				      0, 
				      BOARD_COL * width, 
				      BOARD_ROW * height);
		
		//draw each board square with its contents
		for(int row = 0; row < chessBoard.length; row++)
		{
			for(int col = 0; col < chessBoard[row].length; col++)
			{
				// draw the square at x(col* width), y(row* height) position
				chessBoard[row][col].draw(col * width,
													row * height,
													g2);
			}
		}
		
		// if you have a piece selected
		if(chessPiece != null)
		{
			// highlight the selected piece
			g2.setColor(Color.GREEN);
			g2.drawRect(chessPiece.getPosition().getPosX() * width,
					chessPiece.getPosition().getPosY() * height,
					hiWidth,
					hiHeight);
			
			g2.setColor(Color.BLUE);
		// draw a marker each possible move of the selected piece
			for(int i = 0; i < chessPiece.getMoves().size() ; i++)
			{
				g2.drawOval(chessPiece.getMoves().get(i).getPosX() * width,
								chessPiece.getMoves().get(i).getPosY() * height,
								hiWidth,
								hiHeight);
				/**
				MessagePanel.getText().append("\nFilling Oval at: x = " +
								chessPiece.getMoves().get(i).getPosX() * width +
								" y = " + chessPiece.getMoves().get(i).getPosY() * height);
				*/
			}
		}
		g2.setColor(currentColor);
		repaint();
	}
}
