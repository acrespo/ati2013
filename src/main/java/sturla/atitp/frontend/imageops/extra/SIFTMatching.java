package sturla.atitp.frontend.imageops.extra;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import sturla.atitp.frontend.ImageLabelContainer;
import sturla.atitp.frontend.imageops.ImageOperation;
import sturla.atitp.frontend.imageops.ImageOperationParameters;

public class SIFTMatching extends ImageOperation {

	@Override
	public void performOperation(ImageLabelContainer op1,
			ImageLabelContainer op2, ImageLabelContainer result,
			ImageOperationParameters params) {
		try {
			BufferedReader buffReader = new BufferedReader(new FileReader("/home/acrespo/matching.txt"));
			String line;
			int startX2 = op1.getWidth() + 100;
			int totalCount1 = countLines("/home/acrespo/keys1.txt");
			int totalCount2 = countLines("/home/acrespo/keys2.txt");
			int minCount = totalCount1 > totalCount2 ? totalCount2 : totalCount1;
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
				counter++;
				if (counter < params.x1) {
					int x1 = (int)(double)Double.valueOf(vals[0]);
					int y1 = (int)(double)Double.valueOf(vals[1]);
					int x2 = (int)(double)Double.valueOf(vals[4]);
					int y2 = (int)(double)Double.valueOf(vals[5]);
					params.mainFrame.getGraphics().setColor(Color.MAGENTA);
					params.mainFrame.getGraphics().drawLine(y1, x1 + 43, y2 + startX2, x2 + 43);
				} 
			}
			System.out.printf("Found %d matches.\n", counter);
			System.out.printf("Pct matches: %g.\n", ((double)counter) / minCount);
			
		} catch (IOException e) {
			System.out.println("fuck");
			e.printStackTrace();
		}
		
		
	}
	
	private int countLines(String filePath) {
		try {
			BufferedReader buffReader = new BufferedReader(new FileReader(filePath));
			int counter = 0;
			while ( (buffReader.readLine()) != null) {
				counter++;
			}
			return counter;
		} catch (IOException e) {
			System.out.println("fuck");
			e.printStackTrace();
			return 1;
		}
	}

}
