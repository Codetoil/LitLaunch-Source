package io.github.codetoil.tpsmod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.codetoil.litlaunch.launchcommon.*;
import io.github.codetoil.litlaunch.launchcommon.events.LitEvent;
import io.github.codetoil.tpsmod.commands.CommandHandler;

public class TPSMod implements IMod, LitEventHandler.EventListener {
	public static final String VERSION = "2.0";

	public final static List<Command> commandList = newCommandList();
	public final static Object INSTNACE = new TPSMod();
	private static MeasureTPSdrop[] independentDimensionTPSMeasures;

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
		launchMods.getLOGGER().trace("Got event: " + event);
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
	}
	
	public void Init()
	{
		LaunchMods.getINSTANCE().getLOGGER().info("TPSMod v" + VERSION + " initializing");
	}
	
	public void postInit()
	{
		LaunchMods.getINSTANCE().getLOGGER().info("TPSMod v" + VERSION + " postinitializing");
		int dimensionAmount = LaunchCommon.getGetFields().getDimAmount();
		LaunchMods.getINSTANCE().getLOGGER().info("Dimensions Amount: " + dimensionAmount);
		LaunchMods.getINSTANCE().getLOGGER().info("Dimensions Available: " + Arrays.toString(LaunchCommon.getGetFields().getDimsAvailable()));
		TPSMod.independentDimensionTPSMeasures = new MeasureTPSdrop[dimensionAmount];
		for (int i = 0; i < dimensionAmount; i++)
		{
			MeasureTPSdrop temp = new MeasureTPSdrop(LaunchCommon.getGetFields().getDimsAvailable()[i]);
			TPSMod.independentDimensionTPSMeasures[i] = temp;
			LitEventHandler.COMMON.addListener(temp.calculateTPS);
		}
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
		onTick();
	}
}
