package tanks.world;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import tanks.CONSTANTS;

public class WorldKeyboardListener implements KeyEventDispatcher {
	
	private World world;

	public WorldKeyboardListener(World world) {
		//super();
		this.world = world;
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
	}

	//@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() != KeyEvent.KEY_PRESSED) return false;
		if (e.getKeyCode() == KeyEvent.VK_MINUS) {
			world.setSleepMillis(world.getSleepMillis() - CONSTANTS.DELTA_SLEEP_MILLISEK);
			return true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_PLUS) {
			world.setSleepMillis(world.getSleepMillis()	+ CONSTANTS.DELTA_SLEEP_MILLISEK);
			return true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_H) {
			world.setRunning(!world.isRunning());
			return true;
		}
		//System.out.println(e);
		return false;
	}

}
