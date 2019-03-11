package io.github.littoil.tpsmod.commands;

import io.github.littoil.litlaunch.launchcommon.LaunchTPSMOD;

public class CommandHandler {
	public static void executeTPS()
	{
		LaunchTPSMOD.INSTANCE.LOGGER.info("executed //tps!");
	}
	public static void executeTPSTOALL()
	{
		LaunchTPSMOD.INSTANCE.LOGGER.info("executed //tpstoall!");
	}
}
