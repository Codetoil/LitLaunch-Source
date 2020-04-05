package io.github.codetoil.litlaunch._native.mc1_14;

import io.github.codetoil.litlaunch.api.Command;
import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.core.ILaunch;
import io.github.codetoil.litlaunch.core.LaunchCommon;
import io.github.codetoil.litlaunch.core.exceptions.FailedBootstrapException;
import io.github.codetoil.litlaunch.modloader.ModFinder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.logging.log4j.LogManager;

import java.util.List;

@Mod(LaunchCommon.MODID)
public class Launch1_14 implements ILaunch
{
	public static final String VERSION = GetFields.INSTANCE.getVERSION() + "-" + LaunchCommon.VERSION;

	public Launch1_14() throws Throwable
	{
		if (FMLEnvironment.dist.isClient())
		{
			LaunchCommon.setSide(Command.Side.CLIENT);
		}
		else if (FMLEnvironment.dist.isDedicatedServer())
		{
			LaunchCommon.setSide(Command.Side.SERVER);
		}
		else
		{
			throw new FailedBootstrapException("FML IS NOT SIDED!");
		}
		LaunchCommon.setGamePath(FMLLoader.getGamePath());
		LaunchCommon.setDoThing(DoThing.INSTANCE);
		LaunchCommon.setGetFields(GetFields.INSTANCE);
		try
		{
			LaunchCommon.bootstrap(LogManager.getLogger(LaunchCommon.MODID), this, Logger1_14.getInstance());
		}
		catch (Throwable t)
		{
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
		if (LaunchCommon.getCcproxy() != null)
		{
			FrontEnd.error("Tried re-setting proxy!");
			result = false;
		}
		else
		{
			Dist side = FMLEnvironment.dist;
			switch (side)
			{
				case CLIENT:
					LaunchCommon.setCcproxy(new io.github.codetoil.litlaunch._native.mc1_14.proxy.ClientProxy1_14());
					result = true;
					break;
				case DEDICATED_SERVER:
					LaunchCommon.setCcproxy(new io.github.codetoil.litlaunch._native.mc1_14.proxy.ServerProxy1_14());
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
			try
			{
				Object oCommands = modClass.getField("commandList").get(null);
				List lCommands;
				if (oCommands instanceof List)
				{
					lCommands = (List) oCommands;
					lCommands.forEach((command) -> {
						if (command instanceof Command)
						{
							if (Command.Side.CLIENT.equals(((Command) command).side) || Command.Side.BOTH.equals(((Command) command).side))
							{
								new CommandNew((Command) command, event.getCommandDispatcher());
							}
						}
					});
				}
				else
				{
					FrontEnd.error("Mod " + modClass + " does not have a method named \"commandList\". This is neccesary for the api to work though. Skipping!");
				}

			}
			catch (Throwable pThrowable)
			{
				pThrowable.printStackTrace();
			}

		});
	}

}
