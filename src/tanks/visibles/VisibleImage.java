package tanks.visibles;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

public class VisibleImage {

	private Image image;
	private AffineTransform matrix;
	private double correctionAngle;

	public VisibleImage(double x, double y, double angle, Image image,
			double correctionAngle) {
		this.image = image;
		this.matrix = new AffineTransform();
		this.correctionAngle = correctionAngle;
		//setXYandAngle(x, y, angle);
	}

	public Image getImage() {
		return image;
	}
/*
	public void paint(Graphics2D g) {
		g.drawImage(image, matrix, null);
	}
*/
	public void paint(Graphics2D g, double x, double y, double angle) {
		setXYandAngle(x, y, angle);
		// System.out.println(g);
		g.drawImage(image, matrix, null);
	}

	private void setXYandAngle(double x, double y, double angle) {
		this.matrix.setToTranslation(x - image.getWidth(null) / 2.0, y
				- image.getHeight(null) / 2.0);
		this.matrix.rotate(angle + correctionAngle, image.getWidth(null) / 2.0,
				image.getHeight(null) / 2.0);
	}
}
