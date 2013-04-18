package sturla.atitp.imageprocessing.edgeDetector;

public class LorentzEdgeDetector implements EdgeDetector {

	private double sigma;
	
	public LorentzEdgeDetector(double sigma){
		this.sigma = sigma;
	}
	
	@Override
	public double g(double x) {
		double den = Math.pow(Math.abs(x), 2) / Math.pow(sigma, 2) + 1;
		return 1/den;
	}

}
