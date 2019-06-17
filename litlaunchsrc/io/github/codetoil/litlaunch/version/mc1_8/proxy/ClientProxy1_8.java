package io.github.codetoil.litlaunch.version.mc1_8.proxy;

import io.github.codetoil.litlaunch.launchcommon.Command;
import io.github.codetoil.litlaunch.launchcommon.LaunchMods;
import io.github.codetoil.litlaunch.launchcommon.proxy.CommonProxy;
import io.github.codetoil.litlaunch.version.mc1_8.CommandNew;
import io.github.codetoil.tpsmod.TPSMod;

public class ClientProxy1_8 implements CommonProxy
{
	@Override
	public void preInit()
	{
		LaunchMods.getINSTANCE().getLOGGER().info("preInitialization!");
		for (Command command : TPSMod.commandList) {
			if (Command.Side.CLIENT.equals(command.side) || Command.Side.BOTH.equals(command.side))
				net.minecraftforge.client.ClientCommandHandler.instance.registerCommand(new CommandNew(command));
		}
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
