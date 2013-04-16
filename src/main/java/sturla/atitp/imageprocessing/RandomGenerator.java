package sturla.atitp.imageprocessing;
import net.sf.doodleproject.numerics4j.random.ExponentialRandomVariable;
import net.sf.doodleproject.numerics4j.random.NormalRandomVariable;
import net.sf.doodleproject.numerics4j.random.RNG;
import net.sf.doodleproject.numerics4j.random.RandomRNG;
import net.sf.doodleproject.numerics4j.random.RayleighRandomVariable;
import net.sf.doodleproject.numerics4j.random.UniformRandomVariable;

public class RandomGenerator {

	private static RNG rng = new RandomRNG(System.currentTimeMillis());

	public static double getUniform(double min, double max) {
		return UniformRandomVariable.nextRandomVariable(min, max, rng);
	}

	public static double getGaussian(double mean, double stdDev) {
		return NormalRandomVariable.nextRandomVariable(mean, stdDev, rng);
	}

	public static double getRayleigh(double mean) {
		return RayleighRandomVariable.nextRandomVariable(mean, rng);
	}

	public static double getExponential(double lambda) {
		return ExponentialRandomVariable.nextRandomVariable(lambda, rng);
	}

}