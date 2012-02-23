package pong.net;
import java.io.*;
import java.net.*;
import java.util.LinkedList;
/**
 * 
 * @author Group 4
 * @version 1.0
 * @since 2012-02-19
 */

public class PongClient
{
	private static final int MESSAGE_READ_HIGHSCORE = 0;
	private static final int MESSAGE_WRITE_HIGHSCORE = 1;
	private static final int STATUS_ERROR = 0;
	private static final int STATUS_OK = 1;
	
	// Some properties
	private String server;
	private int port = -1;
	private Socket client = null;
	private PrintWriter outStream = null;
	private BufferedReader inStream = null;
	
	/**
	 *  Constructor with port
	 * @param port
	 */
	public PongClient(int port)
	{
		server = "127.0.0.1";
		this.port = port;
	}
	
	/**
	 *  Constructor with server and port
	 * @param server
	 * @param port
	 */
	public PongClient(String server, int port)
	{
		this.server = server;
		this.port = port;
	}
	
	private void openConnection()
	{
		// Setup a client
		InetAddress address;
		try
		{
			address = InetAddress.getByName(server);
			client = new Socket(address, port);
		} 
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Reads high score from the server
	 * @return LinkedList<String[]>
	 */
	public LinkedList<String[]> readHighScore(int TIMEOUT)
	{
		int status;
		LinkedList<String[]> list = null;
		
		try
		{
			// Open the connection
			openConnection();
			client.setSoTimeout(TIMEOUT);
			// Create input and output stream
			ObjectOutputStream objectOutStream = new ObjectOutputStream(client.getOutputStream());
			ObjectInputStream objectInStream = new ObjectInputStream(client.getInputStream());
			
			// Write an action message to the server so it can know what's coming next
			objectOutStream.writeInt(MESSAGE_READ_HIGHSCORE);
			objectOutStream.flush();
			
			// Check the status of the read
			status = objectInStream.readInt();	
			
			// Check status
			if(status == STATUS_OK)
			{
				// Read the high score list from the stream
				list = (LinkedList<String[]>) objectInStream.readObject();
			}
			
			// Close the connection
			objectOutStream.close();
			objectInStream.close();
			client.close();
			
			return list;			
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Sends the list of players that might have a high score to the server
	 * @param list
	 * @return true/false 
	 */
	public boolean writeHighScore(LinkedList<String[]> list)
	{
		try
		{
			int status;
			
			// Open the connection
			openConnection();
			
			// Setup the output stream and write the list to it
			ObjectOutputStream objectOutStream = new ObjectOutputStream(
													client.getOutputStream());	
			
			// Setup a input stream so we can read confirmation
			ObjectInputStream objectInStream = new ObjectInputStream(
													client.getInputStream());
			
			// Write the action message to the server so it can know what's coming next
			objectOutStream.writeInt(MESSAGE_WRITE_HIGHSCORE);
			
			// Write to the output stream
			objectOutStream.writeObject(list);
			objectOutStream.flush();
			
			// Check status of the action
			status = objectInStream.readInt();

			// Close the connection
			objectOutStream.close();
			objectInStream.close();
			client.close();
			
			if(status == STATUS_OK)
			{
				return true;
			}
			else
			{
				return false;
			}
			
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return false;
	}
	
	
}