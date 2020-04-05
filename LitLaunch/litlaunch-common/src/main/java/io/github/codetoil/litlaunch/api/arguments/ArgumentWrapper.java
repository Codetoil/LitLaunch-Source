/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.api.arguments;

public class ArgumentWrapper<T> {
    public final String name;
    private final IArgumentParser<? extends IArgumentValue<T>> parser;
    private final boolean isRequired;
    private final boolean isNumber;
    private IArgumentValue<T> value;
    private String description = "";

    public ArgumentWrapper(String name, IArgumentParser<? extends IArgumentValue<T>> parser) {
        this(name, parser, name, true);
    }

    public ArgumentWrapper(String name, IArgumentParser<? extends IArgumentValue<T>> parser, String pDescription, boolean pIsRequired) {
        this.name = name;
        this.parser = parser;
        this.description = pDescription;
        this.isRequired = pIsRequired;
        this.isNumber = parser.isNumber();

    }

    public ArgumentWrapper(String name, IArgumentParser<? extends IArgumentValue<T>> parser, boolean pIsRequired) {
        this(name, parser, name, pIsRequired);
    }

    public ArgumentWrapper(String name, IArgumentParser<? extends IArgumentValue<T>> parser, String pDescription) {
        this(name, parser, pDescription, true);
    }

    public IArgumentValue<T> getValue() {
        return value;
    }

    public void setValue(IArgumentValue<T> pValue) {
        value = pValue;
    }

    public IArgumentParser<? extends IArgumentValue<T>> getParser() {
        return parser;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public String getDescription() {
        return description;
    }

    public ArgumentWrapper<T> setDescription(String pDescription) {
        description = pDescription;
        return this;
    }

    public boolean isNumber() {
        return isNumber;
    }

    @Override
    public String toString() {
        return "ArgumentWrapper{name=" + name + ", value=" + value + ", description=" + description + ", parser=" + parser + ", isRequired=" + isRequired + "}";
    }
}
