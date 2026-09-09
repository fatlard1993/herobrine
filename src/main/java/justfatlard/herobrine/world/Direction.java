package justfatlard.herobrine.world;

/** The four horizontal headings, ordered so that turning is an index step. */
public enum Direction {
	NORTH(0, -1),
	EAST(1, 0),
	SOUTH(0, 1),
	WEST(-1, 0);

	private final int dx;
	private final int dz;

	Direction(int dx, int dz) {
		this.dx = dx;
		this.dz = dz;
	}

	public int dx() {
		return dx;
	}

	public int dz() {
		return dz;
	}

	public Direction turn(boolean clockwise) {
		return values()[(ordinal() + (clockwise ? 1 : 3)) % 4];
	}

	/** Minecraft yaw runs clockwise from south, so the ordinal offset is two, not zero. */
	public static Direction nearest(float yaw) {
		return values()[Math.floorMod(Math.round(yaw / 90f) + 2, 4)];
	}
}
