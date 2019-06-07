/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.api;

import io.github.codetoil.litlaunch.backend.LaunchCommon;
import io.github.codetoil.litlaunch.backend.ILogger;
import io.github.codetoil.litlaunch.backend.LitClassLoader;
import io.github.codetoil.litlaunch.event.LitEventHandler;
import io.github.codetoil.litlaunch.exceptions.FailedBootstrapException;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LaunchMods
{
	public final static List<Class<?>> possibilityList = new ArrayList<>();
	public final static List<Class<?>> validMods = new ArrayList<>();
	private final static List<String> MOD_LIST = new ArrayList<>();
	private final static LaunchMods INSTANCE = new LaunchMods();
	public final LitClassLoader litClassLoader = new LitClassLoader();
	private static ILogger LOGGER;

	private LaunchMods()
	{

	}

	public static boolean locateMods()
	{
		trace("Locating Mods!");
		try {
			getINSTANCE().litClassLoader.setClassLoader(LaunchMods.class.getClassLoader());
			// Set classloader to use as the classloader that loaded this class, to make sure all classes use the same classloader
			for (String modPath : MOD_LIST) {
				trace("locating mod \"" + modPath + "\"");
				if (getINSTANCE().litClassLoader.classExists(modPath)) {
					Class<?> lClass = getINSTANCE().litClassLoader.getClass(modPath);
					if (lClass != null) {
						trace("mod \"" + modPath + "\" exists! Registering! Class: " + lClass);
						possibilityList.add(lClass);
					} else {
						trace("Classloader could not find mod \"" + modPath + "\"!");
					}
				} else {
					trace("mod \"" + modPath + "\" does not exist! please make sure the class is in the classpath!");
				}
			}
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
		return possibilityList.size() > 0;
	}

	public static void trace(Object object)
	{
		getLOGGER().trace(object);
	}

	public static LaunchMods getINSTANCE()
	{
		return INSTANCE;
	}

	public static ILogger getLOGGER()
	{
		return LOGGER;
	}

	public static void setLOGGER(ILogger LOGGER)
	{
		if (LaunchMods.LOGGER == null) {
			LaunchMods.LOGGER = LOGGER;
		}
	}

	public static boolean validateMods()
	{
		trace("Validating mods!");
		try {
			for (Class<?> possibility : possibilityList) {
				try {
					// Goal : Validate Mod
					// Step 1 : Get some random instance in order to get main instance (Can't force a static method in java, so the method to get the main instance has to be nonstatic)
					Object randomINSTANCE = possibility.newInstance();
					// Step 2 : Get the main instance as a listener of the mod!
					Method getListener = getINSTANCE().litClassLoader.getMethodOfClass(possibility, "getListener");
					Method getModINSTANCE = getINSTANCE().litClassLoader.getMethodOfClass(possibility, "getModINSTANCE");
					if (getListener != null && getModINSTANCE != null) {
						Object oListener = getListener.invoke(randomINSTANCE);
						Object oMod = getModINSTANCE.invoke(randomINSTANCE);
						if (oListener instanceof LitEventHandler.EventListener && oMod instanceof IMod) {
							trace("Mod \"" + possibility.getName() + "\" is valid! Adding to valid mod list and adding listener!");
							validMods.add(possibility);
							// Step 2a : Convert oListener to EventListener class
							LitEventHandler.EventListener lListener = (LitEventHandler.EventListener) oListener;
							// Step 3 : Add the event listener!
							LitEventHandler.COMMON.addListener(lListener);
						} else {
							trace("Mod \"" + possibility.getName() + "\" does not implement LitEventHandler.EventListener, and such cannot be loaded. Skipping Mod!");
						}
					} else {
						trace("Failed initializing Mod \"" + possibility.getName() + "\" because methods needed to load it cannot be located. Skipping Mod!");
					}
				}
				catch (InstantiationException t) {
					trace("Failed initializing Mod \"" + possibility.getName() + "\" due to an InstantationException! Skipping Mod!");
				}

			}
		}
		catch (Throwable e) {
			e.printStackTrace();
			throw new FailedBootstrapException("Throwable thrown in bootstrap!!!", e);
		}
		return true;
	}

	public static boolean readModList(File configFile) throws IOException
	{
		trace("Reading mod list!");
		boolean result = false;
		if (configFile.exists()) {
			Properties lProperties = new Properties();
			InputStream ioInp = new FileInputStream(configFile);
			lProperties.load(ioInp);
			String verbose = lProperties.getProperty("verbose");
			trace("Verbose option: " + verbose);
			boolean lV = Boolean.valueOf(verbose);
			trace("Verbose boolean: "  + lV);
			LaunchCommon.setVerbose(lV);
			trace("LaunchCommon verbose: " + LaunchCommon.isVerbose());
			String modNum = lProperties.getProperty("Mods");
			if (modNum != null) {
				try {
					long mods = Long.parseUnsignedLong(modNum);
					trace("Number of mods: " + mods);
					for (int i = 1; i <= mods; i++) {
						String modClass = lProperties.getProperty("Mod" + i);
						if (modClass != null) {
							trace("Adding class: " + modClass);
							MOD_LIST.add(modClass);
						} else {
							error("No class given for mod " + i + "!");
						}
					}
				}
				catch (NumberFormatException e) {
					e.printStackTrace();
					throw new FailedBootstrapException("The number of mods is invalid!");
				}
			} else {
				error("No config property \"Mods\"(e.g. Mods=1)");
				setDefaultModList(configFile);
			}
		} else {
			error("No mod config file! Creating new config file!");
			setDefaultModList(configFile);
		}
		return result;
	}

	private static void setDefaultModList(File configFile) throws IOException
	{
		Properties lProperties = new Properties();
		configFile.createNewFile();
		OutputStream ioOut = new FileOutputStream(configFile);
		lProperties.setProperty("Mods", "1");
		lProperties.setProperty("Mod1", "io.github.codetoil.tpsmod.TPSMod");
		lProperties.setProperty("verbose", "false");
		lProperties.store(ioOut, " This is where you specify which mods are to be loaded in LitLaunch.\n Mods gives the number of mods (specifically the number of main-classes) are to be installed.\n Each mod has a main-class, which is where the loader looks at to see if the mod is valid.\n Main-Classes are to be listed by the Mods\n Mods' main class are listed in a property in the format of \"Mod#=<Main-Class>\". For example the tpsmod has a property of \"Mod#=io.github.codetoil.tpsmod.TPSMod\".\n The # symbol is to be replaced by the number, representing the priority it is to be loaded.\n For example \"Mod1=io.github.codetoil.tpsmod.TPSMod\" means that the TPSMod is to be installed with highest priority.\n Mod1 first, Mod2 second, etc.\n Each mod is specified on a newline, but on the same line as the property name.\n Enabling verbose usally spams the debug log, as verbose makes it so all events are logged to the console as trace.");
		if (configFile.exists()) {
			readModList(configFile);
		} else {
			throw new FailedBootstrapException("Couldn't create config file!");
		}

	}

	private static boolean isModAnInterface(String modPath)
	{
		Class<?>[] inters = getINSTANCE().litClassLoader.getClass(modPath).getInterfaces();
		boolean result = false;
		for (Class<?> clazz : inters) {
			result = result || clazz.getName().equals("io.github.codetoil.litlaunch.launchcommon.IMod");
		}
		return result;
	}

	public static void debug(Object object) {getLOGGER().debug(object);
	}

	public static void info(Object object)
	{
		getLOGGER().info(object);
	}

	public static void warn(Object object)
	{
		getLOGGER().warn(object);
	}

	public static void error(Object object)
	{
		getLOGGER().error(object);
	}

	public static void fatal(Object object)
	{
		getLOGGER().fatal(object);
	}
}
