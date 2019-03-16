package io.github.littoil.litlaunch.launchforge;

import io.github.littoil.litlaunch.launchcommon.LaunchCommon;

public abstract class LaunchForge {
    public static final String MODID = "tpsmod";

	public static void preInit()
	{
		LaunchCommon.preInit();
	}
	
	public static void init()
	{
		LaunchCommon.init();
	}

	public static void postInit()
	{
		LaunchCommon.postInit();
	}
	
	public static void serverLoad()
	{
		LaunchCommon.serverLoad();
	}

	public abstract boolean setProxy();
    
}
