/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.modloader;

/**
 * A class to safely peek at a class without loading it to avoid running non-litlaunch related code
    * DO NOT USE, DOES NOT WORK *

 */
public class PeekClassLoader extends ClassLoader
{
	public PeekClassLoader(ClassLoader parent)
	{
		super(parent);
	}

	/**
	 * A method to peek at a class without instantiating it
	 * @param className Name of class
	 * @return Class object
	 * @throws ClassNotFoundException if the object could not be found
	 */
	public Class<?> peekAtClass(String className) throws ClassNotFoundException
	{
		return this.findClass(className);
	}
}
