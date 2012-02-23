package pong.view.graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.*;
/**
 * Class that extends Rectangle to represent paddles on the game plan.
 * Contains methods to move, colour and deactivate the paddles.
 * @author Group 4
 *
 */
public class Paddle extends Rectangle implements Colorable 
{
	private Color color;
	private Color INACTIVE_COLOR = Color.DARK_GRAY;
	private boolean active;
	private int originalX;
	private int originalY;
	
	public Paddle(int x, int y, int width, int height, boolean active, Color color)
	{
		// Initiate the rectangle
		super(x,y,width,height);
		
		this.setColor(color);
		this.originalX = x;
		this.originalY = y;
		this.active = active;
		
	}
	
	/**
	 * Moves the paddle the the new position
	 * @param dx difference in x coordinates
	 * @param dy difference in y coordinates
	 */
	public void move(int dx, int dy)
	{
		if(active)
		{
			super.x += dx;
			super.y += dy;
		}
	}
	
	public void resetPaddle()
	{
		this.x = originalX;
		this.y = originalY;
	}
	
	/**
	 * Deactivates the paddle and turns it into a wall
	 * @param x new x coordinate of the wall
	 * @param y new y coordinate of the wall
	 * @param width new width of the wall
	 * @param height new height of the wall
	 */
	public void deactivate(int x, int y, int width, int height)
	{
		// Set size to gamePlan the gameplan size
		super.x = x;
		super.y = y;
		this.originalX = x;
		this.originalY = y;
		// Position deactived paddle
		super.width = width;
		super.height = height;
		
		// Set to inactive color
		this.setColor(INACTIVE_COLOR);
		
		//Set active as false
		this.active = false;
	}

	/**
	 * Sets the color of the paddle
	 * @param color new colour of the paddle
	 */
	@Override
	public void setColor(Color color)
	{
		this.color = color;		
	}

	/**
	 * Returns the color of the paddle
	 * @return the colour of the paddle.
	 */
	@Override
	public Color getColor()
	{
		return color;
	}
	public boolean isActive()
	{
		return active;
	}
}
 