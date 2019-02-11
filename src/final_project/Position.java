package final_project;

public class Position
{
	private int posX;
	private int posY;
	
	public Position(int x, int y)
	{
		this.posX = x;
		this.posY = y;
	}

	/**
	 * @return the posX
	 */
	public int getPosX()
	{
		return posX;
	}

	/**
	 * @return the posY
	 */
	public int getPosY()
	{
		return posY;
	}

	/**
	 * @param posX the posX to set
	 */
	public void setPosX(int posX)
	{
		this.posX = posX;
	}

	/**
	 * @param posY the posY to set
	 */
	public void setPosY(int posY)
	{
		this.posY = posY;
	}
	
	/**
	 * Overrides the equals method of the Object Cosmic superclass.
	 * Equal if both the x and y positions are the same.
	 */
	public boolean equals(Object otherObject)
	{
		Position other = (Position)otherObject;
		return (this.getPosX() == other.getPosX()
				&& this.getPosY() == other.getPosY());
	}
}
