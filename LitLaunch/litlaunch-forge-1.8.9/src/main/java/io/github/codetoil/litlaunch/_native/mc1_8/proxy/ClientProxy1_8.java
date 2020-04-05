/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch._native.mc1_8.proxy;

import io.github.codetoil.litlaunch._native.mc1_8.CommandNew;
import io.github.codetoil.litlaunch.api.Command;
import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.core.CommonProxy;
import io.github.codetoil.litlaunch.core.LaunchCommon;
import io.github.codetoil.litlaunch.modloader.ModFinder;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class ClientProxy1_8 implements CommonProxy {
    @Override
    public void setGamePath() {
        FrontEnd.info("LitLaunch Client Proxy Setting Game Path On Lit Launch!");
        LaunchCommon.setGamePath(Minecraft.getMinecraft().mcDataDir.toPath());
    }

    @Override
    public void construction() {
        FrontEnd.info("LitLaunch Client Proxy Constructing!");
    }

    @Override
    public void preInit() {
        FrontEnd.info("LitLaunch Client Proxy PreInitializing!");
        ModFinder.validMods.forEach((modClass) -> {
            try {
                Object oCommands = modClass.getField("commandList").get(null);
                List lCommands;
                if (oCommands instanceof List) {
                    lCommands = (List) oCommands;
                    lCommands.forEach((command) -> {
                        if (command instanceof Command) {
                            if (Command.Side.CLIENT.equals(((Command) command).side) || Command.Side.BOTH.equals(((Command) command).side)) {
                                net.minecraftforge.client.ClientCommandHandler.instance.registerCommand(new CommandNew((Command) command));
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

    @Override
    public void init() {
        FrontEnd.info("LitLaunch Client Proxy Initializing!");

    }

    @Override
    public void postInit() {
        FrontEnd.info("LitLaunch Client Proxy PostInitializing!");

    }

    @Override
    public void serverLoad() {
        FrontEnd.info("LitLaunch Client Proxy Loading Server!");

    }
}
