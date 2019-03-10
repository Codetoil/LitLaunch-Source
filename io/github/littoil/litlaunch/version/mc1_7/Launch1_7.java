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

@Mod(modid = Launch1_7.MODID, version = Launch1_7.VERSION)
public class Launch1_7 extends LaunchForge<FMLPreInitializationEvent, FMLInitializationEvent, FMLPostInitializationEvent, FMLServerStartingEvent> {
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
		if (ccproxy != null)
		{
			LaunchTPSMOD.INSTANCE.LOGGER.error("Tried re-setting proxy!");
			result = false;
		}
		else
		{
			Side side = FMLCommonHandler.instance().getSide();
			switch (side) {
				case CLIENT:
					ccproxy = new ClientProxy1_7();
					result = true;
					break;
				case SERVER:
					ccproxy = new ServerProxy1_7();
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
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}

	@EventHandler
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@EventHandler
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

	@EventHandler
	@Override
	public void serverLoad(FMLServerStartingEvent event) {
		super.serverLoad(event);
		TPSMod.commandList.forEach((command) -> {
			if (Command.Side.SERVER.equals(command.side) || Command.Side.BOTH.equals(command.side))
					event.registerServerCommand(new CommandNew(command));
		});
	}

}
