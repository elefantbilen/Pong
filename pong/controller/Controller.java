package pong.controller;
import pong.model.GameModel;
import javax.swing.*;
import pong.view.GameView;
import pong.view.graphics.Ball;
import pong.view.graphics.GameChangerIcon;
import pong.view.graphics.Paddle;

import java.awt.event.*;
import java.util.Random;


/**
 * A class that functions as a game engine, performing all the
 * necessary algorithms for movement within the game.
 * @author Grupp 4
 * @version 2.0
 * @version 2012-02-13
 *
 */
public class Controller implements ActionListener
{
	private final int PADDLE_BASE_SPEED					=   10;
	private final int PADDLE_BASE_SPEED_CORRECTION  	= 	100;
	
	public final int COLLISION_INVERT_X 				=	-1;
	public final int COLLISION_INVERT_Y 				= 	-1;
	
	public final int PLAYER_ONE_PADDLE_ACTION_UP 		= 	1;
	public final int PLAYER_ONE_PADDLE_ACTION_DOWN 		= 	2;
	public final int PLAYER_TWO_PADDLE_ACTION_UP		= 	3;
	public final int PLAYER_TWO_PADDLE_ACTION_DOWN 		= 	4;
	public final int PLAYER_THREE_PADDLE_ACTION_UP 		= 	5;
	public final int PLAYER_THREE_PADDLE_ACTION_DOWN 	= 	6;
	public final int PLAYER_FOUR_PADDLE_ACTION_UP 		= 	7;
	public final int PLAYER_FOUR_PADDLE_ACTION_DOWN		=	8;
	
	public final int PLAYER_ONE							=	0;
	public final int PLAYER_TWO							=	1;
	public final int PLAYER_THREE						=	2;
	public final int PLAYER_FOUR						=	3;
	
	private final int PERIOD							=	12;
	
	private GameView gameView;
	private Timer gameTimer;
	private GameModel gameModel;
	private GameChangerHandler gameChangerHandler;
	
	private int lastPlayerHit; 
	private int[] centerCoordinate;
	private int ballSpeedX;
	private int ballSpeedY;
	private int gamePlanSide;
	
	private int changerPeriod							=	0;
	
	private int[] paddleSpeed =  new int[4];
	//private boolean gameOn; 
	
	/**
	 * Constructor that initiates and instances all objects.
	 * @param gameView reference to the class GameView 
	 * @param gameModel reference to the class GameModel.
	 */
	public Controller(GameView gameView, GameModel gameModel)
	{
		this.gameView = gameView;
		this.gameModel = gameModel;
		gameTimer = new Timer(PERIOD, this);
		gameTimer.setInitialDelay(2000);
		gameChangerHandler = new GameChangerHandler();
		gameChangerHandler.addObserver(gameView);
	}
	
	/**
	 * Action event.
	 * 
	 * Timer uses this to update the game.
	 * 
	 * @param e The action event that call the method
	 */
	public void actionPerformed(ActionEvent e)
	{
		gameTimer();
		gameChangerTimer();
		
	}
	
	/**
	 * The game's actions during a tick
	 * 
	 * Updates everything that has taken place since last tick; ball, paddles
	 * and also checks collision detection
	 */
	public void gameTimer()
	{
		
		collisionDetect();			
		verticalPaddleUpdate();
		horizontalPaddleUpdate();
		gameModel.setBallSpeed(ballSpeedX, ballSpeedY);	
		gameModel.setPaddleUNO(ballSpeedY);
	}
	
	/**
	 * Timer for gameChanger
	 * 
	 * The timer for creating a new GameChanger object and
	 * subsequently removing it.
	 */
	
	public void gameChangerTimer()
	{
		changerPeriod++;
		if(changerPeriod == 400)
		{
			gameChangerHandler.createGameChanger(centerCoordinate[0], centerCoordinate[1]);
			
		}

		if(changerPeriod == 1000)
		{
			gameChangerHandler.deactivateGameChanger();
			changerPeriod = 0;
		}	
	}
	
	/**
	 * Starts the game
	 * 
	 * Sets up the the constraints and rules for the game, after that, start the timer
	 * 
	 * @param gamePlanSide the dynamic size of the gameplan, sent from GameView
	 */
	public void start(int gamePlanSide)
	{
		if(gamePlanSide != 0)
		{
			this.gamePlanSide = gamePlanSide;
		}
		
		Random random = new Random();
		double[] speedListX = {2,1,-1,-2,-2,-1, 1, 2};
		double[] speedListY = {1,2, 2, 1,-1,-2,-2,-1};
		int index = random.nextInt(speedListX.length);
		double speedScale = 0.004;
		ballSpeedX = (int) (this.gamePlanSide * speedListX[index] * speedScale);
		ballSpeedY = (int) (this.gamePlanSide * speedListY[index] * speedScale);
		centerCoordinate = gameView.getCenter();
		gameModel.setGameOn(true);
		gameTimer.start();		
	}
	
