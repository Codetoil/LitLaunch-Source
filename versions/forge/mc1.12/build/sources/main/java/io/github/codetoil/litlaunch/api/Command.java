/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.api;

import java.util.List;
import java.util.function.Consumer;

public class Command
{
	/**
	 * Name of the command.
	 */
	public String name;
	/**
	 * Usage of the command.
	 */
	public String usage;

	/**
	 * the method this command calls when executed.
	 * Is of the form of a Consumer with one input, a List of Strings, those strings being the arguments to the command entered in chat.
	 */
	public Consumer<List<String>> methodToRun;
	/**
	 * Side the command should be registered to. e.g. CLIENT gives a client side command.
	 */
	public Side side;

	public Command()
	{
	}

	public Command(String name, String usage, Consumer<List<String>> methodToRun, Side side)
	{
		this.name = name;
		this.usage = usage;
		this.methodToRun = methodToRun;
		this.side = side;
	}

	public enum Side
	{
		CLIENT,
		SERVER,
		BOTH;
	}
}
