/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.api;

/**
 * The common for all versions common proxy!
 * */
public interface CommonProxy {
    
    void preInit();

    void init();

    void postInit();

    void serverLoad();
}
