package sturla.atitp.imageprocessing;


public class MaskFactory {

	public static Mask buildHighPassMask(int width, int height) {
		Mask mask = new Mask(width, height);
		double pixelAmount = width * height;
		for(int i = - mask.getWidth() / 2 ; i <= mask.getWidth() / 2; i++) {
			for(int j = - mask.getHeight() / 2; j <= mask.getHeight() / 2; j++) {
				mask.setPixel(i, j, (double) -1 / pixelAmount);
			}
		}
		mask.setPixel(0, 0, (double) (pixelAmount - 1) / pixelAmount);
		return mask;
	}
	
	public static Mask buildLowPassMask(int width, int height) {
		Mask mask = new Mask(width, height);
		double pixelAmount = width * height;
		for(int i = - mask.getWidth() / 2 ; i <= mask.getWidth() / 2; i++) {
			for(int j = - mask.getHeight() / 2; j <= mask.getHeight() / 2; j++) {
				mask.setPixel(i, j, 1 / pixelAmount);
			}
		}
		return mask;
	}
	
	public static Mask buildGaussianMask(int size, double sigma) {
		Mask mask = new Mask(size);
		double total = 0;
		for(int i = - mask.getWidth() / 2 ; i <= mask.getWidth() / 2; i++) {
			for(int j = - mask.getHeight() / 2; j <= mask.getHeight() / 2; j++) {
				double factor = Math.pow(2 * Math.PI * Math.pow(sigma, 2), -1);
				double exp = - (Math.pow(i, 2) + Math.pow(j, 2)) / (2 * Math.pow(sigma, 2));
				double pixelValue = factor * Math.pow(Math.E, exp);
				total += pixelValue;
				mask.setPixel(i, j, pixelValue);
			}
		}
		for(int i = - mask.getWidth() / 2 ; i <= mask.getWidth() / 2; i++) {
			for(int j = - mask.getHeight() / 2; j <= mask.getHeight() / 2; j++) {
				double oldPixel = mask.getValue(i, j);
				mask.setPixel(i, j, oldPixel / total);
			}
		}
		return mask;
	}
	
	public static TwoMaskContainer buildRobertsMasks(){
		int size = 3;
		Mask dx = new Mask(size);
		Mask dy = new Mask(size);
		
		dx.setPixel(0, 0, 1);
		dx.setPixel(1, 1, -1);

		dy.setPixel(0, 1, 1);
		dy.setPixel(1, 0, -1);
		
		return new TwoMaskContainer(dx, dy);
	}
	
	public static TwoMaskContainer buildPrewittMasks(){
		int size = 3;
		Mask dx = new Mask(size);
		Mask dy = new Mask(size);
		
		dx.setPixel(-1, -1, -1);
		dx.setPixel(-1, 0, -1);
		dx.setPixel(-1, 1, -1);
		dx.setPixel(1, -1, 1);
		dx.setPixel(1, 0, 1);
		dx.setPixel(1, 1, 1);
		
		dy.setPixel(-1, -1, -1);
		dy.setPixel(0, -1, -1);
		dy.setPixel(1, -1, -1);
		dy.setPixel(-1, 1, 1);
		dy.setPixel(0, 1, 1);
		dy.setPixel(1, 1, 1);
		
		return new TwoMaskContainer(dx, dy);
	}
	
	public static TwoMaskContainer buildSobelMasks(){
		int size = 3;
		Mask dx = new Mask(size);
		Mask dy = new Mask(size);
		
		dx.setPixel(-1, -1, -1);
		dx.setPixel(-1, 0, -2);
		dx.setPixel(-1, 1, -1);
		dx.setPixel(1, -1, 1);
		dx.setPixel(1, 0, 2);
		dx.setPixel(1, 1, 1);
		
		dy.setPixel(-1, -1, -1);
		dy.setPixel(0, -1, -2);
		dy.setPixel(1, -1, -1);
		dy.setPixel(-1, 1, 1);
		dy.setPixel(0, 1, 2);
		dy.setPixel(1, 1, 1);	
		
		return new TwoMaskContainer(dx, dy);
	}
	
	private static Mask buildMaskA0(){
		Mask mask = new Mask(3);
		
		mask.setPixel(-1, -1, 1);
		mask.setPixel(-1, 0, 1);
		mask.setPixel(-1, 1, 1);
		
		mask.setPixel(0, -1, 1);
		mask.setPixel(0, 0, -2);
		mask.setPixel(0, 1, 1);
		
		mask.setPixel(1, -1, -1);
		mask.setPixel(1, 0, -1);
		mask.setPixel(1, 1, -1);
		
		return mask;
	}
	
	public static FourMaskContainer buildMaskA(){
		Mask mask0 = buildMaskA0();
		Mask mask45 = MaskRotator.rotate45(mask0);
		Mask mask90 = MaskRotator.rotate90(mask0);
		Mask mask135 = MaskRotator.rotate135(mask0);
		
		return new FourMaskContainer(mask0, mask45, mask90, mask135);
	}
	
	private static Mask buildMaskBKirsh0(){
		Mask mask = new Mask(3);
		
		mask.setPixel(-1, -1, 5);
		mask.setPixel(-1, 0, 5);
		mask.setPixel(-1, 1, 5);
		
		mask.setPixel(0, -1, -3);
		mask.setPixel(0, 0, 0);
		mask.setPixel(0, 1, -3);
		
		mask.setPixel(1, -1, -3);
		mask.setPixel(1, 0, -3);
		mask.setPixel(1, 1, -3);
		
		return mask;
	}
	
