package pong.view.menu;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import pong.view.GameView;

/** 
* @author Group 4
* @version 4.0
* @since 2012-02-22
*/

public class ButtonPanel extends JPanel {

	private JButton gameMenuButton;
	private JButton rematchButton;
	private JButton exitButton;
	private GameView gameView;
	
	/**
	 * The constructor of the class
	 * @param gameView
	 */
	public ButtonPanel(GameView gameView)
	{
		this.gameView = gameView;
		setLayout(new GridLayout(3,1));
		makeButtons();
	}
	
	/**
	 * Makes buttons and add them to itself
	 */
	private void makeButtons()
	{
		gameMenuButton = new JButton("Menu");
		gameMenuButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gameView.endGameToMenu();
			}
		});
			
		rematchButton = new JButton("Rematch");
		rematchButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gameView.rematch();
			}
		});
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		
		add(gameMenuButton);
		add(rematchButton);
		add(exitButton);
		
	}
	

	
}
