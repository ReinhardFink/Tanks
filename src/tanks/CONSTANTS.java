package tanks;

public class CONSTANTS {

	/*
	 * for All
	 */
	public static final String RESOURCES_FILEBASE = "./resources";
	public static final String IMAGES = "/images";
	public static final String EXPLOSIONS = "/explosion";
	public static final int SLEEP_MILLISEK = 60;
	public static final int MIN_SLEEP_MILLISEK = 1;
	public static final int DELTA_SLEEP_MILLISEK = 1;

	/*
	 * Player
	 */
	public static final int MAX_INTERACTIV_PLAYERS = 2;
	public static final int INDEX_UP = 0;
	public static final int INDEX_DOWN = 1;
	public static final int INDEX_TANK_LEFT = 2;
	public static final int INDEX_TANK_RIGHT = 3;
	public static final int INDEX_TURRET_LEFT = 4;
	public static final int INDEX_TURRET_RIGHT = 5;
	public static final int INDEX_TURRET_FIRE = 6;
	public static final int INDEX_RADAR_LEFT = 7;
	public static final int INDEX_RADAR_RIGHT = 8;

	/*
	 * Bullit
	 */
	public static final int BULLIT_RADIUS = 4;
	public static final double BULLIT_SPEED = 15;

	/*
	 * Explosion
	 */
	// public static final String EXPLOSION_FILEBASE =
	// "./resources/images/explosion/explosion";
	public static final String EXPLOSION_FILEBASE = RESOURCES_FILEBASE + IMAGES
			+ EXPLOSIONS + "/explosion";
	public static final int[] EXPLOSION_MAX_IMAGES = { 17, 71 };

	/*
	 * Tank
	 */
	public static final double TANK_SPEED = 5.0;
	public static final double TANK_DELTA_ANGLE = Math.PI / 20;
	public static final double TURRET_DELTA_ANGLE = Math.PI / 20;
	public static final double RADAR_DELTA_ANGLE = Math.PI / 40;
	public static final int TURRET_LENGTH = 30;
	public static final int MAX_LIVE_POINTS = 100;
	public static final int MINUS_LIVE_POINTS = 5;
	public static final String TANK_FILENAME = RESOURCES_FILEBASE + IMAGES
			+ "/body.png";
	public static final String TURRET_FILENAME = RESOURCES_FILEBASE + IMAGES
			+ "/turret.png";
	public static final String RADAR_FILENAME = RESOURCES_FILEBASE + IMAGES
			+ "/radar.png";
	public static final boolean DRAW_TANK_COLLISIONRADIUS = false;
	public static final boolean RADAR_ON = false;

	/*
	 * Wall
	 */
	public static final double SIDE = 590;
	public static final double MPX = 1;

	/*
	 * Chess Field
	 */
	public static final int MAX_QUEENS = 8;
	public static final double CHESS_FIELD_LENGTH = 60;
	public static final double QUEEN_START_POSITION = CHESS_FIELD_LENGTH / 2;

}
