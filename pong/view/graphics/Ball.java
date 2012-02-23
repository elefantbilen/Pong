package pong.view.graphics;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * A class that extends the Ellipse2D.Double to represent a ball on the game plan.
 * Contains methods to move, reset and colouring the ball.
 * @author frsimon
 *
 */

public class Ball extends Ellipse2D.Double implements Colorable
{
	private double originalRadius;
	private Color color;
	public Ball(double centerX, double centerY, double radius)
	{
		super(centerX - radius, centerY - radius, radius, radius);	
		this.originalRadius=radius;
		color = Color.WHITE;
	}
	
	/**
	 * Uses the parameter to move the ball accordingly to the difference
	 * in x and y coordinates.
	 * @param dx a double used for the difference in x coordinates.
	 * @param dy a double used for the difference in y coordinates.
	 */
	public void move(double dx, double dy)
	{
		this.x += dx;
		this.y += dy;
	}

	/**
	 * Sets the colour of the ball using the parameter.
	 * @param color a colour represented through the class Color.
	 */
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	/**
	 * Returns the current colour of the ball.
	 */
	public Color getColor()
	{
		return color;
	}
	
	/**
	 * Resets the ball to initial x,y coordinates and size values.
	 * @param x center x coordinate of the game plan.
	 * @param y center y coordinate of the game plan.
	 */
	public void resetBall(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.height = originalRadius;
		this.width = originalRadius;
	}
	
	/**
	 * Returns the current diameter of the ball.
	 * @return a double displaying the diameter.
	 */
	public double getDiameter()
	{
		return this.height;
	}
	
	/**
	 * Sets the size of the ball using a double factor to be multiplied with ball's
	 * height and width.
	 * @param sizeFactor a double representing the size factor.
	 */
	public void setSize(double sizeFactor)
	{
		this.height *= sizeFactor;
		this.width *= sizeFactor;
	}
}
