package final_project;

import java.awt.Color;
import java.util.ArrayList;

public class Pawn extends ChessPiece
{
	private boolean hasMoved; // to be used for moving two square first move
	
	// Constructor with the position of the piece (row/col, the width and height, what
	// image file to use, and the piece color (Black or White)
	public Pawn(Position pos, int w, int h, String image, Color color)
	{
		super(pos, w, h, image, color);
		hasMoved = false;
	}

	/**
	 * This method tells the piece that it's moved
	 */
	public void hasMoved()
	{
		hasMoved = true;
	}
	
	/**
	 * This method calculates the pieces' possible move from their current position.
	 * @param pos the list of all positions on the board (row/col)
	 * @return returns true if the piece's move list has at least one position
	 */
	@Override
	public boolean calcMoves(ArrayList<Position> pos)
	{
		// Tell what positions were removed when cleared
		for(int i = 0; i < getMoves().size(); i++)
		{
			MessagePanel.getText().append("\nRemoving " +
													getMoves().get(i).getPosX() + "," +
													getMoves().get(i).getPosY());
		}
		this.getMoves().clear(); // clear previously calculated moves
		
		// used to store each position to be add to the moves list
		Position location = new Position(0,0); // current location
		Position move = new Position(0,0); // position to move to
		
		// find the position that is the same as the pawn
		for(int i = 0; i < pos.size(); i++)
		{
			// When it finds the piece's current position in the board list
			if(pos.get(i).equals(this.getPosition()))
			{
				MessagePanel.getText().append("\nCalculating moves....");
				
				// if they are the same position, store that in the location and move variable
				location.setPosX(pos.get(i).getPosX());
				location.setPosY(pos.get(i).getPosY());
				MessagePanel.getText().append("\nCurrent position... " + 
														location.getPosX() + "," +
						                        location.getPosY() +
						                        location.toString());
				
				move.setPosX(pos.get(i).getPosX());
				move.setPosY(pos.get(i).getPosY());
				MessagePanel.getText().append("\nCurrent move... " + 
						move.getPosX() + "," +
                  move.getPosY() +
                  move.toString());
				break; // once the location is found, break out of loop
			}
		}
		
		// (Pawn) add the next square along the y-axis, to the move list
		if(this.getColor().equals(Color.BLACK)) // black pieces move down the board (+y)
		{
			move = new Position(location.getPosX(), (location.getPosY() + 1)); // move down the board
			this.getMoves().add(move); // add the move to the list
			
			//add attack spots
			move = new Position((location.getPosX() - 1), (location.getPosY() + 1));
			this.getMoves().add(move);
			move = new Position((location.getPosX() + 1), (location.getPosY() + 1));
			this.getMoves().add(move);
			
			MessagePanel.getText().append("\nMove added: " +
													move.getPosX() + "," +
													move.getPosY() + " Total Moves = " + this.getMoves().size());
			//if the piece hasn't moved yet
			if(!hasMoved)
			{
				move = new Position(location.getPosX(), (location.getPosY() + 2)); // move two square down the board
				this.getMoves().add(move);
				
				MessagePanel.getText().append("\nMove added: " +
						move.getPosX() + "," +
						move.getPosY() + " Total Moves = " + this.getMoves().size());
			} 
			return true; // there are moves in the list
		}
		else if(this.getColor().equals(Color.WHITE))// white pieces move up the board(-y)
		{
			move = new Position(location.getPosX(), (location.getPosY() - 1)); // move up the board
			this.getMoves().add(move);// add the move to the list
			
			//add attack spots
			move = new Position((location.getPosX() - 1), (location.getPosY() - 1));
			this.getMoves().add(move);
			move = new Position((location.getPosX() + 1), (location.getPosY() - 1));
			this.getMoves().add(move);
			
			MessagePanel.getText().append("\nMove added: " +
					move.getPosX() + "," +
					move.getPosY() + " Total Moves = " + this.getMoves().size());
			//if the piece hasn't moved yet, calculate a second forward square
			if(!hasMoved)
			{
				move = new Position(location.getPosX(), (location.getPosY() - 2)); // move two square up the board
				this.getMoves().add(move); // add the extra move.
				
				MessagePanel.getText().append("\nMove added: " +
						move.getPosX() + "," +
						move.getPosY() + " Total Moves = " + this.getMoves().size());
			}
			MessagePanel.getText().append("\nReturning true");
			return true;
		}
		
		// if the move list has at least one move, return true; this code never executes
		// according to Eclipse, and shows an error
		if(!this.getMoves().isEmpty())
		{
			return true;
			//MessagePanel.getText().append("Has available moves...");
		}
		
		// if the code makes it here, then no moves were on the list.
		return false;
	}
}
