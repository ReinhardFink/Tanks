package tanks.visibles;

import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tanks.CONSTANTS;
import tanks.players.Queen;
import tanks.players.StateMachineQueen;
import tanks.world.World;

public class VisibleObjectFactory {

	private World world;
	private ExecutorService threadPoolExecutor;

	public VisibleObjectFactory(World world) {
		super();
		this.world = world;
		threadPoolExecutor = Executors.newCachedThreadPool();
	}

	public Wall createWall(int position) {
		Wall visible = null;
		double radius = CONSTANTS.SIDE * CONSTANTS.SIDE / (8 * CONSTANTS.MPX)
				+ CONSTANTS.MPX / 2;
		double distance = radius - CONSTANTS.MPX;
		switch (position) {
		case 0:
			visible = new Wall(world, CONSTANTS.SIDE / 2, -distance, radius);
			break;
		case 1:
			visible = new Wall(world, CONSTANTS.SIDE / 2, -18 + CONSTANTS.SIDE
					+ distance, radius);
			break;
		case 2:
			visible = new Wall(world, -distance, CONSTANTS.SIDE / 2, radius);
			break;
		case 3:
			visible = new Wall(world, CONSTANTS.SIDE + distance, CONSTANTS.SIDE / 2,
					radius);
			break;
		}
		return visible;
	}

	public OneRecktangle createChessField(double x, double y, Color color) {
		return new OneRecktangle(world, x, y, color);
	}

	public Tank createTankWithInteractivPlayer(double x, double y, double angle) {
		Tank t = new Tank(world, x, y, angle, world.getPlayerManager().getInteractivPlayer());
		threadPoolExecutor.execute(t);
		return t;
	}

	public Bullit createBullit(double x, double y, double angle, Tank shooter) {
		Bullit b = new Bullit(world, x, y, angle, shooter);
		threadPoolExecutor.execute(b);
		return b;
	}

	public Explosion createExplosion(double x, double y, int index) {
		Explosion e = new Explosion(world, x, y, index);
		threadPoolExecutor.execute(e);
		return e;
	}

	public QueenTank createQueenTank(double x, double y, double angle, int index) {
		QueenTank qt = new QueenTank(world, x, y, angle, new Queen(index));
		threadPoolExecutor.execute(qt);
		return qt;
	}

	public QueenTank createStateMachineQueenTank(double x, double y,
			double angle, int index) {
		QueenTank smqt = new QueenTank(world, x, y, angle, new StateMachineQueen(index));
		threadPoolExecutor.execute(smqt);
		return smqt;
	}
}
