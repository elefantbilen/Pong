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
public class PongServer extends Thread
{
	// Some property fields
	private static final int MESSAGE_READ_HIGHSCORE = 0;
	private static final int MESSAGE_WRITE_HIGHSCORE = 1;
	private static final int STATUS_ERROR = 0;
	private static final int STATUS_OK = 1;
	
	
	private int port = -1;
	private boolean isRunning = false;
	private ServerSocket server = null;
	private Socket client = null;
	private PrintWriter outStream = null;
	private BufferedReader inStream = null;

	// Constructors
	public PongServer()
	{
	}

	public PongServer(int port)
	{
		this.port = port;
	}


	/**
	 *  Run the thread and listens to the port. Reads or writes the highscore
	 */
	final public void run()
	{
		int action;
		
		isRunning = true;

		// Try to create a server socket on the port
		try
		{
			server = new ServerSocket(port);
		} 
		catch (IOException ex)
		{
			System.err.println(ex.getMessage());
			return;
		}

		while (isRunning)
		{
			// Wait for client to talk to us
			try
			{
				// Accept the connection
				client = server.accept();
				System.out.println("Accepted connection");
			} 
			catch (IOException ex)
			{
				System.err.println(ex.getMessage());
				return;
			}

			// Connection is now accepted, create the io streams and try to communicate
			try
			{				
				// Create input and output stream
				ObjectOutputStream objectOutStream = new ObjectOutputStream(
														client.getOutputStream());
				ObjectInputStream objectInStream = new ObjectInputStream(
													client.getInputStream());
				
				System.out.println("Created streams");
				System.out.println("There are " + objectInStream.available() + " bytes to read.");
				
				// Read the action message from the client
				action = objectInStream.readInt();
				System.out.println("Accepted action");
				
				if(action == MESSAGE_READ_HIGHSCORE)
				{
					// Read high score from file and return it to the client via the stream if successful
					LinkedList<String[]> list = readFile();
					if(null == list)
					{
						System.out.println("Return error");
						
						// Couldn't find a list to read from, send back error message
						objectOutStream.writeInt(STATUS_ERROR);
					}
					else
					{
						System.out.println("Return ok");
						
						// File was read ok, send back status and the list
						objectOutStream.writeInt(STATUS_OK);
						objectOutStream.writeObject(list);
					}
					//objectOutStream.flush();
				}
				else if(action == MESSAGE_WRITE_HIGHSCORE)
				{
					// Read next object and write it to the high score file
					LinkedList<String[]> list = (LinkedList<String[]>) objectInStream.readObject();
					
					// Return the status to the client by writing to 
					// the out stream, write high score returns the status
					objectOutStream.writeInt(writeToFile(list));
				}
				objectOutStream.flush();

			} 
			catch (IOException e)
			{
				System.err.println(e.getMessage());
				return;
			} 
			catch (ClassNotFoundException e)
			{
				System.err.println(e.getMessage());
			}
		}

	}
	/**
	 * Reads a highscore from a file
	 * @return Highscore 
	 */
	private LinkedList<String[]> readFile()
	{
		try 
		{
			ObjectInputStream reader = new ObjectInputStream(
											new FileInputStream("HighScore.Ballz"));
			LinkedList<String[]> list = (LinkedList<String[]>) reader.readObject();
			return list;
		}
		catch(FileNotFoundException e) 
		{
			System.out.println("" + e.getMessage());
			return null;
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("" + e.getMessage());
			return null;
		}
		catch(IOException e) 
		{
			System.out.println("" + e.getMessage());
			return null;
		}
	}
	/**
	 * Writes an object to a file
	 * @param list
	 * @return true/false 
	 */
	private int writeToFile(LinkedList<String[]> list)
	{
		try 
		{			
			ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("HighScore.Ballz")); 
			writer.writeObject(list);
			return STATUS_OK;
		}
		catch(IOException e)
		{
			System.out.println("Something went wrong : "+e.getMessage());
		}
		return STATUS_ERROR;
	}

}