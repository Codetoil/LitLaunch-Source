/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.api;

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
	 */
	public Runnable runnable;
	/**
	 * Side the command should be registered to. E.g. CLIENT gives a client side command.
	 */
	public Side side;

	public Command()
	{
	}

	public Command(String name, String usage, Runnable runnable, Side side)
	{
		this.name = name;
		this.usage = usage;
		this.runnable = runnable;
		this.side = side;
	}

	public enum Side
	{
		CLIENT,
		SERVER,
		BOTH;
	}
}
