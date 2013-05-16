package sturla.atitp.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sturla.atitp.imageprocessing.Image;
import sturla.atitp.imageprocessing.MaskFactory;
import sturla.atitp.imageprocessing.Point;

public class TrackingDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private List<Point> mask = new ArrayList<Point>();
	private MainFrame mainFrame;

	public TrackingDialog(MainFrame mainFrame2){
		
		this.mainFrame = mainFrame2;
		setTitle("Tracking");
		setBounds(1, 1, 250, 130);
		Dimension size = getToolkit().getScreenSize();
		setLocation(size.width/3 - getWidth()/3, size.height/3 - getHeight()/3);
		this.setResizable(false);
		setLayout(null);

		JPanel pan1 = new JPanel();
		pan1.setBounds(0, 0, 250, 40);


		JLabel msg = new JLabel("Click two points: first will be theta1, second theta2.");
		msg.setSize(250, 40);
		
		final JButton okButton = new JButton("OK");
		okButton.setEnabled(false);
		okButton.setSize(250, 40);
		okButton.setBounds(0, 50, 250, 50);
		okButton.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				Image aux = mainFrame.currentImageLabel.getImage().copy();
				aux.applyMask(MaskFactory.buildGaussianMask(5, 5), 1, 1, aux.getWidth()-1, aux.getHeight()-1);
				aux.tracking(mainFrame.getMask());
				mainFrame.repaint();
		//		panel.removeMouseListener(panel.getMouseListeners()[0]);
				dispose();
			}
		});
		
		mainFrame.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent event) {
				int x = event.getX();
				int y = event.getY();
				Image img = mainFrame.currentImageLabel.getImage();
				if(img.validPixel(x, y)){
					mask.add(new Point(x, y));
					mainFrame.loadMask(mask);
					mainFrame.repaint();
					if(mask.size() == 2){
						okButton.setEnabled(true);
						mainFrame.removeMouseListener(mainFrame.getMouseListeners()[0]);
						Image aux = mainFrame.currentImageLabel.getImage().copy();
						aux.applyMask(MaskFactory.buildGaussianMask(5, 5), 1, 1, aux.getWidth()-1, aux.getHeight()-1);
						aux.tracking(mainFrame.getMask());
						if(mask != null){
							for(Point p: mask){
								aux.setRGBPixel(p.x, p.y, Color.GREEN.getRGB());
							}
								
						}
				//		panel.removeMouseListener(panel.getMouseListeners()[0]);
						dispose();
					}
				}					
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}			
		});

		pan1.add(msg);
		this.add(pan1);
		this.add(okButton);

	};

}


