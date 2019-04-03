package io.github.littoil.litlaunch.version.mc1_7.proxy;

import io.github.littoil.litlaunch.launchcommon.Command;
import io.github.littoil.litlaunch.launchcommon.LaunchMods;
import io.github.littoil.litlaunch.launchcommon.proxy.CommonProxy;
import io.github.littoil.litlaunch.version.mc1_7.CommandNew;
import io.github.littoil.tpsmod.TPSMod;

    public class ClientProxy1_7 implements CommonProxy
    {
        @Override
        public void preInit() {
            LaunchMods.getINSTANCE().getLOGGER().info("preInitialization!");
            TPSMod.commandList.forEach((command) -> {
                if (Command.Side.CLIENT.equals(command.side) || Command.Side.BOTH.equals(command.side))
                    net.minecraftforge.client.ClientCommandHandler.instance.registerCommand(new CommandNew(command));
            });
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
}
