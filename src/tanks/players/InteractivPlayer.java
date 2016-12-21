package tanks.players;

import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import tanks.CONSTANTS;
import tanks.visibles.Tank;

public class InteractivPlayer implements PlayerInterface, Observer {

	private List<KeyEvent> keyEventList;
	private Tank tank;
	private int[] keys;

	public InteractivPlayer(int[] keys) {
		super();
		keyEventList = Collections.synchronizedList(new LinkedList<KeyEvent>());
		this.keys = keys;
	}

	public void setTank(Tank tank) {
		this.tank = tank;
	}

	public Tank getTank() {
		return this.tank;
	}
	/*
	 * noch schlecht syncronisiert
	 * 
	 */
	/*
	//@Override
	public void play() {
		synchronized (keyEventList) {
			Iterator<KeyEvent> eventIterator = keyEventList.iterator();
			if (eventIterator.hasNext()) {
				KeyEvent e = eventIterator.next();
				// System.out.println("3: " + e);
				if (e.getKeyCode() == keys[CONSTANTS.INDEX_UP])
					tank.move(CONSTANTS.TANK_SPEED);
				else if (e.getKeyCode() == keys[CONSTANTS.INDEX_DOWN])
					tank.move(-CONSTANTS.TANK_SPEED);
				else if (e.getKeyCode() == keys[CONSTANTS.INDEX_TANK_LEFT])
					tank.turn(-CONSTANTS.TANK_DELTA_ANGLE);
				else if (e.getKeyCode() == keys[CONSTANTS.INDEX_TANK_RIGHT])
					tank.turn(CONSTANTS.TANK_DELTA_ANGLE);
				else if (e.getKeyCode() == keys[CONSTANTS.INDEX_TURRET_LEFT])
					tank.turnTurret(-CONSTANTS.TANK_DELTA_ANGLE);
				else if (e.getKeyCode() == keys[CONSTANTS.INDEX_TURRET_RIGHT])
					tank.turnTurret(CONSTANTS.TANK_DELTA_ANGLE);
				else if (e.getKeyCode() == keys[CONSTANTS.INDEX_TURRET_FIRE])
					tank.fire(2);
				else if (e.getKeyCode() == keys[CONSTANTS.INDEX_RADAR_LEFT])
					tank.turnRadar(-CONSTANTS.TANK_DELTA_ANGLE);
				else if (e.getKeyCode() == keys[CONSTANTS.INDEX_RADAR_RIGHT])
					tank.turnRadar(CONSTANTS.TANK_DELTA_ANGLE);
				else {
				} // Event not for me!!
				eventIterator.remove();
			}
		}
	}
	*/
	
	//@Override
	public void update(Observable o, Object e) {
		if (keyEventList.size() < 5)
			keyEventList.add((KeyEvent) e);
	}

	//@Override
	public void play() {
		KeyEvent e = null;
		synchronized (keyEventList) {
			Iterator<KeyEvent> eventIterator = keyEventList.iterator();
			if (eventIterator.hasNext()) {
				e = eventIterator.next();
				eventIterator.remove();
			}	
		}
		if (e == null) return;
		//System.out.println("3: " + e);
		if (e.getKeyCode() == keys[CONSTANTS.INDEX_UP])
			tank.move(CONSTANTS.TANK_SPEED);
		else if (e.getKeyCode() == keys[CONSTANTS.INDEX_DOWN])
			tank.move(-CONSTANTS.TANK_SPEED);
		else if (e.getKeyCode() == keys[CONSTANTS.INDEX_TANK_LEFT])
			tank.turn(-CONSTANTS.TANK_DELTA_ANGLE);
		else if (e.getKeyCode() == keys[CONSTANTS.INDEX_TANK_RIGHT])
			tank.turn(CONSTANTS.TANK_DELTA_ANGLE);
		else if (e.getKeyCode() == keys[CONSTANTS.INDEX_TURRET_LEFT])
			tank.turnTurret(-CONSTANTS.TANK_DELTA_ANGLE);
		else if (e.getKeyCode() == keys[CONSTANTS.INDEX_TURRET_RIGHT])
			tank.turnTurret(CONSTANTS.TANK_DELTA_ANGLE);
		else if (e.getKeyCode() == keys[CONSTANTS.INDEX_TURRET_FIRE])
			tank.fire(2);
		else if (e.getKeyCode() == keys[CONSTANTS.INDEX_RADAR_LEFT])
			tank.turnRadar(-CONSTANTS.TANK_DELTA_ANGLE);
		else if (e.getKeyCode() == keys[CONSTANTS.INDEX_RADAR_RIGHT])
			tank.turnRadar(CONSTANTS.TANK_DELTA_ANGLE);
		else {
		} // Event not for me!!
	}
}
