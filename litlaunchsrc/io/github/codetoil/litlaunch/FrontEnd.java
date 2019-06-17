/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch;

import io.github.codetoil.litlaunch.api.*;
import io.github.codetoil.litlaunch.backend.ILogger;
import io.github.codetoil.litlaunch.backend.LaunchCommon;
import io.github.codetoil.litlaunch.backend.LitClassLoader;
import io.github.codetoil.litlaunch.event.LitEventHandler;

import java.util.List;

/**
 * The front end of LitLaunch, allows to access to the api from a single class
 */
public class FrontEnd
{
	public static final String LITLAUNCH_VERSION = LaunchCommon.VERSION;
	public static final String LITLAUNCH_MOD = LaunchCommon.MODID;
	public static final Object[] EMPTY_ARRAY = LaunchCommon.EMPTY;

	public static String MINECRAFT_VERSION()
	{
		return LaunchCommon.getGetFields().getVERSION();
	}

	public static CommonProxy ACTIVE_PROXY()
	{
		return LaunchCommon.getCcproxy();
	}

	public static IGetFields GET_FIELDS()
	{
		return LaunchCommon.getGetFields();
	}

	public static IDoThing DO_THING_OBJECT()
	{
		return LaunchCommon.getDoThing();
	}

	public static boolean IS_LITLAUNCH_VERBOSE()
	{
		return LaunchCommon.isVerbose();
	}

	public static double INITIAL_TIME_FOR_MEASURING_TIME()
	{
		return LaunchCommon.getTimeInit();
	}

	public static Command.Side SIDE()
	{
		return LaunchCommon.getSide();
	}

	public static LaunchMods LAUNCHMODS()
	{
		return LaunchMods.getINSTANCE();
	}

	public static void debug(Object obj)
	{
		LOGGER().debug(obj);
	}

	public static ILogger LOGGER()
	{
		return LaunchMods.getLOGGER();
	}

	public static void info(Object obj)
	{
		LOGGER().info(obj);
	}

	public static void trace(Object obj)
	{
		LOGGER().trace(obj);
	}

	public static void warn(Object obj)
	{
		LOGGER().warn(obj);
	}

	public static void fatal(Object obj)
	{
		LOGGER().fatal(obj);
	}

	public static void error(Object obj)
	{
		LOGGER().error(obj);
	}

	public static List<Class<?>> MODS_LOADED()
	{
		return LaunchMods.validMods;
	}

	public static LitClassLoader CLASSLOADER()
	{
		return LaunchMods.getINSTANCE().litClassLoader;
	}

	public static LitEventHandler COMMON_NO_SPAM_EVENT_HANDLER()
	{
		return LitEventHandler.COMMON;
	}
}
