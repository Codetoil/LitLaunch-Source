package io.github.littoil.litlaunch.version.mc1_13;

import io.github.littoil.litlaunch.launchcommon.LaunchCommon;
import io.github.littoil.litlaunch.launchcommon.proxy.CommonClientProxy;
import io.github.littoil.litlaunch.launchcommon.proxy.CommonServerProxy;
import io.github.littoil.litlaunch.launchforge.LaunchForge;
import io.github.littoil.tpsmod.TPSMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Launch1_13.MODID)
public class Launch1_13 extends LaunchForge<FMLCommonSetupEvent, Object, Object, FMLServerStartingEvent> {
    public static final String VERSION = "1.13-0.0.0.0";
    public static Launch1_13 INSTANCE = new Launch1_13();
    
    
    public Launch1_13()
    {
    	LaunchCommon.VERSION = VERSION;
    	Dist dist = net.minecraftforge.fml.loading.FMLEnvironment.dist;
    	switch (dist)
    	{
    	case CLIENT:
    		ccproxy = new CommonClientProxy();
    		break;
    	case DEDICATED_SERVER:
    		ccproxy = new CommonServerProxy();
    		break;
    	default:
    		LOGGER.error("FML is not disted(client vs server). This may not go well!");
    		break;
    	}
		FMLJavaModLoadingContext jmlc = FMLJavaModLoadingContext.get();
		if (jmlc == null)
		{
			LOGGER.error("JavaModLoadingContext Null! WTF");
			return;
		}
    	IEventBus modeb = jmlc.getModEventBus();
    	modeb.addListener(this::setup);
    	modeb.addListener(this::setupClient);
    	modeb.addListener(this::serverLoad1_13);
    	super.LaunchInit();
    	LOGGER.info(Launch1_13.MODID);
    }
    
    private void setup(final FMLCommonSetupEvent event) {
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
	}

}
