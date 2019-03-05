package io.github.littoil.litlaunch.launchcommon;

import io.github.littoil.tpsmod.TPSMod;

public class LaunchCommon implements ILaunch {
    public static TPSMod tpsmod;
    public static String VERSION;
    
    public ILogger LOGGER;
    public static LaunchCommon INSTANCE;

    public void LaunchInit()
    {
    	Class<?> tpsmodclass;
    	try {
			ClassLoader.getSystemClassLoader().loadClass("io.github.littoil.tpsmod.TPSMod");
			tpsmodclass = Class.forName("io.github.littoil.tpsmod.TPSMod");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		try {
			tpsmod = (TPSMod) tpsmodclass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
    
	@Override
	public void preInit() {
		LOGGER.info("LitLaunch v" + VERSION + " preinitializing");
	}

	@Override
	public void init() {
		LOGGER.info("LitLaunch v" + VERSION + " initializing");
		
	}

	@Override
	public void postInit() {
		LOGGER.info("LitLaunch v" + VERSION + " postinitializing");
		
	}

	@Override
	public void serverLoad() {
		LOGGER.info("LitLaunch v" + VERSION + " server loading");
		
	}

	@Override
	public void sendEvent(String eventName) {
		if (tpsmod instanceof io.github.littoil.tpsmod.TPSMod)
    	{
        	LOGGER.info("Sent event \"" + eventName + "\"");
    		io.github.littoil.litlaunch.launchcommon.events.LitEvent event = new io.github.littoil.litlaunch.launchcommon.events.LitEvent(eventName);
        	TPSMod.eventhandler.newEvent(event);
    		//((io.github.littoil.tpsmod.TPSMod) tpsmod).newEvent(event);
    	}
    	else
    	{
    		LOGGER.error("tpsmod is not of instance type TPSMod. What???");
    		LOGGER.error("Failed to send event \"" + eventName + "\"");
    		LOGGER.error("Try again later...");
    		return;
    	}
	}

}
