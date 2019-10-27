package io.github.ianthisawesome.tpsmod.commands;

import io.github.ianthisawesome.tpsmod.TpsMod;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

import java.util.ArrayList;
import java.util.List;

public class CommandALLTPS implements ICommand
{
	private final List aliases = new ArrayList();

	public CommandALLTPS()
	{
		this.aliases.add("tpstoall");
		this.aliases.add("alltps");
	}

	public String getCommandName()
	{
		return "tpstoall";
	}

	public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return "message.tpsmod.alltps.help";
	}

	public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		TpsMod.theWorldLogger.getChatMessage.doCommandStuffCommandALLTPS(p_71515_1_, p_71515_2_);
	}

	public int compareTo(Object arg0)
	{
		return 0;
	}

	public List getCommandAliases()
	{
		return this.aliases;
	}

	public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_)
	{
		return true;
	}

	public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		List tabCompletions = new ArrayList();
		tabCompletions.add("help");
		tabCompletions.add("?");
		return tabCompletions;
	}

	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_)
	{
		return false;
	}
}
