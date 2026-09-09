package justfatlard.herobrine.observation;

import justfatlard.herobrine.world.Vec3i;

/**
 * One sample of what a player's surroundings are doing at the instant of sampling.
 *
 * @param position      the player's block position
 * @param yaw           the player's heading, Minecraft convention: zero faces south
 * @param lightLevel    light at the player's feet, 0 to 15
 * @param nearbyPlayers other players within earshot; company is the strongest deterrent there is
 * @param ticksAlone    ticks since this player last had company
 */
public record Conditions(Vec3i position, float yaw, int lightLevel, int nearbyPlayers, long ticksAlone) {
	private static final int SEA_LEVEL = 63;

	/** A player nobody has sampled yet: lit, at sea level, in company. */
	public static final Conditions UNOBSERVED = new Conditions(new Vec3i(0, SEA_LEVEL, 0), 0f, 15, 1, 0L);

	/** One full day alone, after which further solitude adds nothing. */
	private static final long SOLITUDE_PLATEAU = 24_000L;

	private static final double DARKNESS_SHARE = 0.5;
	private static final double DEPTH_SHARE = 0.2;
	private static final double SOLITUDE_SHARE = 0.3;

	/** Blocks below sea level, negative above it. */
	public int depth() {
		return SEA_LEVEL - position.y();
	}

	/**
	 * How far these conditions favour him, 0 to 1.
	 *
	 * <p>Company zeroes it outright rather than weighting against it: two players in a dark hole
	 * are a mining trip, and the shares below only mean anything for one player in one.
	 */
	public double pressure() {
		if (nearbyPlayers > 0) return 0.0;

		double darkness = (15 - Math.clamp(lightLevel, 0, 15)) / 15.0;
		double depth = Math.clamp(depth() / 64.0, 0.0, 1.0);
		double solitude = Math.clamp((double) ticksAlone / SOLITUDE_PLATEAU, 0.0, 1.0);

		return darkness * DARKNESS_SHARE + depth * DEPTH_SHARE + solitude * SOLITUDE_SHARE;
	}
}
