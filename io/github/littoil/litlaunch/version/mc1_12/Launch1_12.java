package io.github.littoil.litlaunch.version.mc1_12;

import io.github.littoil.litlaunch.launchcommon.Command;
import io.github.littoil.litlaunch.launchcommon.ILaunch;
import io.github.littoil.litlaunch.launchcommon.LaunchCommon;
import io.github.littoil.litlaunch.launchcommon.LaunchMods;
import io.github.littoil.litlaunch.launchforge.LaunchForge;
import io.github.littoil.litlaunch.version.mc1_12.proxy.ClientProxy1_12;
import io.github.littoil.litlaunch.version.mc1_12.proxy.ServerProxy1_12;
import io.github.littoil.tpsmod.TPSMod;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;

@Mod(modid = LaunchForge.MODID, version = Launch1_12.VERSION)
public class Launch1_12 implements ILaunch {
	public static final String VERSION = "1.12-0.0.0.4-2.0";


	public Launch1_12()
	{
		LaunchCommon.bootstrap(LogManager.getLogger(LaunchForge.MODID), this, Logger1_12.getInstance());
		if (!setProxy()) {
			LaunchMods.getINSTANCE().getLOGGER().error("Proxies not set!");
		}
	}

	/*
		Important private methods to split up the code
	 */

	public boolean setProxy()
	{
		boolean result;
		if (LaunchCommon.ccproxy != null)
		{
			LaunchMods.getINSTANCE().getLOGGER().error("Tried re-setting proxy!");
			result = false;
		}
		else
		{
			Side side = FMLCommonHandler.instance().getSide();
			switch (side) {
				case CLIENT:
					LaunchCommon.ccproxy = new ClientProxy1_12();
					result = true;
					break;
				case SERVER:
					LaunchCommon.ccproxy = new ServerProxy1_12();
					result = true;
					break;
				default:
					LaunchMods.getINSTANCE().getLOGGER().error("FML is not sided(client vs server). This may not go well!");
					result = false;
					break;
			}
		}
		return result;
	}

	/*
		Initialization etc.
	 */

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		LaunchForge.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		LaunchForge.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		LaunchForge.postInit();
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		LaunchForge.serverLoad();
		TPSMod.commandList.forEach((command) -> {
			if (Command.Side.SERVER.equals(command.side) || Command.Side.BOTH.equals(command.side))
				event.registerServerCommand(new CommandNew(command));
		});
	}

	// Get Private Objects
	//nothing
}