	/**
	 * Stop the timer
	 */
	public void stop()
	{
		gameTimer.stop();
		gameModel.setGameOn(true);
	}
	/**
	 * Moves the horizontalPaddles if their paddleSpeed has been set
	 */
	private void horizontalPaddleUpdate()
	{
		Paddle[] paddles = gameView.getPaddles();
		
		for(int paddleIndex = 2; paddleIndex < 4; paddleIndex++)
		{
			//Test f�r player 3 och 4
			int dynamicSpeedConstant = ((int)paddles[paddleIndex].getWidth() / PADDLE_BASE_SPEED_CORRECTION);
			if(paddles[paddleIndex].getX() < (paddles[PLAYER_ONE].getX() + gameView.getWallThickness()))
			{
				int dXCorrection = (int)(paddles[PLAYER_ONE].getX() + gameView.getWallThickness() - paddles[paddleIndex].getX());
				gameModel.setPaddleAction(paddleIndex,
						dXCorrection, 
						0);
				paddleSpeed[paddleIndex] = 0;
			}
			else if((paddles[paddleIndex].getX() + paddles[paddleIndex].getWidth()) > paddles[PLAYER_TWO].getX())
			{
				int dXCorrection = (int)(paddles[PLAYER_TWO].getX() - (paddles[paddleIndex].getX() + paddles[paddleIndex].getWidth()));
				gameModel.setPaddleAction(paddleIndex,
						dXCorrection, 
						0);
				paddleSpeed[paddleIndex] = 0;
			}
			else
			{
				gameModel.setPaddleAction(paddleIndex,paddleSpeed[paddleIndex]*dynamicSpeedConstant , 0);
			}
								
						
			if (paddleSpeed[paddleIndex]>0)
			{
				paddleSpeed[paddleIndex]--;
			}
			if (paddleSpeed[paddleIndex]<0)
			{
				paddleSpeed[paddleIndex]++;
			}
		}
	}
	/**
	 * Moves the verticalPaddles if their paddleSpeed has been set
	 */
	private void verticalPaddleUpdate()
	{
		Paddle[] paddles = gameView.getPaddles();
		
		for(int paddleIndex = 0; paddleIndex < 2; paddleIndex++)
		{
			//Test f�r player 1 och 2
			int dynamicSpeedConstant =((int)paddles[paddleIndex].getHeight() / PADDLE_BASE_SPEED_CORRECTION);
			if(paddles[paddleIndex].getY() < gameView.getWallThickness())
			{
				int dYCorrection = (int)(gameView.getWallThickness() - paddles[paddleIndex].getY());
				gameModel.setPaddleAction(paddleIndex,
						0, 
						dYCorrection);
				paddleSpeed[paddleIndex] = 0;
			}
			if((paddles[paddleIndex].getY() + paddles[paddleIndex].getHeight()) > paddles[PLAYER_THREE].getY())
			{
				int dYCorrection = (int)(paddles[PLAYER_THREE].getY() - paddles[paddleIndex].getY() - paddles[paddleIndex].getHeight());
				gameModel.setPaddleAction(paddleIndex,
						0, 
						dYCorrection);
				paddleSpeed[paddleIndex] = 0;
			}
			else
			{
				gameModel.setPaddleAction(paddleIndex,
								0, 
								paddleSpeed[paddleIndex]*dynamicSpeedConstant);
			}			
			if (paddleSpeed[paddleIndex]>0)
			{
				paddleSpeed[paddleIndex]--;
			}
			if (paddleSpeed[paddleIndex]<0)
			{
				paddleSpeed[paddleIndex]++;
			}
		}
	}
	


