package io.github.littoil.litlaunch.version.mc1_13;

import io.github.littoil.litlaunch.launchcommon.LaunchTPSMOD;
import io.github.littoil.litlaunch.launchforge.LaunchForge;
import io.github.littoil.litlaunch.version.mc1_13.proxy.ClientProxy1_13;
import io.github.littoil.litlaunch.version.mc1_13.proxy.ServerProxy1_13;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Launch1_13.MODID)
public class Launch1_13 extends LaunchForge<FMLCommonSetupEvent, Object, Object, FMLServerStartingEvent> {
    public static final String VERSION = "1.13-0.0.0.2";

    public Launch1_13() {
		LaunchTPSMOD.INSTANCE.LOGGER = new Logger1_13();
		if (!setProxy()) {
			LaunchTPSMOD.INSTANCE.LOGGER.error("Proxies not set!");
		}
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
		MinecraftForge.EVENT_BUS.register(this);
		//FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverLoad);
		LaunchTPSMOD.INSTANCE.LOGGER.info("Initialized Launch1_13");
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
			Dist dist = FMLEnvironment.dist;
			switch (dist) {
				case CLIENT:
					ccproxy = new ClientProxy1_13();
					result = true;
					break;
				case DEDICATED_SERVER:
					ccproxy = new ServerProxy1_13();
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

	public void setup(final FMLCommonSetupEvent event) {
    	super.preInit(event);
	}

	public void setupClient(final FMLClientSetupEvent event)
	{
		if (FMLEnvironment.dist.isClient())
		{
			ccproxy.preInit();
		}
	}

	@SubscribeEvent
	public void serverLoad(FMLServerStartingEvent event) {
    	super.serverLoad(event);
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
			TPSMod.commandList.forEach((command) -> {
				if (io.github.littoil.litlaunch.launchcommon.Command.Side.SERVER.equals(command.side) || io.github.littoil.litlaunch.launchcommon.Command.Side.BOTH.equals(command.side))
				{
					//event.getCommandDispatcher();
					new CommandNew(command, event.getCommandDispatcher());
				}
			});
		}
	}*/

}
