package sturla.atitp.frontend.imageops.binaryops;

import sturla.atitp.frontend.ImageLabelContainer;
import sturla.atitp.frontend.imageops.ImageOperation;
import sturla.atitp.frontend.imageops.ImageOperationParameters;
import sturla.atitp.imageprocessing.Image;

public class AddImageOperation extends ImageOperation {

	@Override
	public void performOperation(ImageLabelContainer op1,
			ImageLabelContainer op2, ImageLabelContainer result,
			ImageOperationParameters params) {
		Image img1 = op1.getImage().copy();
		Image img2 = op2.getImage();
		img1.add(img2);
		result.setImage(img1);
	}

}
