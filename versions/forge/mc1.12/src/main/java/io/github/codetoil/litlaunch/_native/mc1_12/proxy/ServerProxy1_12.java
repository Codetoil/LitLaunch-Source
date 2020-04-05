/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch._native.mc1_12.proxy;


import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.core.CommonProxy;
import io.github.codetoil.litlaunch.core.LaunchCommon;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ServerProxy1_12 implements CommonProxy
{
	@Override
	public void setGamePath()
	{
		FrontEnd.info("LitLaunch Server Proxy Setting Game Path On Lit Launch!");
		LaunchCommon.setGamePath(FMLCommonHandler.instance().getMinecraftServerInstance().getFile(".").toPath());
	}

	@Override
	public void construction()
	{
		FrontEnd.info("LitLaunch Server Proxy Constructing!");

	}

	@Override
	public void preInit()
	{
		FrontEnd.info("LitLaunch Server Proxy PreInitializing!");

	}

	@Override
	public void init()
	{
		FrontEnd.info("LitLaunch Server Proxy Initializing!");

	}

	@Override
	public void postInit()
	{
		FrontEnd.info("LitLaunch Server Proxy PostInitializing!");

	}

	@Override
	public void serverLoad()
	{
		FrontEnd.info("LitLaunch Server Proxy Loading Server!");

	}
}
