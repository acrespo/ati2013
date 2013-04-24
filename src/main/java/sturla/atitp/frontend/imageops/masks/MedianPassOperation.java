package sturla.atitp.frontend.imageops.masks;

import java.awt.Point;

import sturla.atitp.frontend.ImageLabelContainer;
import sturla.atitp.frontend.imageops.ImageOperation;
import sturla.atitp.frontend.imageops.ImageOperationParameters;
import sturla.atitp.imageprocessing.Image;

public class MedianPassOperation extends ImageOperation {

	@Override
	public void performOperation(ImageLabelContainer op1,
			ImageLabelContainer op2, ImageLabelContainer result,
			ImageOperationParameters params) {
		Image img = op1.getImage().copy();
		img.applyMedianMask(new Point(params.maskSize, params.maskSize), params.x1, params.x2, 
				params.y1, params.y2);
		result.setImage(img);
	}

}
