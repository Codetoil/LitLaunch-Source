package io.github.ianthisawesome.tpsmod.proxy;

import cpw.mods.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommonProxy
{

	protected static final Logger LOGGER = LogManager.getLogger();

	public void preInit()
	{
		LOGGER.debug("Common Preinit Proxy");
	}

	public void serverLoad(FMLServerStartingEvent event)
	{

	}


}
