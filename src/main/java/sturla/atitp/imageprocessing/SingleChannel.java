package sturla.atitp.imageprocessing;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.TreeSet;

class SingleChannel implements Cloneable {
	
	static final int MIN_CHANNEL_COLOR = 0;
	static final int MAX_CHANNEL_COLOR = 255;

	private int width;
	private int height;

	private double[] channel;

	public SingleChannel(int width, int height){
		if(width <= 0 || height <= 0) {
			throw new IllegalArgumentException("Images must have at least 1x1 pixel size");
		}

		this.width = width;
		this.height = height;
		this.channel = new double[width*height];
	}

	public SingleChannel(int width, int height, BufferedImage bi, int chnl){
		this(width, height);

		if(chnl < 0 || chnl > 2 ) {
			throw new IllegalArgumentException("SingleChannel must be between 0 and 2");
		}

		for(int x = 0 ; x < width ; x++ ) {
			for(int y = 0 ; y < height ; y++) {
				setPixel(x, y, bi.getData().getPixel(x, y, new double[3])[chnl]);
			}
		}
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public double getPixel(int x, int y) {
		if (!validPixel(x, y)) {
			throw new IndexOutOfBoundsException();
		}

		return  channel[y * this.getWidth() + x];
	}

	public void setPixel(int x, int y, double color){
		if (!validPixel(x, y)) {
			throw new IndexOutOfBoundsException();
		}

		channel[y * this.getWidth() + x] = color;
	}

	public SingleChannel cropImage(int x1, int y1, int x2, int y2) {
		if(!validSquare(x1,y1, x2, y2)){
			throw new IllegalArgumentException("Not a valid square.");
		}

		SingleChannel newImage = new SingleChannel(y2 - y1, x2 - x1);	
		int i = 0;
		for(int y = y1; y < y2; y++) {
			for(int x = x1; x < x2 ; x++) {
				newImage.channel[i++] = this.getPixel(x, y);				
			}
		}

		return newImage;
	}

	public boolean validPixel(int x, int y) {
		boolean validX = x >= 0 && x < this.getWidth();
		boolean validY = y >= 0 && y < this.getHeight(); 
		return validX && validY;
	}

	public boolean validSquare(int x1, int y1, int x2, int y2){
		boolean validX = this.validPixel(x1, y1);
		boolean validY = this.validPixel(x2, y2);
		boolean validSquare = (x1 < x2) && (y1 < y2);
		return validX && validY && validSquare;
	}

	public double[] getPixels() {
		return this.channel;
	}

	public void add(SingleChannel SingleChannel2) {
		for( int x = 0 ; x < width ; x++ ) {
			for( int y = 0 ; y < height ; y++) {
				double color = this.getPixel(x, y) + SingleChannel2.getPixel(x, y);
				this.setPixel(x, y, color);
			}
		}
	}

	public void substract(SingleChannel SingleChannel2) {
		for( int x = 0 ; x < width ; x++ ) {
			for( int y = 0 ; y < height ; y++) {
				double color = this.getPixel(x, y) - SingleChannel2.getPixel(x, y);
				this.setPixel(x, y, color);
			}
		}
	}

	public void multiply(SingleChannel SingleChannel2) {
		for( int x = 0 ; x < width ; x++ ) {
			for( int y = 0 ; y < height ; y++) {
				double color = this.getPixel(x, y) * SingleChannel2.getPixel(x, y);
				this.setPixel(x, y, color);
			}
		}
	}

	public void dynamicRangeCompression(double min, double max) {
		double c = (MAX_CHANNEL_COLOR - 1) / Math.log(1 + max - min);		
		for( int x = 0 ; x < width ; x++ ) {
			for( int y = 0 ; y < height ; y++) {
				double color = (double) (c * Math.log(1 + this.getPixel(x, y) - min));
				this.setPixel(x, y, color);
			}
		}
	}

	public void negative() {
		for( int x = 0 ; x < width ; x++ ) {
			for( int y = 0 ; y < height ; y++) {
				double color = this.getPixel(x, y);
				this.setPixel(x, y, MAX_CHANNEL_COLOR - color);
			}
		}
	}

	public void threshold(double thresholdLimit) {
		double blackColor = MIN_CHANNEL_COLOR;
		double whiteColor = MAX_CHANNEL_COLOR;

		for( int x = 0 ; x < width ; x++ ) {
			for( int y = 0 ; y < height ; y++) {
				double colorToApply = this.getPixel(x, y) > thresholdLimit? whiteColor : blackColor;
				this.setPixel(x, y, colorToApply);
			}
		}
	}

	public void incrementContrast(double x1, double x2, double y1, double y2) {
		if(!(x1 < x2) || !(y1 < y2)) {
			throw new IllegalArgumentException("The params are incorrect because they have no order");
		}

		for( int x = 0 ; x < width ; x++ ){
			for( int y = 0 ; y < height ; y++){
				double thisPixel = this.getPixel(x, y);

				double m = 0;
				double b = 0;
				if(thisPixel < x1) {
					m = y1 / x1;
					b = 0;
				} else if(thisPixel > x2) {
					m = (255 - y2) / (255 - x2);
					b = y2 - m * x2;
				} else {
					m = (y2 - y1) / (x2 - x1);
					b = y1 - m * x1;
				}
				double newPixel = m * thisPixel + b;

				this.setPixel(x, y, newPixel);
			}
		}
	}

	public void equalize() {
		int[] histData = this.getColorOccurrences();
		double[] newSingleChannel = new double[this.channel.length];

		for( int i = 0 ; i < newSingleChannel.length ; i++ ){
				int grayLevel = truncatePixel((int)Math.floor(this.channel[i]));

				double newValue = 0;
				for( int k = 0 ; k < grayLevel ; k++){
					newValue += histData[k];
				}

				newValue = newValue * (255.0/newSingleChannel.length);
				newSingleChannel[i] = newValue;
		}

		this.channel = newSingleChannel;
	}

	private int[] getColorOccurrences() {
		int[] dataset = new int[Image.GRAY_LEVEL_AMOUNT];

		for( int i = 0 ; i < this.channel.length ; i++ ){
				int grayLevel = truncatePixel((int)Math.floor(this.channel[i]));
				dataset[grayLevel] += 1;
		}

		return dataset;
	}

	public void multiply(double scalar) {
		for(int i = 0 ; i < this.channel.length ; i++){
			double newValue = this.channel[i] * scalar;
			this.channel[i] = newValue;
		}
	}

	int truncatePixel(double notTruncatedValue) {
		if(notTruncatedValue > MAX_CHANNEL_COLOR) {
			return MAX_CHANNEL_COLOR;
		} else if(notTruncatedValue < MIN_CHANNEL_COLOR) {
			return MIN_CHANNEL_COLOR;
		}
		return (int)notTruncatedValue;
	}

	@Override
	public SingleChannel clone() {
		SingleChannel newSingleChannel = new SingleChannel(width, height);

		for(int i = 0 ; i < width ; i++ ){
			for(int j = 0 ; j < height ; j++){
				newSingleChannel.setPixel(i, j, this.getPixel(i, j));
			}
		}
		return newSingleChannel;
	}

	public void applyMask(Mask mask, int w, int h, int endW, int endH){
		// save values first
		int pixels = (endW - w + 1) * (endH - h + 1);
		double[] vals = new double[pixels];
		int i = 0;
		for( int y = h ; y <= endH ; y++ ){
			for( int x = w ; x <= endW ; x++){
				vals[i] = applyPixelMask(x, y, mask);
				i++;
			}
		}
		// copy values to vector
		i = 0;
		for( int y = h ; y <= endH ; y++ ){
			for( int x = w ; x <= endW ; x++){
				channel[getWidth() * y + x] = vals[i];
				i++;
			}
		}
	}

	private double applyPixelMask(int x, int y, Mask mask) {
		double newColor = 0;
		for(int i = - mask.getWidth() / 2 ; i <= mask.getWidth() / 2; i++) {
			for(int j = - mask.getHeight() / 2; j <= mask.getHeight() / 2; j++) {
				if(this.validPixel(x + i, y + j)) {
					double oldColor = this.getPixel(x + i, y + j);
					newColor += oldColor * mask.getValue(i, j);
				}
			}
		}
		return newColor;
	}

	public void applyMedianMask(Point maskSize, int w, int endw, int h, int endh) {
		for( int y = h ; y <= endh ; y++ ){
			for( int x = w ; x <= endw ; x++){
				channel[getWidth() * y + x] = applyMedianPixelMask(x, y, maskSize);
			}
		}
	}

	private double applyMedianPixelMask(int x, int y, Point maskSize) {
		TreeSet<Double> pixelsAffected = new TreeSet<Double>(); 
		for(int i = - maskSize.x / 2 ; i <= maskSize.x / 2; i++) {
			for(int j = - maskSize.y / 2; j <= maskSize.y / 2; j++) {
				if(this.validPixel(x + i, y + j)) {
					double oldColor = this.getPixel(x + i, y + j); 
					pixelsAffected.add(oldColor);
				}
			}
		}

		double valueToReturn = 0;
		int indexToReturn = pixelsAffected.size() / 2;
		Iterator<Double> iterator = pixelsAffected.iterator();
		for(int i = 0; iterator.hasNext(); i++) {
			double each = iterator.next();
			if(i == indexToReturn) {
				valueToReturn = each;
			}
		}
		return valueToReturn;
	}
	
	public SingleChannel copy() {
		SingleChannel copy = new SingleChannel(this.width, this.height);
		copy.channel = this.channel.clone();
		return copy;
	}

}
