package io.github.littoil.litlaunch.version.mc1_8;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import io.github.littoil.litlaunch.launchcommon.Command;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

public class CommandNew implements ICommand {

	Command comm;
	
	public CommandNew() {}
	
	public CommandNew(Command comm) {
		this.comm = comm;
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return comm.usage;
	}

	@Override
	public String getName() {
		return comm.name;
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {
		try {
			comm.runnable.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public List<Object> getAliases() {
		return new ArrayList<Object>();
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		return true;
	}

	@Override
	public List<?> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		return new ArrayList<Object>();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}

}
