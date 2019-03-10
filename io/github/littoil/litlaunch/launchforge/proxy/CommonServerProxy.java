package io.github.littoil.litlaunch.launchforge.proxy;

/**
 * The common for all versions server proxy!
 * */
public abstract class CommonServerProxy extends CommonCommonProxy {

	@Override
	public abstract void preInit();

	@Override
	public abstract void init();

	@Override
	public abstract void postInit();

	@Override
	public abstract void serverLoad();
	
}
