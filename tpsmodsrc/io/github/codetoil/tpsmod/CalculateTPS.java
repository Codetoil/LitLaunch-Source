package io.github.codetoil.tpsmod;

import io.github.codetoil.litlaunch.launchcommon.LaunchMods;
import io.github.codetoil.litlaunch.launchcommon.LitEventHandler;
import io.github.codetoil.litlaunch.launchcommon.events.LitEvent;

public class CalculateTPS implements LitEventHandler.EventListener {
	private final int Dimension;
	public CalculateTPS(int Dimension)
	{
		this.Dimension = Dimension;
	}
	public void RecievedEvent(LitEvent event) {
		if (event.getType().equals("updateTPS"))
		{
			if (event.getData()[2].equals(this.Dimension)) {
				LaunchMods.getINSTANCE().getLOGGER().info("Update TPS for dimension " + Dimension);
			}
		}
	}

	@Override
	public LitEventHandler.EventListener getMainInstance() {
		return this;
	}
}
