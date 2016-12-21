package tanks.world;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import tanks.CONSTANTS;
import tanks.players.PlayerManager;
import tanks.visibles.Visible;
import tanks.visibles.VisibleObjectFactory;

public class World {

	private List<Visible> visibleList;
	private WorldPanel worldPanel;
	private VisibleObjectFactory visibleObjectFactory;
	private PlayerManager playerManager;
	private Timer paintTimer;
	
	private int sleepMillis;
	private boolean isRunning;

	public World() {
		super();
		this.sleepMillis = CONSTANTS.SLEEP_MILLISEK;
		this.isRunning = true;
		// System.out.println("Hello World!");
		this.visibleList = Collections.synchronizedList(new LinkedList<Visible>());
		this.worldPanel = new WorldPanel(this);
		this.playerManager = new PlayerManager();
		this.visibleObjectFactory = new VisibleObjectFactory(this);
		new WorldKeyboardListener(this);
		// create visible Objects
		this.create4Walls();
		this.createCessField();
		//this.create8Queens();
		this.create8StateMachineQueens();
		this.create2Players();
		this.startPaintTimer();
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public int getSleepMillis() {
		return sleepMillis;
	}

	public void setSleepMillis(int sleepMillis) {
		this.sleepMillis = sleepMillis;
		if (this.sleepMillis < CONSTANTS.MIN_SLEEP_MILLISEK)		
			this.sleepMillis = CONSTANTS.MIN_SLEEP_MILLISEK;
		this.paintTimer.setDelay(this.sleepMillis);
		System.out.println(this.sleepMillis);
	}
	
	/*
	 * originally old version.
	 * moved into AbstractVisible.java
	 */
	public synchronized void waitForPainting() {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	public List<Visible> getVisibleList() {
		return visibleList;
	}

	public List<Visible> getDisplayList() {
		// Synchronisation nicht nötig, da visibleList synchronsiert ist.
		// Damit kann problemlos eine Copy erstellt werden, aus der dann die
		// neue ArrayList erstellt wird.
		return new ArrayList<Visible>(visibleList);
		//return this.visibleList;
	}

	public JPanel getWorldPanel() {
		return worldPanel;
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}

	public VisibleObjectFactory getvisibleObjectFactory() {
		return visibleObjectFactory;
	}

	private void startPaintTimer() {
		this.paintTimer = new Timer(sleepMillis, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
		    	  worldPanel.repaint();
		    	  if (World.this.isRunning())
		    		  synchronized(World.this) {
		    			  // wake up all waiting Mover - Threads
						World.this.notifyAll();
		    		  }
		    	  else {
		    		  try {
						Thread.sleep(30*World.this.getSleepMillis());
						System.out.println("World stopped");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    	  }
		      }});
		this.paintTimer.start();
	}

	private void create4Walls() {
		for (int i = 0; i < 4; i++)
			this.visibleList.add(this.visibleObjectFactory.createWall(i));
	}

	private void createCessField() {
		int colorIndex = 0;
		Color[] color = { Color.WHITE, Color.GRAY };
		for (int line = 0; line < CONSTANTS.MAX_QUEENS; line++)
			for (int row = 0; row < CONSTANTS.MAX_QUEENS; row++) {
				colorIndex = (line + row) % 2;
				double x = CONSTANTS.CHESS_FIELD_LENGTH + row
						* CONSTANTS.CHESS_FIELD_LENGTH;
				double y = 1.5 * CONSTANTS.CHESS_FIELD_LENGTH + line
						* CONSTANTS.CHESS_FIELD_LENGTH;
				this.visibleList.add(this.visibleObjectFactory
						.createChessField(x, y, color[colorIndex]));
			}
	}

	@SuppressWarnings("unused")
	private void create8Queens() {
		for (int queen = 0; queen < CONSTANTS.MAX_QUEENS; queen++) {
			double x = CONSTANTS.CHESS_FIELD_LENGTH + queen
					* CONSTANTS.CHESS_FIELD_LENGTH;
			double y = CONSTANTS.QUEEN_START_POSITION;
			this.visibleList.add(visibleObjectFactory.createQueenTank(x, y,
					Math.PI / 2, queen));
		}
	}

	private void create8StateMachineQueens() {
		for (int queen = 0; queen < CONSTANTS.MAX_QUEENS; queen++) {
			double x = CONSTANTS.CHESS_FIELD_LENGTH + queen
					* CONSTANTS.CHESS_FIELD_LENGTH;
			double y = CONSTANTS.QUEEN_START_POSITION;
			this.visibleList.add(visibleObjectFactory
					.createStateMachineQueenTank(x, y, Math.PI / 2, queen));
		}
	}

	private void create2Players() {
		this.visibleList.add(visibleObjectFactory
				.createTankWithInteractivPlayer(400, 400, 0));
		this.visibleList.add(visibleObjectFactory
				.createTankWithInteractivPlayer(500, 500, 0));
	}

	/* (non-Javadoc)
	 * 
	 * erste Lösung
	 * Dooooooo
	 * ooooDooo
	 * oooooooD
	 * oooooDoo
	 * ooDooooo
	 * ooooooDo
	 * oDoooooo
	 * 
	 */
}
