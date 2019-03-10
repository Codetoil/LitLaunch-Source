package io.github.littoil.litlaunch.launchcommon;

import io.github.littoil.tpsmod.TPSMod;

public class LaunchTPSMOD {
    private final static String TPS_MOD_CLASS_STR = "io.github.littoil.tpsmod.TPSMod";
    public final static String MOD_ID = "tpsmod";

    protected static LitEventHandler eventHandler = new LitEventHandler();

    public Class<?> tpsModClass;
    public Object tpsmod;
    public ILogger LOGGER;

    public static LaunchTPSMOD INSTANCE = new LaunchTPSMOD();

    public LaunchTPSMOD()
    {
        try {
            tpsModClass = LitClassLoader.getClass(TPS_MOD_CLASS_STR);
            tpsmod = tpsModClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
