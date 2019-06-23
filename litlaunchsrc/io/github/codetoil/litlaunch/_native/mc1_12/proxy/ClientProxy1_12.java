/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch._native.mc1_12.proxy;

import io.github.codetoil.litlaunch.api.Command;
import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.core.CommonProxy;
import io.github.codetoil.litlaunch.core.LaunchCommon;
import io.github.codetoil.litlaunch.modloader.ModFinder;
import io.github.codetoil.litlaunch._native.mc1_12.CommandNew;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class ClientProxy1_12 implements CommonProxy
{
	@Override
	public void setGamePath()
	{
		FrontEnd.info("Lit Launch Client Proxy Setting Game Path On Lit Launch!");
		LaunchCommon.setGamePath(Minecraft.getMinecraft().mcDataDir.toPath());
	}

	@Override
	public void construction()
	{
		FrontEnd.info("Lit Launch Client Proxy Constructing!");
	}

	@Override
	public void preInit()
	{
		FrontEnd.info("Lit Launch Client Proxy PreInitializing!");
		ModFinder.validMods.forEach((modClass) -> {
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
					FrontEnd.error("Mod " + modClass + " does not have a method named \"commandList\". This is neccesary for the api to work though. Skipping!");
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
		FrontEnd.info("Lit Launch Client Proxy Initializing!");

	}

	@Override
	public void postInit()
	{
		FrontEnd.info("Lit Launch Client Proxy PostInitializing!");

	}

	@Override
	public void serverLoad()
	{
		FrontEnd.info("Lit Launch Client Proxy Loading Server!");

	}
}
