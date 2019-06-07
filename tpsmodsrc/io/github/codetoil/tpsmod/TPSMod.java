/*
 * Copyright Codetoil (c) 2019
 */

package io.github.codetoil.tpsmod;

import io.github.codetoil.litlaunch.FrontEnd;
import io.github.codetoil.litlaunch.api.Command;
import io.github.codetoil.litlaunch.api.IMod;
import io.github.codetoil.litlaunch.backend.LaunchCommon;
import io.github.codetoil.litlaunch.api.LaunchMods;
import io.github.codetoil.litlaunch.event.LitEventHandler;
import io.github.codetoil.litlaunch.event.LitEvent;
import io.github.codetoil.tpsmod.commands.CommandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TPSMod implements IMod, LitEventHandler.EventListener
{
	public static final String VERSION = "2.0";

	public final static List<Command> commandList = newCommandList();
	public final static Object INSTNACE = new TPSMod();
	private static MeasureTPSdrop[] independentDimensionTPSMeasures;

	public TPSMod()
	{
	}

	public static MeasureTPSdrop[] getIndependentDimensionTPSMeasures()
	{
		return independentDimensionTPSMeasures;
	}

	private static List<Command> newCommandList()
	{
		List<Command> internalCommandList = new ArrayList<>();
		try {
			internalCommandList.add(new Command("/tps", " //tps [help]", CommandHandler::executeTPS, Command.Side.BOTH));
			internalCommandList.add(new Command("/tpstoall", " //tpstoall [help] or //alltps [help]", CommandHandler::executeTPSTOALL, Command.Side.BOTH));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return internalCommandList;
	}

	@Override
	public List<Command> getCommandList()
	{
		return commandList;
	}

	public void construction()
	{
		FrontEnd.info("TPSMod v" + VERSION + " constructing");
	}

	public void preInit()
	{
		FrontEnd.info("TPSMod v" + VERSION + " preinitializing");
	}

	public void Init()
	{
		FrontEnd.info("TPSMod v" + VERSION + " initializing");
	}

	public void postInit()
	{
		FrontEnd.info("TPSMod v" + VERSION + " postinitializing");
		int dimensionAmount = FrontEnd.GET_FIELDS().getDimsAvailable().length;
		FrontEnd.info("Dimensions Amount: " + dimensionAmount);
		FrontEnd.info("Dimensions Available: " + Arrays.toString(FrontEnd.GET_FIELDS().getDimsAvailable()));
		TPSMod.independentDimensionTPSMeasures = new MeasureTPSdrop[dimensionAmount];
		for (int i = 0; i < dimensionAmount; i++) {
			MeasureTPSdrop temp = new MeasureTPSdrop(FrontEnd.GET_FIELDS().getDimsAvailable()[i]);
			TPSMod.independentDimensionTPSMeasures[i] = temp;
		}
	}

	public void serverLoad()
	{
		FrontEnd.info("TPSMod v" + VERSION + " starting server");
	}

	@Override
	public IMod getModINSTANCE()
	{
		return (IMod) INSTNACE;
	}

	@Override
	public void ReceivedEvent(LitEvent event)
	{
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
		}
		catch (IllegalArgumentException e) {
			//LaunchMods.debug("Unknown event " + event.toString());
		}
	}

	@Override
	public LitEventHandler.EventListener getListener()
	{
		return (LitEventHandler.EventListener) INSTNACE;
	}

	public enum Events
	{
		Construction(),
		PreInit(),
		Init(),
		PostInit(),
		ServerLoad();
	}
}
