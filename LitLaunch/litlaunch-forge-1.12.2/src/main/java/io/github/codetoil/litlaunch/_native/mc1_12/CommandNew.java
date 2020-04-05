/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch._native.mc1_12;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import io.github.codetoil.litlaunch.api.Command;
import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.api.arguments.ArgumentWrapper;
import io.github.codetoil.litlaunch.api.arguments.IArgumentParser;
import io.github.codetoil.litlaunch.api.arguments.IArgumentValue;
import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandNew implements ICommand {

    Command comm;

    public CommandNew() {
    }

    public CommandNew(Command comm) {
        this.comm = comm;
    }

    @Override
    public String getName() {
        return comm.name;
    }

    @Override
    public List<String> getAliases() {
        return new ArrayList<>();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        List<ArgumentWrapper<?>> argWrappers = parseArgs(args, sender);
        try {
            comm.methodToRun.accept(argWrappers);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public List<ArgumentWrapper<?>> parseArgs(String[] argsIn, ICommandSender sender) throws WrongUsageException {
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
            if (arg.isRequired()) {
                argToSpec.put(arg, parser.accepts(arg.name, arg.getDescription()).withRequiredArg());
            } else {
                argToSpec.put(arg, parser.accepts(arg.name, arg.getDescription()).withOptionalArg());
            }
        });
        FrontEnd.verbose("argToSpec: " + argToSpec);
        OptionSet optionSet;
        try {
            optionSet = parser.parse(argsIn);
            FrontEnd.verbose("optionSet: " + optionSet);
        } catch (OptionException e) {
            throw new WrongUsageException(getUsage(sender));
        }
        argToSpec.forEach((arg, spec) -> {
            Object value1 = optionSet.valueOf(spec);
            FrontEnd.verbose("value1: " + value1);
            if (value1 instanceof String) {
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

    @Override
    public String getUsage(ICommandSender sender) {
        return comm.generateHelp();
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
                                          net.minecraft.util.math.BlockPos targetPos) {
        List<String> tabs = new ArrayList<>();
        comm.args.forEach((name, arg) -> {
            tabs.add("--" + name);
        });
        return tabs;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand arg0) {
        return this.comm.name.compareTo(arg0.getName());
    }

}
