package io.github.littoil.litlaunch.launchforge;

import io.github.littoil.litlaunch.launchcommon.LaunchCommon;
import io.github.littoil.litlaunch.launchforge.proxy.CommonCommonProxy;

public abstract class LaunchForge {
    public static final String MODID = "tpsmod";
    public static CommonCommonProxy ccproxy;

	public static void preInit()
	{
		LaunchCommon.preInit();
		ccproxy.preInit();
	}
	
	public static void init()
	{
		LaunchCommon.init();
		ccproxy.init();
	}

	public static void postInit()
	{
		LaunchCommon.postInit();
		ccproxy.postInit();
	}
	
	public static void serverLoad()
	{
		LaunchCommon.serverLoad();
		ccproxy.serverLoad();
	}

	public abstract boolean setProxy();
    
}
