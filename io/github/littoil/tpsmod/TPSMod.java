package io.github.littoil.tpsmod;

import java.util.ArrayList;
import java.util.List;

import io.github.littoil.litlaunch.launchcommon.Command;
import io.github.littoil.litlaunch.launchcommon.IMod;
import io.github.littoil.litlaunch.launchcommon.LitEventHandler;
import io.github.littoil.litlaunch.launchcommon.LaunchMods;
import io.github.littoil.litlaunch.launchcommon.events.LitEvent;
import io.github.littoil.tpsmod.commands.CommandHandler;

public class TPSMod implements IMod, LitEventHandler.EventListener {
	public static final String VERSION = "1.2.1";

	public final static List<Command> commandList = newCommandList();
	public final static IMod INSTNACE = new TPSMod();

	public TPSMod()
	{
	}

	@Override
	public IMod getMainINSTNACE() {
		return INSTNACE;
	}

	private static List<Command> newCommandList()
	{
		List<Command> internalCommandList = new ArrayList<Command>();
		try {
			internalCommandList.add(new Command("/tps", "message.tpsmod.tps.help", CommandHandler::executeTPS, Command.Side.BOTH));
			internalCommandList.add(new Command("/tpstoall", "message.tpsmod.tpstoall.help", CommandHandler::executeTPSTOALL, Command.Side.BOTH));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return internalCommandList;
	}

	@Override
	public List<Command> getCommandList() {
		return commandList;
	}

	@Override
	public void RecievedEvent(LitEvent event) {
		switch (event.getType())
		{
			case "PreInit":
				preInit();
				break;
			case "Init":
				Init();
				break;
			case "PostInit":
				postInit();
				break;
			case "ServerLoad":
				serverLoad();
				break;
		}
	}

	public void preInit()
	{
		LaunchMods.getINSTANCE().getLOGGER().info("TPSMod v" + VERSION + " preinitializing");
	}
	
	public void Init()
	{
		LaunchMods.getINSTANCE().getLOGGER().info("TPSMod v" + VERSION + " initializing");
	}
	
	public void postInit()
	{
		LaunchMods.getINSTANCE().getLOGGER().info("TPSMod v" + VERSION + " postinitializing");
	}

	public void serverLoad()
	{
		LaunchMods.getINSTANCE().getLOGGER().info("TPSMod v" + VERSION + " starting server");
	}

}
