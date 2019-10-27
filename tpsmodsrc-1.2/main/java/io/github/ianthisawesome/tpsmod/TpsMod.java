/*
 * Copyright Codetoil (c) 2019
 */

package io.github.ianthisawesome.tpsmod;


import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import io.github.ianthisawesome.tpsmod.proxy.CommonProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = TpsMod.MODID, version = TpsMod.VERSION)
public class TpsMod
{
	public static final String MODID = "tpsmod";
	public static final String VERSION = "1.2";
	private static final Logger LOGGER = LogManager.getLogger();

	public static GetTPS getTPS;

	@SidedProxy(clientSide = "io.github.ianthisawesome.tpsmod.proxy.ClientProxy",
			serverSide = "io.github.ianthisawesome.tpsmod.proxy.ServerProxy")
	public static CommonProxy proxy;

	//Blocks and items etc.
	@EventHandler
	public void preinit(FMLPreInitializationEvent event)
	{
		LOGGER.debug("[TPS Mod]: Preinit");
		proxy.preInit();
	}

	//Recipies and such
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		LOGGER.debug("[TPS Mod]: Init");
		//Timer timer = new Timer();
		//timer.schedule(new TPSCounter(), (long) 0, (long) (timeToMeasure * 1000.0));
	}

	//Interactions w/ other mods
	@EventHandler
	public void postinit(FMLPostInitializationEvent event)
	{
		LOGGER.debug("[TPS Mod]: Postinit");
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		// register server commands

		proxy.serverLoad(event);
	}
}
