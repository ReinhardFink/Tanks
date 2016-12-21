package tanks.visibles;

import tanks.players.PlayerInterface;
import tanks.world.World;

public class QueenTank extends Tank {

	private int madeHits;
	private int madeTankHits;

	public QueenTank(World world, double x, double y, double angle,
			PlayerInterface player) {
		super(world, x, y, angle, player);
		resetTankHitStatistik();
	}

	public void resetTankHitStatistik() {
		this.madeHits = 0;
		this.madeTankHits = 0;
	}

	public boolean isTankHitStatistikFinished() {
		return madeHits == 3;
	}

	public int getTankHitStatistik() {
		return this.madeTankHits;
	}

	public void myBullitHits(Visible visible) {
		this.madeHits++;
		if (visible.getClass() == QueenTank.class)
			madeTankHits++;
		// System.out.println(tankHits + " ");
	}

}
