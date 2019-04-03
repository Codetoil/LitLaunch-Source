package io.github.littoil.tpsmod;

import io.github.littoil.litlaunch.launchcommon.LaunchMods;
import io.github.littoil.litlaunch.launchcommon.LitEventHandler;
import io.github.littoil.litlaunch.launchcommon.events.LitEvent;

public class CalculateTPS implements LitEventHandler.EventListener {
	private static final Object INSTANCE = new CalculateTPS();
	public CalculateTPS(){}
	public void RecievedEvent(LitEvent event) {
		if (event.getType().equals("updateTPS"))
		{
			LaunchMods.getINSTANCE().getLOGGER().info("Update TPS");
		}
	}

	@Override
	public LitEventHandler.EventListener getMainInstance() {
		return (LitEventHandler.EventListener) INSTANCE;
	}
}
