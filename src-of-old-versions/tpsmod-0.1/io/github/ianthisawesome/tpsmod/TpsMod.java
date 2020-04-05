package io.github.ianthisawesome.tpsmod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Timer;

@Mod(
		modid = "tpsmod",
		version = "0.1"
)
public class TpsMod
{
	public static final String MODID = "tpsmod";
	public static final String VERSION = "0.1";
	private static final Logger LOGGER = LogManager.getLogger();

	@EventHandler
	public void preinit(FMLPreInitializationEvent event)
	{
		LOGGER.info("Preinit");
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		LOGGER.info("Init");
	}

	@EventHandler
	public void postinit(FMLPostInitializationEvent event)
	{
		LOGGER.info("Postinit");
		Timer timer = new Timer();
		timer.schedule(new CheckedLoggedIn(), 0L, 1000L);
	}
}
