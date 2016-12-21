package tanks.visibles;

import java.util.Iterator;
import java.util.List;

import tanks.world.World;

public abstract class AbstractMover extends AbstractVisible implements MoveAble,
		Visible {

	private double angle;

	public AbstractMover(World world, double x, double y, double angle) {
		super(world, x, y, 0.0);
		this.angle = angle;
	}

	public double getAngle() {
		return angle;
	}

	public void turn(double angle) {
		this.angle += angle;
	}

	public void move(double speed) {
		setX(getX() + (Math.cos(angle) * speed));
		setY(getY() + (Math.sin(angle) * speed));
		Visible visible = hasCrashWith();
		if (visible != null)
			onCrashWith(visible);
		//System.out.println("move");
	}

	public abstract void onCrashWith(Visible visible);

	private Visible hasCrashWith() {
		List<Visible> visibleList = getWorld().getVisibleList();
		// has to be synchronized because of Iterator!
		synchronized (visibleList) {
			Iterator<Visible> visibleIterator = visibleList.iterator();
			Visible chrashObject = null;
			double minDistance = Integer.MAX_VALUE;
			while (visibleIterator.hasNext()) {
				Visible visible = visibleIterator.next();
				if (visible == this)
					continue;
				double distance = calcDistanceTo(visible.getX(), visible.getY());
				if (distance < visible.getCollisionRadius()
						+ this.getCollisionRadius()) {
					if (distance < minDistance) {
						minDistance = distance;
						chrashObject = visible;
					}
					// System.out.println("BUMMS: " + getClass() + " crashes
					// with "
					// + visible.getClass() + " in Distance "
					// +calcDistanceTo(visible.getX(),visible.getY()));
				}
			}
			return chrashObject;
		}
	}

	private double calcDistanceTo(double x, double y) {
		return Math.sqrt((this.getX() - x) * (this.getX() - x)
				+ (this.getY() - y) * (this.getY() - y));
	}

}
