package io.github.littoil.litlaunch.launchcommon;

import io.github.littoil.litlaunch.launchcommon.events.LitEvent;

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
