/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.core;

/**
 * The common for all versions common proxy!
 */
public interface CommonProxy {
    void setGamePath();

    void construction();

    void preInit();

    void init();

    void postInit();

    void serverLoad();
}
