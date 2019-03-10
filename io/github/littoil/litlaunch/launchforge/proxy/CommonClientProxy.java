package io.github.littoil.litlaunch.launchforge.proxy;

/**
 * The common for all versions client proxy!
 * */
public abstract class CommonClientProxy extends CommonCommonProxy {

	@Override
	public abstract void preInit();

	@Override
	public abstract void init();

	@Override
	public abstract void postInit();

	@Override
	public abstract void serverLoad();

}
