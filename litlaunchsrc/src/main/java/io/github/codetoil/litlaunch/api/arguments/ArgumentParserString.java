/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.api.arguments;

public class ArgumentParserString implements IArgumentParser<IArgumentValue<String>>
{
	@Override
	public ArgumentValue<String> parse(String input)
	{
		return new ArgumentValue<>(input, this);
	}

	@Override
	public Class<?> getClassOfT()
	{
		return String.class;
	}

	@Override
	public boolean isNumber()
	{
		return false;
	}
}
