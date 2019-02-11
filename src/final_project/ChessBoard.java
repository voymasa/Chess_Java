package final_project;

import java.awt.Color;
import java.awt.Graphics2D;

public class ChessBoard
{
	private Position position; // by row and column
	private Color color;
	private ChessPiece piece;
	
	private int width;
	private int height;
	
	public ChessBoard(Position pos, int w, int h, Color color)
	{
		this.position = new Position(pos.getPosX(), pos.getPosY());
		
		this.color = color;
		this.width = w;
		this.height = h;
	}

	/**
	 * @return the position
	 */
	public Position getPosition()
	{
		return position;
	}

	/**
	 * @return the piece
	 */
	public ChessPiece getPiece()
	{
		return piece;
	}

	/**
	 * @return the width
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * @param piece the piece to set
	 */
	public void setPiece(ChessPiece piece)
	{
		this.piece = piece;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height)
	{
		this.height = height;
	}
	
	public void draw(int x, int y, Graphics2D g2)
	{
		g2.setColor(color);
		g2.fillRect(x, y, width, height);
		
		if(this.getPiece() != null)
		{
			this.getPiece().draw(x, y, g2);
		}
	}
}
