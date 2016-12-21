package tanks.visibles;

import java.awt.Graphics2D;

public interface Visible {

	public double getX();

	public double getY();

	public double getCollisionRadius();
	
	public void paint(Graphics2D g);

	public void remove();

}
