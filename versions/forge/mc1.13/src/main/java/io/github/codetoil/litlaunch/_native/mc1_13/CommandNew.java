package io.github.codetoil.litlaunch._native.mc1_13;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import io.github.codetoil.litlaunch.api.Command;
import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.api.arguments.ArgumentParserListString;
import io.github.codetoil.litlaunch.api.arguments.ArgumentWrapper;
import io.github.codetoil.litlaunch.api.arguments.IArgumentParser;
import io.github.codetoil.litlaunch.api.arguments.IArgumentValue;
import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TextComponentString;

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

	public void register(CommandDispatcher<CommandSource> disp)
	{
		LiteralArgumentBuilder<CommandSource> argbuilder = Commands.literal(comm.name);
		argbuilder.then(Commands.argument("args", new ArgumentParserWrapperType<>(new ArgumentParserListString())));
		argbuilder.executes((pCommandContext) -> {
			execute(pCommandContext);
			return 1;
		});
		disp.register(argbuilder);
	}

	public void execute(CommandContext<CommandSource> context)
	{
		FrontEnd.verbose(comm);
		FrontEnd.verbose(context);
		List<String> args = context.getArgument("args", List.class);
		try
		{
			FrontEnd.verbose(comm);
			FrontEnd.verbose(comm.methodToRun);
			comm.methodToRun.accept(parseArgs((String[]) args.toArray()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	//public String getUsage(ICommandSender sender) {
	//	return comm.usage;
	//}

	public List<ArgumentWrapper<?>> parseArgs(String[] argsIn) throws CommandSyntaxException
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
		try
		{
			optionSet = parser.parse(argsIn);
			FrontEnd.verbose("optionSet: " + optionSet);
		}
		catch (OptionException e)
		{
			//throw new WrongUsageException(getUsage(sender));
			throw new com.mojang.brigadier.exceptions.CommandSyntaxException(new SimpleCommandExceptionType(new TextComponentString(comm.generateHelp())), new TextComponentString(comm.generateHelp()));
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

	public Command getComm()
	{
		return comm;
	}

	public boolean isUsernameIndex(String[] args, int index)
	{
		return false;
	}
}
