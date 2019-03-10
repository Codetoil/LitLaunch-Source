package io.github.littoil.litlaunch.version.mc1_7.proxy;

import io.github.littoil.litlaunch.launchcommon.Command;
import io.github.littoil.litlaunch.launchcommon.LaunchTPSMOD;
import io.github.littoil.litlaunch.launchcommon.proxy.CommonClientProxy;
import io.github.littoil.litlaunch.version.mc1_7.CommandNew;
import io.github.littoil.tpsmod.TPSMod;

    public class ClientProxy1_7 extends CommonClientProxy {
        @Override
        public void preInit() {
            LOGGER.info("preInitialization!");
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