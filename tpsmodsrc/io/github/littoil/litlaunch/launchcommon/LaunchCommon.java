package io.github.littoil.litlaunch.launchcommon;

import io.github.littoil.litlaunch.launchcommon.events.LitEvent;
import io.github.littoil.litlaunch.launchcommon.proxy.CommonProxy;

public abstract class LaunchCommon {
    public static CommonProxy ccproxy;
    private static IGetFields getFields;
    private static double timeInit = 0.0;
    public static final Object[] EMPTY = new Object[0];

	public static IGetFields getGetFields() {
		return getFields;
	}

	public static void setGetFields(IGetFields getFields) {
		if (LaunchCommon.getFields == null)
		{
			LaunchCommon.getFields = getFields;
		}
	}

	public static void bootstrap(Object logger, ILaunch launch, ILogger iLogger) throws Throwable
	{
		timeInit = getTimeInSeconds(); // Makes it so that time is readable
		//Inserting the logger to the logger wrapper!
		LaunchMods.getINSTANCE().setLOGGER(iLogger);
		LaunchMods.getINSTANCE().getLOGGER().setInternalLogger(logger);

		//main bootstrap
		LaunchMods.getINSTANCE().getLOGGER().info("Preforming LitLaunch Bootstrap!");
		try {
			if (launch.setProxy()) {
				LaunchMods.getINSTANCE().getLOGGER().error("Set Proxies");
			}
			else
			{
				LaunchMods.getINSTANCE().getLOGGER().error("Proxies not set!");
			}
		} catch (Throwable e)
		{
			e.printStackTrace();
		}
		LaunchMods.getINSTANCE().getLOGGER().info("Constructing mods!");
		LaunchMods.constructMods();
	}

	public static void preInit()
    {
        LitEventHandler.COMMON.post(new LitEvent(LaunchMods.getINSTANCE(), "PreInit", EMPTY));
        ccproxy.preInit();
    }

    public static void init()
    {
        LitEventHandler.COMMON.post(new LitEvent(LaunchMods.getINSTANCE(), "Init", EMPTY));
        ccproxy.init();
    }

    public static void postInit()
    {
        LitEventHandler.COMMON.post(new LitEvent(LaunchMods.getINSTANCE(), "PostInit", EMPTY));
        ccproxy.postInit();
    }

    public static void serverLoad()
    {
        LitEventHandler.COMMON.post(new LitEvent(LaunchMods.getINSTANCE(), "ServerLoad", EMPTY));
        ccproxy.serverLoad();
    }

	public static double getTimeInSeconds()
	{
		return ((double) System.currentTimeMillis()) / 1000.0 - timeInit;
	}
}
