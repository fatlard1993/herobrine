package justfatlard.herobrine.manifestation;

/**
 * One player's running accumulation of the conditions he prefers.
 *
 * <p>Smoothed rather than sampled raw, so a single torch does not undo an hour underground and a
 * single dark cave does not summon him. Each sample closes a fixed fraction of the distance
 * between where dread is and where the present conditions could hold it, which means dread
 * approaches its ceiling and does not arrive at it.
 */
public final class AmbientDread {
	/** What total dread reads as. Saturation is the ceiling; nothing exceeds it. */
	public static final double SATURATION = 1.0;

	/** Fraction of the remaining distance to the ceiling closed per sample. */
	private static final double APPROACH_RATE = 0.0009;

	private double level;

	/**
	 * Folds one sample in.
	 *
	 * @param pressure how far the present conditions favour him, 0 to 1
	 */
	public void sample(double pressure) {
		double ceiling = SATURATION * Math.clamp(pressure, 0.0, 1.0);

		level += (ceiling - level) * APPROACH_RATE;
	}

	public double level() {
		return level;
	}
}
