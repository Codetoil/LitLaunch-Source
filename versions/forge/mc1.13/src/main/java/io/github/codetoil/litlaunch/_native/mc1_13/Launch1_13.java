package io.github.codetoil.litlaunch._native.mc1_13;

import io.github.codetoil.litlaunch._native.mc1_13.GetFields;
import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.api.Command;
import io.github.codetoil.litlaunch.core.ILaunch;
import io.github.codetoil.litlaunch.core.LaunchCommon;
import io.github.codetoil.litlaunch.core.exceptions.FailedBootstrapException;
import io.github.codetoil.litlaunch._native.mc1_13.proxy.ClientProxy1_13;
import io.github.codetoil.litlaunch._native.mc1_13.proxy.ServerProxy1_13;
import io.github.codetoil.litlaunch.modloader.ModFinder;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.*;
import net.minecraftforge.fml.javafmlmod.*;
import net.minecraftforge.fml.loading.*;
import org.apache.logging.log4j.LogManager;

import java.util.List;

@Mod(LaunchCommon.MODID)
public class Launch1_13 implements ILaunch
{
	public static final String VERSION = GetFields.INSTANCE.getVERSION() + "-" + LaunchCommon.VERSION;

	public Launch1_13() throws Throwable
	{
		if (FMLEnvironment.dist.isClient()) {
			LaunchCommon.setSide(Command.Side.CLIENT);
		} else if (FMLEnvironment.dist.isDedicatedServer()) {
			LaunchCommon.setSide(Command.Side.SERVER);
		} else {
			throw new FailedBootstrapException("FML IS NOT SIDED!");
		}
		LaunchCommon.setGamePath(FMLLoader.getGamePath());
		LaunchCommon.setDoThing(DoThing.INSTANCE);
		LaunchCommon.setGetFields(GetFields.INSTANCE);
		try {
			LaunchCommon.bootstrap(LogManager.getLogger(LaunchCommon.MODID), this, Logger1_13.getInstance());
		}
		catch (Throwable t) {
			FailedBootstrapException lFailedBootstrapException = new FailedBootstrapException();
			lFailedBootstrapException.initCause(t);
			throw lFailedBootstrapException;
		}
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(EventHandler.class);
		//FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverLoad);
	}

	public void setup(final FMLCommonSetupEvent event)
	{
		LaunchCommon.preInit();
		LaunchCommon.init();
		LaunchCommon.postInit();
	}

	public void setupClient(final FMLClientSetupEvent event)
	{
		LaunchCommon.getCcproxy().preInit();
	}

	public boolean setProxy()
	{
		boolean result;
		if (LaunchCommon.getCcproxy() != null) {
			FrontEnd.error("Tried re-setting proxy!");
			result = false;
		} else {
			Dist side = FMLEnvironment.dist;
			switch (side) {
				case CLIENT:
					LaunchCommon.setCcproxy(new ClientProxy1_13());
					result = true;
					break;
				case DEDICATED_SERVER:
					LaunchCommon.setCcproxy(new ServerProxy1_13());
					result = true;
					break;
				default:
					FrontEnd.error("FML is not sided(client vs server). This should not happen!");
					result = false;
					break;
			}
		}
		return result;
	}

	@SubscribeEvent
	public void serverLoad(FMLServerStartingEvent event)
	{
		LaunchCommon.serverLoad();
		ModFinder.validMods.forEach((modClass) -> {
			try {
				Object oCommands = modClass.getField("commandList").get(null);
				List lCommands;
				if (oCommands instanceof List) {
					lCommands = (List) oCommands;
					lCommands.forEach((command) -> {
						if (command instanceof Command) {
							if (Command.Side.CLIENT.equals(((Command) command).side) || Command.Side.BOTH.equals(((Command) command).side))
								new CommandNew((Command) command, event.getCommandDispatcher());
						}
					});
				} else {
					FrontEnd.error("Mod " + modClass + " does not have a method named \"commandList\". This is neccesary for the api to work though. Skipping!");
				}

			}
			catch (Throwable pThrowable) {
				pThrowable.printStackTrace();
			}

		});
	}

}
