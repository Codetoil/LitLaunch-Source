package io.github.codetoil.litlaunch.version.mc1_8;

import io.github.codetoil.litlaunch.event.LitEvent;
import io.github.codetoil.litlaunch.event.LitEventHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventHandler
{
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (event.phase.equals(TickEvent.Phase.END)) {
			LitEventHandler.COMMON.post(new LitEvent(this, "onTick"), true);
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void ServerConnect(FMLNetworkEvent.ClientConnectedToServerEvent event)
	{
		LitEventHandler.COMMON.post(new LitEvent(EventHandler.class, "ServerConnect"));
	}
}
