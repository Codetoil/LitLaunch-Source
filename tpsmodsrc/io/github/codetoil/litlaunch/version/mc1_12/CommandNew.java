package io.github.codetoil.litlaunch.version.mc1_12;

import java.util.ArrayList;
import java.util.List;

import io.github.codetoil.litlaunch.launchcommon.Command;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandNew implements ICommand {

	Command comm;
	
	public CommandNew() {}
	
	public CommandNew(Command comm) {
		this.comm = comm;
	}
	@Override
	public String getName() {
		return comm.name;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}

	@Override
	public int compareTo(ICommand arg0) {
		return 0;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return comm.usage;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		try {
			comm.runnable.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			net.minecraft.util.math.BlockPos targetPos) {
		return new ArrayList<String>();
	}

	@Override
	public List<String> getAliases() {
		return new ArrayList<String>();
	}

}
