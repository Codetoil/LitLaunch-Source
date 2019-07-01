package io.github.codetoil.litlaunch._native.mc1_13.proxy;

import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.api.Command;
import io.github.codetoil.litlaunch.core.CommonProxy;
import io.github.codetoil.litlaunch.core.ConfigFile;
import io.github.codetoil.litlaunch.core.LaunchCommon;
import io.github.codetoil.litlaunch.modloader.ModFinder;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientProxy1_13 implements CommonProxy
{
	public final static List<Command> commandList = new ArrayList<>();
	public final static List<String> commandNameList = new ArrayList<>();

	@Override
	public void setGamePath()
	{
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
							{
								if (Command.Side.CLIENT.equals(((Command) command).side) || Command.Side.BOTH.equals(((Command) command).side))
								{
									commandList.add((Command) command);
									commandNameList.add(((Command) command).name);
								}
							}

						}
					});
				} else {
					FrontEnd.error("Mod " + modClass + " does not have a field named \"commandList\". This is neccesary for the command api to work though. Skipping!");
				}

			}
			catch (Throwable pThrowable) {
				pThrowable.printStackTrace();
			}

		});
		MinecraftForge.EVENT_BUS.register(this);
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

	public List<String> getCommandNameList()
	{
		return commandNameList;
	}

	@SubscribeEvent
	public void onChat(ClientChatEvent e)
	{
		if (e.getMessage() != null) {
			for (Command command: commandList) {
				if (e.getMessage().contains("/" + command.name))
				{
					e.setCanceled(true);
					String[] lStrings = e.getMessage().split(" ");
					List<String> lStringList = new ArrayList<>(Arrays.asList(lStrings));
					FrontEnd.debug("strlistinit: " + lStringList);
					if (!lStringList.isEmpty())
					{
						lStringList.remove(0);
					}
					FrontEnd.info(command.name);
					FrontEnd.info("strlist: " + lStringList);
					try {
						command.methodToRun.accept(lStringList);
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}
}
