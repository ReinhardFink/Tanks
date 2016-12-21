package tanks;

import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import tanks.world.World;

public class StartTanksApplet extends JApplet {

	private static final long serialVersionUID = 3546365024081097528L;

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			} 
		});
	};

	static void createAndShowGUI() {
		// JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame aFrame = new JFrame("World");
		aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aFrame.setContentPane(new World().getWorldPanel());
		aFrame.setSize(new Dimension(600, 600));
		aFrame.setLocation(130, 30);
		aFrame.setVisible(true);
	}

	public void init() {
		setContentPane(new World().getWorldPanel());
	}
}
