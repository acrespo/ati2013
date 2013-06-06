package sturla.atitp.frontend.imageops.extra;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sturla.atitp.frontend.ImageLabelContainer;
import sturla.atitp.frontend.imageops.ImageOperation;
import sturla.atitp.frontend.imageops.ImageOperationParameters;
import sturla.atitp.imageprocessing.Point;

public class SIFTMatching extends ImageOperation {

	@Override
	public void performOperation(ImageLabelContainer op1,
			ImageLabelContainer op2, ImageLabelContainer result,
			ImageOperationParameters params) {
		try {
			BufferedReader buffReader = new BufferedReader(new FileReader("/home/acrespo/matching.txt"));
			String line;
			int height = op1.getHeight();
			int width = op1.getWidth();
			int startX2 = op1.getWidth() + 100;
			while ( (line = buffReader.readLine()) != null) {
				line = line.trim();
				String[] readVals = line.split(" ");
				String[] vals = new String[8];
				int i = 0;
				for (String str : readVals) {
					if (!str.equals("")) {
						vals[i] = str;
						++i;
					}
				}
				int x1 = (int)(double)Double.valueOf(vals[0]);
				int y1 = (int)(double)Double.valueOf(vals[1]);
				int x2 = (int)(double)Double.valueOf(vals[4]);
				int y2 = (int)(double)Double.valueOf(vals[5]);
				params.mainFrame.getGraphics().drawLine(x1 , height - y1, x2 + startX2, height - y2);
			}
			
			
		} catch (IOException e) {
			System.out.println("fuck");
			e.printStackTrace();
		}
		
		
	}

}
