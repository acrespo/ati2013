package sturla.atitp.frontend.menus.arithmeticMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import sturla.atitp.frontend.MainFrame;
import sturla.atitp.frontend.imageops.MultiplyScalarOperation;
import sturla.atitp.frontend.imageops.NegativeImageOperation;
import sturla.atitp.frontend.imageops.binaryops.AddImageOperation;
import sturla.atitp.frontend.imageops.binaryops.MultiplyImageOperation;
import sturla.atitp.frontend.imageops.binaryops.SubtractImageOperation;

public class ArithmeticMenu extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;

	public ArithmeticMenu(MainFrame parent) {
		super("Arithmetic");
		
		this.mainFrame = parent;
		
		JMenuItem add = new JMenuItem("Add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFrame.currOperation = new AddImageOperation();
				mainFrame.hideSliders();
				if (!mainFrame.secondImageLabel.hasImage() && mainFrame.resultImageLabel.hasImage()) {
					mainFrame.secondImageLabel.setImage(mainFrame.resultImageLabel.getImage());
				}
				mainFrame.displayTextFields(false);
			}
		});
		JMenuItem subtract = new JMenuItem("Subtract");
		subtract.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFrame.currOperation = new SubtractImageOperation();
				mainFrame.hideSliders();
				if (!mainFrame.secondImageLabel.hasImage() && mainFrame.resultImageLabel.hasImage()) {
					mainFrame.secondImageLabel.setImage(mainFrame.resultImageLabel.getImage());
				}
				mainFrame.displayTextFields(false);
			}
		});
		JMenuItem multiply = new JMenuItem("Multiply");
		multiply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFrame.currOperation = new MultiplyImageOperation();
				mainFrame.hideSliders();
				if (!mainFrame.secondImageLabel.hasImage() && mainFrame.resultImageLabel.hasImage()) {
					mainFrame.secondImageLabel.setImage(mainFrame.resultImageLabel.getImage());
				}
				mainFrame.displayTextFields(false);
			}
		});
		JMenuItem multiplyScalar = new JMenuItem("Multiply scalar");
		multiplyScalar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFrame.currOperation = new MultiplyScalarOperation();
				mainFrame.setFirstSlider(0, 400, 0);
				mainFrame.parameterSlider2.setVisible(false);
				mainFrame.parameterField2.setVisible(false);
				mainFrame.displayTextFields(false);
			}
		});
		JMenuItem multiplyBigScalar = new JMenuItem("Multiply big scalar");
		multiplyBigScalar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFrame.currOperation = new MultiplyScalarOperation();
				mainFrame.setFirstSlider(0, 40000, 0);
				mainFrame.parameterSlider2.setVisible(false);
				mainFrame.parameterField2.setVisible(false);
				mainFrame.displayTextFields(false);
			}
		});
		JMenuItem negative = new JMenuItem("Negative");
		negative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFrame.currOperation = new NegativeImageOperation();
				mainFrame.hideSliders();
				mainFrame.displayTextFields(false);
			}
		});
		this.add(add);
		this.add(multiply);
		this.add(subtract);
		this.add(negative);
		this.add(multiplyScalar);
		this.add(multiplyBigScalar);

	}

}
