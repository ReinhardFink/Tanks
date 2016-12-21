package tanks.visibles;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import tanks.CONSTANTS;
import tanks.world.World;

public class Explosion extends AbstractVisible implements Visible {
	
	static Image[][] imageArray;
	
	static {
		imageArray = new Image[2][];
		readExplosionNuber(0);
		readExplosionNuber(1);
		
	}

	private static void readExplosionNuber(int index) {
		imageArray[index] = new Image[CONSTANTS.EXPLOSION_MAX_IMAGES[index]];
		for (int i = 0; i < CONSTANTS.EXPLOSION_MAX_IMAGES[index]; i++) {
			StringBuffer filename = new StringBuffer(CONSTANTS.EXPLOSION_FILEBASE);
			filename.append(index + 1);
			filename.append("-");
			filename.append(i+1);
			filename.append(".png");
			//System.out.println(filename);
			try {
				Explosion.imageArray[index][i] = ImageIO.read(new File(filename.toString()));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println();
				System.out.println("Image not found");
			}
		}
	}

	private int imageIndex;

	private VisibleImage image;
	private int imageCounter;

	public Explosion(World world, double x, double y, int imageIndex) {
		super(world, x, y, 0.0);
		this.imageIndex = imageIndex;
		this.imageCounter = 0;
	}

	@Override
	public void paint(Graphics2D g) {
		if (image != null)
			image.paint(g, getX(), getY(), 0);
	}
	
	@Override
	public void work() {
		loadPicture();
	}

	private void loadPicture() {
		if (imageCounter >= CONSTANTS.EXPLOSION_MAX_IMAGES[imageIndex]) {
			remove();
			return;
		}
		/*
		StringBuffer filename = new StringBuffer(CONSTANTS.EXPLOSION_FILEBASE);
		filename.append(imageIndex + 1);
		filename.append("-");
		filename.append(imageCounter++);
		filename.append(".png");
		// System.out.println(filename);
		try {
			image = new VisibleImage(getX(), getY(), 0, ImageIO.read(new File(filename
					.toString())), 0);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println();
			System.out.println("Image not found");
		}
		*/
		image = new VisibleImage(getX(), getY(), 0, Explosion.imageArray[imageIndex][imageCounter++], 0);
	}

}
