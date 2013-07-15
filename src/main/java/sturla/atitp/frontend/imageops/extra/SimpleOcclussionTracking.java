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
import sturla.atitp.imageprocessing.TrackingStats;

public class SimpleOcclussionTracking extends ImageOperation {
	
	private Iterator<File> files;
	private ImageLabelContainer result;
	
	private TrackingStats stats;
	Timer timer;
	
	
	@Override
	public void performOperation(ImageLabelContainer op1,
			ImageLabelContainer op2, ImageLabelContainer pResult,
			ImageOperationParameters params) {
		
		this.result = pResult;
		stats = new TrackingStats(params.value, params.maskSize);
		ActionListener trackNewImageAction = new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent ae) {
	    		if (files.hasNext()) {
	    			try{
	    				Image img = ImageLoader.loadImage(files.next());
	    				img.simpleOcclussionTracking(stats);
	    				result.setImage(img);
	    				timer.start();
	    			} catch (Exception e) {
	    				e.printStackTrace();
	    			}
	    		}
			}
		};
		
		List<Point> points = new ArrayList<Point>();
		for (int i = params.x1; i <= params.x2; i++) {
			for (int j = params.y1; j <= params.y2; j++) {
				points.add(new Point(i, j));
			}
		}
		stats.firstSelection(points);
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