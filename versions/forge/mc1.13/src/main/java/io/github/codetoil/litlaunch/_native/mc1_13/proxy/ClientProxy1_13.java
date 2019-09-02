package io.github.codetoil.litlaunch._native.mc1_13.proxy;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.api.Command;
import io.github.codetoil.litlaunch.api.arguments.ArgumentWrapper;
import io.github.codetoil.litlaunch.api.arguments.IArgumentParser;
import io.github.codetoil.litlaunch.api.arguments.IArgumentValue;
import io.github.codetoil.litlaunch.core.CommonProxy;
import io.github.codetoil.litlaunch.core.ConfigFile;
import io.github.codetoil.litlaunch.core.LaunchCommon;
import io.github.codetoil.litlaunch.modloader.ModFinder;
import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientProxy1_13 implements CommonProxy
{
	public final static List<Command> commandList = new ArrayList<>();
	public final static List<String> commandNameList = new ArrayList<>();

	@Override
	public void setGamePath()
	{
	}

	@Override
	public void construction()
	{
		FrontEnd.info("LitLaunch Client Proxy Constructing!");
	}

	@Override
	public void preInit()
	{
		FrontEnd.info("LitLaunch Client Proxy PreInitializing!");
		ModFinder.validMods.forEach((modClass) -> {
			try {
				Object oCommands = modClass.getField("commandList").get(null);
				List lCommands;
				if (oCommands instanceof List) {
					lCommands = (List) oCommands;
					lCommands.forEach((command) -> {
						if (command instanceof Command) {
							{
								if (Command.Side.CLIENT.equals(((Command) command).side) || Command.Side.BOTH.equals(((Command) command).side))
								{
									commandList.add((Command) command);
									commandNameList.add(((Command) command).name);
								}
							}

						}
					});
				} else {
					FrontEnd.error("Mod " + modClass + " does not have a field named \"commandList\". This is neccesary for the command api to work though. Skipping!");
				}

			}
			catch (Throwable pThrowable) {
				pThrowable.printStackTrace();
			}

		});
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void init()
	{
		FrontEnd.info("LitLaunch Client Proxy Initializing!");

	}

	@Override
	public void postInit()
	{
		FrontEnd.info("LitLaunch Client Proxy PostInitializing!");

	}

	@Override
	public void serverLoad()
	{
		FrontEnd.info("LitLaunch Client Proxy Loading Server!");

	}

	public List<String> getCommandNameList()
	{
		return commandNameList;
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onChat(ClientChatEvent e)
	{
		if (e.getMessage() != null) {
			for (Command command: commandList) {
				if (e.getMessage().contains("/" + command.name))
				{
					e.setCanceled(true);
					String[] args = e.getMessage().split(" ");
					List<ArgumentWrapper<?>> argList;
					try {
						argList = parseArgs(args, command);
					}
					catch (CommandSyntaxException t)
					{
						Message help = t.getRawMessage();
						if (help instanceof ITextComponent)
						{
							Minecraft.getInstance().player.sendStatusMessage((ITextComponent) help, false);
						}
						else
						{
							String helpSTR = t.getMessage();
							Minecraft.getInstance().player.sendStatusMessage(new TextComponentString(helpSTR), false);
						}
						return;
					}
					try {
						command.methodToRun.accept(argList);
					}
					catch (Throwable t) {
						t.printStackTrace();
					}
				}
			}
		}
	}

	public List<ArgumentWrapper<?>> parseArgs(String[] argsIn, Command comm) throws CommandSyntaxException
	{
		FrontEnd.verbose("argsIn: " + Arrays.asList(argsIn));
		List<ArgumentWrapper<?>> out = Lists.newArrayList();
		FrontEnd.verbose("out: " + Arrays.asList(argsIn));
		OptionParser parser = new OptionParser();
		FrontEnd.verbose("parser: " + parser);
		BiMap<ArgumentWrapper<?>, OptionSpec<?>> argToSpec = HashBiMap.create();
		FrontEnd.verbose("argToSpec: " + argToSpec);
		comm.args.forEach((name, arg) -> {
			FrontEnd.verbose("adding arg!");
			FrontEnd.verbose("name: " + name);
			FrontEnd.verbose("arg: " + arg);
			if (arg.isRequired())
			{
				argToSpec.put(arg, parser.accepts(arg.name, arg.getDescription()).withRequiredArg());
			}
			else
			{
				argToSpec.put(arg, parser.accepts(arg.name, arg.getDescription()).withOptionalArg());
			}
		});
		FrontEnd.verbose("argToSpec: " + argToSpec);
		OptionSet optionSet;
		try {
			optionSet = parser.parse(argsIn);
			FrontEnd.verbose("optionSet: " + optionSet);
		} catch (OptionException e)
		{
			String help = comm.generateHelp();
			Message message = new TextComponentString(help);
			throw new CommandSyntaxException(new SimpleCommandExceptionType(message), message);
		}
		argToSpec.forEach((arg, spec) -> {
			Object value1 = optionSet.valueOf(spec);
			FrontEnd.verbose("value1: " + value1);
			if (value1 instanceof String)
			{
				String value = (String) value1;
				FrontEnd.verbose("value: " + value);
				IArgumentParser<? extends IArgumentValue<?>> parser_ = arg.getParser();
				FrontEnd.verbose("parser: " + parser_);
				IArgumentValue val_ = parser_.parse(value);
				FrontEnd.verbose("val_: " + val_);
				arg.setValue(val_);
				FrontEnd.verbose("arg: " + arg);
				out.add(arg);
				FrontEnd.verbose("out: " + out);
			}
		});
		return out;
	}
}
