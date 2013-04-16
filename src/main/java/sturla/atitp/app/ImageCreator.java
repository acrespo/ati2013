package sturla.atitp.app;

import java.awt.Color;

import sturla.atitp.imageprocessing.Image;
import sturla.atitp.imageprocessing.Image.ImageFormat;
import sturla.atitp.imageprocessing.Image.ImageType;
import sturla.atitp.imageprocessing.RGBImage;


public class ImageCreator {

	public static Image createDegrade(boolean isColor, int height, int width, int color1, int color2) {
	
		Image degrade = null;
		
		if(isColor){
			degrade = new RGBImage(height, width, ImageFormat.BMP, ImageType.RGB);
		} else{
			degrade = new RGBImage(height, width, ImageFormat.BMP, ImageType.GRAYSCALE);
		}
			
		Color c1 = new Color(color1);
		Color cAux = new Color(color1);
		Color c2 = new Color(color2);
		
		float redFactor = (float)(c2.getRed() - c1.getRed()) / height;
		float greenFactor = (float)(c2.getGreen() - c1.getGreen()) / height;
		float blueFactor = (float)(c2.getBlue() - c1.getBlue()) / height;
		
		float red = 0;
		float green = 0;
		float blue = 0;
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width ; x++){
					degrade.setRGBPixel(x, y, c1.getRGB());
			}					
			red = red + redFactor;
			green = green + greenFactor;
			blue = blue + blueFactor;
			
