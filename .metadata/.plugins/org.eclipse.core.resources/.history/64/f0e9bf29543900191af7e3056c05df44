package io.github.littoil.litlaunch.launchforge;

import io.github.littoil.litlaunch.launchcommon.LaunchCommon;
import io.github.littoil.litlaunch.launchcommon.proxy.CommonCommonProxy;

public abstract class LaunchForge<PreInitEvent, InitEvent, PostInitEvent, ServerLoadEvent> extends LaunchCommon {
    public static final String MODID = "tpsmod";
    public static CommonCommonProxy ccproxy;
    
    
    
	public void preInit(PreInitEvent event)
	{
		super.preInit();
		sendEvent("PreInit");
	}
	
	public void init(InitEvent event)
	{
		super.init();
		sendEvent("Init");
	}

	public void postInit(PostInitEvent event)
	{
		super.postInit();
		sendEvent("PostInit");
	}
	
	public void serverLoad(ServerLoadEvent event)
	{
		super.serverLoad();
		sendEvent("ServerLoad");
	}
    
	@Override
    public void sendEvent(String eventName)
    {
    	super.sendEvent(eventName);
    }
    
}
