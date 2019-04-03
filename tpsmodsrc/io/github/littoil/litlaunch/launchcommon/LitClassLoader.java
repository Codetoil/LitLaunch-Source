package io.github.littoil.litlaunch.launchcommon;

import net.minecraft.launchwrapper.Launch;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

public class LitClassLoader {
    public static ClassLoader classLoader = Launch.classLoader;
    public static boolean classExists(String nameClass)
    {
        boolean result;
        try {
            classLoader.loadClass(nameClass);
            result = true;
        } catch (ClassNotFoundException e)
        {
            result = false;
        }
        return result;
    }

    public static Class<?> getClass(String className)
    {
        Class<?> classOut;
        try {
            classOut = classLoader.loadClass(className);
        } catch (ClassNotFoundException e)
        {
            classOut = null;
        }
        return classOut;
    }

    public static Method getMethodOfClass(Class<?> classIn, String methodName, Class<?>... paramTypes)
    {
        for (Method method : classIn.getMethods())
        {
            if (methodName.equals(method.getName()))
            {
                LaunchMods.getINSTANCE().getLOGGER().info(Arrays.asList(method.getParameterTypes()));
                LaunchMods.getINSTANCE().getLOGGER().info(Arrays.asList(paramTypes));
                if (areClassListsEqual(method.getParameterTypes(), paramTypes))
                {
                    return method;
                }
            }
        }
        return null;
    }

    public static Constructor<?> getConstructorOfClass(Class<?> classIn, Class<?>... paramTypes)
    {
        LaunchMods.getINSTANCE().getLOGGER().info(Arrays.asList(classIn.getConstructors()));
        for (Constructor<?> constructor : classIn.getConstructors())
        {
            LaunchMods.getINSTANCE().getLOGGER().info(Arrays.asList(constructor.getParameterTypes()));
            LaunchMods.getINSTANCE().getLOGGER().info(Arrays.asList(paramTypes));
            if (areClassListsEqual(constructor.getParameterTypes(), paramTypes))
            {
                return constructor;
            }
        }
        return null;
    }

    public static boolean areClassListsEqual(Class<?>[] classList1, Class<?>[] classList2)
    {
        boolean result = true;
        if (classList1.length == classList2.length)
        {
            for (int i = 0; i < classList1.length; i++)
            {
                result = result && (classList1[i].getName().equals(classList2[i].getName()));
            }
        }
        else
        {
            result = false;
        }
        return result;
    }
}
