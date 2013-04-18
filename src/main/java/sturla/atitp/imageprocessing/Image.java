package sturla.atitp.imageprocessing;

import java.awt.Point;
import java.awt.image.BufferedImage;

import sturla.atitp.imageprocessing.edgeDetector.EdgeDetector;
import sturla.atitp.imageprocessing.synthesization.SynthesizationType;

public interface Image {
	
	public static enum Channel {
		RED, GREEN, BLUE
	}

	public static enum ImageType {
		RGB, GRAYSCALE
	}

	public static enum ImageFormat {
		BMP, PGM, PPM, RAW
	}
	
	public static final int GRAY_LEVEL_AMOUNT = 256;

	public void setPixel(int x, int y, Channel channel, double color);

	public void setRGBPixel(int x, int y, int rgb);

	public double getPixelFromChannel(int x, int y, Channel channel);

	public int getRGBPixel(int x, int y);

	public int getHeight();

	public int getWidth();

	public ImageType getType();

	public ImageFormat getImageFormat();

	public Image cropImage(int x1, int y1, int x2, int y2);	

	public Image add(Image img);

	public Image substract(Image img);

	public Image multiply(Image img);

	public void multiply(double scalar);

	public void dynamicRangeCompression();

	public void negative();

	public void threshold(double thresholdLimit);

	public double[] getHistogramPixels();

	public void incrementContrast(double x1, double x2, double y1, double y2);

	public void equalizeGrays();

	public double getGraylevelFromPixel(int x, int y);

	public void whiteNoise(double stdDev);

	public void rayleighNoise(double mean);

	public void exponentialNoise(double mean);

	public void saltAndPepperNoise(double minLimit, double maxLimit);

	public void applyMask(Mask mask, int w, int h, int endW, int endH);

	public void applyMedianMask(Point maskSize, int w, int h, int endW, int endH);
	
	public Image copy();
	
	public BufferedImage toBufferedImage();
	
	public BufferedImage thresholdBinaryImage(double t);
	
	public void applyIsotropicDiffusion(int iterations);
	
	public void applyAnisotropicDiffusion(double lambda, int iterations, EdgeDetector bd);
	
	public void applyRobertsBorderDetection(SynthesizationType st);
	
	public void applyPrewittBorderDetection(SynthesizationType st);
	
	public void applySobelBorderDetection(SynthesizationType st);
	
	public void synthesize(SynthesizationType st, Image ... chnls);
	
	public void applyMaskABorderDetection(SynthesizationType st);
	
	public void applyMaskBKirshBorderDetection(SynthesizationType st);
	
	public void applyMaskCBorderDetection(SynthesizationType st);
	
	public void applyMaskDBorderDetection(SynthesizationType st);
	
	public void applyLaplaceMask(int w, int h, int endW, int endH);
	
	public void applyLaplaceVarianceMask(int varianceThreshold, int w, int h, int endW, int endH);
	
	public void applyLaplaceGaussianMask(int maskSize, double sigma, int w, int h, int endW, int endH);

	public void applyZeroCrossing(double threshold);
	
	public void globalThreshold();
	
	public void otsuThreshold();

}
