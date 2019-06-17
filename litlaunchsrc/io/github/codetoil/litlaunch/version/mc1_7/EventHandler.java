/*
 * Copyright Codetoil (c) 2019
 */

package io.github.codetoil.litlaunch.version.mc1_7;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.github.codetoil.litlaunch.event.LitEvent;
import io.github.codetoil.litlaunch.event.LitEventHandler;

public class EventHandler
{
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onTick(ClientTickEvent event)
	{
		if (event.phase.equals(Phase.END)) {
			LitEventHandler.COMMON.post(new LitEvent(EventHandler.class, "onTick"), true);
		}
	}
}
