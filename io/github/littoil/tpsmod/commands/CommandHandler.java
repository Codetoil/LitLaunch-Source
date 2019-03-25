package io.github.littoil.tpsmod.commands;

import io.github.littoil.litlaunch.launchcommon.IGetFields;
import io.github.littoil.litlaunch.launchcommon.LaunchCommon;
import io.github.littoil.litlaunch.launchcommon.LaunchMods;
import net.minecraft.launchwrapper.Launch;

public class CommandHandler {
	public static void executeTPS()
	{
		LaunchMods.getINSTANCE().getLOGGER().info("executed //tps!");
		LaunchMods.getINSTANCE().getLOGGER().info(LaunchCommon.getGetFields().getTotalWorldTime());
		LaunchMods.getINSTANCE().getLOGGER().info(LaunchCommon.getTimeInSeconds());
	}
	public static void executeTPSTOALL()
	{
		LaunchMods.getINSTANCE().getLOGGER().info("executed //tpstoall!");
	}
}
