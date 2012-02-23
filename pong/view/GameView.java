package pong.view;
import pong.MainPong;
import pong.controller.Controller;
import pong.controller.GameChangerHandler;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import pong.event.GameEventBall;
import pong.event.GameEventGameover;
import pong.event.GameEventPaddle;
import pong.event.GameEventPlayer;
import pong.model.GameModel;
import pong.view.graphics.Ball;
import pong.view.graphics.Colorable;
import pong.view.graphics.GameChangerIcon;
import pong.view.graphics.Paddle;
import pong.view.menu.ButtonPanel;
import pong.view.panel.HighScorePanel;
import pong.view.panel.ScorePanel;


/**
 * This is a JPanel which implements Observer that contains 
 * the UI elements of the game. It also has a reference to 
 * the model for updating purposes. It has to implement 
 * the update method from the interface to be able to 
 * receive the notifyObservers event from the model.
 * 
 * @author Group 4
 * @version 4.0
 * @since 2012-02-22
 */
 
public class GameView extends JPanel implements Observer 
{

	private int gamePlanSide;
	private int wallThickness;
	private int numberOfPlayers;
	private int paddleThickness;
	private int paddleLength;
	private int northMargin;
	private int southMargin;
	private static double WALL_PERCENTAGE 	= 	0.015;
	public Color[] COLOR_ARRAY 				= 	new Color[] { new Color(30, 144, 255), 
																Color.PINK, 
																Color.YELLOW, 
																Color.GREEN };
	private GameChangerIcon gameChangerIcon;
	private GameModel gameModel;
	private ArrayList<Shape> shapeArray;
	private Ball ball;
	private Paddle[] paddles = new Paddle[4];
	private JLabel winnerTextLabel;
	private HighScorePanel highScorePanel;

	private Controller controller;

	private ScorePanel scorePanel;
	private MainPong mainPong;
	
	/**
	 * Constructor that creates the controller and a game 
	 * view of the board, adds itself as an Observer to 
	 * the gameModel  and calls createGUI.
	 * @param numberOfPlayers 
	 * @param gameModel
	 */
	public GameView(int numberOfPlayers, GameModel gameModel, MainPong mainPong) 
	{
		this.gameModel = gameModel;
		this.numberOfPlayers = numberOfPlayers;
		this.controller = new Controller(this, gameModel);
		this.mainPong = mainPong;
		gameModel.addObserver(this);
		//gameChanger.addObserver(this);
		
	}
	/**
	 * 
	 * @return wallThickness
	 */
	public double getWallThickness() 
	{
		return (double) wallThickness;
	}
	
	/**
	 * Starts the game
	 */
	public void startGame()
	{
		controller.start(gamePlanSide);
	}
	
	/**
	 * Creates the specific components through use of method 
	 * calls such as createWalls()
	 * @param gamePlanSideOuter & gamePlanMargin
	 */
	public void createGUI(int gamePlanSideOuter, int gamePlanMargin)
	{		
		// Set some basic measurement vars and initiate arrays
		setMargins();
		this.gamePlanSide = gamePlanSideOuter - northMargin - southMargin;
		this.wallThickness = (int)(gamePlanSideOuter * WALL_PERCENTAGE);
		this.paddleThickness = wallThickness;
		this.paddleLength = (int)(gamePlanSide * 0.2);
		this.setBackground(Color.black);
		
		// Create a list that will hold all our graphical elements that need
		// to be drawn
		shapeArray = new ArrayList<Shape>(8);
		
		// Create the GUI.
		createLayout();
		createPaddles();
		createBall();
		
		// Add the key listener
		createListeners();
	}
	
