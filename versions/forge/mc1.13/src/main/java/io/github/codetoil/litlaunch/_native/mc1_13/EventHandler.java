/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch._native.mc1_13;

import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.core.event.LitEvent;
import io.github.codetoil.litlaunch.core.event.LitEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;

public class EventHandler
{
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void ServerConnect(NetworkEvent.ClientCustomPayloadLoginEvent event)
	{
		LitEventHandler.COMMON.post(new LitEvent(EventHandler.class, LitEvent.TYPE.SERVERCONNECT));
		NetworkManager manager = Objects.requireNonNull(Minecraft.getInstance().getConnection()).getNetworkManager();
		if (manager.getNetHandler() instanceof INetHandlerPlayClient)
		{
			manager.setNetHandler(new EventThrowingClientPlayNetHandler((NetHandlerPlayClient) manager.getNetHandler()));
		}
		else
		{
			FrontEnd.error("Error, could not set net handler");
		}
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onClientTick(TickEvent.ClientTickEvent event)
	{
		if (event.phase.equals(TickEvent.Phase.END))
		{
			LitEventHandler.COMMON.post(new LitEvent(this, LitEvent.TYPE.CLIENTTICK, FrontEnd.EMPTY), true);
		}
	}

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event)
	{
		if (event.phase.equals(TickEvent.Phase.END))
		{
			LitEventHandler.COMMON.post(new LitEvent(this, LitEvent.TYPE.SERVERTICK), true);
		}
	}
}
