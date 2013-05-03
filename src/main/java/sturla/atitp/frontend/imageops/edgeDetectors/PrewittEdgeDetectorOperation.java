package sturla.atitp.frontend.imageops.edgeDetectors;

import sturla.atitp.frontend.ImageLabelContainer;
import sturla.atitp.frontend.imageops.ImageOperation;
import sturla.atitp.frontend.imageops.ImageOperationParameters;
import sturla.atitp.imageprocessing.Image;

public class PrewittEdgeDetectorOperation extends ImageOperation {

	@Override
	public void performOperation(ImageLabelContainer op1,
			ImageLabelContainer op2, ImageLabelContainer result,
			ImageOperationParameters params) {

		Image img = op1.getImage().copy();
		img.applyPrewittEdgeDetection(params.st);
		result.setImage(img);
		
	}

}
