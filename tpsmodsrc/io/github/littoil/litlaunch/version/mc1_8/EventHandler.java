package io.github.littoil.litlaunch.version.mc1_8;

import io.github.littoil.litlaunch.launchcommon.LitEventHandler;
import io.github.littoil.litlaunch.launchcommon.events.LitEvent;
import io.github.littoil.tpsmod.MeasureTPSdrop;
import io.github.littoil.tpsmod.TPSMod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventHandler {
	@SubscribeEvent
	public void onTick(TickEvent event)
	{
		if (event.phase.equals(TickEvent.Phase.END))
		{
			LitEventHandler.COMMON.post(new LitEvent(this, "checkIFTWTDrop"));
		}
	}
}
