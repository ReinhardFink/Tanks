package tanks.visibles;

import java.awt.Graphics2D;

import tanks.world.World;

/**
 * @author fink
 *
 */
public abstract class AbstractVisible implements Runnable, Visible {

	private World world;
	private double x;
	private double y;
	private double collisionRadius;
	private boolean isAlive;

	public AbstractVisible(World world, double x, double y, double collisionRadius) {
		super();
		this.world = world;
		this.x = x;
		this.y = y;
		this.collisionRadius = collisionRadius;
		this.isAlive = true;
	}

	public void setCollisionRadius(double collisionRadius) {
		this.collisionRadius = collisionRadius;
	}

	public double getCollisionRadius() {
		return collisionRadius;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getX() {
		return x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getY() {
		return y;
	}

	public World getWorld() {
		return world;
	}
 
	public final void run() {
		while (isAlive) {
			work();
			this.waitForPainting();
			//world.waitForPainting();
		}
	}

	private void waitForPainting() {
		synchronized(world) { // get Lock on Object world!
			try {
				world.wait(); // free Lock on Object world and wait for notifyAll()
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void remove() {
		world.getVisibleList().remove(this);
		this.isAlive = false;
	}

	public void work() {
	};

	public abstract void paint(Graphics2D g);

}
