package io.github.littoil.tpsmod.commands;

import io.github.littoil.litlaunch.launchcommon.LaunchTPSMOD;

public class CommandHandler {
	public static class RunnableTPS implements Runnable
	{
		@Override
		public void run() {
			LaunchTPSMOD.INSTANCE.LOGGER.info("test!");
		}
	}
}
