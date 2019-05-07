package io.github.codetoil.litlaunch.launchcommon;

import java.util.List;

public interface IMod{
	List<Command> getCommandList();
	void construction();
	void preInit();
	void Init();
	void postInit();
	void serverLoad();
	IMod getMainINSTNACE();
}
