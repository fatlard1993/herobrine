package justfatlard.herobrine;

import justfatlard.herobrine.manifestation.ManifestationGate;
import justfatlard.herobrine.manifestation.SightingScheduler;
import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
	public static final String MOD_ID = "herobrine-justfatlard";

	@Override
	public void onInitialize(){
		SightingScheduler.INSTANCE.arm(ManifestationGate.DEFAULT);

		System.out.println("Added Herobrine");
	}
}
