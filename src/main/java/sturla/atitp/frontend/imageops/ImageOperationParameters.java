package sturla.atitp.frontend.imageops;

import java.io.File;

import sturla.atitp.frontend.MainFrame;
import sturla.atitp.imageprocessing.edgeDetector.EdgeDetector;
import sturla.atitp.imageprocessing.synthesization.SynthesizationType;

public class ImageOperationParameters {
	
	public double value;
	public double value2;
	
	public int x1;
	public int x2;
	public int y1;
	public int y2;
	
	public int maskSize;
	public EdgeDetector bd;
	public SynthesizationType st;
	
	public File imageFile;
	
	public MainFrame mainFrame;
}
