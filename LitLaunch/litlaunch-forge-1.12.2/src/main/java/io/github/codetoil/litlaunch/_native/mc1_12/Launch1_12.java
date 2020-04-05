/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch._native.mc1_12;

import io.github.codetoil.litlaunch._native.mc1_12.proxy.ClientProxy1_12;
import io.github.codetoil.litlaunch._native.mc1_12.proxy.ServerProxy1_12;
import io.github.codetoil.litlaunch.api.Command;
import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.core.ILaunch;
import io.github.codetoil.litlaunch.core.LaunchCommon;
import io.github.codetoil.litlaunch.core.exceptions.FailedBootstrapException;
import io.github.codetoil.litlaunch.modloader.ModFinder;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;

import java.util.List;

@Mod(modid = LaunchCommon.MODID, version = Launch1_12.VERSION, name = LaunchCommon.NAME)
public class Launch1_12 implements ILaunch {
    public static final String VERSION = MinecraftForge.MC_VERSION + "-" + LaunchCommon.VERSION;
    public static final io.github.codetoil.litlaunch._native.mc1_12.EventHandler handler = new io.github.codetoil.litlaunch._native.mc1_12.EventHandler();


    public Launch1_12() {
        if (FMLCommonHandler.instance().getSide().isClient()) {
            LaunchCommon.setSide(Command.Side.CLIENT);
        } else if (FMLCommonHandler.instance().getSide().isServer()) {
            LaunchCommon.setSide(Command.Side.SERVER);
        } else {
            throw new FailedBootstrapException("FML IS NOT SIDED!");
        }
        LaunchCommon.setDoThing(DoThing.INSTANCE);
        LaunchCommon.setGetFields(GetFields.INSTANCE);
        try {
            LaunchCommon.bootstrap(LogManager.getLogger(LaunchCommon.MODID), this, Logger1_12.getInstance());
        } catch (Throwable t) {
            FailedBootstrapException lFailedBootstrapException = new FailedBootstrapException();
            lFailedBootstrapException.initCause(t);
            throw lFailedBootstrapException;
        }
    }

    public boolean setProxy() {
        boolean result;
        if (LaunchCommon.getCcproxy() != null) {
            FrontEnd.error("Tried re-setting proxy!");
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
                    FrontEnd.error("FML is not sided(client vs server). This should not happen!");
                    result = false;
                    break;
            }
        }
        return result;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LaunchCommon.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        LaunchCommon.init();
        MinecraftForge.EVENT_BUS.register(handler);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LaunchCommon.postInit();
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        LaunchCommon.serverLoad();
        ModFinder.validMods.forEach((modClass) -> {
            try {
                Object oCommands = modClass.getField("commandList").get(null);
                List lCommands;
                if (oCommands instanceof List) {
                    lCommands = (List) oCommands;
                    lCommands.forEach((command) -> {
                        if (command instanceof Command) {
                            if (Command.Side.CLIENT.equals(((Command) command).side) || Command.Side.BOTH.equals(((Command) command).side)) {
                                event.registerServerCommand(new CommandNew((Command) command));
                            }
                        }
                    });
                } else {
                    FrontEnd.error("Mod " + modClass + " does not have a method named \"commandList\". This is neccesary for the api to work though. Skipping!");
                }

            } catch (Throwable pThrowable) {
                pThrowable.printStackTrace();
            }

        });
    }
}
