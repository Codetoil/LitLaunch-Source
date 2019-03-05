package io.github.littoil.tpsmod;

import io.github.littoil.litlaunch.launchcommon.LaunchCommon;
import io.github.littoil.litlaunch.launchcommon.events.LitEvent;

public class LitEventHandler {
    
    private TPSMod tpsmod;
    
    public LitEventHandler()
    {
    }
    
    public LitEventHandler(TPSMod tpsmod)
    {
    	LaunchCommon.INSTANCE.LOGGER.info("Creating new Event Handler!");
    	this.tpsmod = tpsmod;
    }
    
	public void newEvent(LitEvent event)
	{
		LaunchCommon.INSTANCE.LOGGER.info("Have new event \"" + event.name + "\"");
		switch (event.name)
		{
		case "PreInit":
			tpsmod.preInit();
			break;
		case "Init":
			tpsmod.Init();
			break;
		case "PostInit":
			tpsmod.postInit();
			break;
		case "ServerLoad":
			tpsmod.serverStart();
			break;
		default:
			LaunchCommon.INSTANCE.LOGGER.error("Unknown event \"" + event.name + "\"");
			break;
		}
	}
}
