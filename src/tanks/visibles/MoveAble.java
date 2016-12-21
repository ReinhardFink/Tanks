package tanks.visibles;

import tanks.world.World;

public interface MoveAble extends Visible {

	public World getWorld();

	public void turn(double angle);

	public void move(double speed);

}
