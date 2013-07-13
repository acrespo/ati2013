package sturla.atitp.frontend.imageops.extra;

import java.util.ArrayList;
import java.util.List;

import sturla.atitp.frontend.ImageLabelContainer;
import sturla.atitp.frontend.imageops.ImageOperation;
import sturla.atitp.frontend.imageops.ImageOperationParameters;
import sturla.atitp.imageprocessing.Image;
import sturla.atitp.imageprocessing.Point;

public class SingleImageTrackingOperation extends ImageOperation {

	@Override
	public void performOperation(ImageLabelContainer op1,
			ImageLabelContainer op2, ImageLabelContainer result,
			ImageOperationParameters params) {
		Image img = op1.getImage().copy();
		List<Point> initialSurface = new ArrayList<Point>();
		for (int i = params.x1; i <= params.x2; i++) {
			for (int j = params.y1; j <= params.y2; j++) {
				initialSurface.add(new Point(i, j));
			}
		}
		img.tracking(initialSurface, null, null, null);
		result.setImage(img);
	}
}
