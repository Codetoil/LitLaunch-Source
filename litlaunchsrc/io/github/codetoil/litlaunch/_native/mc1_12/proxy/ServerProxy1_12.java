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
		FrontEnd.info("Lit Launch Server Proxy Setting Game Path On Lit Launch!");
		LaunchCommon.setGamePath(FMLCommonHandler.instance().getMinecraftServerInstance().getFile(".").toPath());
	}

	@Override
	public void construction()
	{
		FrontEnd.info("Lit Launch Server Proxy Constructing!");

	}

	@Override
	public void preInit()
	{
		FrontEnd.info("Lit Launch Server Proxy PreInitializing!");

	}

	@Override
	public void init()
	{
		FrontEnd.info("Lit Launch Server Proxy Initializing!");

	}

	@Override
	public void postInit()
	{
		FrontEnd.info("Lit Launch Server Proxy PostInitializing!");

	}

	@Override
	public void serverLoad()
	{
		FrontEnd.info("Lit Launch Server Proxy Loading Server!");

	}
}
