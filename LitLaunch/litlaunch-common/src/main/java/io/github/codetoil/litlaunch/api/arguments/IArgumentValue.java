/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.api.arguments;

public interface IArgumentValue<T> {
    IArgumentParser<? extends IArgumentValue> getParser();

    T getValue();
}
