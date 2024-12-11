
import java.util.*;
import java.lang.Math;
import java.math.BigInteger;

public class StatsLibrary { 
	
    public BigInteger factorial(int x) {
        BigInteger result = BigInteger.valueOf(x);
        
        for (int i = 2; i <= x; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        
        return result;
    }

    
    public BigInteger combination(int n, int r) {
        BigInteger numerator = factorial(n);
        BigInteger denominator = factorial(r).multiply(factorial(n - r));
        
        return numerator.divide(denominator);
    }


	public double hypergeometricDistribution(int n, int m, int r, int y) {
		double probability;
		
		probability = (combination(r, y).multiply(combination(n - r, m - y))).doubleValue() / (combination(n, m).doubleValue());
		
		return probability;
	}
	// Negative Binomial Distribution
    public double negativeBinomialDistribution(int r, double p, int x) {
        if (r <= 0 || p <= 0 || p >= 1 || x < 0) {
            throw new IllegalArgumentException("Invalid parameters for negative binomial distribution.");
        }
        double q = 1 - p;
        double comb = combination(r + x - 1, x).doubleValue();
        return comb * Math.pow(p, r) * Math.pow(q, x);
    }
    //Poisson Distribution
    public double poissonDistribution(int lambda, int y) {
        double expLambda = Math.exp(-lambda);
        double numerator = Math.pow(lambda, y);
        double denominator = factorial(y).doubleValue();
        return (numerator / denominator) * expLambda;
    }
  //Expected value and variance of a Poisson distribution.
    public int poissonExpectedAndVariance(int lambda) {
        return lambda;
    }

    //Standard deviation of a Poisson distribution.
    public double poissonStandardDeviation(int lambda) {
        return Math.sqrt(lambda);
    }
    //Chebyshev's
    public double chebyshev(double lowerRange, double upperRange, double mean, double standardDeviation) {
        double k = (upperRange - mean) / standardDeviation;
        return 1 - (1 / (k * k));
    }
    public double chebyshevGivenVariance(double lowerRange, double upperRange, double mean, double variance) {
        double standardDeviation = Math.sqrt(variance);
        double k = (upperRange - mean) / standardDeviation;
        return 1 - (1 / (k * k));
    }
    
    }
    

