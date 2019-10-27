/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch._native.mc1_13;

import com.google.common.collect.Lists;
import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.api.IGetFields;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.Iterator;
import java.util.List;

public class GetFields implements IGetFields
{
	public static final IGetFields INSTANCE = new io.github.codetoil.litlaunch._native.mc1_13.GetFields();

	private GetFields()
	{
	}

	@Override
	public synchronized long getTotalWorldTime(int dimension)
	{
		long result = -1;
		try
		{
			DimensionType lDimensionType = DimensionType.getById(dimension);
			if (lDimensionType != null)
			{
				World world = DimensionManager.getWorld(ServerLifecycleHooks.getCurrentServer(), lDimensionType, false, false);
				if (FMLEnvironment.dist.isClient() && world == null) // World for dimension is not loaded, but check entityworld
				{
					world = getWorldClient();
					if (world != null)
					{
						if (world.dimension.getType().getId() == dimension)
						{
							result = world.getGameTime();
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
						result = world.getGameTime();
					}
				}
			}
			else
			{
				return -1;
			}
		}
		catch (Throwable e)
		{
			FrontEnd.error("Something went wrong!");
			e.printStackTrace();
		}

		return result;
	}

	@OnlyIn(Dist.CLIENT)
	public World getWorldClient()
	{
		return Minecraft.getInstance().world;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public int getDimRunning()
	{
		return Minecraft.getInstance().player.dimension.getId();
	}

	@Override
	public int[] getDimsAvailable()
	{
		Iterator<DimensionType> dims = DimensionType.func_212681_b().iterator();
		List<DimensionType> dimIDs = Lists.newArrayList();
		while (dims.hasNext())
		{
			dimIDs.add(dims.next());
		}
		int[] dims_ = new int[dimIDs.size()];
		int j = 0;
		for (DimensionType dimT :
				dimIDs)
		{
			dims_[j++] = dimT.getId();
		}
		return dims_;
	}

	@Override
	public String getVERSION()
	{
		return "1.13.x";
	}
}
