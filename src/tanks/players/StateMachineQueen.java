package tanks.players;

import tanks.CONSTANTS;
import tanks.visibles.QueenTank;
import tanks.visibles.Tank;

public class StateMachineQueen implements PlayerInterface {

	private static final int STATE_IDLE = 0;
	private static final int STATE_MOVE_FW = STATE_IDLE + 1;
	private static final int STATE_TURN_RIGHT_45 = STATE_MOVE_FW + 1;
	private static final int STATE_TURN_LEFT_135 = STATE_TURN_RIGHT_45 + 1;
	private static final int STATE_FIRE = STATE_TURN_LEFT_135 + 1;
	private static final int STATE_CALL_LEFT_QUEEN = STATE_FIRE + 1;
	private static final int STATE_CALL_RIGHT_QUEEN = STATE_CALL_LEFT_QUEEN + 1;
	private static final int STATE_MOVE_TO_START = STATE_CALL_RIGHT_QUEEN + 1;
	private static final int STATE_CHECK_HITS = STATE_MOVE_TO_START + 1;

	private static StateMachineQueen[] queens = new StateMachineQueen[CONSTANTS.MAX_QUEENS];
	private static int solutions = 0;
	private static int moves = 0;

	private QueenTank tank;
	private int myNumber;
	private int myPosition;
	private int state;
	private int shots;

	public StateMachineQueen(int myNumber) {
		this.myNumber = myNumber;
		this.myPosition = 0;
		if (this.myNumber == 0) {
			this.state = STATE_MOVE_FW;
		} else
			this.state = STATE_IDLE;
		this.shots = 0;
		queens[myNumber] = this;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return this.state;
	}

	// @Override
	public void setTank(Tank tank) {
		this.tank = (QueenTank) tank;
	}

	// @Override
	public Tank getTank() {
		return tank;
	}

	// @Override
	public void play() {
		// if (myNumber==5 && state != 0) System.out.println(state);
		//tank.turnRadar(CONSTANTS.RADAR_DELTA_ANGLE);
		switch (state) {
		case STATE_IDLE:
			tank.turnRadar(CONSTANTS.RADAR_DELTA_ANGLE);
			break;
		case STATE_MOVE_FW:
			if (myPosition == CONSTANTS.MAX_QUEENS)
				state = STATE_CALL_LEFT_QUEEN;
			else if (tank.getY() < CONSTANTS.QUEEN_START_POSITION
					+ CONSTANTS.CHESS_FIELD_LENGTH * (myPosition + 1))
				tank.move(CONSTANTS.TANK_SPEED);
			else {
				myPosition++;
				moves++;
				state = STATE_TURN_RIGHT_45;
			}
			break;
		case STATE_TURN_RIGHT_45:
			if (tank.getTurretAngelRelativeToTank() < Math.PI / 4 * (shots + 1)) {
				tank.turnTurret(CONSTANTS.TURRET_DELTA_ANGLE);
			} else
				state = STATE_FIRE;
			break;
		case STATE_FIRE:
			if (shots++ < 3) {
				tank.fire(2);
				state = STATE_TURN_RIGHT_45;
			} else {
				shots = 0;
				state = STATE_TURN_LEFT_135;
			}
			break;
		case STATE_TURN_LEFT_135:
			if (tank.getTurretAngelRelativeToTank() > 0) {
				tank.turnTurret(-CONSTANTS.TURRET_DELTA_ANGLE);
			} else
				state = STATE_CHECK_HITS;
			break;
		case STATE_CHECK_HITS:
			if (tank.isTankHitStatistikFinished()) {
				if (tank.getTankHitStatistik() == 0) {
					// last tank has found a position => a solution is found
					if (myNumber == CONSTANTS.MAX_QUEENS - 1) {
						solutions++;
						System.out.println("Solution Nr. " + solutions
								+ " after " + moves + " found");
						state = STATE_IDLE; // stehen bleiben
						// state = STATE_MOVE_FW; // weitersuchen
					} else
						state = STATE_CALL_RIGHT_QUEEN;
				} else
					state = STATE_MOVE_FW;
				tank.resetTankHitStatistik();
			}
			break;
		case STATE_CALL_RIGHT_QUEEN:
			/*
			 * right Queen may not be activated, if she is moving back!
			 */
			if (queens[myNumber + 1].getState() == STATE_IDLE) {
				queens[myNumber + 1].setState(STATE_MOVE_FW);
				state = STATE_IDLE;
			}
			break;
		case STATE_CALL_LEFT_QUEEN:
			if (myNumber != 0)
				queens[myNumber - 1].setState(STATE_MOVE_FW);
			state = STATE_MOVE_TO_START;
			break;
		case STATE_MOVE_TO_START:
			if (tank.getY() > CONSTANTS.QUEEN_START_POSITION)
				this.tank.move(-CONSTANTS.TANK_SPEED);
			else {
				myPosition = 0;
				moves++;
				state = STATE_IDLE;
			}
			break;
		}
	}

}
