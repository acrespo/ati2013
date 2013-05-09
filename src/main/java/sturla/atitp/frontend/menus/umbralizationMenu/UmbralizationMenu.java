package sturla.atitp.frontend.menus.umbralizationMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import sturla.atitp.frontend.MainFrame;
import sturla.atitp.frontend.imageops.threshold.BinaryGlobalUmbralizationOperation;
import sturla.atitp.frontend.imageops.threshold.BinaryOtsuUmbralizationOperation;
import sturla.atitp.frontend.imageops.threshold.GlobalUmbralizationOperation;
import sturla.atitp.frontend.imageops.threshold.OtsuUmbralizationOperation;
import sturla.atitp.frontend.imageops.threshold.ThresholdBinaryOperation;
import sturla.atitp.frontend.imageops.threshold.ThresholdWithHysteresisOperation;
import sturla.atitp.frontend.imageops.threshold.TreshHoldOperation;

public class UmbralizationMenu extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MainFrame mainFrame;

	public UmbralizationMenu(MainFrame parent) {
		super("Umbralization");
		
		this.mainFrame = parent;
		
		JMenuItem treshold = new JMenuItem("Threshold");
		treshold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFrame.currOperation = new TreshHoldOperation();
				mainFrame.setFirstSlider(0, 256 * 200, 0);
				mainFrame.parameterSlider2.setVisible(false);
				mainFrame.parameterField2.setVisible(false);
				mainFrame.displayTextFields(false);
			}
		});
		JMenuItem tresholdBinary = new JMenuItem("Threshold binary");
		tresholdBinary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFrame.currOperation = new ThresholdBinaryOperation();
				mainFrame.setFirstSlider(0, 256 * 200, 0);
				mainFrame.parameterSlider2.setVisible(false);
				mainFrame.parameterField2.setVisible(false);
				mainFrame.displayTextFields(false);
			}
		});
		
		JMenuItem globalUmbralization = new JMenuItem("Global Umbralization");
		globalUmbralization.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFrame.currOperation = new GlobalUmbralizationOperation();
				mainFrame.hideSliders();
				mainFrame.displayTextFields(false);
				mainFrame.rectangle.setVisible(false);
			}
		});
		
		JMenuItem otsuUmbralization = new JMenuItem("Otsu Umbralization");
		otsuUmbralization.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFrame.currOperation = new OtsuUmbralizationOperation();
				mainFrame.hideSliders();
				mainFrame.displayTextFields(false);
				mainFrame.rectangle.setVisible(false);
			}
		});
		JMenuItem binaryGlobalUmbralization = new JMenuItem("Binary Global Umbralization");
		binaryGlobalUmbralization.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFrame.currOperation = new BinaryGlobalUmbralizationOperation();
				mainFrame.hideSliders();
				mainFrame.displayTextFields(false);
				mainFrame.rectangle.setVisible(false);
			}
		});
		
		JMenuItem binaryOtsuUmbralization = new JMenuItem("Binary Otsu Umbralization");
		binaryOtsuUmbralization.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFrame.currOperation = new BinaryOtsuUmbralizationOperation();
				mainFrame.hideSliders();
				mainFrame.displayTextFields(false);
				mainFrame.rectangle.setVisible(false);
			}
		});
		
		JMenuItem hysteresisUmbralization = new JMenuItem("HysterUmbralization");
		hysteresisUmbralization.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFrame.currOperation = new ThresholdWithHysteresisOperation();
				mainFrame.hideSliders();
				mainFrame.displayTextFields(false);
				mainFrame.rectangle.setVisible(false);
				mainFrame.value1.setVisible(true);
				mainFrame.value2.setVisible(true);
			}
		});
		
		this.add(treshold);
		this.add(tresholdBinary);
		this.add(globalUmbralization);
		this.add(otsuUmbralization);
		this.add(binaryGlobalUmbralization);
		this.add(binaryOtsuUmbralization);
		this.add(hysteresisUmbralization);
	}

}
