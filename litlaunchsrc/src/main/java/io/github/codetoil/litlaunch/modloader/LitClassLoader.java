/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.modloader;

import io.github.codetoil.litlaunch.api.FrontEnd;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

public class LitClassLoader
{
	private ClassLoader classLoader;

	public ClassLoader getClassLoader()
	{
		return classLoader;
	}

	public void setClassLoader(ClassLoader classLoader)
	{
		if (this.classLoader == null)
		{
			FrontEnd.info("Setting classloader to: " + classLoader);
			this.classLoader = classLoader;
		}
		else
		{
			FrontEnd.info("Classloader is final");
		}
	}

	public boolean classExists(String nameClass)
	{
		boolean result;
		if (classLoader == null)
		{
			FrontEnd.error("ClassLoader not set!");
			return false;
		}
		try
		{
			classLoader.loadClass(nameClass);
			result = true;
		}
		catch (ClassNotFoundException e)
		{
			result = false;
		}
		return result;
	}

	public Class<?> getClass(String className)
	{
		Class<?> classOut;
		if (classLoader == null)
		{
			FrontEnd.error("ClassLoader not set!");
			return null;
		}
		try
		{
			classOut = classLoader.loadClass(className);
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			classOut = null;
		}
		return classOut;
	}

	public Method getMethodOfClass(Class<?> classIn, String methodName, Class<?>... paramTypes)
	{
		for (Method method : classIn.getMethods())
		{
			FrontEnd.trace(method);
			FrontEnd.trace(method.getName());
			if (methodName.equals(method.getName()))
			{
				if (areClassListsEqual(method.getParameterTypes(), paramTypes))
				{
					return method;
				}
			}
		}
		return null;
	}

	public boolean areClassListsEqual(Class<?>[] classList1, Class<?>[] classList2)
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

	public Constructor<?> getConstructorOfClass(Class<?> classIn, Class<?>... paramTypes)
	{
		FrontEnd.info(Arrays.asList(classIn.getConstructors()));
		for (Constructor<?> constructor : classIn.getConstructors())
		{
			//LaunchMods.info(Arrays.asList(constructor.getParameterTypes()));
			//LaunchMods.info(Arrays.asList(paramTypes));
			if (areClassListsEqual(constructor.getParameterTypes(), paramTypes))
			{
				return constructor;
			}
		}
		return null;
	}

	@Override
	public String toString()
	{
		return "LitClassLoader{classLoader=" + classLoader + "}";
	}
}
