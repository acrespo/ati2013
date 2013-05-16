package sturla.atitp.app;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.sanselan.ImageReadException;

import sturla.atitp.imageprocessing.Image;
import sturla.atitp.imageprocessing.Image.ImageType;
import sturla.atitp.imageprocessing.RGBImage;

public class ImageLoader {
	
	
	public static Image loadImage(File arch) throws ImageReadException, IOException{
		
		BufferedImage img = ImageIO.read(arch);
		return new RGBImage(img);
		
//		BufferedImage bi = Sanselan.getBufferedImage(arch);
//		ImageInfo info = Sanselan.getImageInfo(arch);
//		Image.ImageFormat format;
//		
//		if( info.getFormat() == ImageFormat.IMAGE_FORMAT_BMP ){
//			format = Image.ImageFormat.BMP;
//		} else if( info.getFormat() == ImageFormat.IMAGE_FORMAT_PGM ){
//			format = Image.ImageFormat.PGM;
//		} else if( info.getFormat() == ImageFormat.IMAGE_FORMAT_PPM ){
//			format = Image.ImageFormat.PPM;
//		} else if( info.getFormat() == ImageFormat.IMAGE_FORMAT_UNKNOWN ){
//			//TODO: fix this
//			format = Image.ImageFormat.RAW;
//			throw new IllegalStateException("Unsupported image format");
//		} else {
//			throw new IllegalStateException("Unsupported image format");
//		}
//		
//		if(bi.getType() == BufferedImage.TYPE_INT_RGB){
//			return new RGBImage(bi, format, ImageType.RGB);
//		} else if(bi.getType() == BufferedImage.TYPE_BYTE_GRAY){			
//			return new RGBImage(bi, format, ImageType.GRAYSCALE);
//		} else {
//			throw new IllegalStateException("Image wasn't RGB nor Grayscale");
//		}
		
	}

	public static Image loadRaw(File file, int width, int height) throws IOException{

        BufferedImage ret;
        byte[] data = getBytesFromFile(file);
        ret = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = ret.getRaster();
        int k = 0;
        for (int j = 0; j < height; j++) {
        	for (int i = 0; i < width; i++) {
                        raster.setSample(i, j, 0, data[k]);
                        k=k+1;
                }
        }
        Image image = new RGBImage(height, width, Image.ImageFormat.RAW, ImageType.GRAYSCALE);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
            	image.setRGBPixel(i, j, ret.getRGB(i, j));
            }
        }
        return image;

	}

	public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        long length = file.length();
        byte[] bytes = new byte[(int)length];
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
        is.close();
        if (offset < bytes.length) {
        	throw new IOException();
        }
        return bytes;
    }

}
