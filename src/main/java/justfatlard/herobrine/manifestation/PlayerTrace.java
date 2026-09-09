package justfatlard.herobrine.manifestation;

import justfatlard.herobrine.observation.Conditions;
import justfatlard.herobrine.world.Vec3i;

/** What is carried about one player between samples. */
public final class PlayerTrace {
	private final AmbientDread dread = new AmbientDread();

	private Presence presence = Presence.DORMANT;
	private Conditions last = Conditions.UNOBSERVED;
	private long lastSample;

	public void sample(Conditions conditions, long gameTime) {
		dread.sample(conditions.pressure());

		last = conditions;
		lastSample = gameTime;
	}

	public double dread() {
		return dread.level();
	}

	public Presence presence() {
		return presence;
	}

	public void presence(Presence presence) {
		this.presence = presence;
	}

	public Vec3i position() {
		return last.position();
	}

	public float yaw() {
		return last.yaw();
	}

	/** Seeded off the trace itself, so an approach that gets questioned reproduces. */
	public long seed() {
		return lastSample * 31L + last.position().hashCode();
	}
}
