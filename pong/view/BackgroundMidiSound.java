package pong.view;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

/**
 * Plays a default background sound from a URL if not given a custom a url from
 * the user.
 */
public class BackgroundMidiSound extends Thread
{
	private String url;

	/**
	 * Default constructor that sets a default sound
	 */
	public BackgroundMidiSound() 
	{
		this.url = "http://danielkvist.net/music.mid";
	}
	
	/**
	 * Constructor that takes an url to a .mid file as an argument
	 * @param url
	 */
	public BackgroundMidiSound(String url)
	{
		this.url = url;
	}
	
	public void run()
	{
		try {
	        // From file
	        //Sequence sequence = MidiSystem.getSequence(new File(System.getProperty("user.dir") + "/music.mid"));
	    
	        // From URL
			Sequence sequence = MidiSystem.getSequence(new URL(url));
	    
	        // Create a sequencer for the sequence
	        Sequencer sequencer = MidiSystem.getSequencer();
	        sequencer.open();
	        sequencer.setSequence(sequence);
	    
	        // Start playing
	        sequencer.start();
	        
	        // Loop a thousand times!
	        sequencer.setLoopCount(1000);
	    } 
		catch (MalformedURLException e) { e.printStackTrace();} 
		catch (IOException e) { e.printStackTrace();} 
	    catch (MidiUnavailableException e) { e.printStackTrace();} 
	    catch (InvalidMidiDataException e) { e.printStackTrace();}
	}
}
