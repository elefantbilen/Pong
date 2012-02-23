package pong.net;
/**
 * 
 * @author Group 4
 * @version 1.0
 * @since 2012-02-20
 *
 */
public class DServerDriver
{
	/**
	 * A main function for the server
	 * @param args
	 */
	public static void main(String[] args)
	{
		PongServer serverSocket = new PongServer(1234);
		System.out.println("Server is running");
		serverSocket.start();
	}
}