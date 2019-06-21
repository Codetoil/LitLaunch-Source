package io.github.littoil.tpsmod;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;

@Plugin(
		id = "temp",
		name = "TEMP",
		description = "temp mod",
		authors = {
				"Littoil"
		}
)
public class TPSMod1 {

	@Inject
	private Logger logger;

	@Listener
	public void onServerStart(GameStartedServerEvent event) {
		logger.info("Hello World");
	}
}
