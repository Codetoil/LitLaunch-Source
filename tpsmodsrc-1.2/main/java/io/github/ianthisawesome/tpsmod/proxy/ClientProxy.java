/*
 * Copyright Codetoil (c) 2019
 */

package io.github.ianthisawesome.tpsmod.proxy;

import io.github.ianthisawesome.tpsmod.GetTPS;
import io.github.ianthisawesome.tpsmod.TpsInitialLoad;
import io.github.ianthisawesome.tpsmod.TpsMod;
import io.github.ianthisawesome.tpsmod.commands.CommandALLTPS;
import io.github.ianthisawesome.tpsmod.commands.CommandTPS;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;

//Clients only
public class ClientProxy extends CommonProxy
{
	public TpsInitialLoad tpsInitLoad = new TpsInitialLoad();

	@Override
	public void preInit()
	{
		// TODO Auto-generated method stub
		LOGGER.debug("Client Preinit Proxy");
		ClientCommandHandler.instance.registerCommand(new CommandTPS());
		ClientCommandHandler.instance.registerCommand(new CommandALLTPS());
		TpsMod.getTPS = new GetTPS();
		MinecraftForge.EVENT_BUS.register(tpsInitLoad);
	}

}
