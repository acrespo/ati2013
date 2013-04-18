package sturla.atitp.frontend.imageops;

import sturla.atitp.frontend.ImageLabelContainer;
import sturla.atitp.imageprocessing.Image;

public class OtsuUmbralizationOperation extends ImageOperation {

	@Override
	public void performOperation(ImageLabelContainer op1,
			ImageLabelContainer op2, ImageLabelContainer result,
			ImageOperationParameters params) {

		Image img = op1.getImage().copy();
		img.otsuThreshold();
		result.setImage(img);
	}

}