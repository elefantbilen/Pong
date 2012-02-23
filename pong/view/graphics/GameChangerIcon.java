package pong.view.graphics;
import java.awt.Color;
import java.awt.Rectangle;
/**
 * A class that extends Rectangle to be able to represent a Game Changer on the game plan.
 * Can be set to different colours and set as active or not.
 * @author frsimon
 *
 */

public class GameChangerIcon extends Rectangle implements Colorable{
	private Color color;
		
	private static int width = 40;
	private static int height = 40;
	private boolean isActive = false;
	
	public GameChangerIcon(int x, int y, Color color)
	{
		// Initiate the rectangle
		super((x-width/2),(y-height/2),width,height);
		this.setColor(color);
	}
	/**
	 * Sets the color of the GameChangerIcon
	 * @param color the color to be set represented through the class Color.
	 */
	@Override
	public void setColor(Color color) {
		this.color=color;
	}

	/**
	 * Returns the current color of the GameChangerIcon
	 * @return a color represented through Color.
	 */
	@Override
	public Color getColor() {
		return color;
	}
	
	/**
	 * Returns a boolean to determine if the icon is active or not.
	 * @return boolean that represents active or not.
	 */
	public boolean getActive()
	{
		return isActive;
	}
	
	/**
	 * Sets the state of the Icon using the parameter.
	 * @param setState boolean that sets the icon active or inactive.
	 */
	public void setActive(boolean setState)
	{
		this.isActive=setState;
	}
}
