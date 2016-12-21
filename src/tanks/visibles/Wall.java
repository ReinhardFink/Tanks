package tanks.visibles;

import java.awt.Graphics2D;

import tanks.world.World;

public class Wall extends AbstractVisible implements Visible {

	public Wall(World world, double x, double y, double collisionRadius) {
		super(world, x, y, collisionRadius);
	}

	@Override
	public void paint(Graphics2D g) {
		g.drawOval((int) getX() - (int) getCollisionRadius(), (int) getY()
				- (int) getCollisionRadius(), (int) getCollisionRadius() * 2,
				(int) getCollisionRadius() * 2);
	}

}
