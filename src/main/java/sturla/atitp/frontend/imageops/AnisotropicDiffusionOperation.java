package sturla.atitp.frontend.imageops;

import sturla.atitp.frontend.ImageLabelContainer;
import sturla.atitp.imageprocessing.Image;

public class AnisotropicDiffusionOperation extends ImageOperation {

	@Override
	public void performOperation(ImageLabelContainer op1,
			ImageLabelContainer op2, ImageLabelContainer result,
			ImageOperationParameters params) {
		Image img = op1.getImage().copy();
		img.applyAnisotropicDiffusion(params.value, (int) params.value2, params.bd);
		result.setImage(img);
	}
}
