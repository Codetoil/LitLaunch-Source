package io.github.codetoil.litlaunch.version.mc1_13;

import io.github.codetoil.litlaunch.api.IMod;
import io.github.codetoil.litlaunch.api.LaunchMods;
import io.github.codetoil.litlaunch.event.LitEventHandler;
import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModLoading
{
	final static Logger fLogger = LogManager.getLogger("litlaunnch modloader");
 	public static void locateMods()
	{
		fLogger.info("Locating mods!");
		Path modsFolder = FMLLoader.getGamePath().resolve("mods_litlaunch");
		fLogger.debug(modsFolder);
		if (modsFolder.toFile().isDirectory())
		{
			File[] mods = modsFolder.toFile().listFiles();
			fLogger.debug(Arrays.toString(mods));
			for (File mod:
					mods
			) {
				fLogger.debug(mod);
				if (mod.isFile())
				{
					testIfMod(mod);
				}
			}
		}
	}

	private static boolean testIfMod(File mod)
	{
		fLogger.debug("testing mod " + mod);
		boolean result = false;
		try {
			JarFile jarMod = new JarFile(mod);
			Enumeration<JarEntry> files = jarMod.entries();
			URL[] urls = {new URL("jar:file:" + mod.getAbsolutePath()+"!/")};
			URLClassLoader cl = URLClassLoader.newInstance(urls, ModLoading.class.getClassLoader());
			while (files.hasMoreElements())
			{
				JarEntry lEntry = files.nextElement();
				fLogger.debug(lEntry);
				if (lEntry.isDirectory() || !lEntry.getName().endsWith(".class"))
				{
					continue;
				}
				String className = lEntry.getName().substring(0,lEntry.getName().length()-6);
				className = className.replace('/', '.');
				Class c = cl.loadClass(className);
				Class[] interfaces = c.getInterfaces();
				boolean isMod = false;
				boolean isListener = false;
				for (Class interface_:
				interfaces) {
					fLogger.debug(interface_);
					if (interface_.getName().equals(IMod.class.getName()))
					{
						fLogger.debug("Found Class That Implements IMod!!! :D");
						isMod = true;
					}
					if (interface_.getName().equals(LitEventHandler.EventListener.class.getName()))
					{
						fLogger.debug("Found Listener Class!");
						isListener = true;
					}
				}
				if (isMod && isListener)
				{
					fLogger.debug("Mod " + c + " is a LitLaunch Mod!!");
					LaunchMods.possibilityList.add(c);
					fLogger.debug("Added to possibility list");
				}
				fLogger.debug(c);
			}
		} catch (Throwable t)
		{
			t.printStackTrace();
		}
		return result;
	}
}
