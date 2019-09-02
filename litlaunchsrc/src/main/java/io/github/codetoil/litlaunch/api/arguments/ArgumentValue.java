/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.api.arguments;

public class ArgumentValue<T> implements IArgumentValue<T>
{
	public final T value;
	public final IArgumentParser<IArgumentValue<T>> parser;
	public final Class<?> classOfT;

	public ArgumentValue(T value, IArgumentParser<IArgumentValue<T>> parser)
	{
		this.value = value;
		this.parser = parser;
		classOfT = value.getClass();
	}

	@Override
	public T getValue()
	{
		return value;
	}

	@Override
	public IArgumentParser<IArgumentValue<T>> getParser()
	{
		return parser;
	}

	@Override
	public String toString()
	{
		return "ArgumentValue{value=" + value + ", parser=" + parser + "}" ;
	}
}