	/**
	 * Helper function to createGUI() that sets the basic layout with a 
	 * floating panel on the left side of the main panel. The floating
	 * panel is the score panel.
	 */
	private void createLayout()
	{
		// Create a flow layout to be able to float a nested panel to the left
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		fl.setHgap(0);
		fl.setVgap(0);
		
		// Set the flow layout to this panel
		this.setLayout(fl);
		
		// Create a wrapping container for the left panel
		JPanel leftContainer = new JPanel();
		leftContainer.setLayout(new BorderLayout(0, 0));
		leftContainer.setBackground(Color.BLACK);
		leftContainer.setBorder(new EmptyBorder(0, wallThickness, 0, wallThickness));
		
		// Create the winner text field
		winnerTextLabel = new JLabel();
		winnerTextLabel.setText("");
		winnerTextLabel.setBackground(Color.BLACK);
		winnerTextLabel.setForeground(Color.WHITE);
		
		// Create the button panel that's at the bottom of the left container
		ButtonPanel buttonPanel = new ButtonPanel(this);
			
		// Create a new panel that we will use as our score panel, it will be the
		// panel floating to the left
		scorePanel = new ScorePanel(mainPong.GAMEPLAN_X_MARGIN, 
									wallThickness, 
									numberOfPlayers);
		
		// Loop through players and create the fields in the score panel
		for(int i = 0; i < numberOfPlayers; i++)
		{
			// Create the textfields at index i, with the playerName, 
			// playerScore and in color at index i
			scorePanel.createTextField(i, 
										gameModel.getPlayerName(i), 
										0, 
										COLOR_ARRAY[i]);
		}
		
		// Add the components to the left container
		leftContainer.add(scorePanel, BorderLayout.NORTH);		
		leftContainer.add(winnerTextLabel, BorderLayout.CENTER);
		leftContainer.add(buttonPanel, BorderLayout.SOUTH);
		
		// Set size of the left container and add it to the view
		leftContainer.setPreferredSize(new Dimension(mainPong.GAMEPLAN_X_MARGIN, 
													gamePlanSide)); 
		this.add(leftContainer);
		

		
	}
	/**
	 * sets the margins depending on the platform
	 */
	private void setMargins()
	{
		Insets i = mainPong.getInsets();
		//westMargin = i.left;
		//eastMargin = i.right;
		northMargin = i.top;
		southMargin = i.bottom;
		
	}
	/**
	 * Creates the ball
	 */
	private void createBall()
	{
		// Create a new ball in the center of the gamePlan and add it to the shape array
		double radius = wallThickness/2;
		
		ball = new Ball((gamePlanSide / 2) + mainPong.GAMEPLAN_X_MARGIN - radius, 
						(gamePlanSide) / 2 - radius, 
						wallThickness);
		
		shapeArray.add(ball);
	}
	
	/**
	 * Updates the score fields, is called from the update() method
	 * @param playerIndex, points
	 */

	private void updateScoreFields(int playerIndex, int points)
	{
		scorePanel.updateScoreFields(playerIndex, points);
	}

	/**
	 * Adds a key listener by registering a custom KeyDispatcher with the
	 * KeyboardFocusManager. The KeyDispatcher is an inner class to the GameView
	 */
	private void createListeners()
	{		
		// Get the systems keyboard manager
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		
		// Create a new key dispatcher that is defined as a custom inner class
		KeyDispatcher dispatcher = new KeyDispatcher(controller);
		
		// Add our custom key event dispatcher to the systems keyboard focus manager
		manager.addKeyEventDispatcher(dispatcher);		
	}

