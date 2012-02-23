package pong;
import javax.swing.*;

import pong.model.GameModel;
import pong.view.BackgroundMidiSound;
import pong.view.GameView;
import pong.view.menu.GameMenu;

import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * MainPong is a class that has a JFrame to keep all the
 * contents of the game within it. Primary task is to start
 * the game and initiate the necessary objects.
 * @author Grupp 4
 * @version 2.0
 * @since 2012-02-01
 *
 */
public class MainPong {
	private JFrame mainFrame;
	private GameMenu gameMenu;
	private GameView gameView;
	private GameModel gameModel;

	
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	
	
	public int GAMEPLAN_X_MARGIN = 200;
	
	
	/**
	 * Constructor for the class
	 */
	public MainPong() 
	{
		dim = new Dimension((2*(dim.height/3)) + GAMEPLAN_X_MARGIN,2*(dim.height/3));
		gameMenu = new GameMenu(this);
		mainFrame = new JFrame("Ballz/Cojones/Kulor");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		mainFrame.add(gameMenu);
		mainFrame.setPreferredSize(dim);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);	
		GAMEPLAN_X_MARGIN += mainFrame.getInsets().left * 6;
		
		// Start the music!
		BackgroundMidiSound b = new BackgroundMidiSound();
		b.start();
	}
	
	/**
	 * Main function to create the class
	 * @param args
	 */
	
	public static void main(String[] args)
	{

		new MainPong();
		
	}
	/**
	 * Initiates the game through the gameMenu and
	 * creates new instances of View and Model and adds
	 * the View to the content pane - also writes out
	 * "Fesk" to the console for.. Unknown purposes.
	 * @param playerNames
	 */
	public void initGame(String[] playerNames)
	{
		mainFrame.remove(gameMenu);
		gameModel = new GameModel(playerNames);
		gameView = new GameView(playerNames.length, gameModel, this);
		
		gameView.createGUI(mainFrame.getHeight(), GAMEPLAN_X_MARGIN);
		mainFrame.add(gameView);
		mainFrame.validate();
		gameView.startGame();
		System.out.println("Fesk");
		
		
	}
	
	/**
	 * Restart the game. Closes the pending game and opens the GameMenu
	 */
	public void makeNewGame()
	{
		mainFrame.remove(gameView);
		mainFrame.add(gameMenu);
		mainFrame.repaint();
				
	}
	
	/**
	 * Close down the program through gameMenu
	 */
	public void exitGame()
	{
        WindowEvent wev = new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	}
	/**
	 * Returns the Insets from the frame
	 * @return Returns the Insets from the frame
	 */
	public Insets getInsets()
	{
		return mainFrame.getInsets();
	}
}
