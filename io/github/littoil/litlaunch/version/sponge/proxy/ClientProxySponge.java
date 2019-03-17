package io.github.littoil.litlaunch.version.sponge.proxy;

import io.github.littoil.litlaunch.launchcommon.LaunchMods;
import io.github.littoil.litlaunch.launchcommon.LitEventHandler;
import io.github.littoil.litlaunch.launchcommon.events.LitEvent;
import io.github.littoil.litlaunch.launchcommon.proxy.CommonProxy;
//import io.github.littoil.litlaunch.version.sponge.CommandNew;

public class ClientProxySponge implements CommonProxy {
    @Override
    public void preInit() {
        LaunchMods.getINSTANCE().getLOGGER().info("preInitialization!");
        LitEventHandler.CLIENT.post(new LitEvent(this, "PreInit"));
    }

    @Override
    public void init() {
        LaunchMods.getINSTANCE().getLOGGER().info("Initialization!");
        LitEventHandler.CLIENT.post(new LitEvent(this, "Init"));
    }

    @Override
    public void postInit() {
        LaunchMods.getINSTANCE().getLOGGER().info("postInitialization!");
        LitEventHandler.CLIENT.post(new LitEvent(this, "PostInit"));
    }

    @Override
    public void serverLoad() {
        LaunchMods.getINSTANCE().getLOGGER().info("serverInitialization!");
        LitEventHandler.CLIENT.post(new LitEvent(this, "ServerLoad"));
    }
}
