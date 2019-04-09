package io.github.littoil.litlaunch.version.mc1_13;

import io.github.littoil.litlaunch.launchcommon.LitEventHandler;
import io.github.littoil.litlaunch.launchcommon.events.LitEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventHandler {
	@SubscribeEvent
	public void onTick(TickEvent.WorldTickEvent event)
	{
		if (event.phase.equals(TickEvent.Phase.END))
		{
			LitEventHandler.COMMON.post(new LitEvent(this, "onTick", event.world.dimension.getType().getId()));
		}
	}
}
