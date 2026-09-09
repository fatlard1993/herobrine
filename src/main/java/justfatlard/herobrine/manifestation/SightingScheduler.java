package justfatlard.herobrine.manifestation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import justfatlard.herobrine.observation.Conditions;
import justfatlard.herobrine.world.WorldAccess;

/**
 * Drives the whole thing: one sample per player per cadence, folded into that player's trace, and
 * the resulting presence handed to the gate.
 *
 * <p>The cadence is prime so sampling does not settle into step with anything else sharing the
 * server tick loop. Sampling every tick is both wasteful and, in testing, noticeable.
 */
public final class SightingScheduler {
	public static final SightingScheduler INSTANCE = new SightingScheduler();

	private static final int CADENCE = 37;

	private final Map<UUID, PlayerTrace> traces = new HashMap<>();

	private ManifestationGate gate;

	private SightingScheduler() {}

	/** Hands the scheduler the gate it defers to. Until this is called, no trace advances. */
	public void arm(ManifestationGate gate) {
		this.gate = gate;
	}

	public void tick(WorldAccess world, long gameTime, UUID player, Conditions conditions) {
		if (gate == null || gameTime % CADENCE != 0) return;

		PlayerTrace trace = traces.computeIfAbsent(player, id -> new PlayerTrace());

		trace.sample(conditions, gameTime);
		trace.presence(gate.evaluate(trace));

		if (trace.presence().isVisible()) gate.manifest(world, trace);
	}

	/** Drops a player's trace on disconnect. Dread does not survive a logout, and neither does he. */
	public void forget(UUID player) {
		traces.remove(player);
	}
}