	/**
	 * Creates and positions the paddles, deactivates them to a wall if there is no player
	 */
	private void createPaddles()
	{
		for(int i = 0; i < 4; i++)
		{
			switch(i){
			case 0: 
				// Player one paddle (left)
				paddles[i] = new Paddle(mainPong.GAMEPLAN_X_MARGIN, 
										gamePlanSide / 2 - paddleLength/2, 
										paddleThickness, 
										paddleLength, 
										true, 
										COLOR_ARRAY[i]);
				break;
			case 1: 
				// Player 2 paddle (right)
				paddles[i] = new Paddle(mainPong.GAMEPLAN_X_MARGIN + gamePlanSide - paddleThickness,
										gamePlanSide / 2 - paddleLength/2,
										paddleThickness,
										paddleLength,
										true, 
										COLOR_ARRAY[i]);
				if(i >= numberOfPlayers)
				{
					deActivatePaddle(i);
				}
				break;
			case 2: 
				// Player 3 paddle (bottom)
				
				paddles[i] = new Paddle(mainPong.GAMEPLAN_X_MARGIN + (gamePlanSide / 2), 
											gamePlanSide - paddleThickness, 
											paddleLength, 
											paddleThickness, 
											true, 
											COLOR_ARRAY[i]);
				if(i >= numberOfPlayers)
				{
					deActivatePaddle(i);
				}				
				break;
			case 3: 
				//Player 4 paddle (top)
				paddles[i] = new Paddle(mainPong.GAMEPLAN_X_MARGIN + (gamePlanSide / 2), 
											0, 
											paddleLength, 
											paddleThickness, 
											true, 
											COLOR_ARRAY[i]);
				if(i >= numberOfPlayers)
				{
					deActivatePaddle(i);
				}
				break;
			}

			shapeArray.add(paddles[i]);
		}


	}
	
	/**
	 * Returns the ball
	 * @return the current Ball reference.
	 */
	public Ball getBall()
	{
		return ball;
	}
	
	/**
	 * Returns the paddles array
	 */
	public Paddle[] getPaddles()
	{
		return paddles;
	}
	
	/**
	 * Updates the ball position when the view is updated
	 * @param gameEventBall
	 */
	private void updateBallPosition(GameEventBall gameEventBall)
	{
		int dx = gameEventBall.getDx();
		int dy = gameEventBall.getDy();
		ball.move(dx, dy);
	}
	
	/**
	 * Updates the paddle position when the view is updated
	 * @param gameEventPaddle
	 */
	private void updatePaddlePosition(GameEventPaddle gameEventPaddle)
	{
		
		int dx = gameEventPaddle.getDx();
		int dy = gameEventPaddle.getDy();
		int playerIndex = gameEventPaddle.getPlayerIndex();
		paddles[playerIndex].move(dx, dy);
	}
	

