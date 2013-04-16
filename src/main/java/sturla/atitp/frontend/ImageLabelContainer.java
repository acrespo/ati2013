package sturla.atitp.frontend;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sturla.atitp.imageprocessing.Image;

public class ImageLabelContainer {
	
	private Image image;
	private JPanel panel;
	private JLabel label;
	private MainFrame frame;
	
	public ImageLabelContainer(MainFrame frame, JPanel panel) {
		this.panel = panel;
		this.frame = frame;
	}
	
	public void setImage(Image image) {
		if (label != null)
			panel.remove(label);
		label = new JLabel(new ImageIcon(image.toBufferedImage()));
		this.image = image;
		label.setVisible(true);
		label.setLayout(null);
		// Always set bounds to origin to 0,0. Will be moved by somebody else.
		label.setBounds(0, 0, image.getWidth(), image.getHeight());
		panel.add(label);
		frame.adjustImages();
	}
	
	public void setImage(BufferedImage img, int width, int height) {
		if (label != null)
			panel.remove(label);
		label = new JLabel(new ImageIcon(img));
		label.setVisible(true);
		label.setLayout(null);
		label.setBounds(600, 0, width, height);
		panel.add(label);
		label.repaint();
		panel.repaint();
	}
	
	public Image getImage() {
		return image;
	}
	
	public boolean hasImage() {
		return image !=  null;
	}
	
	public void removeImage() {
		if (image == null)
			return;
		label.setVisible(false);
		panel.remove(label);
		image = null;
		label = null;
	}
	
	public int getWidth() {
		if (label == null) return 0;
		return label.getWidth();

	}
	
	public int getHeight() {
		if (label == null) return 0;
		return label.getHeight();
	}
	
	public void setBounds(int x, int y, int width, int height) {
		if (label != null) {
			label.setBounds(x, y, width, height);
		}
	}
	
	public void repaint() {
		label.repaint();
	}
}
