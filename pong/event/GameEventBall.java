package pong.event;
/**
 * Subclass to GameEvent to determine what type of game event that happened.
 * Contains class specific methods that alter the ball's behaviour.
 * @author Group 4
 *
 */
public class GameEventBall extends GameEvent {
	private double ballSpeedFactor = 1;
	private int ballDirectionFactor = 1;

	public GameEventBall() 
	{
	}
	/**
	 * Sets the ball speed factor with the parameter supplied.
	 * @param ballSpeedFactor a double factor that is multiplied with the ball's speed.
	 */
	public void setBallSpeedFactor(double ballSpeedFactor)
	{
		this.ballSpeedFactor = ballSpeedFactor;
	}
	/**
	 * Returns the ball speed factor.
	 * @return Ball speed factor (double).
	 */
	public double getBallSpeedFactor()
	{
		return ballSpeedFactor;
	}
	/**
	 * Sets the ball direction factor for use of increasing, or decreasing, ball speed.
	 * @param ballDirectionFactor an int used for multiplying with the ball's speed.
	 */
	public void setBallDirection(int ballDirectionFactor)
	{
		this.ballDirectionFactor = ballDirectionFactor;
	}
	/**
	 * Returns the current ball direction factor.
	 * @return a ball direction factor as an int.
	 */
	public int getBallDirection()
	{
		return ballDirectionFactor;
	}
}
