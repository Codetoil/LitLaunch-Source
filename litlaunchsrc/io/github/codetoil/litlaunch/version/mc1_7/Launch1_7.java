/*
 * Copyright Codetoil (c) 2019
 */

package io.github.codetoil.litlaunch.version.mc1_7;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import io.github.codetoil.litlaunch.api.Command;
import io.github.codetoil.litlaunch.api.LaunchMods;
import io.github.codetoil.litlaunch.backend.ILaunch;
import io.github.codetoil.litlaunch.backend.LaunchCommon;
import io.github.codetoil.litlaunch.exceptions.FailedBootstrapException;
import io.github.codetoil.litlaunch.version.mc1_7.proxy.ClientProxy1_7;
import io.github.codetoil.litlaunch.version.mc1_7.proxy.ServerProxy1_7;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;

import java.util.List;

//import io.github.codetoil.tpsmod.TPSMod;

@Mod(modid = LaunchCommon.MODID, version = LaunchCommon.VERSION)
public class Launch1_7 implements ILaunch
{
	public static final String VERSION = MinecraftForge.MC_VERSION + "-" + LaunchCommon.VERSION;
	public static final io.github.codetoil.litlaunch.version.mc1_7.EventHandler handler = new io.github.codetoil.litlaunch.version.mc1_7.EventHandler();

	public Launch1_7()
	{
		if (FMLCommonHandler.instance().getSide().isClient()) {
			LaunchCommon.setSide(Command.Side.CLIENT);
		} else if (FMLCommonHandler.instance().getSide().isServer()) {
			LaunchCommon.setSide(Command.Side.SERVER);
		} else {
			throw new FailedBootstrapException("FML IS NOT SIDED!");
		}
		LaunchCommon.setDoThing(DoThing.INSTANCE);
		LaunchCommon.setGetFields(GetFields.INSTANCE);
	}

	public boolean setProxy()
	{
		boolean result;
		if (LaunchCommon.getCcproxy() != null) {
			LaunchMods.getINSTANCE().getLOGGER().error("Tried re-setting proxy!");
			result = false;
		} else {
			Side side = FMLCommonHandler.instance().getSide();
			switch (side) {
				case CLIENT:
					LaunchCommon.setCcproxy(new ClientProxy1_7());
					result = true;
					break;
				case SERVER:
					LaunchCommon.setCcproxy(new ServerProxy1_7());
					result = true;
					break;
				default:
					LaunchMods.getINSTANCE().getLOGGER().error("FML is not sided(client vs server). This should not happen!");
					result = false;
					break;
			}
		}
		return result;
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		try {
			LaunchCommon.bootstrap(LogManager.getLogger(LaunchCommon.MODID), this, Logger1_7.getInstance(), event.getSuggestedConfigurationFile());
		}
		catch (Throwable t) {
			FailedBootstrapException lFailedBootstrapException = new FailedBootstrapException();
			lFailedBootstrapException.initCause(t);
			throw lFailedBootstrapException;
		}
		LaunchCommon.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		LaunchCommon.init();
		MinecraftForge.EVENT_BUS.register(handler);
		FMLCommonHandler.instance().bus().register(handler);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		LaunchCommon.postInit();
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		LaunchCommon.serverLoad();
		LaunchMods.validMods.forEach((modClass) -> {
			try {
				Object oCommands = modClass.getField("commandList").get(null);
				List lCommands;
				if (oCommands instanceof List) {
					lCommands = (List) oCommands;
					lCommands.forEach((command) -> {
						if (command instanceof Command) {
							if (Command.Side.CLIENT.equals(((Command) command).side) || Command.Side.BOTH.equals(((Command) command).side))
								event.registerServerCommand(new CommandNew((Command) command));
						}
					});
				} else {
					LaunchMods.error("Mod " + modClass + " does not have a method named \"commandList\". This is neccesary for the api to work though. Skipping!");
				}

			}
			catch (Throwable pThrowable) {
				pThrowable.printStackTrace();
			}

		});
	}

}
