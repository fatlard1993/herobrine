package justfatlard.herobrine.manifestation;

import justfatlard.herobrine.world.Vec3i;

/**
 * Solves for where he stands.
 *
 * <p>Far enough back to be a shape rather than a skin, and off the player's heading by more than
 * half the widest field of view a client can be set to. Turning around is what finds him; the edge
 * of somebody's peripheral vision never does, because a shape caught there is a shape they get to
 * look straight at.
 */
public final class StalkVector {
	/** Half the widest horizontal field of view a vanilla client renders, with margin. */
	private static final double BLIND_ARC_DEGREES = 100.0;

	private StalkVector() {}

	public static Vec3i solve(Vec3i player, float yaw, int distance) {
		double bearing = Math.toRadians(yaw + BLIND_ARC_DEGREES);

		// Minecraft yaw: zero faces +Z, and x runs against the sine rather than with it.
		int x = player.x() + (int) Math.round(-Math.sin(bearing) * distance);
		int z = player.z() + (int) Math.round(Math.cos(bearing) * distance);

		return new Vec3i(x, player.y(), z);
	}
}
