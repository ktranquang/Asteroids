package game;

import java.util.Random;

//Random number generator class
public class Randomizer extends Random {

	public static final long serialVersionUID = 1L;

	public Randomizer() {
		super();
	}

	public Randomizer(long seed) {
		super(seed);
	}

	/** Return a double in the range 0 to high exclusive of high. */
	public double nextDouble(double high) {
		return nextDouble() * high;
	}

	/** Return a double in the range low to high exclusive of high. */
	public double nextDouble(double low, double high) {
		return low + nextDouble(high - low);
	}

	/** Return an int in the range low to high inclusive. */
	public int nextInt(int low, int high) {
		return low + nextInt(high + 1 - low);
	}
}
