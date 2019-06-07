/*
 * Copyright Codetoil (c) 2019
 */

package io.github.ianthisawesome.tpsmod;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.world.WorldEvent;

public class TpsInitialLoad {
	@SubscribeEvent
	public void worldLoad(WorldEvent.Load event)
	{
		new TpsInitialLoad.WaitForLoad("Tps Initial Load").start();
	}
	
	@SubscribeEvent
	public void worldUnLoad(WorldEvent.Unload event)
	{
		TpsMod.getTPS.resetVals();
	}
	
	class WaitForLoad extends ThreadLogged
	{

		public WaitForLoad(String name) {
			super(name);
		}

		@Override
		public void runOn() {
			while (Minecraft.getMinecraft().theWorld == null)
			{
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			LOGGER.debug("World no longer null");
			TpsMod.getTPS.updatePreviousTotalWorldTime(Minecraft.getMinecraft().theWorld);
			TpsMod.getTPS.updateTPS(Minecraft.getMinecraft().theWorld);
		}
		
	}
}
