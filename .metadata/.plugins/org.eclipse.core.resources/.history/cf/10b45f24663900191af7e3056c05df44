package io.github.littoil.litlaunch.version.mc1_13;

import io.github.littoil.litlaunch.launchcommon.LaunchCommon;
import io.github.littoil.litlaunch.launchcommon.proxy.CommonClientProxy;
import io.github.littoil.litlaunch.launchcommon.proxy.CommonServerProxy;
import io.github.littoil.litlaunch.launchforge.LaunchForge;
import io.github.littoil.tpsmod.TPSMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Launch1_13.MODID)
public class Launch1_13 extends LaunchForge<FMLCommonSetupEvent, Object, Object, FMLServerStartingEvent> {
    public static final String VERSION = "1.13-0.0.0.0";
    public static Launch1_13 INSTANCE = new Launch1_13();
    
    public Launch1_13()
    {
    	LaunchCommon.VERSION = VERSION;
    	Dist dist = FMLEnvironment.dist;
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
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit1_13);
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInitClient);
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverLoad1_13);
    	super.LaunchInit();
    }
    
	public void preInit1_13(final FMLCommonSetupEvent event) {
		super.preInit(event);
	}
	
	public void preInitClient(final FMLClientSetupEvent event) {
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

	@Override
	public void init(Object event) {
		super.init(event);
	}

	@Override
	public void postInit(Object event) {
		super.postInit(event);
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
