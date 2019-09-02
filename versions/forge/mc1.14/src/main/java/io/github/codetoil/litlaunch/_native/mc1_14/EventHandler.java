/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch._native.mc1_14;

import io.github.codetoil.litlaunch.core.LaunchCommon;
import io.github.codetoil.litlaunch.core.event.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.HashMap;

public class EventHandler
{
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onClientTick(TickEvent.ClientTickEvent event)
	{
		if (event.phase.equals(TickEvent.Phase.END)) {
			LitEventHandler.COMMON.post(new LitEvent(this, LitEvent.TYPE.CLIENTTICK, LaunchCommon.EMPTY), true);
		}
	}

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event)
	{
		if (event.phase.equals(TickEvent.Phase.END)) {
			LitEventHandler.COMMON.post(new LitEvent(this, LitEvent.TYPE.SERVERTICK, LaunchCommon.EMPTY), true);
		}
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void ServerConnect(NetworkEvent.ClientCustomPayloadLoginEvent event)
	{
		LitEventHandler.COMMON.post(new LitEvent(EventHandler.class, LitEvent.TYPE.SERVERCONNECT, LaunchCommon.EMPTY));
	}
}
