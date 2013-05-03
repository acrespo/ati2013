package sturla.atitp.frontend.menus.noiseMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import sturla.atitp.frontend.MainFrame;
import sturla.atitp.frontend.imageops.noise.ExponentialNoiseOperation;
import sturla.atitp.frontend.imageops.noise.RayleighNoiseOperation;
import sturla.atitp.frontend.imageops.noise.SaltPepperOperation;
import sturla.atitp.frontend.imageops.noise.WhiteNoiseOperation;

public class NoiseMenu extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;

	public NoiseMenu(MainFrame parent) {
		// TODO Auto-generated constructor stub
		super("Noise");
		
		this.mainFrame = parent;
		
		JMenuItem rayleighNoiseItem = new JMenuItem("Rayleigh");
		rayleighNoiseItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFrame.currOperation = new RayleighNoiseOperation();
				mainFrame.setFirstSlider(0, 200, 0);
				mainFrame.parameterSlider2.setVisible(false);
				mainFrame.parameterField2.setVisible(false);
				mainFrame.displayTextFields(false);
			}
		});
		JMenuItem saltAndPepper = new JMenuItem("Salt And Pepper");
		saltAndPepper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFrame.currOperation = new SaltPepperOperation();
				mainFrame.setFirstSlider(0, 200, 0);
				mainFrame.setSecondSlider(0, 200, 200);
				mainFrame.displayTextFields(false);
			}
		});
		
		JMenuItem whiteNoiseItem = new JMenuItem("WhiteNoise");
		whiteNoiseItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFrame.currOperation = new WhiteNoiseOperation();
				mainFrame.setFirstSlider(0, 400, 0);
				mainFrame.parameterSlider2.setVisible(false);
				mainFrame.parameterField2.setVisible(false);
				mainFrame.displayTextFields(false);
			}
		});
		JMenuItem exponentialNoiseItem = new JMenuItem("Exponential Noise");
		exponentialNoiseItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mainFrame.currOperation = new ExponentialNoiseOperation();
				mainFrame.setFirstSlider(0, 400, 0);
				mainFrame.parameterSlider2.setVisible(false);
				mainFrame.parameterField2.setVisible(false);
				mainFrame.displayTextFields(false);
			}
		});
		
		this.add(rayleighNoiseItem);
		this.add(saltAndPepper);
		this.add(whiteNoiseItem);
		this.add(exponentialNoiseItem);

	}
}
