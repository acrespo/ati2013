package sturla.atitp.imageprocessing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import sturla.atitp.app.Utils;
import sturla.atitp.imageprocessing.edgeDetector.EdgeDetector;
import sturla.atitp.imageprocessing.synthesization.SynthesizationType;

public class RGBImage implements Image{
	
	private ImageType type;
	private ImageFormat format;
	private SingleChannel red;
	private SingleChannel green;
	private SingleChannel blue;

	public RGBImage(int height, int width, ImageFormat format, ImageType type) {
		if( format == null ){
			throw new IllegalArgumentException("ImageFormat can't be null");
		}
		this.red = new SingleChannel(width, height);
		this.green = new SingleChannel(width, height);
		this.blue = new SingleChannel(width, height);

		this.format = format;
		this.type = type;
	}

	public RGBImage(BufferedImage bi, ImageFormat format, ImageType type){
		this(bi.getHeight(), bi.getWidth(), format, type);
		for(int x = 0 ; x < bi.getWidth() ; x++){
			for(int y = 0 ; y < bi.getHeight() ; y++ ){
				Color c = new Color(bi.getRGB(x, y));
				red.setPixel(x, y, c.getRed());
				green.setPixel(x, y, c.getGreen());
				blue.setPixel(x, y, c.getBlue());
			}
		}
	}

	private RGBImage(SingleChannel red, SingleChannel green, SingleChannel blue, 
			ImageFormat format, ImageType type){
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.format = format;
		this.type = type;
	}

	public void setPixel(int x, int y, Channel channel, double color) {

		if( !red.validPixel(x, y)){
			throw new IllegalArgumentException("Invalid pixels on setPixel");
		}

		if( channel == Channel.RED ){
			red.setPixel(x, y, color);
			return;
		}
		if( channel == Channel.GREEN ){
			green.setPixel(x, y, color);
			return;
		}
		if( channel == Channel.BLUE ){
			blue.setPixel(x, y, color);
			return;
		}
		throw new IllegalStateException();
	}

	@Override
	public void setRGBPixel(int x, int y, int rgb) {
		this.setPixel(x, y, Channel.RED, Utils.getRedFromRGB(rgb));
		this.setPixel(x, y, Channel.GREEN, Utils.getGreenFromRGB(rgb));
		this.setPixel(x, y, Channel.BLUE, Utils.getBlueFromRGB(rgb));
	}

	@Override
	public int getHeight() {
		return red.getHeight();
	}

	@Override
	public int getWidth() {
		return red.getWidth();
	}

	@Override
	public double getPixelFromChannel(int x, int y, Channel channel) {
		if( channel == Channel.RED ){
			return red.getPixel(x, y);
		}
		if( channel == Channel.GREEN ){
			return green.getPixel(x, y);
		}
		if( channel == Channel.BLUE ){
			return blue.getPixel(x, y);
		}
		throw new IllegalStateException();
	}

	@Override
	public int getRGBPixel(int x, int y) {
		int red = this.red.truncatePixel(getPixelFromChannel(x, y, Channel.RED));
		int green = this.green.truncatePixel(getPixelFromChannel(x, y, Channel.GREEN));
		int blue = this.blue.truncatePixel(getPixelFromChannel(x, y, Channel.BLUE));
		return new Color(red, green, blue).getRGB();
	}

	@Override
	public ImageType getType() {
		return type;
	}

	@Override
	public ImageFormat getImageFormat() {
		return format;
	}

	@Override
	public Image cropImage(int x1, int y1, int x2, int y2) {
		SingleChannel red = this.red.cropImage(x1, y1, x2, y2);
		SingleChannel green = this.green.cropImage(x1, y1, x2, y2);
		SingleChannel blue = this.blue.cropImage(x1, y1, x2, y2);

		return new RGBImage(red, green, blue, format, type);
	}

	@Override
	public Image add(Image img) {
		RGBImage ci = (RGBImage)img;

		this.red.add(ci.red);
		this.green.add(ci.green);
		this.blue.add(ci.blue);
		return this;
	}

	@Override
	public Image multiply(Image img) {
		RGBImage ci = (RGBImage)img;

		this.red.multiply(ci.red);
		this.green.multiply(ci.green);
		this.blue.multiply(ci.blue);
		return this;
	}

	@Override
	public Image substract(Image img) {
		RGBImage ci = (RGBImage)img;

		this.red.substract(ci.red);
		this.green.substract(ci.green);
		this.blue.substract(ci.blue);
		return this;
	}

