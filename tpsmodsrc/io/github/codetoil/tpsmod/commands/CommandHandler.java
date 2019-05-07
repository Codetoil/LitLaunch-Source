package io.github.codetoil.tpsmod.commands;

import io.github.codetoil.litlaunch.launchcommon.LaunchCommon;
import io.github.codetoil.litlaunch.launchcommon.LaunchMods;

public class CommandHandler {
	public static void executeTPS()
	{
		LaunchMods.getINSTANCE().getLOGGER().info("executed //tps!");
		LaunchMods.getINSTANCE().getLOGGER().info(LaunchCommon.getGetFields().getTotalWorldTime(0));
		LaunchMods.getINSTANCE().getLOGGER().info(LaunchCommon.getTimeInSeconds());
	}
	public static void executeTPSTOALL()
	{
		LaunchMods.getINSTANCE().getLOGGER().info("executed //tpstoall!");
	}
}
