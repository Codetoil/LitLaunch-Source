package io.github.ianthisawesome.tpsmod.commands;

import io.github.ianthisawesome.tpsmod.TpsMod;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.util.ArrayList;
import java.util.List;

public class CommandALLTPS extends CommandBase
{

	private final List<String> aliases;

	public CommandALLTPS()
	{
		aliases = new ArrayList<String>();
		aliases.add("/tpstoall");
		aliases.add("/alltps");
	}

	public int getRequiredPermissionLevel()
	{
		return 0;
	}

	@Override
	public String getCommandName()
	{
		return "/tpstoall";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return "message.tpsmod.alltps.help";
	}

	@Override
	public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		TpsMod.getTPS.doCommandStuffCommandALLTPS(p_71515_1_, p_71515_2_);
	}

	@Override
	public int compareTo(Object arg0)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> getCommandAliases()
	{
		return this.aliases;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_)
	{
		return true;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		List<String> tabCompletions = new ArrayList<String>();
		tabCompletions.add("help");
		tabCompletions.add("?");
		return tabCompletions;
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
