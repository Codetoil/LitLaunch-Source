/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.version.mc1_12.proxy;

import io.github.codetoil.litlaunch.api.Command;
import io.github.codetoil.litlaunch.api.CommonProxy;
import io.github.codetoil.litlaunch.api.LaunchMods;
import io.github.codetoil.litlaunch.version.mc1_12.CommandNew;

import java.util.List;

public class ClientProxy1_12 implements CommonProxy
{
	@Override
	public void preInit()
	{
		LaunchMods.info("preInitialization!");
		LaunchMods.validMods.forEach((modClass) -> {
			try {
				Object oCommands = modClass.getField("commandList").get(null);
				List lCommands;
				if (oCommands instanceof List) {
					lCommands = (List) oCommands;
					lCommands.forEach((command) -> {
						if (command instanceof Command) {
							if (Command.Side.CLIENT.equals(((Command) command).side) || Command.Side.BOTH.equals(((Command) command).side))
								net.minecraftforge.client.ClientCommandHandler.instance.registerCommand(new CommandNew((Command) command));
						}
					});
				} else {
					LaunchMods.error("Mod " + modClass + " does not have a method named \"commandList\". This is neccesary for the api to work though. Skipping!");
				}

			}
			catch (Throwable pThrowable) {
				pThrowable.printStackTrace();
			}

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
