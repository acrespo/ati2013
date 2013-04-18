package sturla.atitp.frontend;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class EdgeDetectorDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EdgeDetectorDialog(JRadioButton leclercRadioButton, JRadioButton lorentzRadioButton) {

		setBounds(1, 1, 450, 170);
		Dimension size = getToolkit().getScreenSize();
		setLocation(size.width/3 - getWidth()/3, size.height/3 - getHeight()/3);
		this.setResizable(false);
		setLayout(null);
		
		JPanel borderDetectorPanel = new JPanel();
		borderDetectorPanel.setBorder(BorderFactory.createTitledBorder("Edge Detector:"));
		borderDetectorPanel.setBounds(0, 50, 450, 50);
		

		borderDetectorPanel.add(leclercRadioButton);
		borderDetectorPanel.add(lorentzRadioButton);

		leclercRadioButton.setVisible(true);
		lorentzRadioButton.setVisible(true);
		this.add(borderDetectorPanel);		
	}
}
