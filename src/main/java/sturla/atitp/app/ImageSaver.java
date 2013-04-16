package sturla.atitp.app;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.sanselan.ImageFormat;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.Sanselan;

import sturla.atitp.imageprocessing.Image;

public class ImageSaver {
	
	public static void saveImage(File arch, Image image) throws ImageWriteException, IOException {
		
		String[] cadena = (arch.getName()).split("\\.");
		String extension = cadena[cadena.length-1];

		BufferedImage bi = null;
		ImageFormat format = null;
		
		if(!extension.equals("raw")){
			bi = new BufferedImage(image.getWidth(), image.getHeight(), 
					Utils.toBufferedImageType(image.getType()));
			format = Utils.toSanselanImageFormat(image.getImageFormat());
		} else {
			throw new UnsupportedOperationException("Still not supporting saving raw");
		}
		
		Utils.populateEmptyBufferedImage(bi, image);
		
		Sanselan.writeImage(bi, arch, format, null);
		
	}

//	private static void saveRaw(File file, SingleChannel image) {
//		
//			try {
//				
//		        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
//	
//		        Color color;
//			    for (int i = 0; i < image.getWidth() * image.getHeight(); i++) {
//			    	color = new Color(image.getPixel(i));			    	
//			    	bw.write((byte)color.getRed());					    	
//			    	bw.write((byte)color.getGreen());				    	
//			    	bw.write((byte)color.getBlue());			
//				}
//		        
//		        bw.close();
//	        
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		
//	}

}