	public static FourMaskContainer buildMaskBKirsh(){
		Mask mask0 = buildMaskBKirsh0();
		Mask mask45 = MaskRotator.rotate45(mask0);
		Mask mask90 = MaskRotator.rotate90(mask0);
		Mask mask135 = MaskRotator.rotate135(mask0);
		
		return new FourMaskContainer(mask0, mask45, mask90, mask135);
	}
	
	private static Mask buildMaskC0(){
		Mask mask = new Mask(3);
		
		mask.setPixel(-1, -1, 1);
		mask.setPixel(-1, 0, 1);
		mask.setPixel(-1, 1, 1);
		
		mask.setPixel(0, -1, 0);
		mask.setPixel(0, 0, 0);
		mask.setPixel(0, 1, 0);
		
		mask.setPixel(1, -1, -1);
		mask.setPixel(1, 0, -1);
		mask.setPixel(1, 1, -1);
		
		return mask;
	}
	
	public static FourMaskContainer buildMaskC(){
		Mask mask0 = buildMaskC0();
		Mask mask45 = MaskRotator.rotate45(mask0);
		Mask mask90 = MaskRotator.rotate90(mask0);
		Mask mask135 = MaskRotator.rotate135(mask0);
		
		return new FourMaskContainer(mask0, mask45, mask90, mask135);
	}
	
	private static Mask buildMaskD0(){
		Mask mask = new Mask(3);
		
		mask.setPixel(-1, -1, 1);
		mask.setPixel(-1, 0, 2);
		mask.setPixel(-1, 1, 1);
		
		mask.setPixel(0, -1, 0);
		mask.setPixel(0, 0, 0);
		mask.setPixel(0, 1, 0);
		
		mask.setPixel(1, -1, -1);
		mask.setPixel(1, 0, -2);
		mask.setPixel(1, 1, -1);
		
		return mask;
	}
	
	public static FourMaskContainer buildMaskD(){
		Mask mask0 = buildMaskD0();
		Mask mask45 = MaskRotator.rotate45(mask0);
		Mask mask90 = MaskRotator.rotate90(mask0);
		Mask mask135 = MaskRotator.rotate135(mask0);
		
		return new FourMaskContainer(mask0, mask45, mask90, mask135);
	}
	
	public static Mask buildSusanMask() {
		Mask mask = new Mask(7);
		
		for(int i = - mask.getWidth() / 2 ; i <= mask.getWidth() / 2; i++) {
			for(int j = - mask.getHeight() / 2; j <= mask.getHeight() / 2; j++) {
				mask.setPixel(i, j, 1);
			}
		}
		
		mask.setPixel(-3, -3, 0);
		mask.setPixel(-3, -2, 0);
		mask.setPixel(-3, 2, 0);
		mask.setPixel(-3, 3, 0);
		
		mask.setPixel(-2, -3, 0);
		mask.setPixel(-2, 3, 0);
		
		mask.setPixel(2, -3, 0);
		mask.setPixel(2, 3, 0);
		
		mask.setPixel(3, -3, 0);
		mask.setPixel(3, -2, 0);
		mask.setPixel(3, 2, 0);
		mask.setPixel(3, 3, 0);
		
		return mask;
	}

	public static Mask buildLaplaceMask(){
		Mask mask = new Mask(3);
		
		mask.setPixel(-1, -1, 0);
		mask.setPixel(-1, 0, -1);
		mask.setPixel(-1, 1, 0);
		
		mask.setPixel(0, -1, -1);
		mask.setPixel(0, 0, 4);
		mask.setPixel(0, 1, -1);
		
		mask.setPixel(1, -1, 0);
		mask.setPixel(1, 0, -1);
		mask.setPixel(1, 1, 0);
		
		return mask;
	}

	public static Mask buildLaplaceDiagonalMask(){
		Mask mask = new Mask(3);
		
		mask.setPixel(-1, -1, -1);
		mask.setPixel(-1, 0, -1);
		mask.setPixel(-1, 1, -1);
		
		mask.setPixel(0, -1, -1);
		mask.setPixel(0, 0, 8);
		mask.setPixel(0, 1, -1);
		
		mask.setPixel(1, -1, -1);
		mask.setPixel(1, 0, -1);
		mask.setPixel(1, 1, -1);
		
		return mask;
	}
	
	public static Mask buildLaplaceGaussianMask(int size, double sigma){
		Mask mask = new Mask(size);
		
		for(int i = - mask.getWidth() / 2 ; i <= mask.getWidth() / 2; i++) {
			for(int j = - mask.getHeight() / 2; j <= mask.getHeight() / 2; j++) {
				double factor = Math.pow(Math.sqrt(2 * Math.PI) * Math.pow(sigma, 3), -1);
				double exp = - (Math.pow(i, 2) + Math.pow(j, 2)) / (2 * Math.pow(sigma, 2));
				double term = 2 - (Math.pow(i, 2) + Math.pow(j, 2))/Math.pow(sigma, 2);
				double pixelValue = -1 * factor * term * Math.pow(Math.E, exp);
				mask.setPixel(i, j, pixelValue);
			}
		}
		return mask;		
	}
	
}
