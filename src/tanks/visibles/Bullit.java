package tanks.visibles;

import java.awt.Color;
import java.awt.Graphics2D;

import tanks.CONSTANTS;
import tanks.world.World;

public class Bullit extends AbstractMover {

	private Tank shooter;

	public Bullit(World world, double x, double y, double angle, Tank shooter) {
		super(world, x, y, angle);
		this.shooter = shooter;
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillOval((int) getX() - CONSTANTS.BULLIT_RADIUS / 2, (int) getY()
				- CONSTANTS.BULLIT_RADIUS / 2, CONSTANTS.BULLIT_RADIUS,
				CONSTANTS.BULLIT_RADIUS);
	}

	@Override
	public void work() {
		move(CONSTANTS.BULLIT_SPEED);
	}

	@Override
	public double getCollisionRadius() {
		return CONSTANTS.BULLIT_RADIUS;
	}

	@Override
	public void onCrashWith(Visible visible) {
		if (visible.getClass() == OneRecktangle.class)
			return;
		shooter.myBullitHits(visible);
		if (visible.getClass() == Tank.class)
			((Tank) visible).reduceLivePoints();
		this.explode();
	}

	private void explode() {
		getWorld().getVisibleList().add(
				getWorld().getvisibleObjectFactory().createExplosion(getX(),
						getY(), 0));
		remove();
	}
}
