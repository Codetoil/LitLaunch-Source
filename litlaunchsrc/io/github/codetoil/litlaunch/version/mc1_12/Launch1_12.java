/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.version.mc1_12;

import io.github.codetoil.litlaunch.launchcommon.*;
import io.github.codetoil.litlaunch.version.mc1_12.proxy.ClientProxy1_12;
import io.github.codetoil.litlaunch.version.mc1_12.proxy.ServerProxy1_12;
import io.github.codetoil.tpsmod.TPSMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;

@Mod(modid = LaunchForge.MODID, version = Launch1_12.VERSION)
public class Launch1_12 implements ILaunch
{
	public static final String VERSION = MinecraftForge.MC_VERSION + "-" + LaunchCommon.VERSION;

	public Launch1_12()
	{
		if (FMLCommonHandler.instance().getSide().isClient()) {
			LaunchCommon.setSide(Command.Side.CLIENT);
		} else if (FMLCommonHandler.instance().getSide().isServer()) {
			LaunchCommon.setSide(Command.Side.SERVER);
		}
		LaunchCommon.setGetFields(GetFields.INSTANCE);
	}

	/*
		Important private methods to split up the code
	 */

	public boolean setProxy()
	{
		boolean result;
		if (LaunchCommon.getCcproxy() != null) {
			LaunchMods.error("Tried re-setting proxy!");
			result = false;
		} else {
			Side side = FMLCommonHandler.instance().getSide();
			switch (side) {
				case CLIENT:
					LaunchCommon.setCcproxy(new ClientProxy1_12());
					result = true;
					break;
				case SERVER:
					LaunchCommon.setCcproxy(new ServerProxy1_12());
					result = true;
					break;
				default:
					LaunchMods.error("FML is not sided(client vs server). This should not happen!");
					result = false;
					break;
			}
		}
		return result;
	}

	/*
		Initialization etc.
	 */

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) throws Throwable
	{
		LaunchCommon.bootstrap(LogManager.getLogger(LaunchForge.MODID), this, Logger1_12.getInstance(), event.getSuggestedConfigurationFile());
		MinecraftForge.EVENT_BUS.register(io.github.codetoil.litlaunch.version.mc1_12.EventHandler.class);
		LaunchForge.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		LaunchForge.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		LaunchForge.postInit();
		//FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getTotalWorldTime();
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		LaunchForge.serverLoad();
		for (Command command : TPSMod.commandList) {
			if (Command.Side.SERVER.equals(command.side) || Command.Side.BOTH.equals(command.side))
				event.registerServerCommand(new CommandNew(command));
		}
	}

	// Get Private Objects
}
