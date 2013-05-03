package sturla.atitp.frontend.menus.edgeDetectorsMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import sturla.atitp.frontend.MainFrame;
import sturla.atitp.frontend.SynthetizationDialog;
import sturla.atitp.frontend.imageops.edgeDetectors.PrewittEdgeDetectorOperation;
import sturla.atitp.frontend.imageops.edgeDetectors.RobertsEdgeDetectorOperation;
import sturla.atitp.frontend.imageops.edgeDetectors.SobelEdgeDetectorOperation;

public class EdgeDetectorsMenu extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;

	
	public EdgeDetectorsMenu(MainFrame parent) {
		super("Edge Detectors");
		this.mainFrame = parent;
				
		JMenuItem robertsEdgeDetector = new JMenuItem("Roberts Edge Detector");
		robertsEdgeDetector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFrame.currOperation = new RobertsEdgeDetectorOperation();
				mainFrame.hideSliders();
				mainFrame.displayTextFields(false);
				mainFrame.rectangle.setVisible(false);
				mainFrame.maskSize.setVisible(true);
				SynthetizationDialog synthetizationDialog = new SynthetizationDialog(mainFrame.maxRadioButton, mainFrame.minRadioButton, mainFrame.avgRadioButton, mainFrame.absRadioButton);
				synthetizationDialog.setVisible(true);
			}
		});
		
		JMenuItem prewittEdgeDetector = new JMenuItem("Prewitt Edge Detector");
		prewittEdgeDetector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFrame.currOperation = new PrewittEdgeDetectorOperation();
				mainFrame.hideSliders();
				mainFrame.displayTextFields(false);
				mainFrame.rectangle.setVisible(false);
				mainFrame.maskSize.setVisible(true);
				SynthetizationDialog synthetizationDialog = new SynthetizationDialog(mainFrame.maxRadioButton, mainFrame.minRadioButton, mainFrame.avgRadioButton, mainFrame.absRadioButton);
				synthetizationDialog.setVisible(true);
			}
		});
		
		JMenuItem sobelEdgeDetector = new JMenuItem("Sobel Edge Detector");
		sobelEdgeDetector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFrame.currOperation = new SobelEdgeDetectorOperation();
				mainFrame.hideSliders();
				mainFrame.displayTextFields(false);
				mainFrame.rectangle.setVisible(false);
				mainFrame.maskSize.setVisible(true);
				SynthetizationDialog synthetizationDialog = new SynthetizationDialog(mainFrame.maxRadioButton, mainFrame.minRadioButton, mainFrame.avgRadioButton, mainFrame.absRadioButton);
				synthetizationDialog.setVisible(true);
			}
		});
		
		this.add(robertsEdgeDetector);
		this.add(prewittEdgeDetector);
		this.add(sobelEdgeDetector);
	}
}
