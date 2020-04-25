/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.api;

import io.github.codetoil.litlaunch.core.CommonProxy;
import io.github.codetoil.litlaunch.core.ILogger;
import io.github.codetoil.litlaunch.core.LaunchCommon;
import io.github.codetoil.litlaunch.core.event.LitEventHandler;
import io.github.codetoil.litlaunch.modloader.LitClassLoader;
import io.github.codetoil.litlaunch.modloader.ModFinder;

import java.util.List;

/**
 * The front end of LitLaunch, allows to access to the api from a single class
 */
public class FrontEnd {
    public static String LITLAUNCH_VERSION = LaunchCommon.VERSION;
    public static final String LITLAUNCH_MOD = LaunchCommon.MODID;
    public static final ChainableMap<String, Object> EMPTY = LaunchCommon.EMPTY;

    public static String minecraftVersion() {
        return LaunchCommon.getGetFields().getVERSION();
    }

    public static CommonProxy activeProxy() {
        return LaunchCommon.getCcproxy();
    }

    public static IGetFields getFields() {
        return LaunchCommon.getGetFields();
    }

    public static IDoThing doThingObject() {
        return LaunchCommon.getDoThing();
    }

    public static boolean isLitLaunchVerbose() {
        return LaunchCommon.isVerbose();
    }

    public static double timeFrame() {
        return LaunchCommon.getTimeInit();
    }

    public static Command.Side getSide() {
        return LaunchCommon.getSide();
    }

    public static void debug(Object obj) {
        getLogger().debug(obj);
    }

    public static ILogger getLogger() {
        return LaunchCommon.getLOGGER();
    }

    public static void info(Object obj) {
        getLogger().info(obj);
    }

    public static void trace(Object obj) {
        getLogger().trace(obj);
    }

    public static void warn(Object obj) {
        getLogger().warn(obj);
    }

    public static void fatal(Object obj) {
        getLogger().fatal(obj);
    }

    public static void error(Object obj) {
        getLogger().error(obj);
    }

    public static void verbose(Object obj) {
        getLogger().verbose(obj);
    }

    public static List<Class<?>> modsLoaded() {
        return ModFinder.validMods;
    }

    public static LitClassLoader classLoader() {
        return ModFinder.litClassLoader;
    }

    public static LitEventHandler commonEventHandler() {
        return LitEventHandler.COMMON;
    }

    public static LitEventHandler clientEventHandler() {
        return LitEventHandler.CLIENT;
    }

    public static LitEventHandler serverEventHandler() {
        return LitEventHandler.SERVER;
    }


}
