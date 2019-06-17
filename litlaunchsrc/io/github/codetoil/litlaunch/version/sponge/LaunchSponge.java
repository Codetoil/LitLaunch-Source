package io.github.codetoil.litlaunch.version.sponge;

import com.google.inject.Inject;
import io.github.codetoil.litlaunch.launchcommon.*;
import io.github.codetoil.litlaunch.version.sponge.proxy.ServerProxySponge;
import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.*;
import org.spongepowered.api.plugin.Plugin;

@Plugin(
		id = "tpsmod",
		name = "TPS-Mod",
		description = "tpsmod sponge version",
		url = "https://github.com/Littoil/TPS-Mod",
		authors = {
				"Littoil"
		}
)
public class LaunchSponge implements ILaunch
{
	public static final String VERSION = "sponge-0.0.0.4-2.0";
	private static LaunchSponge launchsponge;
	/*
		Fields
	 */
	@Inject
	// The Logger injected by sponge. This is the logger used internally
	private Logger logger;

	public LaunchSponge()
	{
		if (launchsponge == null) {
			launchsponge = this;
		}
	}

	/*
		Important private methods to split up the code
	 */

	public static Logger getSpongeLogger()
	{
		return getLaunchSponge().logger;
	}

	/*
		Initialization etc.
	 */

	public static LaunchSponge getLaunchSponge()
	{
		return launchsponge;
	}

	public boolean setProxy()
	{
		boolean result;
		if (LaunchCommon.ccproxy != null) {
			LaunchMods.getINSTANCE().getLOGGER().error("Tried re-setting proxy!");
			result = false;
		} else {
			LaunchMods.getINSTANCE().getLOGGER().info("This is sponge, assuming server");
			//Can launch sponge as a client, but WHY WOULD YOU???
			LaunchCommon.ccproxy = new ServerProxySponge();
			result = true;
		}
		return result;
	}

	@Listener
	public void onConstruction(GameConstructionEvent event)
	{
		if (LaunchCommon.bootstrap(logger, this, LoggerSponge.getInstance())) {
			LaunchMods.getINSTANCE().getLOGGER().info("LitLaunch Bootstrap Completed");
		} else {
			LaunchMods.getINSTANCE().getLOGGER().error("LitLaunch Bootstrap Failed!");
		}
	}

	@Listener
	public void onPreInit(GamePreInitializationEvent event)
	{
		LaunchCommon.preInit();
	}

	@Listener
	public void onInit(GameInitializationEvent event)
	{
		LaunchCommon.init();
	}

	// Get Private Objects

	@Listener
	public void onPostInit(GamePostInitializationEvent event)
	{
		LaunchCommon.postInit();
	}

	@Listener
	public void onServerLoad(GameStartedServerEvent event)
	{
		LaunchCommon.serverLoad();
	}
}
