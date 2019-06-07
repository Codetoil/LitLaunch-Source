/*
 * Copyright Codetoil (c) 2019
 */

package io.github.codetoil.litlaunch.version.mc1_7;

import io.github.codetoil.litlaunch.api.Command;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

import java.util.ArrayList;
import java.util.List;

public class CommandNew implements ICommand
{

	Command comm;

	public CommandNew()
	{
	}

	public CommandNew(Command comm)
	{
		this.comm = comm;
	}

	@Override
	public String getCommandName()
	{
		return comm.name;
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return comm.usage;
	}

	@Override
	public List<?> getCommandAliases()
	{
		return new ArrayList<Object>();
	}

	@Override
	public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		try {
			comm.runnable.run();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_)
	{
		return true;
	}

	@Override
	public List<?> addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		return new ArrayList<Object>();
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_)
	{
		return false;
	}

	@Override
	public int compareTo(Object o)
	{
		return 0;
	}

}
