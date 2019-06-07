package io.github.ianthisawesome.tpsmod.proxy;

import cpw.mods.fml.common.event.FMLServerStartingEvent;
import io.github.ianthisawesome.tpsmod.commands.CommandALLTPS;
import io.github.ianthisawesome.tpsmod.commands.CommandTPS;

//Dedicated servers only
public class ServerProxy extends CommonProxy {

	@Override
	public void preInit() {
		// TODO Auto-generated method stub
		super.preInit();
		LOGGER.debug("Server Preinit Proxy");
		
	}
	
	@Override
	public void serverLoad(FMLServerStartingEvent event)
	{
		super.serverLoad(event);
		LOGGER.debug("Server Load");
		event.registerServerCommand(new CommandTPS());
		event.registerServerCommand(new CommandALLTPS());
	}

}
