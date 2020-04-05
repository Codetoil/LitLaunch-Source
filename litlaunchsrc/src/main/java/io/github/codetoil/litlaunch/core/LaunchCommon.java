/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.core;

import io.github.codetoil.litlaunch.api.ChainableMap;
import io.github.codetoil.litlaunch.api.Command;
import io.github.codetoil.litlaunch.api.IDoThing;
import io.github.codetoil.litlaunch.api.IGetFields;
import io.github.codetoil.litlaunch.core.event.LitEvent;
import io.github.codetoil.litlaunch.core.event.LitEventHandler;
import io.github.codetoil.litlaunch.modloader.ModFinder;

import java.io.File;
import java.nio.file.Path;

public abstract class LaunchCommon
{
	public static final String NAME = "LitLaunch";
	public static final String VERSION = "0.0.3.15";
	public static final String MODID = "litlaunch";
	public static final ChainableMap<String, Object> EMPTY = new ChainableMap<>();
	private static CommonProxy ccproxy;
	private static IGetFields getFields;
	private static IDoThing doThing;
	private static Boolean verbose;
	private static double timeInit;
	private static Command.Side side;
	private static ILogger LOGGER;
	private static Path GamePath;

	public static double getTimeInit()
	{
		return timeInit;
	}

	public static void setVerbose(boolean pVerbose)
	{
		if (verbose == null)
		{
			verbose = pVerbose;
		}
	}

	public static IGetFields getGetFields()
	{
		return getFields;
	}

	public static void setGetFields(IGetFields getFields)
	{
		if (LaunchCommon.getFields == null)
		{
			LaunchCommon.getFields = getFields;
		}
	}

	public static IDoThing getDoThing()
	{
		return doThing;
	}

	public static void setDoThing(IDoThing pDoThing)
	{
		if (LaunchCommon.doThing == null)
		{
			LaunchCommon.doThing = pDoThing;
		}
	}

	public static Command.Side getSide()
	{
		return side;
	}

	public static void setSide(Command.Side side)
	{
		if (LaunchCommon.side == null)
		{
			LaunchCommon.side = side;
		}
	}

	public static void bootstrap(Object logger, ILaunch launch, ILogger iLogger) throws Throwable
	{
		timeInit = getTimeInSeconds();
		// Required to make it so that time is readable in a specific way
		// It is time 0 in the clock timer thing, the origin for measuring time.
		// Change in time = (clock time final - time origin) - (clock time initial - time origin)
		// time origin could be 0, but I rather it not be...

		//Inserting the logger to the logger wrapper!
		setLOGGER(iLogger);
		getLOGGER().setInternalLogger(logger);

		//main bootstrap
		info("Preforming LitLaunch Bootstrap!");
		try
		{
			if (launch.setProxy())
			{
				debug("Set Proxies");
			}
			else
			{
				error("Proxies not set!");
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
		getCcproxy().setGamePath();
		info("Locating config file!");
		File litlaunchCFG = getGamePath().resolve("config/litlaunch.cfg").toFile();
		info("Reading config file for mods and settings...");
		ConfigFile.readConfig(litlaunchCFG);
		info("Finding and validating mods!");
		ModFinder.locateMods(LaunchCommon.getGamePath().resolve("mods_litlaunch"));
		ModFinder.locateMods(LaunchCommon.getGamePath().resolve("mods"));
		ModFinder.validateMods();
		info("Now Garbage Collecting!");
		System.gc();
		info("Done! Now sending construction event!");
		debug("verbose: " + isVerbose());
		if (isVerbose())
		{
			warn("Warning: You have decided for LitLaunch to be verbose! If you did not intend for this to be verbose, Make sure to change the config file. You will see a spam of debug messages under \"trace\".");
		}
		LitEventHandler.COMMON.post(new LitEvent(LaunchCommon.class, LitEvent.TYPE.CONSTRUCTION, EMPTY));
		getCcproxy().construction();
	}

	public static double getTimeInSeconds()
	{
		return ((double) System.currentTimeMillis()) / 1000.0 - timeInit;
	}

	public static ILogger getLOGGER()
	{
		return LOGGER;
	}

	public static void setLOGGER(ILogger pILogger)
	{
		if (LOGGER == null)
		{
			LOGGER = pILogger;
		}
	}

	public static void info(Object object)
	{
		getLOGGER().info(object);
	}

	public static void debug(Object object)
	{
		getLOGGER().debug(object);
	}

	public static void error(Object object)
	{
		getLOGGER().error(object);
	}

	public static CommonProxy getCcproxy()
	{
		return ccproxy;
	}

	public static void setCcproxy(CommonProxy ccproxy)
	{
		if (LaunchCommon.ccproxy == null)
		{
			LaunchCommon.ccproxy = ccproxy;
		}
		else
		{
			info("CCProxy ALREADY SET");
		}
	}

	public static Path getGamePath()
	{
		return GamePath;
	}

	public static Boolean isVerbose()
	{
		return verbose;
	}

	public static void warn(Object object)
	{
		getLOGGER().warn(object);
	}

	public static void setGamePath(Path pGamePath)
	{
		GamePath = pGamePath;
	}

	public static void preInit()
	{
		LitEventHandler.COMMON.post(new LitEvent(LaunchCommon.class, LitEvent.TYPE.PREINIT, EMPTY));
		getCcproxy().preInit();
	}

	public static void init()
	{
		LitEventHandler.COMMON.post(new LitEvent(LaunchCommon.class, LitEvent.TYPE.INIT, EMPTY));
		LitEventHandler.COMMON.post(new LitEvent(LaunchCommon.class, LitEvent.TYPE.TESTSPAM, EMPTY), true);
		getCcproxy().init();
	}

	public static void postInit()
	{
		LitEventHandler.COMMON.post(new LitEvent(LaunchCommon.class, LitEvent.TYPE.POSTINIT, EMPTY));
		getCcproxy().postInit();
	}

	public static void serverLoad()
	{
		LitEventHandler.COMMON.post(new LitEvent(LaunchCommon.class, LitEvent.TYPE.SERVERLOAD, EMPTY));
		getCcproxy().serverLoad();
	}

	public static void fatal(Object object)
	{
		getLOGGER().fatal(object);
	}

	public static void verbose(Object object)
	{
		getLOGGER().verbose(object);
	}

	public static void trace(Object object)
	{
		getLOGGER().trace(object);
	}
}
