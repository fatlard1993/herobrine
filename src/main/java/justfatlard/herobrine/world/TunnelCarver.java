package justfatlard.herobrine.world;

import java.util.Random;

/**
 * Carves the corridors that turn up in caves nobody has been in.
 *
 * <p>Two blocks tall, one wide, unlit, and stopping without having reached anything. Branching is
 * deliberately absent: a branching tunnel reads as somebody's strip mine, and the whole effect
 * depends on it not reading as anybody's.
 */
public final class TunnelCarver {
	private static final int SEGMENT_LENGTH = 12;
	private static final int MAX_SEGMENTS = 4;

	private final WorldAccess world;

	public TunnelCarver(WorldAccess world) {
		this.world = world;
	}

	/**
	 * Runs a corridor from {@code origin}, turning between segments.
	 *
	 * <p>The run stops the moment it breaks out of stone. Punching into a cave, a ravine or the
	 * side of somebody's basement turns a corridor that was found into damage that was done.
	 */
	public void carve(Vec3i origin, Direction heading, long seed) {
		Random random = new Random(seed);
		Vec3i cursor = origin;
		int segments = 1 + random.nextInt(MAX_SEGMENTS);

		for (int segment = 0; segment < segments; ++segment) {
			for (int step = 0; step < SEGMENT_LENGTH; ++step) {
				cursor = cursor.offset(heading.dx(), 0, heading.dz());

				if (!world.isSolid(cursor)) return;

				world.removeBlock(cursor);
				world.removeBlock(cursor.offset(0, 1, 0));
			}

			heading = heading.turn(random.nextBoolean());
		}
	}
}
