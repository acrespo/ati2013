package sturla.atitp.frontend.imageops;

import sturla.atitp.app.Utils;
import sturla.atitp.frontend.ImageLabelContainer;

public class HistogramOperation extends ImageOperation {

	@Override
	public void performOperation(ImageLabelContainer op1,
			ImageLabelContainer op2, ImageLabelContainer result,
			ImageOperationParameters params) {
		result.setImage(Utils.generateHistogram(op1.getImage()), 400, 200);
	}

}
