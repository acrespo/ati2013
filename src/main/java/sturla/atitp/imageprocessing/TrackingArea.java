package sturla.atitp.imageprocessing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class TrackingArea {
	
	private static int[][] DIRECTIONS = { {-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	
	private static int GAUSS_MASK_SIZE = 5;
	private static double GAUSS_SIGMA = 0.5;
	
	private static Mask GAUSS_MASK =
			MaskFactory.buildGaussianMask(GAUSS_MASK_SIZE, GAUSS_SIGMA);
	
	private double[] averageIn;
	private double[] averageOut;
	
	private int height;
	private int width;
	
	private Set<Point> limitIn;
	private Set<Point> limitOut;
	
	private int[][] phi;
	
	private int[][] f;
	
	private SingleChannel red;
	private SingleChannel blue;
	private SingleChannel green;
	
	TrackingArea(List<Point> initial,
			SingleChannel red, SingleChannel green, SingleChannel blue,
			double[] averageIn, double[] averageOut) {
		this.width = red.getWidth();
		this.height = red.getHeight();
		this.red = red;
		this.blue = blue;
		this.green  = green;
		limitIn = new HashSet<Point>();
		limitOut = new HashSet<Point>();
		
		phi = new int[width][height];
		f = new int[width][height];
		double acumR = 0;
		double acumG  = 0;
		double acumB = 0;
		int i = 0;
		for (Point p : initial) {
			phi[p.x][p.y] = -3;
			acumR += red.getPixel(p.x, p.y);
			acumG += green.getPixel(p.x, p.y);
			acumB += blue.getPixel(p.x, p.y);
			i++;
		}
		if (averageIn == null) {
			averageIn = new double[3];
			averageIn[0] = acumR / i;
			averageIn[1] = acumG / i;
			averageIn[2] = acumB / i;
		}
		this.averageIn = averageIn;
		acumR = acumG = acumB = 0;
		i = 0;
		for (int w = 0; w < width; w ++) {
			for (int h = 0; h < height; h ++) {
				Point p = new Point(w, h);
				if (phi[w][h] != -3) {
					phi[w][h] = 3;
					acumR += red.getPixel(p.x, p.y);
					acumG += green.getPixel(p.x, p.y);
					acumB += blue.getPixel(p.x, p.y);
					i++;
				}
			}
		}
		if (averageOut == null) {
			averageOut = new double[3];
			averageOut[0] = acumR / i;
			averageOut[1] = acumG / i;
			averageOut[2] = acumB / i;
		}
		this.averageOut = averageOut;
		// init Lin
		for (int w = 0; w < width; w ++) {
			for (int h = 0; h < height; h ++) {
				if (phi[w][h] == -3) {
					for (int[] dir : DIRECTIONS) {
						int newW = w + dir[0];
						int newH = h + dir[1];
						if (outOfBounds(newW, newH)) continue;
						if (phi[newW][newH] == 3) {
							phi[w][h] = -1;
							limitIn.add(new Point(w, h));
						}
					}
				}
			}
		}
		// init Lout
		for (int w = 0; w < width; w ++) {
			for (int h = 0; h < height; h ++) {
				if (phi[w][h] == 3) {
					for (int[] dir : DIRECTIONS) {
						int newW = w + dir[0];
						int newH = h + dir[1];
						if (outOfBounds(newW, newH)) continue;
						if (phi[newW][newH] == -1) {
							phi[w][h] = 1;
							limitOut.add(new Point(w, h));
						}
					}
				}
			}
		}
	}
	
	public Set<Point> limitIn() {
		return limitIn;
	}
	
	public Set<Point> limitOut() {
		return limitOut;
	}
	
	public void switchIn(Point p) {
		limitOut.remove(p);
		limitIn.add(p);
		phi[p.x][p.y] = -1;
		for (int[] dir : DIRECTIONS) {
			int w = p.x + dir[0];
			int h = p.y + dir[1];
			if (outOfBounds(w, h)) continue;
			if (phi[w][h] == 3) {
				phi[w][h] = 1;
				limitOut.add(new Point(w, h));
			}
		}
	}
	
	public void switchOut(Point p) {
		limitIn.remove(p);
		limitOut.add(p);
		phi[p.x][p.y] = 1;
		for (int[] dir : DIRECTIONS) {
			int w = p.x + dir[0];
			int h = p.y + dir[1];
			if (outOfBounds(w, h)) continue;
			if (phi[w][h] == -3) {
				phi[w][h] = -1;
				limitIn.add(new Point(w, h));
			}
		}
	}
	
	private boolean outOfBounds(int w, int h) {
		return w < 0 || w >= width || h < 0 || h >= height; 
	}
	
	public int f(Point p) {
		if (f[p.x][p.y] == 0) {
			f[p.x][p.y] = calcF(p);
		}
		return f[p.x][p.y];
	}
	
	private int calcF(Point p) {
		double p1, p2;
		double red, green, blue;
		red = this.red.getPixel(p.x, p.y);
		green = this.green.getPixel(p.x, p.y);
		blue =  this.blue.getPixel(p.x, p.y);
		
		p1 = Math.sqrt(Math.pow((averageIn[0] - red), 2) + Math.pow((averageIn[1] - green), 2) + Math.pow((averageIn[2] - blue), 2));
		p2 = Math.sqrt(Math.pow((averageOut[0] - red), 2) + Math.pow((averageOut[1] - green), 2) + Math.pow((averageOut[2] - blue), 2));
		double psigma1 = 1 - p1 / (Math.sqrt(3)*255);
		double psigma2 = 1 - p2 / (3*255);
		
		return (int)Math.signum(Math.log(psigma1/psigma2));
	}
	
	public void shrinkLimitIn() {
		List<Point> affectedPoints = new ArrayList<Point>();
		for (Point p : limitIn) {
			int count = 0;
			for (int[] dir : DIRECTIONS) {
				int w = p.x + dir[0];
				int h = p.y + dir[1];
				if (outOfBounds(w, h)) continue;
				if (phi[w][h] < 0) {
					count++;
				}
			}
			if (count == 4) {
				affectedPoints.add(p);
			}
		}
		for (Point p : affectedPoints) {
			limitIn.remove(p);
			phi[p.x][p.y] = -3;
		}
	}
	
	public void shrinkLimitOut() {
		List<Point> affectedPoints = new ArrayList<Point>();
		for (Point p : limitOut) {
			int count = 0;
			for (int[] dir : DIRECTIONS) {
				int w = p.x + dir[0];
				int h = p.y + dir[1];
				if (outOfBounds(w, h)) continue;
				if (phi[w][h] > 0) {
					count++;
				}
			}
			if (count == 4) {
				affectedPoints.add(p);
			}
		}
		for (Point p : affectedPoints) {
			limitOut.remove(p);
			phi[p.x][p.y] = 3;
		}
	}
	
	public boolean stoppingCondition() {
		for (Point p : limitIn) {
			if (f(p) < 0) {
				return false;
			}
		}
		for (Point p : limitOut) {
			if (f(p) > 0) {
				return false;
			}
		}
		
		return true;
	}
	
	public void smoothCurve() {
		List<Point> affectedPoints = new ArrayList<Point>();
		for (int i = 0; i < GAUSS_MASK_SIZE; i ++) {
			for (Point p : limitOut()) {
				if (gauss(p) < 0) {
					affectedPoints.add(p);
				}
			}
			for (Point p : affectedPoints) {
				switchIn(p);
			}
			affectedPoints.clear();
			for (Point p : limitIn()) {
				if (gauss(p) > 0) {
					affectedPoints.add(p);
				}
			}
			for (Point p : affectedPoints) {
				switchOut(p);
			}
			affectedPoints.clear();			
		}
	}
	
	public List<Point> getFinalArea() {
		List<Point> area = new ArrayList<Point>();
		for (int w = 0; w < width; w ++) {
			for (int h = 0; h < height; h ++) {
				if (phi[w][h] < 0) {
					area.add(new Point(w, h));
				}
			}
		}
		return area;
	}
	
	private int gauss(Point p) {
		double value = 0;
		for(int i = - GAUSS_MASK.getWidth() / 2 ; i <= GAUSS_MASK.getWidth() / 2; i++) {
			for(int j = - GAUSS_MASK.getHeight() / 2; j <= GAUSS_MASK.getHeight() / 2; j++) {
				if(!outOfBounds(p.x + i, p.y + j)) {
					value += phi[p.x + i][p.y + j] * GAUSS_MASK.getValue(i, j);
				}
			}
		}
		return (int) Math.signum(value);
	}
	
	public double[] getAverageIn() {
		return averageIn;
	}
	
	public double[] getAverageOut() {
		return averageOut;
	}

}
