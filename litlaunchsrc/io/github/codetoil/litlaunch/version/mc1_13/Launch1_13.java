package io.github.codetoil.litlaunch.version.mc1_13;

import io.github.codetoil.litlaunch.launchcommon.*;
import io.github.codetoil.litlaunch.launchforge.*;
import io.github.codetoil.litlaunch.version.mc1_13.proxy.ClientProxy1_13;
import io.github.codetoil.litlaunch.version.mc1_13.proxy.ServerProxy1_13;
import io.github.codetoil.tpsmod.TPSMod;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.*;
import net.minecraftforge.fml.javafmlmod.*;
import net.minecraftforge.fml.loading.*;
import org.apache.logging.log4j.LogManager;

@Mod(LaunchForge.MODID)
public class Launch1_13 implements ILaunch
{
	public static final String VERSION = "1.13-0.0.0.4-2.0";

	public Launch1_13() throws Throwable
	{

		LaunchCommon.bootstrap(LogManager.getLogger(LaunchForge.MODID), this, Logger1_13.getInstance());
		LaunchCommon.setGetFields(GetFields.INSTANCE);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(EventHandler.class);
		//FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverLoad);
		LaunchMods.getINSTANCE().getLOGGER().info("Initialized Launch1_13");
	}

	public void setup(final FMLCommonSetupEvent event)
	{

		LaunchForge.preInit();
	}

	public void setupClient(final FMLClientSetupEvent event)
	{
		LaunchCommon.ccproxy.preInit();
	}

	public boolean setProxy()
	{
		boolean result;
		if (LaunchCommon.ccproxy != null) {
			LaunchMods.getINSTANCE().getLOGGER().error("Tried re-setting proxy!");
			result = false;
		} else {
			Dist dist = FMLEnvironment.dist;
			switch (dist) {
				case CLIENT:
					LaunchCommon.ccproxy = new ClientProxy1_13();
					result = true;
					break;
				case DEDICATED_SERVER:
					LaunchCommon.ccproxy = new ServerProxy1_13();
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

	@SubscribeEvent
	public void serverLoad(FMLServerStartingEvent event)
	{
		LaunchForge.serverLoad();
		TPSMod.commandList.forEach((command) -> {
			if (io.github.codetoil.litlaunch.launchcommon.Command.Side.SERVER.equals(command.side) || io.github.codetoil.litlaunch.launchcommon.Command.Side.BOTH.equals(command.side)) {
				new CommandNew(command, event.getCommandDispatcher());
			}
		});
	}

}