	/**
	 * Paints the shapes in the shape array list
	 */
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		for (Shape s : shapeArray)
		{
			Colorable c = (Colorable) s;
			g2.setColor(c.getColor());
			g2.fill(s);
		}
	}

	
	/**
	 * Called by the system when the model notifies it's observers 
	 * sorts the different events.
	 * 
	 */
	@Override
	public void update(Observable observable, Object object) 
	{
		
		if(observable instanceof GameChangerHandler)
		{
			if(object instanceof GameChangerIcon)
			{
				gameChangerIcon = (GameChangerIcon)object;
				
				if(gameChangerIcon.getActive())
				{
					shapeArray.add(gameChangerIcon);
				}
				else if(gameChangerIcon.getActive()!=true)
				{
					shapeArray.remove(gameChangerIcon);
				}
			}
			if(object instanceof GameEventBall)
			{
				GameEventBall gameEventBall = (GameEventBall)object;
				
				if(ball.getHeight()<gamePlanSide/3)
				{
					ball.setSize(gameEventBall.getSize());
				}
					
			}
		}
		
		if(observable instanceof GameModel)
		{
			if(object instanceof GameEventBall)
			{
				GameEventBall gameEventBall = (GameEventBall) object;
				updateBallPosition(gameEventBall);
			}
			else if(object instanceof GameEventPaddle)
				{
					GameEventPaddle gameEventPaddle = (GameEventPaddle) object;
					updatePaddlePosition(gameEventPaddle);
				}

			else if(object instanceof GameEventPlayer)	
			{
				GameEventPlayer gameEventPlayer = (GameEventPlayer) object;

				if (gameEventPlayer.getScoreEvent()==true)
				{
					updateScoreFields(gameEventPlayer.getPlayerIndex(), gameEventPlayer.getPoints());
				}
				else if (gameEventPlayer.getPlayerDead()==true)
				{
					deActivatePaddle(gameEventPlayer.getPlayerIndex());
				}
			}
			
			else if(object instanceof GameEventGameover)
			{
				GameEventGameover gameEventGameover = (GameEventGameover) object;
				
				if(gameEventGameover.getGameOver() == true)	
				{
					gameOver(gameEventGameover);
				}

			}
		}
		validate();
		repaint();
	}
	/**
	 * Ends the game by stop the timer and calls mainPong for creating a new game
	 */
	public void endGameToMenu()
	{
		controller.stop();
		mainPong.makeNewGame();
	}
	/**
	 * Starts a rematch by removing the highscore and creates a new ball and paddles
	 */
	public void rematch()
	{
		gameModel.resetGame();
		if (highScorePanel != null)
		{
			this.remove(highScorePanel);
			highScorePanel = null;
			System.gc();
		}
		winnerTextLabel.setText("");
		shapeArray.clear();
		createBall();
		createPaddles();
		startGame();
	}
	/**
	 * 
	 * @return numberOfPlayers
	 */
	public int getNumberOfPlayers()
	{
		return numberOfPlayers;
	}
	/**
	 * 
	 * @return gamePlanSide
	 */
	public int getGamePlanSide()
	{
		return gamePlanSide;
	}
	/**
	 * Resets the balls size and coordinates.
	 */
	public void resetBall()
	{
		//int radius = (int)ball.width/2;
		int radius = (int)ball.getOriginalRadius();
		
		ball.resetBall((mainPong.GAMEPLAN_X_MARGIN+(gamePlanSide) / 2)- radius , 
				((gamePlanSide) / 2) - radius);
	}
	
	/**
	 * Deactivates a paddle into a wall
	 * @param index tells which paddle to deactivate
	 */
	private void deActivatePaddle(int index)
	{
		
			switch(index){
			case 0: 
				// Player one paddle (left)
				paddles[index].deactivate(mainPong.GAMEPLAN_X_MARGIN, 
										0, 
										wallThickness, 
										gamePlanSide);
				break;
			case 1: 
				// Player 2 paddle (right)
				paddles[index].deactivate(mainPong.GAMEPLAN_X_MARGIN + gamePlanSide - wallThickness, 
										0, 
										wallThickness, 
										gamePlanSide);
				break;
			case 2: 
				// Player 3 paddle (bottom)
				paddles[index].deactivate(mainPong.GAMEPLAN_X_MARGIN, 
										gamePlanSide - wallThickness, 
										gamePlanSide, 
										wallThickness);
				break;
			case 3: 
				//Player 4 paddle (top)
				paddles[index].deactivate(mainPong.GAMEPLAN_X_MARGIN, 
											0, 
											gamePlanSide, 
											wallThickness);
				break;
		}
	}
	
	
	/**
	 * Calculates the centerCoordinates of the window
	 * @return centerCoordinates in an array
	 */
	public int[] getCenter()
	{
		int[] centerCoordinates = new int[2];
		centerCoordinates[0] = mainPong.GAMEPLAN_X_MARGIN+(gamePlanSide) / 2;
		centerCoordinates[1] = (gamePlanSide) / 2;
		return centerCoordinates;
	}
	/**
	 * 
	 * @return gameChangerIcon
	 */
	public GameChangerIcon getGameChangerIcon() 
	{
		return gameChangerIcon;
	}
	/**
	 * When the game is over the timer stops and the highscore is shown
	 * @param gameEventGameover
	 */
	private void gameOver(GameEventGameover gameEventGameover)
	{
		//controller.stop();
		ball.setColor(Color.black);
		
		winnerTextLabel.setText("Winner is : " + gameEventGameover.getWinner() + "!");
		
		// Create highscore panel
		highScorePanel = new HighScorePanel(gameEventGameover.getPlayers());
		highScorePanel.setPreferredSize(new Dimension(gamePlanSide, gamePlanSide));
		highScorePanel.setBorder(new EmptyBorder(gamePlanSide / 4, 0, 0, 0));
		
		// Add the high score
		highScorePanel.setVisible(true);
		this.add(highScorePanel);
	}
	
}
