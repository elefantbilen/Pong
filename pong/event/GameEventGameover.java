package pong.event;
import pong.model.Player;
/**
 * A class that is used for initiating an event for game over. Has various methods
 * for getting and setting eventual winners, player indexes and setting the event active.
 * Subclass to GameEvent to inherit useful methods.
 * @author Group 4
 */

public class GameEventGameover extends GameEvent {
	
	private boolean gameOverCheck = false;
	private Player[] players;
	private String winner;
	
	public GameEventGameover() {	}

	/**
	 * Sets the field gameOverCheck to true, to be able to trigger the event.
	 */
	public void setGameOver()
	{
		gameOverCheck = true;
	}
	
	/**
	 * Returns the current game over status.
	 * @return The boolean that states the game over status.
	 */
	public boolean getGameOver()
	{
		return gameOverCheck;
	}
	
	/**
	 * A method used for setting the player array to the parameter for score calculation
	 * at game over.
	 * @param players the player array that is currently used by the game.
	 */
	public void setPlayers(Player[] players) {
		this.players = players;
	}

	/**
	 * Returns the player array for score display.
	 * @return Array of players.
	 */
	public Player[] getPlayers() {
		return players;
	}
	
	/**
	 * Returns a string which contains the winning player's name.
	 * @return A string with the winning player's name.
	 */
	public String getWinner() {
		// TODO Auto-generated method stub
		return winner;
	}
	
	/**
	 * A method used for setting the winner when the game is over to display
	 * to the player who won.
	 * @param winner A string that contains the name of the player who won.
	 */
	public void setWinner(String winner) {
		this.winner = winner;
	}

}
