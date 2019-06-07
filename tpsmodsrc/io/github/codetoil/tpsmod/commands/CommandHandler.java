/*
 * Copyright Codetoil (c) 2019
 */

package io.github.codetoil.tpsmod.commands;

import io.github.codetoil.litlaunch.api.IDoThing;
import io.github.codetoil.litlaunch.backend.LaunchCommon;
import io.github.codetoil.litlaunch.api.LaunchMods;
import io.github.codetoil.tpsmod.MeasureTPSdrop;
import io.github.codetoil.tpsmod.TPSMod;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CommandHandler
{
	public static void executeTPS()
	{
		//LaunchMods.info("executed //tps!");
		double TPS = getTPS();
		String TPS_STR = formatTPS(TPS);
		LaunchCommon.getDoThing().notifyPlayer("[TPS Mod] " + TPS_STR + " tps", IDoThing.Color.YELLOW);
		//LaunchMods.info("TPS: " + TPS_STR);
	}

	private static double getTPS()
	{
		double TPS = Double.NaN;
		int DimPlayer = LaunchCommon.getGetFields().getDimPlayer();
		MeasureTPSdrop[] lTPSdrop = TPSMod.getIndependentDimensionTPSMeasures();
		for (MeasureTPSdrop TPSdrop : lTPSdrop) {
			if (TPSdrop.dimension == DimPlayer) {
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

	public static void executeTPSTOALL()
	{
		//LaunchMods.info("executed //tpstoall!");
		double TPS = getTPS();
		String TPS_STR = formatTPS(TPS);
		LaunchCommon.getDoThing().sendAsChatMessage("The TPS Mod v" + TPSMod.VERSION + " has measured the tps to be " + TPS_STR + " ticks per second");
		//LaunchMods.info("TPS: " + TPS_STR);
	}
}
