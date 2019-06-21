package io.github.codetoil.litlaunch.version.mc1_13.proxy;

import io.github.codetoil.litlaunch.api.Command;
import io.github.codetoil.litlaunch.api.CommonProxy;
import io.github.codetoil.litlaunch.api.LaunchMods;
import io.github.codetoil.litlaunch.version.mc1_13.CommandNew;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class ClientProxy1_13 implements CommonProxy
{
	public final static List<Command> commandList = new ArrayList<>();
	public final static List<String> commandNameList = new ArrayList<>();

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
					LaunchMods.error("Mod " + modClass + " does not have a field named \"commandList\". This is neccesary for the command api to work though. Skipping!");
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

	}

	@Override
	public void postInit()
	{

	}

	@Override
	public void serverLoad()
	{

	}

	public List<String> getCommandNameList()
	{
		return commandNameList;
	}

	@SubscribeEvent
	public void onChat(ClientChatEvent e)
	{
		if (e.getMessage() != null) {
			if (commandNameList.contains(e.getMessage().substring(1))) {
				e.setCanceled(true);
				commandList.forEach((command) -> {
					LaunchMods.info(command.name);
					if (e.getMessage().equals("/" + command.name)) {

						try {
							command.runnable.run();
						}
						catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
			}
		}
	}
}