	/**
	 * Sets the movement of the paddles 
	 * 
	 * Moves the player corresponding paddle if the game is on and player input is received.
	 * 
	 * @param paddleMovement the identifier for the paddle to be affected and for which direction
	 */
	public void calculatePaddleAction(int paddleMovement)
	{
		if (gameModel.isGameOn())
		{
			Paddle[] paddles = gameView.getPaddles();
			switch (paddleMovement)
			{
				case PLAYER_ONE_PADDLE_ACTION_UP:
					if(paddles[PLAYER_ONE].getY() > gameView.getWallThickness())
					{
						paddleSpeed[PLAYER_ONE] -= PADDLE_BASE_SPEED;
					}	
					break;
					
				case PLAYER_ONE_PADDLE_ACTION_DOWN:
					if((paddles[PLAYER_ONE].getY() + paddles[PLAYER_ONE].getHeight()) < 
						(paddles[PLAYER_THREE].getY() - gameView.getWallThickness()))
					{
						paddleSpeed[PLAYER_ONE] += PADDLE_BASE_SPEED;
					}	
					break;
					
				case PLAYER_TWO_PADDLE_ACTION_UP:
					if(paddles[PLAYER_TWO].getY() > gameView.getWallThickness())
					{
						paddleSpeed[PLAYER_TWO] -= PADDLE_BASE_SPEED;
					}
					break;
					
				case PLAYER_TWO_PADDLE_ACTION_DOWN:
					if((paddles[PLAYER_TWO].getY() + paddles[PLAYER_TWO].getHeight()) < 
						(paddles[PLAYER_THREE].getY() - gameView.getWallThickness()))
					{
						paddleSpeed[PLAYER_TWO] += PADDLE_BASE_SPEED;
					}
					break;
					
				case PLAYER_THREE_PADDLE_ACTION_UP:
					if(paddles[PLAYER_THREE].getX() > 
					(paddles[PLAYER_ONE].getX() + gameView.getWallThickness()))
					{	
						paddleSpeed[PLAYER_THREE] -= PADDLE_BASE_SPEED;
					}
					break;
					
				case PLAYER_THREE_PADDLE_ACTION_DOWN:
					if((paddles[PLAYER_THREE].getX()+ paddles[PLAYER_THREE].getWidth()) < 
							paddles[PLAYER_TWO].getX())
					{	
						paddleSpeed[PLAYER_THREE] += PADDLE_BASE_SPEED;
					}
					break;
					
				case PLAYER_FOUR_PADDLE_ACTION_UP:
					if(paddles[PLAYER_FOUR].getX() > 
					(paddles[PLAYER_ONE].getX() + gameView.getWallThickness()))
					{	
						paddleSpeed[PLAYER_FOUR] -= PADDLE_BASE_SPEED;
					}
					break;
					
				case PLAYER_FOUR_PADDLE_ACTION_DOWN:
					if((paddles[PLAYER_FOUR].getX() + paddles[PLAYER_FOUR].getWidth()) < 
							paddles[PLAYER_TWO].getX())
					{	
						paddleSpeed[PLAYER_FOUR] += PADDLE_BASE_SPEED;
					}
					break;
			}
		}
	}
	
