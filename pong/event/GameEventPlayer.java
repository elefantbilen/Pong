package pong.event;
/**
 * Subclass to GameEvent to determine what type of game event that happened.
 * Contains class specific methods that alter the player's stats.
 * @author Group 4
 *
 */
public class GameEventPlayer extends GameEvent {

	private int playerDextraLife;
	private boolean playerDead = false;
	private boolean scoreEvent = false;
	private int points;

	public GameEventPlayer() 
	{
	}
	/**
	 * Sets the field to alter the player's extra life.
	 * @param playerDextraLife an int that adds or removes player extra lives.
	 */
	public void setPlayerLife(int playerDextraLife)
	{
		this.playerDextraLife = playerDextraLife;
	}
	
	/**
	 * Returns the field that is used for setting player's extra life
	 * @return An int used for setting extra lives.
	 */
	public int getPlayerLife()
	{
		return playerDextraLife;
	}
	
	/**
	 * Sets the field to determine the player dead.
	 */
	public void setPlayerDead()
	{
		this.playerDead = true;
	}
	
	/**
	 * Returns the field that indicates if the player is dead.
	 * @return boolean that is true or false depending on the player's state.
	 */
	public boolean getPlayerDead()
	{
		return playerDead;
	}
	
	/**
	 * Sets the score event field to true to indicate that a score event happened.
	 */
	public void setScoreEvent()
	{
		scoreEvent = true;
	}
	
	/**
	 * Returns the current state of the score event.
	 * @return boolean to determine current state of score event.
	 */
	public boolean getScoreEvent()
	{
		return scoreEvent;
	}
		
	/**
	 * Returns the current bonus points.
	 * @return int with the current score points.
	 */
	public int	getPoints()
	{	
		return points;
	}
	
	/**
	 * Sets the bonus points to be used in the event.
	 * @param points an int with the points to be added.
	 */
	public void setPoints(int points)
	{
		this.points = points;
	}
	

}
