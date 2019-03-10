package io.github.littoil.litlaunch.launchcommon;

public class LitClassLoader {
    public static boolean classExists(String nameClass)
    {
        boolean result;
        try {
            ClassLoader.getSystemClassLoader().loadClass(nameClass);
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
            classOut = ClassLoader.getSystemClassLoader().loadClass(className);
        } catch (ClassNotFoundException e)
        {
            classOut = null;
        }
        return classOut;
    }
}
