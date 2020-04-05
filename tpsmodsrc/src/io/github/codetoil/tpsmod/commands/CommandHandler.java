/*
 * Copyright Codetoil (c) 2019
 */

package io.github.codetoil.tpsmod.commands;

import io.github.codetoil.litlaunch.api.Command;
import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.api.IDoThing;
import io.github.codetoil.litlaunch.api.arguments.ArgumentWrapper;
import io.github.codetoil.litlaunch.api.arguments.IArgumentValue;
import io.github.codetoil.litlaunch.core.LaunchCommon;
import io.github.codetoil.tpsmod.MeasureTPSdrop;
import io.github.codetoil.tpsmod.TPSMod;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class CommandHandler
{
	public static void executeTPS(List<ArgumentWrapper<?>> args)
	{
		FrontEnd.verbose(args);
		Integer dimension = getDimensionFromArgs(args);
		if (dimension == null)
		{
			LaunchCommon.getDoThing().notifyUser("Dimension " + args.get(0) + " is not valid or not loaded. The argument must be an integer.", IDoThing.Color.RED);
			return;
		}
		if (LaunchCommon.getTimeInSeconds() - TPSMod.initialLoadTime > 5.0)
		{
			//LaunchMods.info("executed //tps!");
			double TPS = getTPS(dimension);
			String TPS_STR = formatTPS(TPS);
			LaunchCommon.getDoThing().notifyUser("[TPS Mod v" + TPSMod.VERSION + "] " + TPS_STR + " tps in dimension " + dimension, IDoThing.Color.YELLOW);
			//LaunchMods.info("TPS: " + TPS_STR);
		}
		else
		{
			LaunchCommon.getDoThing().notifyUser("The TPS Mod v" + TPSMod.VERSION + " is still loading. Please wait...", IDoThing.Color.RED);
		}
	}

	private static Integer getDimensionFromArgs(List<ArgumentWrapper<?>> argList)
	{
		Integer result = null;
		if (argList.size() == 1)
		{
			ArgumentWrapper arg = argList.get(0);
			IArgumentValue<?> value = arg.getValue();
			Object val = value.getValue();
			if (val instanceof Integer)
			{
				result = (Integer) val;
			}
		}
		else if (argList.size() == 0)
		{
			if (LaunchCommon.getSide().equals(Command.Side.CLIENT))
			{
				result = LaunchCommon.getGetFields().getDimRunning();
			}
		}
		return result;
	}

	private static double getTPS(int dimension)
	{
		double TPS = Double.NaN;
		MeasureTPSdrop[] lTPSdrop = TPSMod.getIndependentDimensionTPSMeasures();
		for (MeasureTPSdrop TPSdrop : lTPSdrop)
		{
			if (TPSdrop.dimension == dimension)
			{
				TPS = TPSdrop.calculateTPS.getTPS();
			}
		}
		return TPS;
	}

	private static String formatTPS(double TPS)
	{
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		return df.format(TPS);
	}

	public static void executeTPSTOALL(List<ArgumentWrapper<?>> args)
	{
		FrontEnd.verbose(args);
		Integer dimension = getDimensionFromArgs(args);
		if (dimension == null)
		{
			LaunchCommon.getDoThing().notifyUser("Dimension " + args.get(0) + " is not valid or not loaded. The argument must be an integer.", IDoThing.Color.RED);
			return;
		}
		if (LaunchCommon.getTimeInSeconds() - TPSMod.initialLoadTime > 5.0)
		{
			//LaunchMods.info("executed //tpstoall!");
			double TPS = getTPS(dimension);
			String TPS_STR = formatTPS(TPS);
			LaunchCommon.getDoThing().sendAsChatMessage("[TPS Mod v" + TPSMod.VERSION + "] " + TPS_STR + " tps in dimension " + dimension);
			//LaunchMods.info("TPS: " + TPS_STR);
		}
		else
		{
			LaunchCommon.getDoThing().notifyUser("The TPS Mod v" + TPSMod.VERSION + " is still loading. Please wait...", IDoThing.Color.RED);
		}
	}
}
