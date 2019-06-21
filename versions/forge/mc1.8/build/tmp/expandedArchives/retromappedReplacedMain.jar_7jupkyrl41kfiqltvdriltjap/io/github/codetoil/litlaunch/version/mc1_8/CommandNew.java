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
	public String func_71517_b()
	{
		return this.comm.name;
	}

	@Override
	public String func_71518_a(ICommandSender p_71518_1_)
	{
		return comm.usage;
	}

	@Override
	public List<String> func_71514_a()
	{
		return new ArrayList<String>();
	}

	@Override
	public void func_71515_b(ICommandSender sender, String[] args) throws CommandException
	{
		try {
			comm.runnable.run();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean func_71519_b(ICommandSender sender)
	{
		return true;
	}

	@Override
	public boolean func_82358_a(String[] args, int index)
	{
		return false;
	}

	@Override
	public List<String> func_180525_a(ICommandSender sender, String[] args, BlockPos pos)
	{
		return new ArrayList<String>();
	}

	@Override
	public int compareTo(ICommand o)
	{
		return 0;
	}
}
