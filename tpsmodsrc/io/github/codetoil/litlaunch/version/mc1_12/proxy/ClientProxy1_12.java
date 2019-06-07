/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.version.mc1_12.proxy;

import io.github.codetoil.litlaunch.launchcommon.Command;
import io.github.codetoil.litlaunch.launchcommon.LaunchMods;
import io.github.codetoil.litlaunch.launchcommon.proxy.CommonProxy;
import io.github.codetoil.litlaunch.version.mc1_12.CommandNew;
import io.github.codetoil.tpsmod.TPSMod;

public class ClientProxy1_12 implements CommonProxy
{
	@Override
	public void preInit()
	{
		LaunchMods.info("preInitialization!");
		TPSMod.commandList.forEach((command) -> {
			if (Command.Side.CLIENT.equals(command.side) || Command.Side.BOTH.equals(command.side))
				net.minecraftforge.client.ClientCommandHandler.instance.registerCommand(new CommandNew(command));
		});
	}

	@Override
	public void init()
	{
	}

	@Override
	public void postInit()
	{

	}

	@Override
	public void serverLoad()
	{

	}
}
