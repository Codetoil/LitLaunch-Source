/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.api.arguments;

import com.google.common.collect.Lists;

import java.util.List;

public class ArgumentParserListString implements IArgumentParser<IArgumentValue<List<String>>>
{
	@Override
	public ArgumentValue<List<String>> parse(String input)
	{
		return new ArgumentValue<>(Lists.newArrayList(input.split(" ")), this);
	}

	@Override
	public Class<?> getClassOfT()
	{
		return List.class;
	}

	@Override
	public boolean isNumber()
	{
		return false;
	}
}