	/**
	 * Collision detection
	 * 
	 * Checks if the ball hits a paddle and inverts the speed to make it look like it bounced.
	 * Also deals points to a player who made the ball bounce. Also only checks the x or y-value
	 * depending on wich direction the ball has.
	 * 
	 */
	public void collisionDetect()
	{
		if(ballSpeedX > 0)
		{
			check(1);
		}
		else
		{
			check(0);
		}
		if (ballSpeedY > 0)
		{
			check(2);
		}
		else
		{
			check(3);
		}		
		checkChanger();
	}
	
	
	/**
	 * Checks whether the ball has hit a paddle.
	 * 
	 * Compares the ball's coordinates with the index of the paddle sent from the
	 * method collisionDetect.
	 * 
	 * @param i The index of the paddle to be checked.
	 */
	private void check(int i)
	{
		Ball ball = gameView.getBall();
		Paddle[] paddles = gameView.getPaddles();
		double ballYPos = ball.getCenterY();
		double ballXPos = ball.getCenterX();
		double ballDiameter = ball.getHeight();
		double ballRadius = ballDiameter / 2;
		double paddleY=paddles[i].getY();
		double paddleX=paddles[i].getX();
		double paddleXWidth=paddles[i].getWidth();
		double paddleYHeight=paddles[i].getHeight();
		switch (i){
			case 0:
				if ((ballXPos-ballRadius) < (paddleX+paddleXWidth))
				{
					if (paddles[i].contains(ballXPos-ballRadius, ballYPos))
					{
						ballSpeedX *= COLLISION_INVERT_X;
						ballSpeedY += paddleSpeed[i] / (PADDLE_BASE_SPEED / 2);
						if (ballSpeedY == 0)
						{
							ballSpeedY = (paddleSpeed[i] / (PADDLE_BASE_SPEED / 2)) ;
						}
						
						if (paddles[i].isActive())
							gameModel.setPlayerScore(i, 1000);
					}
					else 
					{
						if (paddles[i].isActive())
							deathHandler(i);
						else
							ballSpeedY *= COLLISION_INVERT_Y;
					}
				
				}	
				
					
				break;
			case 1:
				if ((ballXPos + ballRadius) > (paddleX))
				{
					if (paddles[i].contains(ballXPos+ballRadius, ballYPos))
					{
						ballSpeedX *= COLLISION_INVERT_X;
						ballSpeedY += paddleSpeed[i] / (PADDLE_BASE_SPEED / 2);
						if (ballSpeedY == 0)
						{
							ballSpeedY = (paddleSpeed[i] / (PADDLE_BASE_SPEED / 2)) ;
						}
						if (paddles[i].isActive())
							gameModel.setPlayerScore(i, 1000);
						
						//po�ng
					}
					else
					{
						if (paddles[i].isActive())
							deathHandler(i);
						else
							ballSpeedY *= COLLISION_INVERT_Y;
					}
				}
				break;
			case 2 :
				if ((ballYPos + ballRadius) > paddleY)
				{
					if (paddles[i].contains(ballXPos, ballYPos+ballRadius))
					{
						ballSpeedY *= COLLISION_INVERT_Y;
						ballSpeedX += paddleSpeed[i] / (PADDLE_BASE_SPEED / 2);
						if (ballSpeedX == 0)
						{
							ballSpeedX = (paddleSpeed[i] / (PADDLE_BASE_SPEED / 2)) ;
						}
						//po�ng
						if (paddles[i].isActive())
							gameModel.setPlayerScore(i, 1000);
					}
					else 
					{
						if (paddles[i].isActive())
							deathHandler(i);
						else
							ballSpeedY *= COLLISION_INVERT_Y;
					}

					
				}
				break;
			case 3:
				if ((ballYPos - ballRadius) < (paddleY + paddleYHeight))
				{
					if (paddles[i].contains(ballXPos, ballYPos-ballRadius))
					{
						ballSpeedY *= COLLISION_INVERT_Y;
						ballSpeedX += paddleSpeed[i] / (PADDLE_BASE_SPEED / 2);
						if (ballSpeedX == 0)
						{
							ballSpeedX = (paddleSpeed[i] / (PADDLE_BASE_SPEED / 2)) ;
						}
						//po�ng
						if (paddles[i].isActive())
							gameModel.setPlayerScore(i, 1000);
					}
					else
					{
						if (paddles[i].isActive())
							deathHandler(i);
						else
							ballSpeedY *= COLLISION_INVERT_Y;
					}
				}
				break;
		}
		
		
	}
	
	/**
	 * Collision detection for the gameChanger icon
	 * 
	 * Checks if the ball has hit the gameChanger icon, if so, award the player a bonus
	 * or modify the the game corresponding to the gameChanger. In this case: alter the 
	 * size of the ball.
	 */
	private void checkChanger()
	{
		GameChangerIcon gameChangerIcon = gameView.getGameChangerIcon();
		Ball ball = gameView.getBall();
		if((gameChangerIcon!=null)&&(gameChangerIcon.getActive()==true))
		{
			if (gameChangerIcon.contains(ball.getCenterX(), ball.getCenterY())||
				(ball.contains(gameChangerIcon.getCenterX(), gameChangerIcon.getCenterY())))	
			{
					gameChangerHandler.deactivateGameChanger();
					changerPeriod=0;
					gameChangerHandler.awardGameChanger(lastPlayerHit);
			}	
		}
	}
	
	/**
	 * For handling the event when a player misses the ball.
	 * 
	 * Affects the player that missed the ball. Decreasing player life and points.
	 * Also stops the timer, resets the ball and and removes the gameChanger icon
	 * 
	 * @param i The player to be affected.
	 */
	private void deathHandler(int i)
	{
		stop();
		gameModel.setPlayerLife(i, true);
		gameModel.setPlayerScore(i, -200);
		gameChangerHandler.deactivateGameChanger();
		resetPaddles();
		gameView.resetBall();
		if (gameModel.isGameOn())
		{
			start(0);
		}
	}
	
	/**
	 * Resets the paddles
	 * 
	 * Moves the paddle into standard position.
	 */
	private void resetPaddles()
	{
		for (Paddle paddle : gameView.getPaddles())
		{
			paddle.resetPaddle();
		}
			
	}
}