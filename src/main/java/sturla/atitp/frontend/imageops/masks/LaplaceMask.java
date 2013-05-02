package sturla.atitp.frontend.imageops.masks;

import sturla.atitp.frontend.ImageLabelContainer;
import sturla.atitp.frontend.imageops.ImageOperation;
import sturla.atitp.frontend.imageops.ImageOperationParameters;
import sturla.atitp.imageprocessing.Image;
import sturla.atitp.imageprocessing.MaskFactory;

public class LaplaceMask extends ImageOperation {

	@Override
	public void performOperation(ImageLabelContainer op1,
			ImageLabelContainer op2, ImageLabelContainer result,
			ImageOperationParameters params) {
		Image img = op1.getImage().copy();
		img.applyMask(MaskFactory.buildLaplaceMask(),
				params.x1, params.y1,
				params.x2, params.y2);
		img.applyZeroCrossing(params.value2);
		result.setImage(img);
	}
}
