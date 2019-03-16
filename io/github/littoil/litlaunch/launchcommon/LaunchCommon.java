package io.github.littoil.litlaunch.launchcommon;

import io.github.littoil.litlaunch.launchcommon.events.LitEvent;
import io.github.littoil.litlaunch.launchcommon.proxy.CommonProxy;

public abstract class LaunchCommon {
    public static CommonProxy ccproxy;

	public static boolean bootstrap(Object logger, ILaunch launch, ILogger iLogger)
	{
		//Inserting the logger to the logger given!
		LaunchMods.getINSTANCE().setLOGGER(iLogger);
		LaunchMods.getINSTANCE().getLOGGER().setInternalLogger(logger);

		//main bootstrap
		LaunchMods.getINSTANCE().getLOGGER().info("Preforming LitLaunch Bootstrap!");
		boolean val;
		try {
			if (launch.setProxy()) {
				LaunchMods.getINSTANCE().getLOGGER().error("Set Proxies");
				val = true;
			}
			else
			{
				LaunchMods.getINSTANCE().getLOGGER().error("Proxies not set!");
				val = false;
			}
		} catch (Throwable e)
		{
			e.printStackTrace();
			val = false;
		}
		return val;
	}

	public static void preInit()
    {
        LitEventHandler.COMMON.post(new LitEvent(LaunchMods.getINSTANCE(), "PreInit"));
        ccproxy.preInit();
    }

    public static void init()
    {
        LitEventHandler.COMMON.post(new LitEvent(LaunchMods.getINSTANCE(), "Init"));
        ccproxy.init();
    }

    public static void postInit()
    {
        LitEventHandler.COMMON.post(new LitEvent(LaunchMods.getINSTANCE(), "PostInit"));
        ccproxy.postInit();
    }

    public static void serverLoad()
    {
        LitEventHandler.COMMON.post(new LitEvent(LaunchMods.getINSTANCE(), "ServerLoad"));
        ccproxy.serverLoad();
    }
}
