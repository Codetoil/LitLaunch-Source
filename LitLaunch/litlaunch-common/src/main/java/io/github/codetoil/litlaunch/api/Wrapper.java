/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.api;

public class Wrapper<T> {
    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T pValue) {
        value = pValue;
    }
}
