/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch._native.mc1_12;

import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.api.IGetFields;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GetFields implements IGetFields
{
	public static final IGetFields INSTANCE = new io.github.codetoil.litlaunch._native.mc1_12.GetFields();

	private GetFields()
	{
	}

	@Override
	public synchronized long getTotalWorldTime(int dimension)
	{
		long result = -1;
		try
		{
			World world = DimensionManager.getWorld(dimension);
			if (FMLCommonHandler.instance().getSide().isClient() && world == null) // World for dimension is not loaded, but check entityworld
			{
				world = getWorldClient();
				if (world != null)
				{
					WorldProvider provider = world.provider;
					if (provider != null)
					{
						if (world.provider.getDimension() == dimension)
						{
							result = world.getTotalWorldTime();
						}
					}
					else
					{
						FrontEnd.error("World provider for Client world could not be loaded!");
						// world provider cannot be loaded
					}
				}
				else
				{
					//LaunchMods.error("Client World Doesn't Exist!");
					// world cannot be loaded
				}
			}
			else
			{
				if (world != null)
				{
					result = world.getTotalWorldTime();
				}
			}
		}
		catch (Throwable e)
		{
			FrontEnd.error("Something went wrong!");
			e.printStackTrace();
		}

		return result;
	}

	@SideOnly(Side.CLIENT)
	public World getWorldClient()
	{
		return Minecraft.getMinecraft().world;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getDimRunning()
	{
		return net.minecraft.client.Minecraft.getMinecraft().player.dimension;
	}

	@Override
	public int[] getDimsAvailable()
	{
		Integer[] dims = DimensionManager.getStaticDimensionIDs();
		int[] dimIDs = new int[dims.length];
		int j = 0;
		for (Integer dim : dims)
		{
			dimIDs[j++] = dim;
		}
		return dimIDs;
	}

	@Override
	public String getVERSION()
	{
		return MinecraftForge.MC_VERSION;
	}
}
