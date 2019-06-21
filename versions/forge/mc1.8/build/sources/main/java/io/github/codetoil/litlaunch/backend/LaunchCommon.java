/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.backend;

import io.github.codetoil.litlaunch.api.*;
import io.github.codetoil.litlaunch.event.LitEvent;
import io.github.codetoil.litlaunch.event.LitEventHandler;

import java.io.File;

public abstract class LaunchCommon
{
	public static final String VERSION = "0.0.2.2";
	public static final String MODID = "litlaunch";
	public static final Object[] EMPTY = new Object[0];
	private static CommonProxy ccproxy;
	private static IGetFields getFields;
	private static IDoThing doThing;
	private static Boolean verbose;
	private static double timeInit;
	private static Command.Side side;

	public static double getTimeInit()
	{
		return timeInit;
	}

	public static void setVerbose(boolean pVerbose)
	{
		if (verbose == null) {
			verbose = pVerbose;
		}
	}

	public static IGetFields getGetFields()
	{
		return getFields;
	}

	public static void setGetFields(IGetFields getFields)
	{
		if (LaunchCommon.getFields == null) {
			LaunchCommon.getFields = getFields;
		}
	}

	public static IDoThing getDoThing()
	{
		return doThing;
	}

	public static void setDoThing(IDoThing pDoThing)
	{
		if (LaunchCommon.doThing == null) {
			LaunchCommon.doThing = pDoThing;
		}
	}

	public static Command.Side getSide()
	{
		return side;
	}

	public static void setSide(Command.Side side)
	{
		if (side == null) {
			LaunchCommon.side = side;
		}
	}

	public static void bootstrap(Object logger, ILaunch launch, ILogger iLogger, File configFile) throws Throwable
	{
		timeInit = getTimeInSeconds();
		// Required to make it so that time is readable in a specific way
		// It is time 0 in the clock timer thing, the origin for measuring time.
		// Change in time = (clock time final - time origin) - (clock time initial - time origin)
		// time origin could be 0, but I rather it not be...

		//Inserting the logger to the logger wrapper!
		LaunchMods.setLOGGER(iLogger);
		LaunchMods.getLOGGER().setInternalLogger(logger);

		//main bootstrap
		LaunchMods.info("Preforming LitLaunch Bootstrap!");
		try {
			if (launch.setProxy()) {
				LaunchMods.debug("Set Proxies");
			} else {
				LaunchMods.error("Proxies not set!");
			}
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
		LaunchMods.info("Reading config file for mods and settings...");
		LaunchMods.readModList(configFile);
		LaunchMods.info("Finding and validating mods!");
		LaunchMods.locateMods();
		LaunchMods.validateMods();
		LaunchMods.info("Done! Now sending construction event!");
		if (isVerbose()) {
			LaunchMods.warn("Warning: You have decided for this to be verbose! If you did not intend for this to be verbose, Make sure to change the config file. You will see a spam of debug messages under \"trace\".");
		}
		LitEventHandler.COMMON.post(new LitEvent(LaunchCommon.class, "Construction", EMPTY));
	}

	public static double getTimeInSeconds()
	{
		return ((double) System.currentTimeMillis()) / 1000.0 - timeInit;
	}

	public static Boolean isVerbose()
	{
		return verbose;
	}

	public static void preInit()
	{
		LitEventHandler.COMMON.post(new LitEvent(LaunchCommon.class, "PreInit", EMPTY));
		getCcproxy().preInit();
	}

	public static CommonProxy getCcproxy()
	{
		return ccproxy;
	}

	public static void setCcproxy(CommonProxy ccproxy)
	{
		if (LaunchCommon.ccproxy == null) {
			LaunchCommon.ccproxy = ccproxy;
		} else {
			LaunchMods.info("CCProxy ALREADY SET");
		}
	}

	public static void init()
	{
		LitEventHandler.COMMON.post(new LitEvent(LaunchCommon.class, "Init", EMPTY));
		LitEventHandler.COMMON.post(new LitEvent(LaunchCommon.class, "TEST", EMPTY), true);
		getCcproxy().init();
	}

	public static void postInit()
	{
		LitEventHandler.COMMON.post(new LitEvent(LaunchCommon.class, "PostInit", EMPTY));
		getCcproxy().postInit();
	}

	public static void serverLoad()
	{
		LitEventHandler.COMMON.post(new LitEvent(LaunchCommon.class, "ServerLoad", EMPTY));
		getCcproxy().serverLoad();
	}
}
