package io.github.codetoil.litlaunch.version.mc1_8;

import io.github.codetoil.litlaunch.launchcommon.Command;
import io.github.codetoil.litlaunch.launchcommon.ILaunch;
import io.github.codetoil.litlaunch.launchcommon.LaunchCommon;
import io.github.codetoil.litlaunch.launchcommon.LaunchMods;
import io.github.codetoil.litlaunch.launchforge.LaunchForge;
import io.github.codetoil.litlaunch.version.mc1_8.proxy.ClientProxy1_8;
import io.github.codetoil.litlaunch.version.mc1_8.proxy.ServerProxy1_8;
import io.github.codetoil.tpsmod.TPSMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;

@Mod(modid = LaunchForge.MODID, version = Launch1_8.VERSION)
public class Launch1_8 implements ILaunch {
	public static final String VERSION = "1.8-0.0.0.5-2.0";

	public Launch1_8() throws Throwable
	{
		LaunchCommon.bootstrap(LogManager.getLogger(LaunchForge.MODID), this, Logger1_8.getInstance());
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
					LaunchCommon.ccproxy = new ClientProxy1_8();
					result = true;
					break;
				case SERVER:
					LaunchCommon.ccproxy = new ServerProxy1_8();
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
		MinecraftForge.EVENT_BUS.register(io.github.codetoil.litlaunch.version.mc1_8.EventHandler.class);
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
		for (Command command : TPSMod.commandList)
		{
			if (Command.Side.SERVER.equals(command.side) || Command.Side.BOTH.equals(command.side))
				event.registerServerCommand(new CommandNew(command));
		}
	}
}
