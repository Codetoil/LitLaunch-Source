package io.github.codetoil.litlaunch._native.mc1_15;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.codetoil.litlaunch.api.arguments.IArgumentParser;
import io.github.codetoil.litlaunch.api.arguments.IArgumentValue;

public class ArgumentParserWrapperType<T> implements ArgumentType<T> {
    public final IArgumentParser<IArgumentValue<T>> parser;

    public ArgumentParserWrapperType(IArgumentParser<IArgumentValue<T>> parser) {
        this.parser = parser;
    }

    @Override
    public T parse(StringReader pStringReader) throws CommandSyntaxException {
        String string = pStringReader.readString();
        IArgumentValue<T> value = parser.parse(string);
        return value.getValue();
    }
}
