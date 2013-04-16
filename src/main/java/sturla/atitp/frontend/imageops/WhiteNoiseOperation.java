package sturla.atitp.frontend.imageops;

import sturla.atitp.frontend.ImageLabelContainer;
import sturla.atitp.imageprocessing.Image;

public class WhiteNoiseOperation extends ImageOperation {

	@Override
	public void performOperation(ImageLabelContainer op1, ImageLabelContainer op2,
			ImageLabelContainer result, ImageOperationParameters params) {
		op2.removeImage();
		Image img = op1.getImage().copy();
		img.whiteNoise(params.value);
		result.setImage(img);
	}
}
