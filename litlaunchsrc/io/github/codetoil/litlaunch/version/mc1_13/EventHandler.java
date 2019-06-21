package io.github.codetoil.litlaunch.version.mc1_13;

import io.github.codetoil.litlaunch.event.LitEvent;
import io.github.codetoil.litlaunch.event.LitEventHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.network.NetworkEvent;

public class EventHandler
{
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onTick(TickEvent.ClientTickEvent event)
	{
		if (event.phase.equals(TickEvent.Phase.END)) {
			LitEventHandler.COMMON.post(new LitEvent(EventHandler.class, "onTick"), true);
		}
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void ServerConnect(NetworkEvent.ClientCustomPayloadLoginEvent event)
	{
		LitEventHandler.COMMON.post(new LitEvent(EventHandler.class, "ServerConnect"));
	}
}
