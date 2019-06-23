package io.github.codetoil.litlaunch.version.mc1_8;

import io.github.codetoil.litlaunch.api.Command;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

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
		return this.comm.name;
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return comm.usage;
	}

	@Override
	public List<String> getCommandAliases()
	{
		return new ArrayList<String>();
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException
	{
		try {
			comm.runnable.run();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender)
	{
		return true;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return false;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
	{
		return new ArrayList<String>();
	}

	@Override
	public int compareTo(ICommand o)
	{
		return 0;
	}
}