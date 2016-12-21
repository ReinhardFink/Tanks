package tanks.visibles;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import tanks.CONSTANTS;
import tanks.players.InteractivPlayer;
import tanks.players.PlayerInterface;
import tanks.world.World;
import tanks.visibles.Bullit;

public class Tank extends AbstractMover {

	private static final int INDEX_TANK = 0;
	private static final int INDEX_TURRET = 1;
	private static final int INDEX_RADAR = 2;

	private PlayerInterface player;
	private ArrayList<VisibleImage> imageList;
	private ArrayList<Double> angleList;

	private int livePoints;
	private int explodeWaits;
	private boolean exploding;
	private double lastSpeed;
	@SuppressWarnings("unused")
	// wird über myBullitHits gesetzt
	private int madeHits;

	public Tank(World world, double x, double y, double angle,
			PlayerInterface player) {
		super(world, x, y, angle);
		this.livePoints = CONSTANTS.MAX_LIVE_POINTS;
		this.explodeWaits = 40;
		this.exploding = false;
		this.lastSpeed = 0;
		this.madeHits = 0;
		this.angleList = new ArrayList<Double>();
		this.angleList.add(angle);
		this.angleList.add(angle);
		this.angleList.add(angle);
		this.player = player;
		this.player.setTank(this);
		this.imageList = new ArrayList<VisibleImage>();
		try {
			imageList.add(new VisibleImage(x, y, angle, ImageIO.read(new File(
					CONSTANTS.TANK_FILENAME)), Math.PI / 2));
			imageList.add(new VisibleImage(x, y, angle, ImageIO.read(new File(
					CONSTANTS.TURRET_FILENAME)), Math.PI / 2));
			imageList.add(new VisibleImage(x, y, angle, ImageIO.read(new File(
					CONSTANTS.RADAR_FILENAME)), Math.PI / 2));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println();
			System.out.println("Image not found");
		}
	}

	@Override
	public void work() {
		if (this.exploding) {
			if (explodeWaits-- < 0) {
				getWorld().getVisibleList().remove(this);
			}

		} else
			this.player.play();
	}

	public void move(double speed) {
		lastSpeed = speed;
		super.move(speed);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tanks.visibles.Mover#onCrashWith(tanks.visibles.Visible) Scheiß Code
	 *      weil alles extra abgefragt werden muss!!
	 */
	public void onCrashWith(Visible visible) {
		if (visible.getClass() == Bullit.class)
			reduceLivePoints();
		else if (visible.getClass() == Wall.class)
			this.move(-2 * lastSpeed);
		else if (visible.getClass() == Tank.class)
			this.move(-2 * lastSpeed);
		else if (visible.getClass() == QueenTank.class)
			this.move(-2 * lastSpeed);
	}

	public void myBullitHits(Visible visible) {
		this.madeHits++;
	}

	public void turn(double angle) {
		super.turn(angle);
		changeAngle(INDEX_TANK, angle);
	}
	
	public double getAngel() {
		return angleList.get(INDEX_TANK);
	}

	public void turnTurret(double angle) {
		changeAngle(INDEX_TURRET, angle);
	}
	
	public double getTurretAngelRelativeToTank() {
		return angleList.get(INDEX_TURRET) - angleList.get(INDEX_TANK);
	}

	public void turnRadar(double angle) {
		changeAngle(INDEX_RADAR, angle);
	}

	public void fire(int intensity) {
		getWorld().getVisibleList().add(
				getWorld().getvisibleObjectFactory().createBullit(
						getX() + Math.cos(angleList.get(INDEX_TURRET))
								* CONSTANTS.TURRET_LENGTH,
						getY() + Math.sin(angleList.get(INDEX_TURRET))
								* CONSTANTS.TURRET_LENGTH,
						angleList.get(INDEX_TURRET), this));
	}

	public void reduceLivePoints() {
		livePoints -= CONSTANTS.MINUS_LIVE_POINTS;
		System.out.println("LIVEPOINTS: " + this + " " + livePoints);
		if (this.livePoints <= 0 && !this.exploding) {
			logoffPlayer();
			this.exploding = true;
			this.explode();
		}
	}

	private void explode() {
		getWorld().getVisibleList().add(
				getWorld().getvisibleObjectFactory().createExplosion(getX(),
						getY(), 1));
	}

	private void logoffPlayer() {
		if (player != null) {
			if (player.getClass() == InteractivPlayer.class)
				getWorld().getPlayerManager().deleteObserver(
						(InteractivPlayer) player);
			this.player = null;
		}
	}

	private void changeAngle(int index, double angle) {
		for (int i = index; i < angleList.size(); i++)
			this.angleList.set(i, this.angleList.get(i) + angle);
	}

	@Override
	public void paint(Graphics2D g) {
		for (int i = 0; i < imageList.size(); i++)
			imageList.get(i).paint(g, getX(), getY(), angleList.get(i));
		// paint spezial
		if (CONSTANTS.DRAW_TANK_COLLISIONRADIUS)
			g.drawOval((int) getX() - (int) getCollisionRadius(), (int) getY()
					- (int) getCollisionRadius(),
					(int) getCollisionRadius() * 2,
					(int) getCollisionRadius() * 2);
		if (CONSTANTS.RADAR_ON) {
			g
					.drawLine((int) getX(), (int) getY(), (int) (getX() + (Math
							.cos(angleList.get(INDEX_RADAR) - 0.1) * 400)),
							(int) (getX() + (Math.sin(angleList
									.get(INDEX_RADAR) - 0.1) * 400)));
			g
					.drawLine((int) getX(), (int) getY(), (int) (getX() + (Math
							.cos(angleList.get(INDEX_RADAR) + 0.1) * 400)),
							(int) (getX() + (Math.sin(angleList
									.get(INDEX_RADAR) + 0.1) * 400)));
		}
	}

	@Override
	public double getCollisionRadius() {
		return (imageList.get(INDEX_TANK).getImage().getWidth(null) / 2 + imageList
				.get(INDEX_TANK).getImage().getHeight(null) / 2) / 2.0;
	}
}
