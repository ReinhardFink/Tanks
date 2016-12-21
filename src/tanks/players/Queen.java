package tanks.players;

import tanks.CONSTANTS;
import tanks.visibles.QueenTank;
import tanks.visibles.Tank;

public class Queen implements PlayerInterface {

	private static int activeQueen = 0;
	private static int solutions = 0;
	private static int moves = 0;

	private QueenTank tank;
	private int myNumber;
	private int myPosition;
	private double turretAngle;
	private int nextShotNumber;
	private boolean isNewMove;

	public Queen(int myNumber) {
		this.myNumber = myNumber;
		// 0 weil dann mit 1 bereits der erste Weg berechnet werden kann
		this.myPosition = 0;
		this.turretAngle = 0;
		this.nextShotNumber = 1;
		this.isNewMove = true;
	}

	//@Override
	public void play() {
		if (myNumber != activeQueen)
			return;
		if (isNewMove) {
			myPosition++;
			turretAngle = 0;
			nextShotNumber = 1;
			tank.resetTankHitStatistik();
			isNewMove = false;
		}
		// tank moves out of field
		if (myPosition > CONSTANTS.MAX_QUEENS) {
			// first tank has checked all positions
			if (activeQueen == 0)
				return;
			moveToStart();
		}
		// tank moves to next position
		else if (tank.getY() < CONSTANTS.QUEEN_START_POSITION
				+ CONSTANTS.CHESS_FIELD_LENGTH * myPosition)
			moveToNextPosition();
		// tank turns turret
		else if (nextShotNumber < 4
				&& turretAngle < nextShotNumber / 4.0 * Math.PI) {
			turretAngle += CONSTANTS.TANK_DELTA_ANGLE;
			this.tank.turnTurret(CONSTANTS.TANK_DELTA_ANGLE);
		}
		// tank shots
		else if (turretAngle >= nextShotNumber / 4.0 * Math.PI) {
			this.nextShotNumber++;
			this.tank.fire(2);
		}
		// tank turns turret back
		else if (nextShotNumber > 3 && turretAngle > 0) {
			turretAngle -= CONSTANTS.TANK_DELTA_ANGLE;
			this.tank.turnTurret(-CONSTANTS.TANK_DELTA_ANGLE);
		}
		// tank checks, if other tank was hit
		else if (tank.isTankHitStatistikFinished()) {
			if (tank.getTankHitStatistik() == 0) {
				// last tank has found a position => a solution is found
				if (activeQueen == CONSTANTS.MAX_QUEENS - 1) {
					solutions++;
					// Programm stoppt mit Wert -2
					activeQueen = -2;
					System.out.println("Solution Nr. " + solutions + " after "
							+ moves + " moves found");
				}
				activeQueen++;
			}
			isNewMove = true;
		}
	}

	private void moveToStart() {
		if (tank.getY() > CONSTANTS.QUEEN_START_POSITION)
			this.tank.move(-CONSTANTS.TANK_SPEED);
		else {
			myPosition = 0;
			moves++;
			isNewMove = true;
			activeQueen--;
		}
	}

	private void moveToNextPosition() {
		this.tank.move(CONSTANTS.TANK_SPEED);
		moves++;
	}

	//@Override
	public void setTank(Tank tank) {
		if (tank.getClass() != QueenTank.class)
			try {
				throw new ClassNotFoundException();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		this.tank = (QueenTank) tank;
	}

	//@Override
	public Tank getTank() {
		return this.tank;
	}

}
