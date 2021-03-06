package sturla.atitp.frontend.imageops.extra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.swing.Timer;

import sturla.atitp.app.ImageLoader;
import sturla.atitp.frontend.ImageLabelContainer;
import sturla.atitp.frontend.imageops.ImageOperation;
import sturla.atitp.frontend.imageops.ImageOperationParameters;
import sturla.atitp.imageprocessing.Image;
import sturla.atitp.imageprocessing.Point;
import sturla.atitp.imageprocessing.TrackingArea;

public class SequenceImageTrackingOperation extends ImageOperation {
	
	private Iterator<File> files;
	private List<Point> currentSurface;
	private TrackingArea currentTrackingArea;
	private TrackingArea lastArea;
	private ImageLabelContainer result;
	
	private double[] avgIn;
	private double[] avgOut;
	Timer timer;
	
	private boolean first = true;
	
	@Override
	public void performOperation(ImageLabelContainer op1,
			ImageLabelContainer op2, ImageLabelContainer pResult,
			ImageOperationParameters params) {
		
		this.result = pResult;
		avgIn = new double[3];
		avgOut = new double[3];
		ActionListener trackNewImageAction = new ActionListener() {

			@Override
	    	public void actionPerformed(ActionEvent ae) {
	    		if (files.hasNext()) {
	    			try{
	    				Image img = ImageLoader.loadImage(files.next());
	    				if (first) {
	    					System.out.println("Calculating new avgin and avgout");
							currentTrackingArea = img.tracking(currentSurface, lastArea, null, null);
							currentSurface = currentTrackingArea.getFinalArea();
	    					avgIn = img.getAvgIn();
	    					avgOut = img.getAvgOut();
	    					first = false;
	    					lastArea = currentTrackingArea;
	    				} else {
							currentTrackingArea = img.tracking(currentSurface, lastArea, avgIn, avgOut);
							currentSurface = currentTrackingArea.getFinalArea();
							lastArea = currentTrackingArea;
	    				}
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
		List<File> fileList = Arrays.asList(new File(params.imageFile.getParent()).listFiles());
		Collections.sort(fileList, new Comparator<File>() {

			@Override
			public int compare(File f1, File f2) {
				return f1.getAbsolutePath().compareTo(f2.getAbsolutePath());
			}
			
		});
		files = fileList.iterator();
		timer = new Timer(params.maskSize, trackNewImageAction);
		timer.start();
	}

}
