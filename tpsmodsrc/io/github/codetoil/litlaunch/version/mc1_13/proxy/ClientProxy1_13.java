package io.github.codetoil.litlaunch.version.mc1_13.proxy;

import io.github.codetoil.litlaunch.launchcommon.Command;
import io.github.codetoil.litlaunch.launchcommon.LaunchMods;
import io.github.codetoil.litlaunch.launchcommon.proxy.CommonProxy;
import io.github.codetoil.tpsmod.TPSMod;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class ClientProxy1_13 implements CommonProxy {
    private static List<String> commandNameList = new ArrayList<String>();

    @Override
    public void preInit() {
        LaunchMods.getINSTANCE().getLOGGER().info("preInitialization!");
        TPSMod.commandList.forEach((command) -> {
            if (Command.Side.CLIENT.equals(command.side) || Command.Side.BOTH.equals(command.side))
                commandNameList.add(command.name);
                //net.minecraftforge.client.ClientCommandHandler.instance.registerCommand(new CommandNew(command));
        });
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void init() {

    }

    @Override
    public void postInit() {

    }

    @Override
    public void serverLoad() {

    }

    public List<String> getCommandNameList()
    {
        return commandNameList;
    }

    @SubscribeEvent
    public void onChat(ClientChatEvent e)
    {
        if (e.getMessage() != null)
        {
            if (commandNameList.contains(e.getMessage().substring(1))) {
                e.setCanceled(true);
                TPSMod.commandList.forEach((command) -> {
                    LaunchMods.getINSTANCE().getLOGGER().info(command.name);
                    if (e.getMessage().equals("/" + command.name))
                    {

                        try {
                            command.runnable.run();
                        } catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}
