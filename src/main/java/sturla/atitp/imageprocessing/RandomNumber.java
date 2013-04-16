package sturla.atitp.imageprocessing;

public class RandomNumber {
	
	public static double exponential(double lambda, double d) {
		return lambda * Math.exp(-lambda * d);		
	}
	
	public static double rayleigh(double e, double d) {
		double a = (d / Math.pow(e, 2));
		double b = Math.pow(d, 2)/(2*Math.pow(e, 2));
		return a * Math.exp(-b);		
	}
	
	public static double gaussian(double o, double u, double d) {
		double a = (1 / (o * Math.sqrt(2*Math.PI)));
		double b = (Math.pow((d-u), 2))/(2*Math.pow(o, 2));
		double c = Math.exp(-b);
		return  a * c;		
	}

}
