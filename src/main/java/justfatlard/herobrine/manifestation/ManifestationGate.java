package justfatlard.herobrine.manifestation;

import justfatlard.herobrine.world.Direction;
import justfatlard.herobrine.world.SignatureLeavings;
import justfatlard.herobrine.world.TunnelCarver;
import justfatlard.herobrine.world.Vec3i;
import justfatlard.herobrine.world.WorldAccess;

/**
 * The last word on whether he shows up.
 *
 * <p>The threshold is saturation, not a merely high reading. Dread approaches saturation and does
 * not reach it, so the gate holds him one step short of the world for as long as the conditions
 * hold, and the approach is the entire product. This is on purpose: he is a rumour, and a rumour
 * that resolves stops being one.
 */
public final class ManifestationGate {
	public static final ManifestationGate DEFAULT = new ManifestationGate();

	/** Dread must be total. He does not turn up on a hunch. */
	private static final double MANIFESTATION_THRESHOLD = AmbientDread.SATURATION;

	private static final int STALK_DISTANCE = 24;
	private static final int UNLIGHT_RADIUS = 8;

	public Presence evaluate(PlayerTrace trace) {
		return trace.dread() >= MANIFESTATION_THRESHOLD ? trace.presence().advance() : trace.presence().recede();
	}

	public void manifest(WorldAccess world, PlayerTrace trace) {
		Vec3i standing = StalkVector.solve(trace.position(), trace.yaw(), STALK_DISTANCE);

		new SignatureLeavings(world).unlight(standing, UNLIGHT_RADIUS);
		new TunnelCarver(world).carve(standing, Direction.nearest(trace.yaw()), trace.seed());

		world.playSound(standing, "minecraft:entity.generic.footsteps", 0.4f, 0.7f);
	}
}
