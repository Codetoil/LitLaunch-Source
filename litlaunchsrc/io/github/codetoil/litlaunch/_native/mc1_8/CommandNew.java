/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch._native.mc1_8;

import io.github.codetoil.litlaunch.api.Command;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;
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
	public List getCommandAliases()
	{
		return new ArrayList<>();
	}

	@Override
	public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		List<String> Args = Arrays.asList(p_71515_2_);
		try {
			comm.methodToRun.accept(Args);
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_)
	{
		return true;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
	{
		return new ArrayList<>();
	}

	@Override
	public int compareTo(ICommand o)
	{
		return this.comm.name.compareTo(o.getCommandName());
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return false;
	}

}
