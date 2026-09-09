package justfatlard.herobrine.world;

/**
 * The seam between the approach logic and whatever is hosting it.
 *
 * <p>Everything in {@code manifestation} and {@code world} is written against this interface
 * rather than against Minecraft's own world types. Mojang's mappings move between snapshots and
 * the behaviour does not, so the two are kept on opposite sides of one narrow surface: the
 * behaviour is what this mod is, and the binding is what the target version is.
 */
public interface WorldAccess {
	int lightLevel(Vec3i pos);

	boolean isSolid(Vec3i pos);

	/** Namespaced id of the block at {@code pos}, {@code minecraft:air} for empty space. */
	String blockAt(Vec3i pos);

	void setBlock(Vec3i pos, String blockId);

	void removeBlock(Vec3i pos);

	void playSound(Vec3i pos, String soundId, float volume, float pitch);

	long dayTime();
}
