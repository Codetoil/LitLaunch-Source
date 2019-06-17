package io.github.codetoil.litlaunch.version.mc1_13;

import com.mojang.brigadier.CommandDispatcher;
import io.github.codetoil.litlaunch.launchcommon.Command;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

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

	public void register(CommandDispatcher<CommandSource> disp)
	{
		disp.register(Commands.literal(comm.name).executes((p_198726_0_) -> {
			execute();
			return 0;
		}));
	}

	//public String getUsage(ICommandSender sender) {
	//	return comm.usage;
	//}

	public void execute()
	{
		try {
			comm.runnable.run();
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
