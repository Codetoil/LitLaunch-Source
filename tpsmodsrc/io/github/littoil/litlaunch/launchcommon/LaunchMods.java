package io.github.littoil.litlaunch.launchcommon;

import io.github.littoil.litlaunch.launchcommon.events.LitEvent;
import io.github.littoil.litlaunch.launchcommon.exceptions.FailedBootstrapException;
import joptsimple.internal.Objects;
import scala.tools.nsc.transform.patmat.Solving;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LaunchMods {
    private final static List<String> MOD_LIST = new ArrayList<String>(Arrays.asList("io.github.littoil.tpsmod.TPSMod"));

    public Class<?> tpsModClass;
    private ILogger LOGGER;

    private final static LaunchMods INSTANCE = new LaunchMods();

    private static Object getEvent(Class<?> LitEventClass, String event) throws Throwable
    {
       //return LitEventClass.getConstructor(LaunchMods.class, String.class, Object[].class).newInstance(INSTANCE, "Construction", LaunchCommon.EMPTY);
        Constructor<?> constructor = LitClassLoader.getConstructorOfClass(LitEventClass, Object.class);
        Object obj = constructor.newInstance(INSTANCE);
        Class<?> classObj = obj.getClass();
        Method setType = LitClassLoader.getMethodOfClass(classObj, "setType", String.class);
        Method setData = LitClassLoader.getMethodOfClass(classObj, "setData", Object[].class);
        setType.invoke(obj, "Construction");
        INSTANCE.getLOGGER().info(setData.getParameterTypes().length);
        setData.invoke(obj, new Object[1]);
        return obj;
        //, "Construction", LaunchCommon.EMPTY
    }

    public static boolean constructMods() throws Throwable
    {
        boolean ret = true;
        try {
            for (String modPath : MOD_LIST)
            {
                if (LitClassLoader.classExists(modPath)) {
                    if (isModAnInterface(modPath))
                    {
                        try {
                            Class<?> classOfMod = LitClassLoader.getClass(modPath);
                            Object mainINSTANCE = LitClassLoader.getMethodOfClass(classOfMod,"getMainINSTNACE").invoke(classOfMod.newInstance());
                            Method method = LitClassLoader.getMethodOfClass(classOfMod, "RecievedEvent", LitEvent.class, LaunchMods.getINSTANCE().getClass());
                            INSTANCE.getLOGGER().info(method.getParameterTypes()[0]);
                            method.invoke(mainINSTANCE, getEvent(method.getParameterTypes()[0], "Construction"), LaunchMods.getINSTANCE());

                            //Object mainINSTNACE = LitClassLoader.getClass(modPath).getMethod("getMainINSTNACE").invoke(LitClassLoader.getClass(modPath).newInstance());
                            //INSTANCE.getLOGGER().info(Arrays.asList(LitClassLoader.getClass(modPath).getMethods()));
                            //LitClassLoader.getClass(modPath).getMethod("RecievedEvent", io.github.littoil.litlaunch.launchcommon.events.LitEvent.class).invoke(mainINSTNACE, new LitEvent(INSTANCE, "Construction"));
                            //Method method = LitClassLoader.getClass(modPath).getMethod("getMainInstance");
                            //LitEventHandler.EventListener mainInstance = (LitEventHandler.EventListener) (method.invoke(LitClassLoader.getClass(modPath).newInstance()));
                            //LitEventHandler.COMMON.addListener(mainInstance);
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
                        System.err.println("Found invalid mod " + modPath + "! Implement IMod!");
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
        Class<?>[] inters = LitClassLoader.getClass(modPath).getInterfaces();
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
