package io.github.littoil.litlaunch.version.mc1_7;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import io.github.littoil.litlaunch.launchcommon.LaunchCommon;
import io.github.littoil.litlaunch.launchcommon.proxy.CommonClientProxy;
import io.github.littoil.litlaunch.launchcommon.proxy.CommonServerProxy;
import io.github.littoil.litlaunch.launchforge.LaunchForge;
import io.github.littoil.tpsmod.TPSMod;
@Mod(modid = Launch1_7.MODID, version = Launch1_7.VERSION)
public class Launch1_7 extends LaunchForge<FMLPreInitializationEvent, FMLInitializationEvent, FMLPostInitializationEvent, FMLServerStartingEvent> {
    public static final String VERSION = "1.7-0.0.0.0";
    public static Launch1_7 INSTANCE = new Launch1_7();
    
    public Launch1_7()
    {
    	LaunchCommon.VERSION = VERSION;
    	Side side = FMLCommonHandler.instance().getSide();
    	switch (side)
    	{
    	case CLIENT:
    		ccproxy = new CommonClientProxy();
    		break;
    	case SERVER:
    		ccproxy = new CommonServerProxy();
    		break;
    	default:
    		LOGGER.error("FML is not sided(client vs server). This may not go well!");
    		break;
    	}
    	super.LaunchInit();
    }
    
	@EventHandler
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		if (FMLCommonHandler.instance().getSide().isClient())
		{
			if (tpsmod instanceof TPSMod)
			{
				TPSMod.commandList.forEach((command) -> {
					if (io.github.littoil.litlaunch.launchcommon.Command.Side.CLIENT.equals(command.side) || io.github.littoil.litlaunch.launchcommon.Command.Side.BOTH.equals(command.side))
							net.minecraftforge.client.ClientCommandHandler.instance.registerCommand(new CommandNew(command));
				});
			}
		}
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
		if (tpsmod instanceof TPSMod)
		{
			TPSMod.commandList.forEach((command) -> {
				if (io.github.littoil.litlaunch.launchcommon.Command.Side.SERVER.equals(command.side) || io.github.littoil.litlaunch.launchcommon.Command.Side.BOTH.equals(command.side))
						event.registerServerCommand(new CommandNew(command));
			});
		}
	}

}
