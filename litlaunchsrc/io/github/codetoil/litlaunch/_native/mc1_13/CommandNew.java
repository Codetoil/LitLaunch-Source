package io.github.codetoil.litlaunch._native.mc1_13;

import com.mojang.brigadier.CommandDispatcher;
import io.github.codetoil.litlaunch.api.Command;
import io.github.codetoil.litlaunch.api.FrontEnd;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

import java.util.Arrays;
import java.util.List;

public class CommandNew
{

	Command comm;
	CommandDispatcher<CommandSource> disp;

	public CommandNew()
	{
	}

	public CommandNew(Command comm, CommandDispatcher<CommandSource> disp)
	{
		this.comm = comm;
		this.disp = disp;
		this.register(this.disp);
	}

	public Command getComm()
	{
		return comm;
	}

	public void register(CommandDispatcher<CommandSource> disp)
	{
		disp.register(Commands.literal(comm.name).then(Commands.argument("args", new InfiniteArgument()).executes((source) -> {
			String str = InfiniteArgument.getString(source);
			execute(str);
			return 0;
		})));
	}

	//public String getUsage(ICommandSender sender) {
	//	return comm.usage;
	//}

	public void execute(String args)
	{
		FrontEnd.debug(args);
		String[] lStrings = args.split(" ");
		List<String> lStringList = Arrays.asList(lStrings);
		FrontEnd.debug("strlistinit: " + lStringList);
		if (!lStringList.isEmpty())
		{
			lStringList.remove(0);
		}
		try {
			comm.methodToRun.accept(lStringList);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

public boolean isUsernameIndex(String[] args, int index)
		{
		return false;
		}
}
