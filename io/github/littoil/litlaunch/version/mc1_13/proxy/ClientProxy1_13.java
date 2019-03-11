package io.github.littoil.litlaunch.version.mc1_13.proxy;

import io.github.littoil.litlaunch.launchcommon.Command;
import io.github.littoil.litlaunch.launchcommon.LaunchTPSMOD;
import io.github.littoil.litlaunch.launchforge.proxy.CommonClientProxy;
import io.github.littoil.tpsmod.TPSMod;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ClientProxy1_13 extends CommonClientProxy {
    private static List<String> commandNameList = new ArrayList<String>();

    @Override
    public void preInit() {
        LOGGER.info("preInitialization!");
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
            if (commandNameList.contains(e.getMessage())) {
                e.setCanceled(true);
                TPSMod.commandList.forEach((command) -> {
                    if (e.getMessage().equals(command.name))
                    {
                        try {
                            command.method.invoke(null);
                        } catch (IllegalAccessException| InvocationTargetException ex)
                        {
                            ex.printStackTrace();
                        } catch (NullPointerException ex)
                        {
                            LaunchTPSMOD.INSTANCE.LOGGER.error("Tried using nonstatic method! Please use static method!");
                            ex.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}
