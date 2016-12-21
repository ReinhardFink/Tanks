package tanks.players;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.Observable;

import tanks.CONSTANTS;
import tanks.players.InteractivPlayer;

public class PlayerManager extends Observable implements KeyEventDispatcher {
	
	private int interactivPlayerCount = 0;

	private static final int[] keys1 = { KeyEvent.VK_UP, KeyEvent.VK_DOWN,
			KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_O, KeyEvent.VK_P,
			KeyEvent.VK_SPACE, KeyEvent.VK_K, KeyEvent.VK_L };

	private static final int[] keys2 = { KeyEvent.VK_S, KeyEvent.VK_X,
			KeyEvent.VK_Y, KeyEvent.VK_C, KeyEvent.VK_A, KeyEvent.VK_D,
			KeyEvent.VK_F, KeyEvent.VK_Q, KeyEvent.VK_W };

	private int[][] keyArray;

	public PlayerManager() {
		super();
		this.interactivPlayerCount = 0;
		this.keyArray = new int[CONSTANTS.MAX_INTERACTIV_PLAYERS][];
		this.keyArray[0] = keys1;
		this.keyArray[1] = keys2;
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
		// this.playerList = new LinkedList<InteractivPlayer>();
	}
	
	public PlayerInterface getInteractivPlayer() {
		if (interactivPlayerCount < CONSTANTS.MAX_INTERACTIV_PLAYERS) {
			InteractivPlayer ip = new InteractivPlayer(keyArray[interactivPlayerCount]);
			interactivPlayerCount++;
			this.addObserver(ip);
			return ip;
		}
		else return null;
	}

	//@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() != KeyEvent.KEY_PRESSED) return false;
		this.setChanged();
		this.notifyObservers(e);
		return false;
	}

}
