package justfatlard.herobrine.world;

/**
 * The leavings: what gets found afterwards, rather than what gets seen at the time.
 *
 * <p>None of it destroys anything a player built. The effect lives entirely on being deniable, and
 * a griefed base is not deniable, it is a bug report.
 */
public final class SignatureLeavings {
	private static final String SAND = "minecraft:sand";
	private static final String TORCH = "minecraft:torch";
	private static final String WALL_TORCH = "minecraft:wall_torch";

	private final WorldAccess world;

	public SignatureLeavings(WorldAccess world) {
		this.world = world;
	}

	/** The sand pyramid, built on whatever is already there and never into it. */
	public void pyramid(Vec3i base, int courses) {
		for (int course = 0; course < courses; ++course) {
			int half = courses - course - 1;

			for (int x = -half; x <= half; ++x) {
				for (int z = -half; z <= half; ++z) {
					world.setBlock(base.offset(x, course, z), SAND);
				}
			}
		}
	}

	/** Puts out every torch within {@code radius} and leaves them out. */
	public void unlight(Vec3i center, int radius) {
		for (int x = -radius; x <= radius; ++x) {
			for (int y = -radius; y <= radius; ++y) {
				for (int z = -radius; z <= radius; ++z) {
					Vec3i pos = center.offset(x, y, z);
					String block = world.blockAt(pos);

					if (TORCH.equals(block) || WALL_TORCH.equals(block)) world.removeBlock(pos);
				}
			}
		}
	}

	/** True once the room is dark enough that a shape in it is a shape and not a skin. */
	public boolean isUnlit(Vec3i center) {
		return world.lightLevel(center) <= 3;
	}
}
