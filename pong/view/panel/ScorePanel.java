package pong.view.panel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** 
* @author Group 4
* @version 4.0
* @since 2012-02-22
*/
public class ScorePanel extends JPanel
{
	private int gameplanMargin;
	private int wallThickness;
	private JTextField[] nameTextFields;
	private JTextField[] scoreTextFields;
/**
 * Constructor of the class
 * @param gamePlanMargin
 * @param wallThickness
 * @param numberOfPlayers
 */
	public ScorePanel(int gamePlanMargin, int wallThickness, int numberOfPlayers) 
	{
		this.gameplanMargin = gamePlanMargin;
		this.wallThickness = wallThickness;
		this.nameTextFields = new JTextField[numberOfPlayers];
		this.scoreTextFields = new JTextField[numberOfPlayers];
		initPanel();
	}
	/**
	 * Initiates the panel
	 */
	private void initPanel()
	{
		// Set a grid layout with infinite rows and 2 cols, also remove the padding (gaps)
		GridLayout gl = new GridLayout(0, 2);
		gl.setHgap(0);
		gl.setVgap(0);
		
		// Set the gridlayout to our floating panel and set it's size and color
		this.setLayout(gl);
		this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(gameplanMargin - (2 * wallThickness), gameplanMargin / 2)); // TODO Check height
		
		
		// Create some headers for the score panel		
		JTextField header1 = new JTextField();
		header1.setText("PONG");
		header1.setEditable(false);
		header1.setBackground(Color.BLACK);
		header1.setForeground(Color.WHITE);
		header1.setBorder(BorderFactory.createEmptyBorder());
		
		JTextField header2 = new JTextField();
		header2.setText("SCORE");
		header2.setEditable(false);
		header2.setBackground(Color.BLACK);
		header2.setForeground(Color.WHITE);
		header2.setBorder(BorderFactory.createEmptyBorder());
		
		// Set the headers to a bigger and bolder font
		Font f = new Font(getName(), Font.BOLD, 24);
		header1.setFont(f);
		header2.setFont(f);
		
		// Add the headers to the panel
		this.add(header1);
		this.add(header2);
	}
	/**
	 * Creates the textfields with name, score, index and color
	 * @param fieldIndex
	 * @param playerName
	 * @param playerScore
	 * @param color
	 */
	public void createTextField(int fieldIndex, String playerName, int playerScore, Color color)
	{	
		// Create name field
		nameTextFields[fieldIndex] = new JTextField();
		nameTextFields[fieldIndex].setEditable(false);
		nameTextFields[fieldIndex].setText(playerName);
		nameTextFields[fieldIndex].setBackground(Color.BLACK);
		nameTextFields[fieldIndex].setForeground(color);
		nameTextFields[fieldIndex].setBorder(BorderFactory.createEmptyBorder());
		
		// Create the score field
		scoreTextFields[fieldIndex] = new JTextField();
		scoreTextFields[fieldIndex].setEditable(false);
		scoreTextFields[fieldIndex].setText("" + playerScore);
		scoreTextFields[fieldIndex].setBackground(Color.BLACK);
		scoreTextFields[fieldIndex].setForeground(color);
		scoreTextFields[fieldIndex].setBorder(BorderFactory.createEmptyBorder());	
		
		// Add the fields to the score panel
		this.add(nameTextFields[fieldIndex]);
		this.add(scoreTextFields[fieldIndex]);
	}
	
	/**
	 * Updates the score fields, is called from the update() method
	 * @param fieldIndex
	 * @param playerScore
	 */
	public void updateScoreFields(int fieldIndex, int playerScore)
	{
		// Set player score into text field
		scoreTextFields[fieldIndex].setText("" + playerScore);
	}
	
}