			c1 = new Color( cAux.getRGB() + (int)((int)red * 0x010000) + (int)((int)green * 0x000100) + (int)((int)blue * 0x000001)  );
		}
		
		return degrade;
	}
	
	public static Image createModule1Image(int height, int width){
		Image module1 = new RGBImage(height, width, ImageFormat.BMP, ImageType.GRAYSCALE);
		
		for(int y = 0 ; y < height ; y++) {
			for(int x = 0; x < width ; x++) {
				module1.setRGBPixel(x, y, new Color(1,1,1).getRGB());
			}
		}
		
		return module1;
	}
	
	public static Image createBinaryImage(int height, int width) {
		Image binaryImage = new RGBImage(height, width, ImageFormat.BMP, ImageType.GRAYSCALE);
		
		Color blackColor = Color.BLACK;
		Color whiteColor = Color.WHITE;
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width ; x++) {
				boolean fitsInSquareByWidth = (x > width / 4 && x < 3 * width / 4);
				boolean fitsInSquareByHeight = (y > height /  4 && y < 3 * height / 4);
				boolean fitsInSquare = (fitsInSquareByWidth && fitsInSquareByHeight);
				Color colorToApply = fitsInSquare? whiteColor : blackColor;
				binaryImage.setRGBPixel(x, y, colorToApply.getRGB());
			}					
		}
		
		return binaryImage;
	}
	
	public static Image A(int height, int width) {
		Image binaryImage = new RGBImage(height, width, ImageFormat.BMP, ImageType.GRAYSCALE);
		
		Color blackColor = Color.BLACK;
		Color whiteColor = Color.WHITE;
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width ; x++) {
				boolean fitsInSquareByWidth = (x < (width / 2) + 10 && x > (width / 2) - 10 );
				boolean fitsInSquareByHeight = (y < (height / 2) + 10 && y > (height / 2) - 10 );
				boolean fitsInSquare = (fitsInSquareByWidth && fitsInSquareByHeight);
				Color colorToApply = fitsInSquare? whiteColor : blackColor;
				binaryImage.setRGBPixel(x, y, colorToApply.getRGB());
			}					
		}
		
		return binaryImage;
	}

	public static Image B(int height, int width) {
		Image binaryImage = new RGBImage(height, width, ImageFormat.BMP, ImageType.GRAYSCALE);
		
		Color blackColor = Color.BLACK;
		Color whiteColor = Color.WHITE;
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width ; x++) {
				boolean fitsInSquareByWidth = (x < (width / 2) + 7 && x > (width / 2) - 7 );
				boolean fitsInSquareByHeight = (y < (height / 2) + 80 && y > (height / 2) - 80 );
				boolean fitsInSquare = (fitsInSquareByWidth && fitsInSquareByHeight);
				Color colorToApply = fitsInSquare? whiteColor : blackColor;
				binaryImage.setRGBPixel(x, y, colorToApply.getRGB());
			}					
		}
		
		return binaryImage;
	}

	public static Image C(int height, int width) {
		Image binaryImage = new RGBImage(height, width, ImageFormat.BMP, ImageType.GRAYSCALE);
		
		Color blackColor = Color.BLACK;
		Color whiteColor = Color.WHITE;
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width ; x++) {
				boolean fitsInSquareByWidth = (x < (width / 2) + 60 && x > (width / 2) - 60 );
				boolean fitsInSquareByHeight = (y < (height / 2) + 80 && y > (height / 2) - 80 );
				boolean fitsInSquare = (fitsInSquareByWidth && fitsInSquareByHeight);
				Color colorToApply = fitsInSquare? whiteColor : blackColor;
				binaryImage.setRGBPixel(x, y, colorToApply.getRGB());
			}					
		}
		
		return binaryImage;
	}

	public static Image D(int height, int width) {
		Image binaryImage = new RGBImage(height, width, ImageFormat.BMP, ImageType.GRAYSCALE);
		
		Color blackColor = Color.BLACK;
		Color whiteColor = Color.WHITE;
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width ; x++) {
				boolean fitsInSquareByWidth = (x < (width / 2) + 40 && x > (width / 2) - 40 );
				boolean fitsInSquareByHeight = (y < (height / 2) + 15 && y > (height / 2) - 15 );
				boolean fitsInSquare = (fitsInSquareByWidth && fitsInSquareByHeight);
				Color colorToApply = fitsInSquare? whiteColor : blackColor;
				binaryImage.setRGBPixel(x, y, colorToApply.getRGB());
			}					
		}
		
		return binaryImage;
	}
	
	public static Image E(int height, int width) {
		Image binaryImage = new RGBImage(height, width, ImageFormat.BMP, ImageType.GRAYSCALE);
		
		Color blackColor = Color.BLACK;
		Color whiteColor = Color.WHITE;
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width ; x++) {
				boolean fitsInSquareByWidth = ((x + y < width + 40) && (x + y > width - 40) );
				boolean fitsInSquareByHeight = (Math.abs(x - y) < 150 );
				boolean fitsInSquare = (fitsInSquareByWidth && fitsInSquareByHeight);
				Color colorToApply = fitsInSquare? whiteColor : blackColor;
				binaryImage.setRGBPixel(x, y, colorToApply.getRGB());
			}					
		}
		
		return binaryImage;
	}

	public static Image F(int height, int width) {
		Image binaryImage = new RGBImage(height, width, ImageFormat.BMP, ImageType.GRAYSCALE);
		
		Color blackColor = Color.BLACK;
		Color whiteColor = Color.WHITE;
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width ; x++) {
				boolean fitsInSquareByWidth = ((x + y < height + 150) && (x + y > height - 150) );
				boolean fitsInSquareByHeight = (Math.abs(x - y) < 40 );
				boolean fitsInSquare = (fitsInSquareByWidth && fitsInSquareByHeight);
				Color colorToApply = fitsInSquare? whiteColor : blackColor;
				binaryImage.setRGBPixel(x, y, colorToApply.getRGB());
			}					
		}
		
		return binaryImage;
	}
	
	public static Image G(int height, int width) {
		Image binaryImage = new RGBImage(height, width, ImageFormat.BMP, ImageType.GRAYSCALE);
		
		Color blackColor = Color.BLACK;
		Color whiteColor = Color.WHITE;
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width ; x++) {
				boolean fitsInSquare = (y % 50 == 0);
				Color colorToApply = fitsInSquare? whiteColor : blackColor;
				binaryImage.setRGBPixel(x, y, colorToApply.getRGB());
			}					
		}
		
		return binaryImage;
	}
	
	public static Image H(int height, int width) {
		Image binaryImage = new RGBImage(height, width, ImageFormat.BMP, ImageType.GRAYSCALE);
		
		Color blackColor = Color.BLACK;
		Color whiteColor = Color.WHITE;
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width ; x++) {
				boolean fitsInSquare = (y % 25 == 0);
				Color colorToApply = fitsInSquare? whiteColor : blackColor;
				binaryImage.setRGBPixel(x, y, colorToApply.getRGB());
			}					
		}
		
		return binaryImage;
	}
	
	public static Image I(int height, int width) {
		Image binaryImage = new RGBImage(height, width, ImageFormat.BMP, ImageType.GRAYSCALE);
		
		Color blackColor = Color.BLACK;
		Color whiteColor = Color.WHITE;
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width ; x++) {
				boolean fitsInSquareByWidth = (((x + y) % 60 == 0)/* && (x + y > width - 40)*/ );
				boolean fitsInSquareByHeight = true;
				boolean fitsInSquare = (fitsInSquareByWidth && fitsInSquareByHeight);
				Color colorToApply = fitsInSquare? whiteColor : blackColor;
				binaryImage.setRGBPixel(x, y, colorToApply.getRGB());
			}					
		}
		
		return binaryImage;
	}
	
	public static Image circle(int height, int width) {
		Image binaryImage = new RGBImage(height, width, ImageFormat.BMP, ImageType.GRAYSCALE);
		
		Color blackColor = Color.BLACK;
		Color whiteColor = Color.WHITE;
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width ; x++) {
				double aTerm = Math.pow(x - width/2, 2);
				double bTerm = Math.pow(y - height/2, 2);
				double rTerm = Math.pow(30, 2);
				boolean fitsInCircle = (aTerm + bTerm) <= rTerm;
				Color colorToApply = fitsInCircle? whiteColor : blackColor;
				binaryImage.setRGBPixel(x, y, colorToApply.getRGB());
			}					
		}
		
		return binaryImage;
	}

}
