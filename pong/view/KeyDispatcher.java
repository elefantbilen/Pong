package pong.view;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

import pong.controller.Controller;

	/**
	 * Class that hijacks key events anywhere in the component hierarchy
	 * and calls on controller to calculate the next move.
	 *
	 */
	public class KeyDispatcher implements KeyEventDispatcher
	{
		private Controller controller;
		public KeyDispatcher(Controller controller)
		{
			this.controller = controller;
		}
		
		@Override
		public boolean dispatchKeyEvent(KeyEvent e)
		{
			if (e.getID() == KeyEvent.KEY_PRESSED) 
			{
				switch(e.getKeyCode())
				{
					case KeyEvent.VK_Q:
						controller.calculatePaddleAction(controller.PLAYER_ONE_PADDLE_ACTION_UP);
						break;
					case KeyEvent.VK_A:
						controller.calculatePaddleAction(controller.PLAYER_ONE_PADDLE_ACTION_DOWN);
						break;
					case KeyEvent.VK_UP:
						controller.calculatePaddleAction(controller.PLAYER_TWO_PADDLE_ACTION_UP);
						break;
					case KeyEvent.VK_DOWN:
						controller.calculatePaddleAction(controller.PLAYER_TWO_PADDLE_ACTION_DOWN);
						break;
					case KeyEvent.VK_K:
						controller.calculatePaddleAction(controller.PLAYER_THREE_PADDLE_ACTION_UP);
						break;
					case KeyEvent.VK_L:
						controller.calculatePaddleAction(controller.PLAYER_THREE_PADDLE_ACTION_DOWN);
						break;
					case KeyEvent.VK_F:
						controller.calculatePaddleAction(controller.PLAYER_FOUR_PADDLE_ACTION_UP);
						break;
					case KeyEvent.VK_G:
						controller.calculatePaddleAction(controller.PLAYER_FOUR_PADDLE_ACTION_DOWN);
						break;
				}
				
	        } 
	        return false;
		}

	}