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
			BufferedReader buffReader = new BufferedReader(new FileReader("/home/acrespo/matching_saint-michel.txt"));
			String line;
			int height = op1.getHeight();
			int width = op1.getWidth();
			int startX2 = op1.getWidth() + 100;
			int counter = 0;
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
				params.mainFrame.getGraphics().setColor(Color.CYAN);
				params.mainFrame.getGraphics().drawLine(y1, x1, y2 + startX2, x2);
				params.mainFrame.getGraphics().draw3DRect(300, 0, 50, 50, true);
				System.out.println(x1);
				System.out.println(y1);
				System.out.println(x2);
				System.out.println(y2);
				counter++;
				if (counter > 15) {
					return;
				} 
				
			}
			
			
		} catch (IOException e) {
			System.out.println("fuck");
			e.printStackTrace();
		}
		
		
	}

}
