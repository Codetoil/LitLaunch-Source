package io.github.littoil.litlaunch.launchcommon.proxy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The common for all versions common proxy!
 * */
public abstract class CommonCommonProxy {

    protected static final Logger LOGGER = LogManager.getLogger();
    
    public abstract void preInit();

    public abstract void init();

    public abstract void postInit();

    public abstract void serverLoad();
}
