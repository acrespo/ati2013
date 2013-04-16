package sturla.atitp.frontend.imageops;

import sturla.atitp.frontend.ImageLabelContainer;
import sturla.atitp.imageprocessing.Image;
import sturla.atitp.imageprocessing.MaskFactory;

public class HighPassOperation extends ImageOperation {

	@Override
	public void performOperation(ImageLabelContainer op1,
			ImageLabelContainer op2, ImageLabelContainer result,
			ImageOperationParameters params) {
		Image img = op1.getImage().copy();
		img.applyMask(MaskFactory.buildHighPassMask(params.maskSize, params.maskSize),
				params.x1, params.y1,
				params.x2, params.y2);
		result.setImage(img);
	}
}
