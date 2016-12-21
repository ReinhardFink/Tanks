package tanks.world;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import tanks.visibles.Visible;

@SuppressWarnings("serial")
public class WorldPanel extends JPanel {

	private World world;
	
	public WorldPanel(World world) {
		super();
		this.world = world;
	}
	public void paint(Graphics g) {		
		super.paint(g);
		for (Visible visible : world.getDisplayList()) {
			if (visible != null)
				visible.paint(((Graphics2D) g));
		}
	}
}
