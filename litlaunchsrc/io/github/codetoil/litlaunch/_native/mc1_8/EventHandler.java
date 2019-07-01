/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch._native.mc1_8;

import io.github.codetoil.litlaunch.core.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventHandler
{
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onClientTick(TickEvent.ClientTickEvent event)
	{
		if (event.phase.equals(TickEvent.Phase.END)) {
			LitEventHandler.COMMON.post(new LitEvent(this, "clientTick"), true);
		}
	}

	@SubscribeEvent
	@SideOnly(Side.SERVER)
	public void onServerTick(TickEvent.ServerTickEvent event)
	{
		if (event.phase.equals(TickEvent.Phase.END)) {
			LitEventHandler.COMMON.post(new LitEvent(this, "serverTick"), true);
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void ServerConnect(FMLNetworkEvent.ClientConnectedToServerEvent event)
	{
		LitEventHandler.COMMON.post(new LitEvent(EventHandler.class, "ServerConnect"));
	}
}
