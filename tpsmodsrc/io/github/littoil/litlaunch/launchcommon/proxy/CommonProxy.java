package io.github.littoil.litlaunch.launchcommon.proxy;

/**
 * The common for all versions common proxy!
 * */
public interface CommonProxy {
    
    public abstract void preInit();

    public abstract void init();

    public abstract void postInit();

    public abstract void serverLoad();
}
