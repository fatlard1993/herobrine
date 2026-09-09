package justfatlard.herobrine.manifestation;

/**
 * How close he is to being in the world, for one player.
 *
 * <p>Presence moves one step per evaluation in either direction. A player who walks into a dark
 * room does not go from {@link #DORMANT} to {@link #MANIFEST} between two ticks, and lighting the
 * room does not undo the approach in one either. The intervening states are the whole effect.
 */
public enum Presence {
	DORMANT,
	AWARE,
	WATCHING,
	APPROACHING,
	MANIFEST;

	public Presence advance() {
		return this == MANIFEST ? MANIFEST : values()[ordinal() + 1];
	}

	public Presence recede() {
		return this == DORMANT ? DORMANT : values()[ordinal() - 1];
	}

	public boolean isVisible() {
		return this == MANIFEST;
	}
}
