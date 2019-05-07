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
import io.github.littoil.litlaunch.launchcommon.ILaunch;
import io.github.littoil.litlaunch.launchcommon.LaunchCommon;
import io.github.littoil.litlaunch.launchcommon.LaunchMods;
import io.github.littoil.litlaunch.launchforge.LaunchForge;
import io.github.littoil.litlaunch.version.mc1_7.proxy.ClientProxy1_7;
import io.github.littoil.litlaunch.version.mc1_7.proxy.ServerProxy1_7;
import io.github.littoil.tpsmod.TPSMod;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;

@Mod(modid = LaunchForge.MODID, version = Launch1_7.VERSION)
public class Launch1_7 implements ILaunch {
    public static final String VERSION = "1.7-0.0.0.5-2.0";

    public Launch1_7() throws Throwable
    {
	    LaunchCommon.bootstrap(LogManager.getLogger(LaunchForge.MODID), this, Logger1_7.getInstance());
	    LaunchCommon.setGetFields(GetFields.INSTANCE);
    }

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
					LaunchCommon.ccproxy = new ClientProxy1_7();
					result = true;
					break;
				case SERVER:
					LaunchCommon.ccproxy = new ServerProxy1_7();
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

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(io.github.littoil.litlaunch.version.mc1_7.EventHandler.class);
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
