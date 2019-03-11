package io.github.littoil.litlaunch.version.mc1_7;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import io.github.littoil.litlaunch.launchcommon.Command;
import io.github.littoil.litlaunch.launchcommon.LaunchTPSMOD;
import io.github.littoil.litlaunch.launchforge.LaunchForge;
import io.github.littoil.litlaunch.version.mc1_7.proxy.ClientProxy1_7;
import io.github.littoil.litlaunch.version.mc1_7.proxy.ServerProxy1_7;
import io.github.littoil.tpsmod.TPSMod;

@Mod(modid = LaunchForge.MODID, version = Launch1_7.VERSION)
public class Launch1_7 {
    public static final String VERSION = "1.7-0.0.0.2";

    public Launch1_7()
    {
    	LaunchTPSMOD.INSTANCE.LOGGER = new Logger1_7();
    	if (!setProxy())
		{
			LaunchTPSMOD.INSTANCE.LOGGER.error("Proxies not set!");
		}
    }

    public boolean setProxy()
	{
		boolean result;
		if (LaunchForge.ccproxy != null)
		{
			LaunchTPSMOD.INSTANCE.LOGGER.error("Tried re-setting proxy!");
			result = false;
		}
		else
		{
			Side side = FMLCommonHandler.instance().getSide();
			switch (side) {
				case CLIENT:
					LaunchForge.ccproxy = new ClientProxy1_7();
					result = true;
					break;
				case SERVER:
					LaunchForge.ccproxy = new ServerProxy1_7();
					result = true;
					break;
				default:
					LaunchTPSMOD.INSTANCE.LOGGER.error("FML is not sided(client vs server). This may not go well!");
					result = false;
					break;
			}
		}
		return result;
	}

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

}
