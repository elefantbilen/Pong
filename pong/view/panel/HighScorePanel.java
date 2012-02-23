package pong.view.panel;
import java.util.LinkedList;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import pong.controller.HighScoreHandler;
import pong.model.Player;


/**
 * @author Grupp 4
 * @version 1.0
 * @since 2012-01-27
 */
public class HighScorePanel extends JPanel {
	
	private LinkedList<String[]> highScoreList = new LinkedList<String[]>();
	private JTable highScoreTable;
	public Player players;
	private HighScoreHandler high;
	

	/**
	 * The constructor of the HighScorePanel class
	 * 
	 * Sends the players of this round and their respective scores to the highScoreHandler
	 * and fetches the result and saves it in a LinkedList called highScoreList.
	 * 
	 * @param playerArray an array of the players in this round.
	 */
	public HighScorePanel(Player[] playerArray)
	{
		high = new HighScoreHandler();
		high.updateScore(playerArray);
		highScoreList = high.getHighScoreList();
		
		this.setLayout(new BorderLayout());
		this.setBackground(Color.BLACK);
		makeTable();
	}
	
	/**
	 * Makes a table with the the current highscores.
	 * 
	 * Fetches the highscore and places it in a table after game over.
	 */
	private void makeTable()
	{
		// Initiate arrays and table header
		String[][] row = new String[10][3];
		String[] col = {"Placering", "Spelare", "Poäng"};
		
		// Create the arrays that will feed the table
		for(int i = 0; i < 10; i++)
		{ 
			Integer tempFlytt = i+1;
			row[i][0] = tempFlytt.toString();
			row[i][1] = highScoreList.get(i)[0];
			row[i][2] = highScoreList.get(i)[1];			
		}		
		
		// Create and style the table
		highScoreTable = new JTable(row,col);
		highScoreTable.setBackground(Color.BLACK);
		highScoreTable.setForeground(Color.WHITE);
		highScoreTable.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		highScoreTable.setRowHeight(30);
		highScoreTable.setEnabled(false);
		
		
		// Get the header and style it
		JTableHeader header = highScoreTable.getTableHeader();	
		header.setEnabled(false);
		header.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		header.setBackground(new Color(0).DARK_GRAY);
		header.setForeground(new Color(0).WHITE);
		header.setPreferredSize(new Dimension(this.WIDTH, 40));
		
		// Add the header and the table
		this.add(header, BorderLayout.NORTH);
		this.add(highScoreTable, BorderLayout.CENTER);
	
	
	}
	
	
}
