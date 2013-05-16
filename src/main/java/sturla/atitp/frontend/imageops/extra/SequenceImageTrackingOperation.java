package sturla.atitp.frontend.imageops.extra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.Timer;

import sturla.atitp.app.ImageLoader;
import sturla.atitp.frontend.ImageLabelContainer;
import sturla.atitp.frontend.imageops.ImageOperation;
import sturla.atitp.frontend.imageops.ImageOperationParameters;
import sturla.atitp.imageprocessing.Image;
import sturla.atitp.imageprocessing.Point;

public class SequenceImageTrackingOperation extends ImageOperation {
	
	private Iterator<File> files;
	private List<Point> currentSurface;
	private ImageLabelContainer result;
	Timer timer;
	
	
	
	@Override
	public void performOperation(ImageLabelContainer op1,
			ImageLabelContainer op2, ImageLabelContainer pResult,
			ImageOperationParameters params) {
		
		this.result = pResult;
		ActionListener trackNewImageAction = new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent ae) {
	    		if (files.hasNext()) {
	    			try{
	    				Image img = ImageLoader.loadImage(files.next());
	    				currentSurface = img.tracking(currentSurface);
	    				result.setImage(img);
	    				timer.start();
	    			} catch (Exception e) {
	    				e.printStackTrace();
	    			}
	    		}
			}
		};
		
		currentSurface = new ArrayList<Point>();
		for (int i = params.x1; i <= params.x2; i++) {
			for (int j = params.y1; j <= params.y2; j++) {
				currentSurface.add(new Point(i, j));
			}
		}
		files = Arrays.asList(new File(params.imageFile.getParent()).listFiles()).iterator();
		timer = new Timer(params.maskSize, trackNewImageAction);
		timer.start();
	}

}
