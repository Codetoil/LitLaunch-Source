/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch._native.mc1_7;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.github.codetoil.litlaunch.core.event.*;

public class EventHandler
{
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onClientTick(TickEvent.ClientTickEvent event)
	{
		if (event.phase.equals(TickEvent.Phase.END)) {
			LitEventHandler.COMMON.post(new LitEvent(this, LitEvent.TYPE.CLIENTTICK), true);
		}
	}

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event)
	{
		if (event.phase.equals(TickEvent.Phase.END)) {
			LitEventHandler.COMMON.post(new LitEvent(this, LitEvent.TYPE.SERVERTICK), true);
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void ServerConnect(FMLNetworkEvent.ClientConnectedToServerEvent event)
	{
		LitEventHandler.COMMON.post(new LitEvent(EventHandler.class, LitEvent.TYPE.SERVERCONNECT));
	}
}
