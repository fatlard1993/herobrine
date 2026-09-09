package justfatlard.herobrine.world;

/**
 * An integer block position.
 *
 * <p>Deliberately not Minecraft's: nothing under this package names a game type, which is what
 * lets the approach logic be reasoned about without a server attached to it.
 */
public record Vec3i(int x, int y, int z) {
	public Vec3i offset(int dx, int dy, int dz) {
		return new Vec3i(x + dx, y + dy, z + dz);
	}

	public double distanceTo(Vec3i other) {
		double dx = x - other.x, dy = y - other.y, dz = z - other.z;

		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}
}
