/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.api;

import java.util.List;

public interface IMod {
    EnumRequireOrNot onClient();

    EnumRequireOrNot onServer();

    String getModID();

    String getVersion();

    List<Command> getCommandList();

    void construction();

    void preInit();

    void Init();

    void postInit();

    void serverLoad();

    IMod getModINSTANCE();
}
