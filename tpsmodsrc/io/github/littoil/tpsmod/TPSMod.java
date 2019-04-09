package io.github.littoil.tpsmod;

import java.util.ArrayList;
import java.util.List;

import io.github.littoil.litlaunch.launchcommon.*;
import io.github.littoil.litlaunch.launchcommon.events.LitEvent;
import io.github.littoil.tpsmod.commands.CommandHandler;

public class TPSMod implements IMod, LitEventHandler.EventListener {
	public static final String VERSION = "2.0";

	public final static List<Command> commandList = newCommandList();
	public final static Object INSTNACE = new TPSMod();

	public TPSMod() {
	}

	public void initializeListener() {
		LitEventHandler.COMMON.addListener(this);
	}

	@Override
	public IMod getMainINSTNACE() {
		return (IMod) INSTNACE;
	}

	@Override
	public LitEventHandler.EventListener getMainInstance() {
		return (LitEventHandler.EventListener) INSTNACE;
	}

	private static List<Command> newCommandList() {
		List<Command> internalCommandList = new ArrayList<Command>();
		try {
			internalCommandList.add(new Command("/tps", "message.tpsmod.tps.help", new Runnable() {
				@Override
				public void run() {
					CommandHandler.executeTPS();
				}
			}, Command.Side.BOTH));
			internalCommandList.add(new Command("/tpstoall", "message.tpsmod.tpstoall.help", new Runnable() {
				@Override
				public void run() {
					CommandHandler.executeTPSTOALL();
				}
			}, Command.Side.BOTH));
		}
		catch (Exception e) {
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
		RecievedEvent(event, LaunchMods.getINSTANCE());
	}

	public void RecievedEvent(LitEvent event, LaunchMods launchMods)
	{
		launchMods.getLOGGER().debug("Got event: " + event);
		try {
			switch (Enum.valueOf(TPSMod.Events.class, event.getType())) {
				case Construction:
					construction();
					break;
				case PreInit:
					preInit();
					break;
				case Init:
					Init();
					break;
				case PostInit:
					postInit();
					break;
				case ServerLoad:
					serverLoad();
					break;
				default:
					break;
			}
		} catch(IllegalArgumentException e)
		{
			LaunchMods.getINSTANCE().getLOGGER().debug("Unknown event " + event.toString());
		}

	}

	public void construction()
	{
		initializeListener();
	}

	public void preInit()
	{
		LaunchMods.getINSTANCE().getLOGGER().info("TPSMod v" + VERSION + " preinitializing");
		LitEventHandler.COMMON.addListener(MeasureTPSdrop.INSTNACE);
		LitEventHandler.COMMON.addListener(MeasureTPSdrop.INSTNACE.calculateTPS);
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

	public enum Events
	{
		Construction(),
		PreInit(),
		Init(),
		PostInit(),
		ServerLoad(),
		updateTPS(),
		checkIFTWTDrop();
	}

}
