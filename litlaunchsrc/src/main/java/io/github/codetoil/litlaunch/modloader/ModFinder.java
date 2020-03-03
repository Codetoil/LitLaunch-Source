/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.modloader;

import io.github.codetoil.litlaunch.api.EnumRequireOrNot;
import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.api.IMod;
import io.github.codetoil.litlaunch.core.LaunchCommon;
import io.github.codetoil.litlaunch.core.event.LitEventHandler;
import io.github.codetoil.litlaunch.core.exceptions.FailedBootstrapException;

import java.io.File;
import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModFinder
{
	public final static LitClassLoader litClassLoader = new LitClassLoader();
	public final static List<Class<?>> possibilityList = new ArrayList<>();
	public final static List<Class<?>> validMods = new ArrayList<>();
	private final static List<String> MOD_LIST = new ArrayList<>();


	public static void locateMods(Path modsFolder)
	{
		FrontEnd.info("Locating mods!");
		FrontEnd.trace(modsFolder);
		if (modsFolder.toFile().isDirectory())
		{
			File[] mods = modsFolder.toFile().listFiles();
			FrontEnd.trace(Arrays.toString(mods));
			for (File mod :
					mods
			)
			{
				FrontEnd.trace(mod);
				if (mod.isFile())
				{
					testIfMod(mod);
				}
			}
		}
	}

	private static boolean testIfMod(File mod)
	{
		FrontEnd.debug("testing mod " + mod);
		boolean result = false;
		try
		{
			JarFile jarMod = new JarFile(mod);
			Enumeration<JarEntry> files = jarMod.entries();
			URL[] urls = {new URL("jar:file:" + mod.getAbsolutePath() + "!/")};
			URLClassLoader cl = URLClassLoader.newInstance(urls, ModFinder.class.getClassLoader());
			FrontEnd.trace(ModFinder.class.getClassLoader());
			FrontEnd.trace(cl);
			while (files.hasMoreElements())
			{
				JarEntry lEntry = files.nextElement();
				FrontEnd.trace(lEntry);
				if (lEntry.isDirectory() || !lEntry.getName().endsWith(".class"))
				{
					continue;
				}
				try
				{
					if (testIfModClass(lEntry, cl))
					{
						FrontEnd.trace("Found Mod Class! Yippy!");
					}
				}
				catch (Throwable t)
				{
					FrontEnd.error("Something went wrong when testing " + lEntry + ", Skipping!");
					t.printStackTrace();
				}
			}
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
		return result;
	}

	private static boolean testIfModClass(JarEntry pEntry, ClassLoader cl) throws ClassNotFoundException
	{
		String className = pEntry.getName().substring(0, pEntry.getName().length() - 6);
		className = className.replace('/', '.');
		SoftReference<Class> c = new SoftReference<>(cl.loadClass(className));
		if (c.get() != null)
		{
			Class[] interfaces = c.get().getInterfaces();
			boolean isMod = false;
			boolean isListener = false;
			for (Class interface_ :
					interfaces)
			{
				FrontEnd.debug(interface_);
				if (interface_.getName().equals(IMod.class.getName()))
				{
					FrontEnd.trace("Found Class That Implements IMod!!! :D");
					isMod = true;
				}
				if (interface_.getName().equals(LitEventHandler.EventListener.class.getName()))
				{
					FrontEnd.trace("Found Listener Class!");
					isListener = true;
				}
			}
			if (isMod && isListener)
			{
				FrontEnd.trace("Mod " + c + " is a LitLaunch Mod!!");
				ModFinder.possibilityList.add(c.get());
				FrontEnd.trace("Added to possibility list");
				return true;
			}
		}
		return false;
	}

	public static boolean validateMods()
	{
		FrontEnd.trace("Validating mods!");
		try
		{
			for (Class<?> possibility : possibilityList)
			{
				try
				{
					// Goal : Validate Mod
					// Step 1 : Get some random instance in order to get main instance (Can't force a static method in java, so the method to get the main instance has to be nonstatic)
					Object randomINSTANCE = possibility.newInstance();
					// Step 2 : Get the main instance as a listener of the mod!
					Method getListener = litClassLoader.getMethodOfClass(possibility, "getListener");
					Method getModINSTANCE = litClassLoader.getMethodOfClass(possibility, "getModINSTANCE");
					if (getListener != null && getModINSTANCE != null)
					{
						Object oListener = getListener.invoke(randomINSTANCE);
						if (oListener instanceof LitEventHandler.EventListener)
						{
							if (!verifySide(getModINSTANCE))
							{
								FrontEnd.trace("Mod \"" + possibility.getName() + "\" is valid! Adding to valid mod list and adding listener!");
								validMods.add(possibility);
								// Step 2a : Convert oListener to EventListener class
								LitEventHandler.EventListener lListener = (LitEventHandler.EventListener) oListener;
								// Step 3 : Add the event listener!
								LitEventHandler.COMMON.addListener(lListener);
							}
							else
							{
								FrontEnd.trace("Mod \"" + possibility.getName() + "\" does not work on the given side, and will not load! Skipping!");
							}
						}
						else
						{
							FrontEnd.error("Mod \"" + possibility.getName() + "\" does not implement LitEventHandler.EventListener, and such cannot be loaded. Skipping Mod!");
						}
					}
					else
					{
						FrontEnd.error("Failed initializing Mod \"" + possibility.getName() + "\" because required methods cannot be located. Skipping it!");
					}
				}
				catch (InstantiationException t)
				{
					FrontEnd.error("Failed initializing Mod \"" + possibility.getName() + "\" due to an InstantationException! Skipping Mod!");
				}

			}
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			throw new FailedBootstrapException("Throwable thrown in bootstrap!!!", e);
		}
		return true;
	}

	private static boolean verifySide(Object object)
	{
		if (object instanceof IMod)
		{
			IMod mod = (IMod) object;
			switch (FrontEnd.SIDE())
			{
				case BOTH:
				case CLIENT:
					return !mod.onClient().equals(EnumRequireOrNot.INCOMPATIBLE);
				case SERVER:
					return !mod.onServer().equals(EnumRequireOrNot.INCOMPATIBLE);
			}
		}
		return false;
	}
}
