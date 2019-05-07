package io.github.codetoil.litlaunch.launchcommon;

import io.github.codetoil.litlaunch.launchcommon.events.LitEvent;
import io.github.codetoil.litlaunch.launchcommon.exceptions.FailedBootstrapException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class LaunchMods {
    private final static List<String> MOD_LIST = new ArrayList<String>(Arrays.asList("io.github.littoil.tpsmod.TPSMod"));

    public Class<?> tpsModClass;
    public final LitClassLoader litClassLoader = new LitClassLoader();
    private ILogger LOGGER;

    private final static LaunchMods INSTANCE = new LaunchMods();

    public static boolean constructMods() throws Throwable
    {
        boolean ret = true;
        try {
            getINSTANCE().litClassLoader.setClassLoader(LaunchMods.class.getClassLoader());
            for (String modPath : MOD_LIST)
            {
                if (getINSTANCE().litClassLoader.classExists(modPath)) {
                    try {
                        Class<?> classOfMod = getINSTANCE().litClassLoader.getClass(modPath);
                        getINSTANCE().getLOGGER().debug(classOfMod.getClassLoader());
                        IMod mainINSTANCE = (IMod) (getINSTANCE().litClassLoader.getMethodOfClass(classOfMod,"getMainINSTNACE").invoke(classOfMod.newInstance()));
                        Method method = getINSTANCE().litClassLoader.getMethodOfClass(classOfMod, "RecievedEvent", LitEvent.class, LaunchMods.getINSTANCE().getClass());
                        INSTANCE.getLOGGER().info(modPath + " : " + Arrays.toString(method.getParameterTypes()));
                        method.invoke(mainINSTANCE, new LitEvent(LaunchMods.getINSTANCE(), "Construction"), LaunchMods.getINSTANCE());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        ret = false;
                        throw new FailedBootstrapException("Failed initializing Mod \"" + modPath + "\"", e);
                    }
                }
                else
                {
                    System.err.println("Found nonexistant mod " + modPath + "! Make sure this jar can access that class!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret = false;
            throw new FailedBootstrapException("Error in bootstrap!!!", e);
        }
        return ret;
    }

    private LaunchMods()
    {

    }

    private static boolean isModAnInterface(String modPath)
    {
        Class<?>[] inters = getINSTANCE().litClassLoader.getClass(modPath).getInterfaces();
        boolean result = false;
        for (int i = 0; i < inters.length; i++) {
            result = result || inters[i].getName().equals("io.github.littoil.litlaunch.launchcommon.IMod");
        }
        return result;
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
