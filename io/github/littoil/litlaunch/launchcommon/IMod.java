package io.github.littoil.litlaunch.launchcommon;

import io.github.littoil.litlaunch.launchcommon.events.LitEvent;

import java.util.List;

public interface IMod {
	public List<Command> getCommandList();
	public void preInit();
	public void Init();
	public void postInit();
	public void serverLoad();
	IMod getMainINSTNACE();
}