	@Override
	public void dynamicRangeCompression() {
		double max = -Double.MAX_VALUE;
		double min = Double.MAX_VALUE;
		for(int i = 0; i < this.getWidth(); i++) {
			for(int j = 0; j < this.getHeight(); j++) {
				double redPixel = red.getPixel(i, j);
				double greenPixel = green.getPixel(i, j);
				double bluePixel = blue.getPixel(i, j);

				min = Math.min(Math.min(min, redPixel), Math.min(greenPixel, bluePixel));
				max = Math.max(Math.max(max, redPixel), Math.max(greenPixel, bluePixel));
			}
		}

		this.red.dynamicRangeCompression(min, max);
		this.green.dynamicRangeCompression(min, max);
		this.blue.dynamicRangeCompression(min, max);
	}

	@Override
	public void negative() {
		this.red.negative();
		this.blue.negative();
		this.green.negative();		
	}

	@Override
	public void threshold(double thresholdLimit) {
		this.red.threshold(thresholdLimit);
		this.blue.threshold(thresholdLimit);
		this.green.threshold(thresholdLimit);
	}

	@Override
	public void incrementContrast(double x1, double x2, double y1, double y2) {
		this.red.incrementContrast(x1, x2, y1, y2);
		this.blue.incrementContrast(x1, x2, y1, y2);
		this.green.incrementContrast(x1, x2, y1, y2);
	}

	@Override
	public void equalizeGrays() {
		this.red.equalize();
		this.green.equalize();
		this.blue.equalize();
	}

	@Override
	public double getGraylevelFromPixel(int x, int y) {
		double red = this.red.getPixel(x, y);
		double green = this.green.getPixel(x, y);
		double blue = this.blue.getPixel(x, y);

		return (red + green + blue)/3.0;
	}

	@Override
	public double[] getHistogramPixels() {
 		double[] result = new double[this.getHeight()*this.getWidth()];
 		
 		for(int i = 0 ; i < result.length ; i++){
 			result[i] = getGraylevelFromPixel(i % this.getWidth(), i/this.getWidth());
 		}
 		
		return result;
	}

	@Override
	public void multiply(double scalar) {
		this.red.multiply(scalar);
		this.green.multiply(scalar);
		this.blue.multiply(scalar);
	}


	@Override
	public void whiteNoise(double stdDev) {
		SingleChannel noisyChannel = new SingleChannel(this.getWidth(), this.getHeight());
		for(int x = 0; x < noisyChannel.getWidth(); x++) {
			for(int y = 0; y < noisyChannel.getHeight() ; y++) {
				double noiseLevel = RandomGenerator.getGaussian(0, SingleChannel.MAX_CHANNEL_COLOR * stdDev);
				noisyChannel.setPixel(x, y, noiseLevel);
			}
		}
		this.red.add(noisyChannel);
		this.green.add(noisyChannel);
		this.blue.add(noisyChannel);
	}

	@Override
	public void rayleighNoise(double mean) {
		SingleChannel noisyChannel = new SingleChannel(this.getWidth(), this.getHeight());
		for(int x = 0; x < noisyChannel.getWidth(); x++) {
			for(int y = 0; y < noisyChannel.getHeight() ; y++) {
				double noiseLevel = RandomGenerator.getRayleigh(mean);
				noisyChannel.setPixel(x, y, noiseLevel);
			}
		}
		this.red.multiply(noisyChannel);
		this.green.multiply(noisyChannel);
		this.blue.multiply(noisyChannel);
	}

	@Override
	public void exponentialNoise(double mean) {
		SingleChannel noisyChannel = new SingleChannel(this.getWidth(), this.getHeight());
		for(int x = 0; x < noisyChannel.getWidth(); x++) {
			for(int y = 0; y < noisyChannel.getHeight() ; y++) {
				double noiseLevel = RandomGenerator.getExponential(mean);
				noisyChannel.setPixel(x, y, noiseLevel);
			}
		}
		this.red.multiply(noisyChannel);
		this.green.multiply(noisyChannel);
		this.blue.multiply(noisyChannel);
	}

	@Override
	public void saltAndPepperNoise(double minLimit, double maxLimit) {
		for(int x = 0; x < this.getWidth(); x++) {
			for(int y = 0; y < this.getHeight() ; y++) {
				double random = RandomGenerator.getUniform(0, 1);
				if (random < minLimit) {
					double noiseLevel = SingleChannel.MIN_CHANNEL_COLOR;
					this.red.setPixel(x, y, noiseLevel);
					this.green.setPixel(x, y, noiseLevel);
					this.blue.setPixel(x, y, noiseLevel);
				} else if (random > maxLimit) {
					double noiseLevel = SingleChannel.MAX_CHANNEL_COLOR;
					this.red.setPixel(x, y, noiseLevel);
					this.green.setPixel(x, y, noiseLevel);
					this.blue.setPixel(x, y, noiseLevel);
				}
			}
		}
	}

