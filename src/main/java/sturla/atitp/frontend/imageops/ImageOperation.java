package sturla.atitp.frontend.imageops;

import sturla.atitp.frontend.ImageLabelContainer;

public abstract class ImageOperation {
	
	public abstract void performOperation(ImageLabelContainer op1,
			ImageLabelContainer op2, ImageLabelContainer result,
			ImageOperationParameters params);

}
