package io.github.littoil.litlaunch.launchcommon;

import io.github.littoil.litlaunch.launchcommon.events.LitEvent;

public class LaunchCommon {
    public static final LaunchCommon INSTANCE = new LaunchCommon();
    public static void preInit()
    {
        LitEventHandler.INSTANCE.post(new LitEvent(INSTANCE, "PreInit"));
    }

    public static void init()
    {
        LitEventHandler.INSTANCE.post(new LitEvent(INSTANCE, "Init"));
    }

    public static void postInit()
    {
        LitEventHandler.INSTANCE.post(new LitEvent(INSTANCE, "PostInit"));
    }

    public static void serverLoad()
    {
        LitEventHandler.INSTANCE.post(new LitEvent(INSTANCE, "ServerLoad"));
    }
}
