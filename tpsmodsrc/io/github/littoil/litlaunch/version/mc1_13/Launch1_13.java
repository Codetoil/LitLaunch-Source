package io.github.littoil.litlaunch.version.mc1_13;

import io.github.littoil.litlaunch.launchcommon.*;
import io.github.littoil.litlaunch.launchforge.*;
import io.github.littoil.litlaunch.version.mc1_13.proxy.*;
import io.github.littoil.tpsmod.TPSMod;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.*;
import net.minecraftforge.fml.javafmlmod.*;
import net.minecraftforge.fml.loading.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(LaunchForge.MODID)
public class Launch1_13 implements ILaunch {
    public static final String VERSION = "1.13-0.0.0.4-2.0";

    public Launch1_13() {

	    LaunchCommon.bootstrap(LogManager.getLogger(LaunchForge.MODID), this, Logger1_13.getInstance());
	    if (!setProxy()) {
		    LaunchMods.getINSTANCE().getLOGGER().error("Proxies not set!");
	    }
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
		MinecraftForge.EVENT_BUS.register(this);
		//FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverLoad);
		LaunchMods.getINSTANCE().getLOGGER().info("Initialized Launch1_13");
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

	public void setup(final FMLCommonSetupEvent event) {

		LaunchForge.preInit();
	}

	public void setupClient(final FMLClientSetupEvent event)
	{
		LaunchCommon.ccproxy.preInit();
	}

	@SubscribeEvent
	public void serverLoad(FMLServerStartingEvent event) {
		LaunchForge.serverLoad();
		TPSMod.commandList.forEach((command) -> {
			if (io.github.littoil.litlaunch.launchcommon.Command.Side.SERVER.equals(command.side) || io.github.littoil.litlaunch.launchcommon.Command.Side.BOTH.equals(command.side))
			{
				new CommandNew((Command) command, event.getCommandDispatcher());
			}
		});
	}



    /*private void setup(final FMLCommonSetupEvent event) {
		super.preInit(event);
	}
	
	private void setupClient(final FMLClientSetupEvent event) {
		if (tpsmod instanceof TPSMod)
		{
			TPSMod.commandList.forEach((command) -> {
				if (io.github.littoil.litlaunch.launchcommon.Command.Side.CLIENT.equals(command.side) || io.github.littoil.litlaunch.launchcommon.Command.Side.BOTH.equals(command.side))
				{
					LOGGER.error("Error: Cannot register command " + command.name + "as client side as it doesn't seem to be supported yet!");
				}
					
					//net.minecraftforge.client.ClientCommandHandler.instance.registerCommand(new CommandNew(command));
			});
		}
	}

	@SubscribeEvent
	public void serverLoad1_13(FMLServerStartingEvent event) {
		super.serverLoad(event);
		if (tpsmod instanceof TPSMod)
		{

		}
	}*/

}
