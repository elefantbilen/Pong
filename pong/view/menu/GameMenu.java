package pong.view.menu;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;

import pong.MainPong;

/**
 * Frame with starting menu for Pong.
 * @author Group 4
 * @version 3.7
 * @since 2012-02-20
 */
public class GameMenu extends JPanel
{
	//Reference to mainPong.
	private MainPong mainPong;
	//For storing the buttons of GameMenu.
	private HashMap<String, JButton> buttonSet;
	//For storing the names of the players/name of the player.	
	private String[] playerNames;
	
	
	/**
	 * Constructor creates buttons and designs GameMenu.
	 * @param mainPong Reference to mainPong.
	 */
	public GameMenu(MainPong mainPong)
	{
		this.mainPong = mainPong;		
		createButtons();
		designGameMenu();		
	}	
		
	/**
	 * Design GameMenu and add buttons.
	 */
	private void designGameMenu()
	{									
		//Start menu
		
		//Create the center field to put in middle of GameMenu.
		JPanel centerField = new JPanel();
		centerField.setLayout(new GridLayout(5, 1, 3, 3));
		centerField.add(buttonSet.get("onePlayerButton"));
		centerField.add(buttonSet.get("twoPlayerButton"));
		centerField.add(buttonSet.get("threePlayerButton"));
		centerField.add(buttonSet.get("fourPlayerButton"));
		centerField.add(buttonSet.get("exitButton"));
		
		//Create Border layout in GameMenu... 
		this.setLayout(new BorderLayout());
		//...add centerField in CENTER area...
		this.add(centerField, BorderLayout.CENTER);
		//...add Welcome message in NORHT area.
		JTextArea welcomeMessage = new JTextArea("\n\tWelcome to Ballz!" +
					"\n\tBienvenido a Cojones!\n\tVälkommen till Kulor!\n");
		welcomeMessage.setEditable(false);
		this.add(welcomeMessage, BorderLayout.NORTH);
		//.. and fix the design.
		this.add(Box.createVerticalStrut(80), BorderLayout.SOUTH);
		this.add(Box.createHorizontalStrut(80),BorderLayout.EAST);
		this.add(Box.createHorizontalStrut(80), BorderLayout.WEST);
		//Set preferred size for the menu		
		this.setPreferredSize(new Dimension(300, 300));
		this.setBackground(Color.white);
		
	}
	
	/**
	 * Create and add buttons in HashMap buttonSet.
	 * Adds actionListeners to buttons.
	 * Buttons: One Player, Two Players, Three Players, Four Players, OK, Exit.
	 */
	private void createButtons()
	{
		//Create a HashMap where buttons are stored.
		buttonSet = new HashMap<String, JButton>();
		
		//Create button and save it in a HashMap. Then add actionListener to button.
		buttonSet.put("onePlayerButton", new JButton("One Player"));
		buttonSet.get("onePlayerButton").addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						clickAPlayerButton(1);
					}
				});
		
		//Create button and save it in a HashMap. Then add actionListener to button.
		buttonSet.put("twoPlayerButton", new JButton("Two Players"));
		buttonSet.get("twoPlayerButton").addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						clickAPlayerButton(2);
					}
				});
		
		//Create button and save it in a HashMap. Then add actionListener to button.
		buttonSet.put("threePlayerButton", new JButton("Three Players"));
		buttonSet.get("threePlayerButton").addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						clickAPlayerButton(3);
					}
				});
		
		//Create button and save it in a HashMap. Then add actionListener to button.
		buttonSet.put("fourPlayerButton", new JButton("Four Players"));
		buttonSet.get("fourPlayerButton").addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						clickAPlayerButton(4);
					}
				});
				
		//Create button and save it in a HashMap. Then add actionListener to button.
		buttonSet.put("exitButton", new JButton("Exit"));
		buttonSet.get("exitButton").addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						clickExitButton();
					}
				});
	}
			
	/**
	 * Action event.
	 * Creates an array of Strings of the right size for playerNames.
	 * Asks for name of player/players.
	 * Then implements initGame method.
	 * @param amount Amount of players
	 */
	private void clickAPlayerButton(int amount)
	{
		boolean startGame = false;
		//Creates an array of the right size to store player names in.
		if(amount == 1)
		{
			playerNames = new String[2];
		}
		else
		{
			playerNames = new String[amount];
		}
		
		//Collect name/names
		for(int i = 0; i < amount; i++)
			{
				String name;
				
				if (amount == 1)
				{
					playerNames[1] = "ARTIFICIAL UNO";					
					name = JOptionPane.showInputDialog("Please enter your name.");
				}
				else
				{
					name = JOptionPane.showInputDialog("Please enter name of player " + (i+1) + ".");
				}
				
				//If cancel button is pressed then start over with collecting names.
				if(name == null)
					break;
				
				//If no name of player is entered than create a name.
				if(name.equals(""))
				{
					name = "Player " + (i+1);
				}
				
				//If players name is too long ask for a shorter name.
				while(name.length() > 15)
				{
						name = JOptionPane.showInputDialog("Too long name," +
								"Please enter your name, must be 10 characters or shorter");			
				}
				
				playerNames[i] = name;
			}
		

		if(playerNames[playerNames.length -1] != null && playerNames[0] != null)
			startGame = true;
			
		if(startGame)
			initGame();
	}
	
	/**
	 * Action event.
	 * Will make mainPong end the game.
	 */	
	private void clickExitButton()
	{
		mainPong.exitGame();
	}
	
	/**
	 * Will makes mainPong start a game and sends along the list of players.
	 */
	private void initGame()
	{
		mainPong.initGame(playerNames);
	}	
}
