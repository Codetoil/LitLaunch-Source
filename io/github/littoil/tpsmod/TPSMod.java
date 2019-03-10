package io.github.littoil.tpsmod;

import java.util.ArrayList;
import java.util.List;

import io.github.littoil.litlaunch.launchcommon.Command;
import io.github.littoil.litlaunch.launchcommon.LitEventHandler;
import io.github.littoil.litlaunch.launchcommon.LaunchTPSMOD;
import io.github.littoil.litlaunch.launchcommon.events.LitEvent;
import io.github.littoil.tpsmod.commands.CommandHandler;

public class TPSMod implements LitEventHandler.EventListener {
	public static final String VERSION = "1.2.1";

	public static List<Command> commandList = newCommandList();

	public TPSMod()
	{

	}

	private static List<Command> newCommandList()
	{
		List<Command> internalCommandList = new ArrayList<Command>();
		try {
			internalCommandList.add(new Command("/tps", "message.tpsmod.tps.help", CommandHandler.class.getMethod("executeTPS"), Command.Side.BOTH));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return internalCommandList;
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
				serverStart();
				break;
		}
	}

	public void preInit()
	{
		LaunchTPSMOD.INSTANCE.LOGGER.info("TPSMod v" + VERSION + " preinitializing");
	}
	
	public void Init()
	{
		LaunchTPSMOD.INSTANCE.LOGGER.info("TPSMod v" + VERSION + " initializing");
	}
	
	public void postInit()
	{
		LaunchTPSMOD.INSTANCE.LOGGER.info("TPSMod v" + VERSION + " postinitializing");
	}
	public void serverStart()
	{
		LaunchTPSMOD.INSTANCE.LOGGER.info("TPSMod v" + VERSION + " starting server");
	}

}
