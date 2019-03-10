package io.github.littoil.litlaunch.launchforge;

import io.github.littoil.litlaunch.launchcommon.LaunchCommon;
import io.github.littoil.litlaunch.launchcommon.LitEventHandler;
import io.github.littoil.litlaunch.launchcommon.proxy.CommonCommonProxy;
import io.github.littoil.litlaunch.launchcommon.events.*;
import net.minecraft.client.Minecraft;

public abstract class LaunchForge<FMLPreInitEvent, FMLInitEvent, FMLPostInitEvent, FMLServerLoadEvent> {
    public static final String MODID = "tpsmod";
    public static CommonCommonProxy ccproxy;

	public void preInit(FMLPreInitEvent event)
	{
		LaunchCommon.preInit();
		ccproxy.preInit();
	}
	
	public void init(FMLInitEvent event)
	{
		LaunchCommon.init();
		ccproxy.init();
	}

	public void postInit(FMLPostInitEvent event)
	{
		LaunchCommon.postInit();
		ccproxy.postInit();
	}
	
	public void serverLoad(FMLServerLoadEvent event)
	{
		LaunchCommon.serverLoad();
		ccproxy.serverLoad();
	}

	public abstract boolean setProxy();
    
}
