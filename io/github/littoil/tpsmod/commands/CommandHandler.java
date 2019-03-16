package io.github.littoil.tpsmod.commands;

import io.github.littoil.litlaunch.launchcommon.LaunchMods;

public class CommandHandler {
	public static void executeTPS()
	{
		LaunchMods.getINSTANCE().getLOGGER().info("executed //tps!");
	}
	public static void executeTPSTOALL()
	{
		LaunchMods.getINSTANCE().getLOGGER().info("executed //tpstoall!");
	}
}
