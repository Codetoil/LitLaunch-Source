package io.github.codetoil.litlaunch._native.mc1_14.proxy;


import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.core.CommonProxy;

public class ServerProxy1_14 implements CommonProxy {
    @Override
    public void setGamePath() {
        FrontEnd.info("LitLaunch Server Proxy Setting Game Path On Lit Launch!");
    }

    @Override
    public void construction() {
        FrontEnd.info("LitLaunch Server Proxy Constructing!");

    }

    @Override
    public void preInit() {
        FrontEnd.info("LitLaunch Server Proxy PreInitializing!");

    }

    @Override
    public void init() {
        FrontEnd.info("LitLaunch Server Proxy Initializing!");

    }

    @Override
    public void postInit() {
        FrontEnd.info("LitLaunch Server Proxy PostInitializing!");

    }

    @Override
    public void serverLoad() {
        FrontEnd.info("LitLaunch Server Proxy Loading Server!");

    }
}
