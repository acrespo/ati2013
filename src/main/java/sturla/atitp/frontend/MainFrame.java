package sturla.atitp.frontend;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.sanselan.ImageReadException;

import sturla.atitp.app.ImageLoader;
import sturla.atitp.app.ImageSaver;
import sturla.atitp.frontend.imageops.AnisotropicDiffusionOperation;
import sturla.atitp.frontend.imageops.ContrastOperation;
import sturla.atitp.frontend.imageops.DynamicRangeCompressionOperation;
import sturla.atitp.frontend.imageops.EqualizeImageOperation;
import sturla.atitp.frontend.imageops.HistogramOperation;
import sturla.atitp.frontend.imageops.ImageOperation;
import sturla.atitp.frontend.imageops.ImageOperationParameters;
import sturla.atitp.frontend.imageops.IsotropicDiffusionOperation;
import sturla.atitp.frontend.imageops.MultiplyScalarOperation;
import sturla.atitp.frontend.imageops.NegativeImageOperation;
import sturla.atitp.frontend.imageops.PrewittEdgeDetectorOperation;
import sturla.atitp.frontend.imageops.RobertsEdgeDetectorOperation;
import sturla.atitp.frontend.imageops.SobelEdgeDetectorOperation;
import sturla.atitp.frontend.imageops.binaryops.AddImageOperation;
import sturla.atitp.frontend.imageops.binaryops.MultiplyImageOperation;
import sturla.atitp.frontend.imageops.binaryops.SubtractImageOperation;
import sturla.atitp.frontend.imageops.masks.FourMaskA;
import sturla.atitp.frontend.imageops.masks.FourMaskC;
import sturla.atitp.frontend.imageops.masks.FourMaskKirsh;
import sturla.atitp.frontend.imageops.masks.GaussianFilterOperation;
import sturla.atitp.frontend.imageops.masks.HighPassOperation;
import sturla.atitp.frontend.imageops.masks.LowPassOperation;
import sturla.atitp.frontend.imageops.masks.MaskA;
import sturla.atitp.frontend.imageops.masks.MaskC;
import sturla.atitp.frontend.imageops.masks.MaskD;
import sturla.atitp.frontend.imageops.masks.MaskKirsh;
import sturla.atitp.frontend.imageops.masks.MedianPassOperation;
import sturla.atitp.frontend.imageops.noise.ExponentialNoiseOperation;
import sturla.atitp.frontend.imageops.noise.RayleighNoiseOperation;
import sturla.atitp.frontend.imageops.noise.SaltPepperOperation;
import sturla.atitp.frontend.imageops.noise.WhiteNoiseOperation;
import sturla.atitp.frontend.imageops.tresh.BinaryGlobalUmbralizationOperation;
import sturla.atitp.frontend.imageops.tresh.BinaryOtsuUmbralizationOperation;
import sturla.atitp.frontend.imageops.tresh.GlobalUmbralizationOperation;
import sturla.atitp.frontend.imageops.tresh.OtsuUmbralizationOperation;
import sturla.atitp.frontend.imageops.tresh.ThresholdBinaryOperation;
import sturla.atitp.frontend.imageops.tresh.TreshHoldOperation;
import sturla.atitp.imageprocessing.edgeDetector.LeclercEdgeDetector;
import sturla.atitp.imageprocessing.edgeDetector.LorentzEdgeDetector;
import sturla.atitp.imageprocessing.synthesization.SynthesizationType;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -4569949105927376584L;

	private JPanel panel;
	private ImageLabelContainer currentImageLabel;
	// used for binary operations
	private ImageLabelContainer secondImageLabel;
	private ImageLabelContainer resultImageLabel;

	private ImageOperation currOperation;

	private JSlider parameterSlider1;
	private JTextArea parameterField1;
	private JSlider parameterSlider2;
	private JTextArea parameterField2;

	private JTextField coordX1;
	private JTextField coordY1;
	private JTextField coordX2;
	private JTextField coordY2;
	private JTextField maskSize;
	private JTextField value1;
	private JTextField value2;
	
	private JTextField rectWidth;
	private JTextField rectHeight;

	private JButton doItButton;
	private DraggableComponent rectangle;

	private JRadioButton leclercRadioButton;
	private JRadioButton lorentzRadioButton;

	private JRadioButton absRadioButton;
	private JRadioButton avgRadioButton;
	private JRadioButton minRadioButton;
	private JRadioButton maxRadioButton;

	public MainFrame() {
		initUI();
	}

	private void initUI() {
		panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);
		initMenu();
		initTextFields();
		initSliders();
		initEdgeDetectorRadioButtions();
		initSynthetizationRadioButtons();
		setTitle("ATI: TP 1");
		setSize(2000, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void initEdgeDetectorRadioButtions() {
		leclercRadioButton = new JRadioButton("Leclerc", true);
		lorentzRadioButton = new JRadioButton("Lorentz");

		leclercRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				leclercRadioButton.setSelected(true);
				lorentzRadioButton.setSelected(!leclercRadioButton.isSelected());
			}
		});

		lorentzRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				lorentzRadioButton.setSelected(true);
				leclercRadioButton.setSelected(!lorentzRadioButton.isSelected());
			}
		});		
	}

	private void initSynthetizationRadioButtons() {
		maxRadioButton = new JRadioButton("Maximum");
		minRadioButton = new JRadioButton("Minimum");
		avgRadioButton = new JRadioButton("Average");
		absRadioButton = new JRadioButton("Norm 2", true);

		maxRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				maxRadioButton.setSelected(true);
				minRadioButton.setSelected(!maxRadioButton.isSelected());
				avgRadioButton.setSelected(!maxRadioButton.isSelected());
				absRadioButton.setSelected(!maxRadioButton.isSelected());
			}
		});

		minRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				minRadioButton.setSelected(true);
				maxRadioButton.setSelected(!minRadioButton.isSelected());
				avgRadioButton.setSelected(!minRadioButton.isSelected());
				absRadioButton.setSelected(!minRadioButton.isSelected());
			}
		});
		
		avgRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				avgRadioButton.setSelected(true);
				maxRadioButton.setSelected(!avgRadioButton.isSelected());
				minRadioButton.setSelected(!avgRadioButton.isSelected());
				absRadioButton.setSelected(!avgRadioButton.isSelected());
			}
		});

		absRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				absRadioButton.setSelected(true);
				maxRadioButton.setSelected(!absRadioButton.isSelected());
				minRadioButton.setSelected(!absRadioButton.isSelected());
				avgRadioButton.setSelected(!absRadioButton.isSelected());
			}
		});		
	}

	private void initTextFields() {
		coordX1 = new JTextField("0");
		coordY1 = new JTextField("0");
		coordX2 = new JTextField("0");
		coordY2 = new JTextField("0");
		rectangle = new DraggableComponent();
		rectWidth = new JTextField("100");
		rectHeight = new JTextField("100");
		maskSize = new JTextField("5");
		value1 = new JTextField("Value1");
		value2 = new JTextField("Value2");
				
		ActionListener act = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	int w = Integer.parseInt(rectWidth.getText());
		    	int h = Integer.parseInt(rectHeight.getText());
		    	rectangle.setDimensions(w, h);
		     }
		};
		rectWidth.addActionListener(act);
		rectHeight.addActionListener(act);
		coordX1.setBounds(200, 550, 30, 30);
		coordX2.setBounds(200, 600, 30, 30);
		coordY1.setBounds(250, 550, 30, 30);
		coordY2.setBounds(250, 600, 30, 30);
		rectWidth.setBounds(300, 600, 30, 30);
		rectHeight.setBounds(350, 600, 30, 30);
		maskSize.setBounds(400, 600, 30, 30);
		value1.setBounds(450, 600, 30, 30);
		value2.setBounds(500, 600, 30, 30);
		
		panel.add(maskSize);
		panel.add(rectWidth);
		panel.add(rectHeight);
		panel.add(rectangle);
		panel.add(coordX1);
		panel.add(coordY1);
		panel.add(coordX2);
		panel.add(coordY2);
		panel.add(value1);
		panel.add(value2);
		displayTextFields(false);
		value1.setVisible(false);
		value2.setVisible(false);
	}
	
	private void setCoords(int x1, int y1, int x2, int y2) {
		coordX1.setText(String.valueOf(x1));
		coordX2.setText(String.valueOf(x2));
		coordY1.setText(String.valueOf(y1));
		coordY2.setText(String.valueOf(y2));
	}

	private void displayTextFields(boolean b) {
		coordX1.setVisible(b);
		coordY1.setVisible(b);
		coordX2.setVisible(b);
		coordY2.setVisible(b);
		rectangle.setVisible(b);
		rectWidth.setVisible(b);
		rectHeight.setVisible(b);
		maskSize.setVisible(b);
		value1.setVisible(false);
		value2.setVisible(false);
	}

	private void initSliders() {
		parameterSlider1 = new JSlider();
		parameterField1 = new JTextArea();
		parameterField1.setBounds(200, 600, 100, 100);
		parameterField1.setVisible(false);
		panel.add(parameterField1);
		parameterSlider2 = new JSlider();
		parameterField2 = new JTextArea();
		parameterField2.setBounds(500, 600, 100, 100);
		parameterField2.setVisible(false);
		panel.add(parameterField2);
		doItButton = new JButton("Do it!");
		doItButton.setBounds(950, 550, 100, 50);
		doItButton.setVisible(true);
		doItButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.doOperation();
			}
		});
		panel.add(doItButton);
	}

	private void doOperation() {
		if (currOperation != null) {
			currOperation.performOperation(currentImageLabel, secondImageLabel,
					resultImageLabel, getParams());
		}
	}

	private ImageOperationParameters getParams() {
		ImageOperationParameters params = new ImageOperationParameters();
		if (parameterSlider1.isVisible()) {
			params.value = (double) parameterSlider1.getValue() / 200;
		}
		if (parameterSlider2.isVisible()) {
			params.value2 = (double) parameterSlider2.getValue() / 200;
		}
		if (coordX1.isVisible()) {
			params.x1 = Integer.valueOf(coordX1.getText());
		}
		if (coordX2.isVisible()) {
			params.x2 = Integer.valueOf(coordX2.getText());
		}
		if (coordY1.isVisible()) {
			params.y1 = Integer.valueOf(coordY1.getText());
		}
		if (coordY2.isVisible()) {
			params.y2 = Integer.valueOf(coordY2.getText());
		}
		if (maskSize.isVisible()) {
			params.maskSize = Integer.valueOf(maskSize.getText());
		}
		if (value1.isVisible()) {
			params.value = Double.valueOf(value1.getText());
		}
		if (value2.isVisible()) {
			params.value2 = Double.valueOf(value2.getText());
		}
		if(leclercRadioButton.isSelected()) {
			params.bd = new LeclercEdgeDetector((int)params.value2);	//Without explicit cast everything goes BADDD
		} else if(lorentzRadioButton.isSelected()) {
			params.bd = new LorentzEdgeDetector((int)params.value2);	//Without explicit cast everything goes BADDDD
		}
		if(maxRadioButton.isSelected()) {
			params.st = SynthesizationType.MAX;
		} else if(minRadioButton.isSelected()) {
			params.st = SynthesizationType.MIN;
		} else if(avgRadioButton.isSelected()) {
			params.st = SynthesizationType.AVG;
		} else if(absRadioButton.isSelected()) {
			params.st = SynthesizationType.ABS;
		}
		
		
		return params;
	}

	private void initMenu() {
		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		JMenuItem chooseFileItem = new JMenuItem("Choose Image");
		chooseFileItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(MainFrame.this);
				setCurrentImage(fc.getSelectedFile());
			}
		});
		JMenuItem chooseFile2Item = new JMenuItem("Choose Second Image");
		chooseFile2Item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(MainFrame.this);
				setSecondImage(fc.getSelectedFile());
			}
		});
		JMenuItem save = new JMenuItem("Save result");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				File file = new File("out.bmp");
				try {
				  ImageSaver.saveImage(file, resultImageLabel.getImage());
				} catch (Exception e) {
					System.out.println("Error");
				}
			}
		});
		JMenuItem swap = new JMenuItem("Result->Input");
		swap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (resultImageLabel.hasImage()) {
					currentImageLabel.setImage(resultImageLabel.getImage());
					resultImageLabel.removeImage();
				}
			}
		});
		file.add(chooseFileItem);
		file.add(chooseFile2Item);
		file.add(save);
		file.add(swap);
		file.add(exitItem);

		JMenu noise = new JMenu("Noise");
		JMenuItem rayleighNoiseItem = new JMenuItem("Rayleigh");
		rayleighNoiseItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new RayleighNoiseOperation();
				MainFrame.this.setFirstSlider(0, 200, 0);
				MainFrame.this.parameterSlider2.setVisible(false);
				MainFrame.this.parameterField2.setVisible(false);
				displayTextFields(false);
			}
		});
		noise.add(rayleighNoiseItem);
		JMenuItem saltAndPepper = new JMenuItem("Salt And Pepper");
		saltAndPepper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new SaltPepperOperation();
				MainFrame.this.setFirstSlider(0, 200, 0);
				MainFrame.this.setSecondSlider(0, 200, 200);
				displayTextFields(false);
			}
		});
		noise.add(saltAndPepper);
		JMenuItem whiteNoiseItem = new JMenuItem("WhiteNoise");
		whiteNoiseItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new WhiteNoiseOperation();
				MainFrame.this.setFirstSlider(0, 400, 0);
				MainFrame.this.parameterSlider2.setVisible(false);
				MainFrame.this.parameterField2.setVisible(false);
				displayTextFields(false);
			}
		});
		noise.add(whiteNoiseItem);
		JMenuItem exponentialNoiseItem = new JMenuItem("Exponential Noise");
		exponentialNoiseItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new ExponentialNoiseOperation();
				MainFrame.this.setFirstSlider(0, 400, 0);
				MainFrame.this.parameterSlider2.setVisible(false);
				MainFrame.this.parameterField2.setVisible(false);
				displayTextFields(false);
			}
		});
		noise.add(exponentialNoiseItem);
		JMenu arithmeticMenu = new JMenu("Arithmetic");
		JMenuItem add = new JMenuItem("Add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new AddImageOperation();
				MainFrame.this.hideSliders();
				if (!secondImageLabel.hasImage() && resultImageLabel.hasImage()) {
					secondImageLabel.setImage(resultImageLabel.getImage());
				}
				displayTextFields(false);
			}
		});
		JMenuItem subtract = new JMenuItem("subtract");
		subtract.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new SubtractImageOperation();
				MainFrame.this.hideSliders();
				if (!secondImageLabel.hasImage() && resultImageLabel.hasImage()) {
					secondImageLabel.setImage(resultImageLabel.getImage());
				}
				displayTextFields(false);
			}
		});
		JMenuItem multiply = new JMenuItem("multiply");
		multiply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new MultiplyImageOperation();
				MainFrame.this.hideSliders();
				if (!secondImageLabel.hasImage() && resultImageLabel.hasImage()) {
					secondImageLabel.setImage(resultImageLabel.getImage());
				}
				displayTextFields(false);
			}
		});
		JMenuItem multiplyScalar = new JMenuItem("multiply scalar");
		multiplyScalar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new MultiplyScalarOperation();
				MainFrame.this.setFirstSlider(0, 400, 0);
				MainFrame.this.parameterSlider2.setVisible(false);
				MainFrame.this.parameterField2.setVisible(false);
				displayTextFields(false);
			}
		});
		JMenuItem multiplyBigScalar = new JMenuItem("multiply big scalar");
		multiplyBigScalar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new MultiplyScalarOperation();
				MainFrame.this.setFirstSlider(0, 40000, 0);
				MainFrame.this.parameterSlider2.setVisible(false);
				MainFrame.this.parameterField2.setVisible(false);
				displayTextFields(false);
			}
		});
		JMenuItem treshold = new JMenuItem("threshold");
		treshold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new TreshHoldOperation();
				MainFrame.this.setFirstSlider(0, 256 * 200, 0);
				MainFrame.this.parameterSlider2.setVisible(false);
				MainFrame.this.parameterField2.setVisible(false);
				displayTextFields(false);
			}
		});
		JMenuItem tresholdBinary = new JMenuItem("threshold binary");
		tresholdBinary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new ThresholdBinaryOperation();
				MainFrame.this.setFirstSlider(0, 256 * 200, 0);
				MainFrame.this.parameterSlider2.setVisible(false);
				MainFrame.this.parameterField2.setVisible(false);
				displayTextFields(false);
			}
		});
		JMenuItem negative = new JMenuItem("Negative");
		negative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new NegativeImageOperation();
				MainFrame.this.hideSliders();
				displayTextFields(false);
			}
		});
		arithmeticMenu.add(add);
		arithmeticMenu.add(multiply);
		arithmeticMenu.add(subtract);
		arithmeticMenu.add(negative);
		arithmeticMenu.add(multiplyScalar);
		arithmeticMenu.add(multiplyBigScalar);
		arithmeticMenu.add(treshold);
		arithmeticMenu.add(tresholdBinary);
		JMenu extra = new JMenu("Extra");
		JMenuItem histogram = new JMenuItem("Histogram");
		histogram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new HistogramOperation();
				MainFrame.this.hideSliders();
				displayTextFields(false);
			}
		});
		JMenuItem equalize = new JMenuItem("Equalize");
		equalize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new EqualizeImageOperation();
				MainFrame.this.hideSliders();
				displayTextFields(false);
			}
		});
		JMenuItem dynamicCompr = new JMenuItem("Dynamic Compression");
		dynamicCompr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new DynamicRangeCompressionOperation();
				MainFrame.this.hideSliders();
				displayTextFields(false);
			}
		});
		JMenu masks = new JMenu("Masks");
		JMenuItem highPassMask = new JMenuItem("HighPass");
		highPassMask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new HighPassOperation();
				MainFrame.this.hideSliders();
				displayTextFields(true);
			}
		});
		masks.add(highPassMask);
		JMenuItem lowPassMask = new JMenuItem("LowPass");
		lowPassMask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new LowPassOperation();
				MainFrame.this.hideSliders();
				displayTextFields(true);
			}
		});
		masks.add(lowPassMask);
		JMenuItem medianPassMask = new JMenuItem("MedianPass");
		medianPassMask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new MedianPassOperation();
				MainFrame.this.hideSliders();
				displayTextFields(true);
			}
		});
		masks.add(medianPassMask);
		JMenuItem contrast = new JMenuItem("Increase contrast");
		contrast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new ContrastOperation();
				MainFrame.this.hideSliders();
				displayTextFields(true);
				rectangle.setVisible(false);
				maskSize.setVisible(false);
			}
		});
		
		JMenuItem gaussianFilterMask = new JMenuItem("Gaussian Filter");
		gaussianFilterMask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new GaussianFilterOperation();
				MainFrame.this.hideSliders();
				displayTextFields(true);
				value2.setVisible(true);
			}
		});
		masks.add(gaussianFilterMask);
		JMenuItem maskA = new JMenuItem("Mask A");
		maskA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new MaskA();
				MainFrame.this.hideSliders();
				displayTextFields(true);
			}
		});
		masks.add(maskA);
		JMenuItem maskA4 = new JMenuItem("Mask A: 4 dirs");
		maskA4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new FourMaskA();
				MainFrame.this.hideSliders();
				displayTextFields(false);
				rectangle.setVisible(false);
			}
		});
		masks.add(maskA4);
		JMenuItem maskKirsh = new JMenuItem("Mask Kirsh");
		maskKirsh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new MaskKirsh();
				MainFrame.this.hideSliders();
				displayTextFields(true);
			}
		});
		masks.add(maskKirsh);
		JMenuItem maskKirsh4 = new JMenuItem("Mask Kirsh: 4 dirs");
		maskKirsh4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new FourMaskKirsh();
				MainFrame.this.hideSliders();
				displayTextFields(false);
				rectangle.setVisible(false);
			}
		});
		masks.add(maskKirsh4);
		JMenuItem maskC = new JMenuItem("Mask C");
		maskC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new MaskC();
				MainFrame.this.hideSliders();
				displayTextFields(true);
			}
		});
		masks.add(maskC);
		JMenuItem maskC4 = new JMenuItem("Mask C: 4 dirs");
		maskC4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new FourMaskC();
				MainFrame.this.hideSliders();
				displayTextFields(false);
				rectangle.setVisible(false);
			}
		});
		masks.add(maskC4);
		JMenuItem maskD = new JMenuItem("Mask D");
		maskD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new MaskD();
				MainFrame.this.hideSliders();
				displayTextFields(true);
			}
		});
		masks.add(maskD);
		JMenuItem maskD4 = new JMenuItem("Mask D: 4 dirs");
		maskD4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new FourMaskC();
				MainFrame.this.hideSliders();
				displayTextFields(false);
				rectangle.setVisible(false);
			}
		});
		masks.add(maskD4);
		
		
		JMenuItem anisotropicDiffusion = new JMenuItem("Anisotropic Diffusion");
		anisotropicDiffusion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new AnisotropicDiffusionOperation();
				MainFrame.this.hideSliders();
				displayTextFields(false);
				rectangle.setVisible(false);
				maskSize.setVisible(false);
				value1.setVisible(true);
				value2.setVisible(true);
				EdgeDetectorDialog edgeDetectorDialog = new EdgeDetectorDialog(leclercRadioButton, lorentzRadioButton);
				edgeDetectorDialog.setVisible(true);
			}
		});
		
		JMenuItem isotropicDiffusion = new JMenuItem("Isotropic Diffusion");
		isotropicDiffusion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new IsotropicDiffusionOperation();
				MainFrame.this.hideSliders();
				displayTextFields(false);
				rectangle.setVisible(false);
				value1.setVisible(true);
			}
		});
		
		
		JMenu edgeDetectors = new JMenu("Edge Detectors");
		
		JMenuItem robertsEdgeDetector = new JMenuItem("Roberts Edge Detector");
		robertsEdgeDetector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new RobertsEdgeDetectorOperation();
				MainFrame.this.hideSliders();
				displayTextFields(false);
				rectangle.setVisible(false);
				maskSize.setVisible(true);
				SynthetizationDialog synthetizationDialog = new SynthetizationDialog(maxRadioButton, minRadioButton, avgRadioButton, absRadioButton);
				synthetizationDialog.setVisible(true);
			}
		});
		
		JMenuItem prewittEdgeDetector = new JMenuItem("Prewitt Edge Detector");
		prewittEdgeDetector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new PrewittEdgeDetectorOperation();
				MainFrame.this.hideSliders();
				displayTextFields(false);
				rectangle.setVisible(false);
				maskSize.setVisible(true);
				SynthetizationDialog synthetizationDialog = new SynthetizationDialog(maxRadioButton, minRadioButton, avgRadioButton, absRadioButton);
				synthetizationDialog.setVisible(true);
			}
		});
		
		JMenuItem sobelEdgeDetector = new JMenuItem("Sobel Edge Detector");
		sobelEdgeDetector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new SobelEdgeDetectorOperation();
				MainFrame.this.hideSliders();
				displayTextFields(false);
				rectangle.setVisible(false);
				maskSize.setVisible(true);
				SynthetizationDialog synthetizationDialog = new SynthetizationDialog(maxRadioButton, minRadioButton, avgRadioButton, absRadioButton);
				synthetizationDialog.setVisible(true);
			}
		});
		
		edgeDetectors.add(robertsEdgeDetector);
		edgeDetectors.add(prewittEdgeDetector);
		edgeDetectors.add(sobelEdgeDetector);
				
		JMenuItem globalUmbralization = new JMenuItem("Global Umbralization");
		globalUmbralization.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new GlobalUmbralizationOperation();
				MainFrame.this.hideSliders();
				displayTextFields(false);
				rectangle.setVisible(false);
			}
		});
		
		JMenuItem otsuUmbralization = new JMenuItem("Otsu Umbralization");
		otsuUmbralization.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new OtsuUmbralizationOperation();
				MainFrame.this.hideSliders();
				displayTextFields(false);
				rectangle.setVisible(false);
			}
		});
		JMenuItem binaryGlobalUmbralization = new JMenuItem("Binary Global Umbralization");
		binaryGlobalUmbralization.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new BinaryGlobalUmbralizationOperation();
				MainFrame.this.hideSliders();
				displayTextFields(false);
				rectangle.setVisible(false);
			}
		});
		
		JMenuItem binaryOtsuUmbralization = new JMenuItem("Binary Otsu Umbralization");
		binaryOtsuUmbralization.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainFrame.this.currOperation = new BinaryOtsuUmbralizationOperation();
				MainFrame.this.hideSliders();
				displayTextFields(false);
				rectangle.setVisible(false);
			}
		});
		
		
		
		extra.add(histogram);
		extra.add(equalize);
		extra.add(dynamicCompr);
		extra.add(contrast);
		extra.add(isotropicDiffusion);
		extra.add(anisotropicDiffusion);
		extra.add(globalUmbralization);
		extra.add(otsuUmbralization);
		extra.add(binaryGlobalUmbralization);
		extra.add(binaryOtsuUmbralization);
		
		menubar.add(file);
		menubar.add(noise);
		menubar.add(arithmeticMenu);
		menubar.add(masks);
		menubar.add(edgeDetectors);
		menubar.add(extra);
		currentImageLabel = new ImageLabelContainer(this, this.panel);
		secondImageLabel = new ImageLabelContainer(this, this.panel);
		resultImageLabel = new ImageLabelContainer(this, this.panel);

		setJMenuBar(menubar);
		panel.repaint();
	}

	private void hideSliders() {
		parameterSlider2.setVisible(false);
		parameterSlider1.setVisible(false);
		parameterField1.setVisible(false);
		parameterField2.setVisible(false);
	}

	private void setCurrentImage(File file) {
		try {
			currentImageLabel.setImage(ImageLoader.loadImage(file));
		} catch (IOException e) {
			System.out.println("OE");
		} catch (ImageReadException e) {
			System.out.println("OE2");
		}
	}

	private void setSecondImage(File file) {
		try {
			secondImageLabel.setImage(ImageLoader.loadImage(file));
		} catch (IOException e) {
			System.out.println("OE");
		} catch (ImageReadException e) {
			System.out.println("OE2");
		}
	}

	private void setFirstSlider(int minValue, int maxValue, int initValue) {
		panel.remove(parameterSlider1);
		secondImageLabel.removeImage();
		parameterSlider1 = new JSlider(JSlider.HORIZONTAL, minValue, maxValue,
				initValue);
		parameterField1.setVisible(true);
		parameterField1.setText(String.format("%.4g", (double) initValue / 200));
		parameterSlider1.setBounds(200, 550, 300, 50);
		parameterSlider1.setVisible(true);
		parameterSlider1.setLayout(null);
		parameterSlider1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				parameterField1.setText(String.format("%.4g", (double) MainFrame.this.parameterSlider1.getValue() / 200));
				parameterField1.repaint();
				MainFrame.this.doOperation();
			}

		});
		this.panel.add(parameterSlider1);
	}

	private void setSecondSlider(int minValue, int maxValue, int initValue) {
		parameterSlider2 = new JSlider(JSlider.HORIZONTAL, minValue, maxValue,
				initValue);
		parameterSlider2.setBounds(500, 550, 300, 50);
		parameterSlider2.setVisible(true);
		parameterField2.setVisible(true);
		parameterSlider2.setLayout(null);
		parameterSlider2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				parameterField2.setText(String
						.format("%.4g",
								(double) MainFrame.this.parameterSlider2
										.getValue() / 200));
				parameterField2.repaint();
				MainFrame.this.doOperation();
			}

		});
		this.panel.add(parameterSlider2);
	}

	public void adjustImages() {
		int currentX = 0;
		currentX += adjustImageLabel(currentImageLabel, currentX);
		currentX += adjustImageLabel(secondImageLabel, currentX);
		adjustImageLabel(resultImageLabel, currentX);
		panel.repaint();
	}

	private int adjustImageLabel(ImageLabelContainer label, int x) {
		int ret = 0;
		if (label.hasImage()) {
			label.setBounds(x, 0, label.getWidth(), label.getHeight());
			ret = label.getWidth();
			label.repaint();
		}
		return ret + 100;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame mf = new MainFrame();
				mf.setVisible(true);
			}
		});
	}

	public class DraggableComponent extends JComponent {

		private static final long serialVersionUID = 8974841922334002891L;
		private volatile int screenX = 0;
		private volatile int screenY = 0;
		private volatile int myX = 0;
		private volatile int myY = 0;

		public DraggableComponent() {
			setBorder(new LineBorder(Color.BLUE, 3));
			setBounds(0, 0, 100, 100);
			setOpaque(false);

			addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
					screenX = e.getXOnScreen();
					screenY = e.getYOnScreen();

					myX = getX();
					myY = getY();
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

			});
			addMouseMotionListener(new MouseMotionListener() {

				@Override
				public void mouseDragged(MouseEvent e) {
					int newX = e.getXOnScreen() - screenX + myX;
					int newY = e.getYOnScreen() - screenY + myY;
					MainFrame.this.setCoords(newX, newY, newX + getWidth(), newY + getHeight());
					setLocation(newX, newY);
					MainFrame.this.doOperation();
				}

				@Override
				public void mouseMoved(MouseEvent e) {
				}

			});
		}
		
		public void setDimensions(int x, int y) {
			setBounds(myX, myY, x, y);
		}

	}
}
