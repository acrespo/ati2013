package sturla.atitp.app;
import java.awt.Color;
import java.awt.image.BufferedImage;

import org.apache.sanselan.ImageFormat;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import sturla.atitp.imageprocessing.Image;
import sturla.atitp.imageprocessing.Image.ImageType;


public class Utils {

	public static int getRGBFromColor(Color c){
		return c.getRGB();
	}

	public static Color getColorFromRGB(int rgb){
		return new Color(rgb);
	}

	public static Color getColorFromColors(float r, float g, float b){
		return new Color(r, g, b);
	}

	public static int getRedFromRGB(int rgb){
		return new Color(rgb).getRed();
	}

	public static int getGreenFromRGB(int rgb){
		return new Color(rgb).getGreen();
	}

	public static int getBlueFromRGB(int rgb){
		return new Color(rgb).getBlue();
	}

	public static int toBufferedImageType(ImageType type){
		if( type == ImageType.GRAYSCALE ){
			return BufferedImage.TYPE_BYTE_GRAY;
		}
		if( type == ImageType.RGB ){
			return BufferedImage.TYPE_INT_RGB;
		}
		throw new IllegalArgumentException();
	}

	public static ImageFormat toSanselanImageFormat(Image.ImageFormat fmt){
		if( fmt == Image.ImageFormat.BMP ){
			return ImageFormat.IMAGE_FORMAT_BMP;
		}
		if( fmt == Image.ImageFormat.PGM ){
			return ImageFormat.IMAGE_FORMAT_PGM;
		}
		if( fmt == Image.ImageFormat.PPM ){
			return ImageFormat.IMAGE_FORMAT_PPM;
		}
		if( fmt == Image.ImageFormat.RAW ){
			return ImageFormat.IMAGE_FORMAT_UNKNOWN;
		}
		throw new IllegalArgumentException();
	}

	public static void populateEmptyBufferedImage(BufferedImage emptyImage, Image image){
		int height = image.getHeight();
		int width = image.getWidth();

		for( int x = 0 ; x < width ; x++ ){
			for( int y = 0 ; y < height ; y++ ){
				int rgbPixel = image.getRGBPixel(x, y);
				emptyImage.setRGB(x, y, rgbPixel);
			}
		}
	}

	public static BufferedImage generateHistogram(Image image){

		HistogramDataset dataset = new HistogramDataset();
		dataset.setType(HistogramType.FREQUENCY);

		double[] histData = image.getHistogramPixels();

		dataset.addSeries("Histogram", histData, histData.length);

		PlotOrientation orientation = PlotOrientation.VERTICAL;
		boolean show = false;
		boolean tooltips = false;
		boolean urls = false;

		JFreeChart chart = ChartFactory.createHistogram("Histogram", "Gray scale", "Pixels amount", dataset, orientation, show, tooltips, urls);
		return chart.createBufferedImage(400, 200);
	}

	public static void assertCompatibility(Image a, Image b){
		if( a.getHeight() != b.getHeight() || a.getWidth() != b.getWidth() 
				|| a.getImageFormat() != b.getImageFormat() 
				|| a.getType() != b.getType() ){
			throw new IllegalArgumentException("Images must be of the same size");
		}
	}

}