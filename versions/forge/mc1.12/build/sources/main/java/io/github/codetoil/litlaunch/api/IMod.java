/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.api;

import java.util.List;

public interface IMod
{
	List<Command> getCommandList();

	void construction();

	void preInit();

	void Init();

	void postInit();

	void serverLoad();

	IMod getModINSTANCE();
}
