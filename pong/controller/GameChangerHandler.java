package pong.controller;
import java.util.*;
import java.awt.Color;
import pong.event.GameEventBall;
import pong.view.graphics.GameChangerIcon;

/**
 * GameChangerHandler is a observable class that handles different GameChangers.
 * Primary task is to let the Observer draw up a new GameChangerIcon,
 *  create and activate a GameChanger and assign the proper GameEvent and
 *	deactivate the GameChanger when it's depleted.
 * @author Grupp 4
 *
 */
public class GameChangerHandler extends Observable {
	private Random random = new Random();
	private int gameChangerIndex;
	private Color[] colors = new Color[] {Color.YELLOW, Color.BLUE, Color.CYAN, Color.GREEN,
										  Color.MAGENTA, Color.ORANGE, Color.RED, Color.PINK};
	private GameChangerIcon gameChangerIcon;
	private GameEventBall gameEventBall;
	private final int BALL_SIZE_CHANGER = 0;

	// For eventual expansion of the class
/*
	private GameEventPaddle gameEventPaddle;
	private GameEventPlayer gameEventPlayer;
	
	private final int BALL_DIRECTION_CHANGER = 1;
	private final int BALL_SPEED_CHANGER = 2;
	private final int PADDLE_SIZE_DECREASE = 3;
	private final int PADDLE_SIZE_INCREASE = 4;
	private final int BONUS_POINTS = 5;
	private final int PLAYER_INCREASE_LIFE = 6;
	private final int PLAYER_LOSE_LIFE = 7;
*/
	
	/**
	 * Constructor for the class, sets the index to the random-generated number.
	 * In this case only 0 will be chosen.
	 */
	public GameChangerHandler()
	{
			gameChangerIndex = random.nextInt(1);
	}
	
	/**
	 * Creates a GameChangerIcon and calls the observer with the GameChangerIcon.
	 * 
	 * @param x Center X-coordinate of the gameplan, received from Controller.
	 * @param y Center Y-coordinate of the gameplan, received from Controller.
	 */
	public void createGameChanger(int x, int y)
	{	
		gameChangerIcon = new GameChangerIcon(x, y, colors[gameChangerIndex]);
		gameChangerIcon.setActive(true);
		setChanged();
		notifyObservers(gameChangerIcon);
	}
	/**
	 * Deactivates a GameChanger by setting it false and notifying the Observer.
	 */
	public void deactivateGameChanger()
	{
		if(gameChangerIcon!=null)
		{
		gameChangerIcon.setActive(false);
		setChanged();
		notifyObservers(gameChangerIcon);
		}
	}
	/**
	 * Creates a GameEvent with correct changes depending on what GameChanger was chosen,
	 * in this case only a size-multiplier and notifies the Observer with the according GameEvent.
	 * 
	 * @param lastPlayerHit The last paddle that hit the ball.
	 */
	public void awardGameChanger(int lastPlayerHit)
	{
		switch(gameChangerIndex)
		{
			case BALL_SIZE_CHANGER:
				int ballSizeFactor = 2;
				gameEventBall = new GameEventBall();
				gameEventBall.setSize(ballSizeFactor);
				setChanged();
				notifyObservers(gameEventBall);
				break;
				
		}
	}
}