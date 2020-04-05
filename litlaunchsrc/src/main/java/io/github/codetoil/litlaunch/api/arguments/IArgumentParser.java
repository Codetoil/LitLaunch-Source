/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.api.arguments;

public interface IArgumentParser<T extends IArgumentValue<?>>
{
	T parse(String input);

	boolean isNumber();

	Class<?> getClassOfT();
}
