package io.github.codetoil.litlaunch.launchcommon;

import io.github.codetoil.litlaunch.launchcommon.events.LitEvent;
import io.github.codetoil.litlaunch.launchcommon.proxy.CommonProxy;

public abstract class LaunchCommon {
    private static CommonProxy ccproxy;
    private static IGetFields getFields;
    private static double timeInit = 0.0;
    private static Command.Side side;
    public static final Object[] EMPTY = new Object[0];

	public static CommonProxy getCcproxy() {
		return ccproxy;
	}

	public static void setCcproxy(CommonProxy ccproxy) {
		if (LaunchCommon.ccproxy == null)
		{
			LaunchCommon.ccproxy = ccproxy;
		}
		else
		{
			LaunchMods.getINSTANCE().getLOGGER().info("CCProxy ALREADY SET");
		}
	}

	public static IGetFields getGetFields() {
		return getFields;
	}

	public static Command.Side getSide() {
		return side;
	}

	public static void setSide(Command.Side side) {
		if (side == null) {
			LaunchCommon.side = side;
		}
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
        getCcproxy().preInit();
    }

    public static void init()
    {
        LitEventHandler.COMMON.post(new LitEvent(LaunchMods.getINSTANCE(), "Init", EMPTY));
	    getCcproxy().init();
    }

    public static void postInit()
    {
        LitEventHandler.COMMON.post(new LitEvent(LaunchMods.getINSTANCE(), "PostInit", EMPTY));
	    getCcproxy().postInit();
    }

    public static void serverLoad()
    {
        LitEventHandler.COMMON.post(new LitEvent(LaunchMods.getINSTANCE(), "ServerLoad", EMPTY));
	    getCcproxy().serverLoad();
    }

	public static double getTimeInSeconds()
	{
		return ((double) System.currentTimeMillis()) / 1000.0 - timeInit;
	}
}
