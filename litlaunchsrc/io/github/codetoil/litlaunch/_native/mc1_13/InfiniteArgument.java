package io.github.codetoil.litlaunch._native.mc1_13;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.codetoil.litlaunch.api.FrontEnd;
import net.minecraft.command.CommandSource;

import java.util.Arrays;

public class InfiniteArgument implements ArgumentType<String> {
	public String result;
	@Override
	public <S> String parse(StringReader reader) throws CommandSyntaxException
	{
		String full = reader.getString();
		FrontEnd.debug(full);
		String[] args = full.split(" ", 2);
		FrontEnd.debug(Arrays.toString(args));
		FrontEnd.debug(args[0]);
		FrontEnd.debug(args[1]);
		result = args[1];
		return args[1];
	}

	public static String getString(CommandContext<CommandSource> context) throws CommandSyntaxException {
		String input = context.getInput();
		FrontEnd.debug("Input: " + input);
		return input;
	}
}
