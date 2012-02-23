package pong.view.graphics;
import java.awt.Color;
/**
 * An interface used for colouring various elements within the gui.
 * @author Group 4
 *
 */

public interface Colorable
{
	/**
	 * Sets the color of the object calling the method with the parameter.
	 * @param color a color represented by the class Color
	 */
	public void setColor(Color color);	
	/**
	 * Returns the current color.
	 * @return A color of the class Color.
	 */
	public Color getColor();
}
