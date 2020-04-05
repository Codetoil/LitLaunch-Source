/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.api;

import com.google.common.collect.BiMap;
import io.github.codetoil.litlaunch.api.arguments.ArgumentWrapper;

import java.util.List;
import java.util.function.Consumer;

public class Command {
    /**
     * Name of the command.
     */
    public String name;

    /**
     * The arguments applied
     */
    public BiMap<String, ArgumentWrapper<?>> args;

    /**
     * the method this command calls when executed.
     * Is of the form of a Consumer with one input, a List of Strings, those strings being the arguments to the command entered in chat.
     */
    public Consumer<List<ArgumentWrapper<?>>> methodToRun;
    /**
     * Side the command should be registered to. e.g. CLIENT gives a client side command.
     */
    public Side side;

    public Command() {

    }

    public Command(String name, Consumer<List<ArgumentWrapper<?>>> methodToRun, BiMap<String, ArgumentWrapper<?>> args, Side side) {
        this.name = name;
        this.methodToRun = methodToRun;
        this.side = side;
        this.args = args;
    }

    public String generateHelp() {
        StringBuilder builder = new StringBuilder("/" + this.name);
        this.args.forEach((name, arg) -> {
            builder.append(" --");
            builder.append(name);
            builder.append(" ");
            if (arg.isNumber()) {
                builder.append("#");
            }
            if (arg.isRequired()) {
                builder.append("<");
            } else {
                builder.append("[");
            }
            builder.append(arg.name);
            if (arg.isRequired()) {
                builder.append(">");
            } else {
                builder.append("]");
            }
        });
        return builder.toString();
    }

    public enum Side {
        CLIENT,
        SERVER,
        BOTH
    }
}
