package pong.controller;
import java.util.LinkedList;

import pong.model.Player;
import pong.net.PongClient;


/**
 * @author Grupp 4
 * @version 1.0
 * @since 2012-01-27
 */
public class HighScoreHandler 
{

	private LinkedList<String[]> highScoreList;
	private final int HIGHSCORELENGTH = 10;
	private final int PORT = 1234;
	private final String IP = "danielkvist.dnsd.me"; // "80.216.115.80";
	private final int TIMEOUT = 5000;
	PongClient client;
	
	/**
	 * The constructor for the class.
	 */
	public HighScoreHandler() 
	{
		client = new PongClient(IP, PORT);
		highScoreList = new LinkedList<String[]>();
		readHighScore();
	}
	
	/**
	 * Get the highScoreList.
	 * 
	 * Returns the highScoreList, a linkedlist with players and their corresponding highscores.
	 * 
	 * @return highScoreList 
	 */
	public LinkedList<String[]> getHighScoreList()
	{
		return highScoreList;
	}
	
	/**
	 * Update the highscore after a round.
	 * 
	 * Compares the individual players' scores and updates the highscore if the score is higher
	 * or equal to one already in the list.
	 * 
	 * @param players An array of the players in the present game.
	 */
	public void updateScore(Player[] players)
	{
		for (int i = 0; i < players.length; i++)
		{
			for (int j = 0; j < highScoreList.size(); j++)
			{
				if (players[i].getScore() >= new Integer(highScoreList.get(j)[1]))
				{
					String[] tempPlayer = new String[2];
					tempPlayer[0] = players[i].getName();
					
					Integer tempScore = new Integer(players[i].getScore());
					tempPlayer[1] = tempScore.toString(); 
					
					highScoreList.add(j, tempPlayer);
					highScoreList.removeLast();
					j = highScoreList.size();
				}
			}
		}
	 
		writeHighScore();
	}
	
	
	public void resetHighScore()
	{
		highScoreList = new LinkedList<String[]>();
		String[] temp = {"Fesk", "0"};
		
		for (int i = 0; i < HIGHSCORELENGTH; i++)
		{
			highScoreList.addFirst(temp);
		}
		
		writeHighScore();

	}

	
	private void writeHighScore()
	{
		
		client.writeHighScore(highScoreList);

	}

	private void readHighScore()
	{
		
		// Ask the client to get the list from the server

		highScoreList = client.readHighScore(TIMEOUT);
		
		// If the list doesn't exist, reset it and create it
		if(null == highScoreList)
		{
			resetHighScore();
		}			
	}
}
