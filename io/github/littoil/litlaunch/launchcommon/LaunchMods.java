package io.github.littoil.litlaunch.launchcommon;

import io.github.littoil.litlaunch.version.sponge.LoggerSponge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LaunchMods {
    private final static List<String> MOD_LIST = new ArrayList<>(Arrays.asList(new String[]{"io.github.littoil.tpsmod.TPSMod"}));

    public Class<?> tpsModClass;
    public List<IMod> modList = new ArrayList<IMod>();
    private ILogger LOGGER;

    private static LaunchMods INSTANCE = new LaunchMods();

    private LaunchMods()
    {
        try {
            MOD_LIST.forEach((modPath) -> {
                if (LitClassLoader.classExists(modPath)) {
                    if (IMod.class.isAssignableFrom(LitClassLoader.getClass(modPath)))
                    {
                        try {
                            modList.add((IMod) LitClassLoader.getClass(modPath).newInstance());
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                        System.err.println("Found invalid mod " + modPath + "! Implement IMod!");
                    }
                }
                else
                {
                    System.err.println("Found nonexistant mod " + modPath + "! Make sure it's in the jar or in a library of the jar!");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LaunchMods getINSTANCE() {
        return INSTANCE;
    }

    public ILogger getLOGGER()
    {
        return LOGGER;
    }

    public void setLOGGER(ILogger LOGGER) {
        if (this.LOGGER == null)
        {
            this.LOGGER = LOGGER;
        }
    }
}