	@Override
	public void applyMask(Mask mask, int w, int h, int endW, int endH) {
		this.red.applyMask(mask, w, h, endW, endH);
		this.green.applyMask(mask, w, h, endW, endH);
		this.blue.applyMask(mask, w, h, endW, endH);
	}

	@Override
	public void applyMedianMask(Point maskSize, int w, int h, int endW, int endH) {
		this.red.applyMedianMask(maskSize, w, h, endW, endH);
		this.green.applyMedianMask(maskSize, w, h, endW, endH);
		this.blue.applyMedianMask(maskSize, w, h, endW, endH);
	}

	public Point getCenter(){

		int x = (int) Math.floor(getWidth()/2);
		int y = (int) Math.floor(getHeight()/2);

		return new Point(x, y);
	}

	@Override
	public Image copy() {
		return new RGBImage(this.red.copy(), this.green.copy(), this.blue.copy(),
				this.format, this.type);
	}
	
	private int[] getRgbArray() {
		int[] array = new int[getWidth() * getHeight()];
		for (int j = 0; j < getHeight(); j ++) {
			for (int i = 0; i < getWidth(); i ++) {
				array[getWidth() * j + i] = getRGBPixel(i, j);
			}
		}
		return array;
	}

	@Override
	public BufferedImage toBufferedImage() {
		BufferedImage buff = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR_PRE);
		buff.setRGB(0, 0, getWidth(), getHeight(), getRgbArray(), 0, getWidth());
		return buff;
	}
	
	@Override
	public BufferedImage thresholdBinaryImage(double t) {
		BufferedImage buff = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR_PRE);
		int[] rgbArray = new int[getWidth() * getHeight()];
		for (int j = 0; j < getHeight(); j ++) {
			for (int i = 0; i < getWidth(); i ++) {
				double r = red.getPixel(i, j);
				double g = green.getPixel(i, j);
				double b = blue.getPixel(i, j);
				boolean underThreshold = (r + g + b) / 3 < t;
				Color c;
				if (underThreshold)
					c = Color.black;
				else
					c = Color.white;
				rgbArray[getWidth() * j + i] = c.getRGB();
			}
		}
		buff.setRGB(0, 0, getWidth(), getHeight(), rgbArray, 0, getWidth());
		return buff;
	}

	@Override
	public void applyIsotropicDiffusion(int iterations){
		this.red.applyIsotropicDiffusion(iterations);
		this.green.applyIsotropicDiffusion(iterations);
		this.blue.applyIsotropicDiffusion(iterations);
	}
	
	@Override
	public void applyAnisotropicDiffusion(double lambda, int iterations, EdgeDetector bd){
		this.red.applyAnisotropicDiffusion(lambda, iterations, bd);
		this.green.applyAnisotropicDiffusion(lambda, iterations, bd);
		this.blue.applyAnisotropicDiffusion(lambda, iterations, bd);
	}

	@Override
	public void applyPrewittBorderDetection(SynthesizationType st) {
		TwoMaskContainer mc = MaskFactory.buildPrewittMasks();
		applyTwoMasksAndSynth(mc, st);
	}

	@Override
	public void applyRobertsBorderDetection(SynthesizationType st) {
		TwoMaskContainer mc = MaskFactory.buildRobertsMasks();
		applyTwoMasksAndSynth(mc, st);
	}

	@Override
	public void applySobelBorderDetection(SynthesizationType st) {
		TwoMaskContainer mc = MaskFactory.buildSobelMasks();
		applyTwoMasksAndSynth(mc, st);
	}
	
	@Override
	public void applyMaskABorderDetection(SynthesizationType st){
		FourMaskContainer mc = MaskFactory.buildMaskA();
		applyFourMasksAndSynth(mc, st);
	}
	
	@Override
	public void applyMaskBKirshBorderDetection(SynthesizationType st){
		FourMaskContainer mc = MaskFactory.buildMaskBKirsh();
		applyFourMasksAndSynth(mc, st);
	}
	
	@Override
	public void applyMaskCBorderDetection(SynthesizationType st){
		FourMaskContainer mc = MaskFactory.buildMaskC();
		applyFourMasksAndSynth(mc, st);
	}
	
	@Override
	public void applyMaskDBorderDetection(SynthesizationType st){
		FourMaskContainer mc = MaskFactory.buildMaskD();
		applyFourMasksAndSynth(mc, st);
	}
	
	private void applyTwoMasksAndSynth(TwoMaskContainer mc, SynthesizationType st){
		RGBImage imageCopy = (RGBImage) this.copy();
		
		this.applyMask(mc.getDXMask(), 1, 1, imageCopy.getWidth() -1, imageCopy.getHeight()-1);
		imageCopy.applyMask(mc.getDYMask(), 1, 1, imageCopy.getWidth()-1, imageCopy.getHeight()-1);
		
		this.synthesize(st, imageCopy);
	}
	
	private void applyFourMasksAndSynth(FourMaskContainer mc, SynthesizationType st){
		RGBImage imageCopy2 = (RGBImage) this.copy();
		RGBImage imageCopy3 = (RGBImage) this.copy();
		RGBImage imageCopy4 = (RGBImage) this.copy();
		
		this.applyMask(mc.getMask0(), 1, 1, this.getWidth()-1, this.getHeight()-1);
		imageCopy2.applyMask(mc.getMask45(), 1, 1, imageCopy2.getWidth()-1, imageCopy2.getHeight()-1);
		imageCopy3.applyMask(mc.getMask90(), 1, 1, imageCopy3.getWidth()-1, imageCopy3.getHeight()-1);
		imageCopy4.applyMask(mc.getMask135(), 1, 1, imageCopy4.getWidth()-1, imageCopy4.getHeight()-1);
		
		this.synthesize(st, imageCopy2, imageCopy3, imageCopy4);
	}

	@Override
	public void synthesize(SynthesizationType st, Image ... imgs){
		Image[] cimgs = imgs;
		
		SingleChannel[] redChnls = new SingleChannel[cimgs.length];
		SingleChannel[] greenChnls = new SingleChannel[cimgs.length];
		SingleChannel[] blueChnls = new SingleChannel[cimgs.length];
		
		for(int i = 0 ; i < cimgs.length ; i++ ){
			redChnls[i] = ((RGBImage)cimgs[i]).red;
			greenChnls[i] = ((RGBImage)cimgs[i]).green;
			blueChnls[i] = ((RGBImage)cimgs[i]).blue;
		}
		
		this.red.synthesize(st, redChnls);
		this.green.synthesize(st, greenChnls);
		this.blue.synthesize(st, blueChnls);
		
	}
	
	@Override
	public void applyLaplaceMask(int w, int h, int endW, int endH){
		this.applyMask(MaskFactory.buildLaplaceMask(), w, h, endW, endH);
	}
	
	@Override
	public void applyLaplaceVarianceMask(int varianceThreshold, int w, int h, int endW, int endH) {
		this.applyMask(MaskFactory.buildLaplaceMask(), w, h, endW, endH);

		this.red.localVarianceEvaluation(varianceThreshold);
		this.green.localVarianceEvaluation(varianceThreshold);
		this.blue.localVarianceEvaluation(varianceThreshold);
	}
	
	@Override
	public void applyLaplaceGaussianMask(int maskSize, double sigma, int w, int h, int endW, int endH) {
		this.applyMask(MaskFactory.buildLaplaceGaussianMask(maskSize, sigma), w, h, endW, endH);
	}
	
	@Override
	public void applyZeroCrossing(double threshold){
		this.red.zeroCross(threshold);
		this.green.zeroCross(threshold);
		this.blue.zeroCross(threshold);
	}
	
	@Override
	public void globalThreshold() {
		this.red.globalThreshold();
		this.green.globalThreshold();
		this.blue.globalThreshold();
	}
	
	@Override
	public void otsuThreshold() {
		this.red.otsuThreshold();
		this.green.otsuThreshold();
		this.blue.otsuThreshold();
	}
	
	@Override
	public void binaryGlobalThreshold() {
		SingleChannel ch = getGreyChannel();
		ch.globalThreshold();
		red = ch;
		green = ch;
		blue = ch;
	}
	
	@Override
	public void binaryOtsuThreshold() {
		SingleChannel ch = getGreyChannel();
		ch.otsuThreshold();
		red = ch;
		green = ch;
		blue = ch;
	}
	
	private SingleChannel getGreyChannel() {
		SingleChannel ch = new SingleChannel(getWidth(), getHeight());
		for (int i = 0; i < getHeight(); i ++) {
			for (int j = 0; j < getWidth(); j ++) {
				double grey = (red.getPixel(i, j) + green.getPixel(i, j) + blue.getPixel(i, j)) / 3;
				ch.setPixel(i, j, grey);
			}
		}
		return ch;
	}
	
	
	@Override
	public void applyCannyBorderDetection() {
		this.red.applyCannyBorderDetection();
		this.green.applyCannyBorderDetection();
		this.blue.applyCannyBorderDetection();
	}
	
	@Override
	public void thresholdWithHysteresis(double lowThreshold, double highThreshold) {
		this.red.thresholdWithHysteresis(lowThreshold, highThreshold);
		this.green.thresholdWithHysteresis(lowThreshold, highThreshold);
		this.blue.thresholdWithHysteresis(lowThreshold, highThreshold);
	}

	@Override
	public void applySusanMask(boolean detectBorders, boolean detectCorners) {
		this.red.applySusanMask(detectBorders, detectCorners);
		this.green.applySusanMask(detectBorders, detectCorners);
		this.blue.applySusanMask(detectBorders, detectCorners);
	}

	@Override
	public void houghTransformForLines() {
		RGBImage backup = (RGBImage) copy();
		
		this.red.houghTransformForLines(0.75, 1, 1);
		this.green = this.red;
		this.blue = this.red;
		
		this.add(backup);
	}

	@Override
	public void houghTransformForCircles() {
		RGBImage backup = (RGBImage) copy();
		
		this.red.houghTransformForCircles(5, 1, 1, 2);
		this.green = this.red;
		this.blue = this.red;
		
		this.add(backup);
	}
	
	@Override
	public void applyHarrisCornerDetector(int size, Double sigma) {
		this.red.applyHarrisCornerDetector(size, sigma);
		this.green.applyHarrisCornerDetector(size, sigma);
		this.blue.applyHarrisCornerDetector(size, sigma);		
	}

	@Override
	public boolean validPixel(int x, int y) {
		return this.red.validPixel(x, y);
	}

	@Override
	public void tracking(List<Point> selection) {	
		TitaFunction tita = new TitaFunction(selection, this.red.getHeight(), this.red.getWidth());
		int times = (int)(1.5 * Math.max(this.red.getHeight(), this.red.getWidth()));
		boolean changes = true;
		List<Point> in = tita.getIn();
		List<Point> out = tita.getOut();
		double[] averageIn = getAverage(in);
		double[] averageOut = getAverage(out); 
		
		while((times > 0) && changes){
			changes = false;			
			List<Point> lOut = tita.getlOut();
			for(Point p: lOut){
				if( Fd(p, averageIn, averageOut) > 0){
					tita.setlIn(p);
					for(Point y: p.N4()){
						if(tita.isOut(y)){
							tita.setlOut(y);
						}
					}
					for(Point y: p.N4()){
						if(tita.islIn(y)){
							tita.setIn(y);
						}
					}
					changes = true;
				}
			}
			List<Point> lIn = tita.getlIn();
			for(Point p: lIn){
				if( Fd(p, averageIn, averageOut) < 0){
					tita.setlOut(p);
					for(Point y: p.N4()){
						if(tita.isIn(y)){
							tita.setlIn(y);
						}
					}
					for(Point y: p.N4()){
						if(tita.islOut(y)){
							tita.setOut(y);
						}
					}
					changes = true;
				}
			}				
			times--;
		}		
		selection.clear();
		selection.addAll(tita.getIn());
	}
	
	private double[] getAverage(List<Point> l){
		double[] ret = new double[3];
		ret[0] = 0;
		ret[1] = 0;
		ret[2] = 0;
		for(Point c: l){
			ret[0]+=this.red.getPixel(c.x, c.y);
		}
		ret[0]=ret[0]/l.size();
		
		for(Point c: l){
			ret[1]+=this.green.getPixel(c.x, c.y);
		}
		ret[1]=ret[1]/l.size();
		
		for(Point c: l){
			ret[2]+=this.blue.getPixel(c.x, c.y);
		}
		ret[2]=ret[2]/l.size();
		
		return ret;
	}

	private double Fd(Point p, double[] averageIn, double[] averageOut) {	
		double p1, p2;
		double red, green, blue;
		red = this.red.getPixel(p.x, p.y);
		green = this.green.getPixel(p.x, p.y);
		blue =  this.blue.getPixel(p.x, p.y);
		
		p1 = Math.sqrt(Math.pow((averageIn[0] - red), 2) + Math.pow((averageIn[1] - green), 2) + Math.pow((averageIn[2] - blue), 2));
		p2 = Math.sqrt(Math.pow((averageOut[0] - red), 2) + Math.pow((averageOut[1] - green), 2) + Math.pow((averageOut[2] - blue), 2));
		return p2 - p1;
	}
	
}
