package io.github.codetoil.litlaunch.launchforge;

import io.github.codetoil.litlaunch.launchcommon.LaunchCommon;

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
