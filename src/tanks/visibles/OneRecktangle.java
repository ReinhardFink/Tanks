package tanks.visibles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import tanks.CONSTANTS;
import tanks.world.World;

public class OneRecktangle extends AbstractVisible implements Visible {

	private Color color;

	public OneRecktangle(World world, double x, double y, Color color) {
		super(world, x, y, 0.0);
		this.color = color;
	}

	@Override
	public void paint(Graphics2D g) {
		g.setPaint(color);
		g.fill(new Rectangle2D.Double(getX() - CONSTANTS.CHESS_FIELD_LENGTH / 2, getY()
				- CONSTANTS.CHESS_FIELD_LENGTH / 2,
				CONSTANTS.CHESS_FIELD_LENGTH, CONSTANTS.CHESS_FIELD_LENGTH));
	}

}
