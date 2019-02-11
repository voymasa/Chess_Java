package final_project;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * 
 * @author Aaron Voymas
 * @since December 2015
 */
public abstract class ChessPiece
{
	private Position position; // by row and column
	private int width; // width of the piece on screen
	private int height; // hight of the piece on screen
	private String imageFileName; // name of the picture file
	private Color color; // the piece color
	private ArrayList<Position> moves; // list of possible moves
	
	/**
	 * Constructor
	 * @param pos the position of the piece row(x) by col(y)
	 * @param w width of the piece
	 * @param h height of the piece
	 * @param image filename of the associated picture
	 * @param color colore of the piece
	 */
	public ChessPiece(Position pos, int w, int h, String image, Color color)
	{
		this.position = new Position(pos.getPosX(), pos.getPosY());
		
		this.width = w;
		this.height = h;
		
		this.imageFileName = image;
		this.color = color;
		
		moves = new ArrayList<Position>();
	}

	/**
	 * @return the position
	 */
	public Position getPosition()
	{
		return position;
	}

	/**
	 * @return the color
	 */
	public Color getColor()
	{
		return color;
	}
	
	/**
	 * 
	 * @return the width
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * 
	 * @return the height
	 */
	public int getHeight()
	{
		return height;
	}

	
	/**
	 * @param size the size to set
	 */
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Abstract method for calculating the piece's possible moves
	 * @param pos a list of all possible board positions
	 * @return the list of possible moves
	 */
	public abstract boolean calcMoves(ArrayList<Position> pos);
	
	/**
	 * Unused method for moving the piece to a new position
	 * @param position the new position
	 */
	public void move(Position position)
	{
		this.position.setPosX(position.getPosX());
		this.position.setPosY(position.getPosY());
	}
	
	/**
	 * 
	 * @return the list of possible moves
	 */
	public ArrayList<Position> getMoves()
	{
		return moves;
	}
	
	//public void draw(int x, int y, Component panel)
	// Credit to Will McLaughlin for base code
	public void draw(int x, int y, Graphics2D g2)
	{
		BufferedImage pieceImg = null;
		try {
			pieceImg = ImageIO.read(new File("src/images/" + imageFileName));
		} catch (Exception e) {
			e.printStackTrace();
		}

		//frame.setBounds(0, 0, loadImg.getWidth(), loadImg.getHeight());
		//frame.setBounds(0, 0, 200, 200);
		
		g2.drawImage(pieceImg, x, y, width, height, null);

	}

	
}
