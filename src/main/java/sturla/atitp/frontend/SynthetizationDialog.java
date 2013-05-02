package sturla.atitp.frontend;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SynthetizationDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SynthetizationDialog(JRadioButton maxRadioButton, JRadioButton minRadioButton, JRadioButton avgRadioButton, JRadioButton absRadioButton) {
		// TODO Auto-generated constructor stub
		
		setBounds(1, 1, 450, 170);
		Dimension size = getToolkit().getScreenSize();
		setLocation(size.width/3 - getWidth()/3, size.height/3 - getHeight()/3);
		this.setResizable(false);
		setLayout(null);
		
		JPanel synthetizationPanel = new JPanel();
		synthetizationPanel.setBorder(BorderFactory.createTitledBorder("Sinthetization:"));
		synthetizationPanel.setBounds(0, 0, 450, 50);
		
		synthetizationPanel.add(maxRadioButton);
		synthetizationPanel.add(minRadioButton);
		synthetizationPanel.add(avgRadioButton);
		synthetizationPanel.add(absRadioButton);

		this.add(synthetizationPanel);
		
	}
}
