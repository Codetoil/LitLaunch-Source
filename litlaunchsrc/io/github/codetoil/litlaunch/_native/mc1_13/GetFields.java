package io.github.codetoil.litlaunch._native.mc1_13;

import com.google.common.collect.Lists;
import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.api.IGetFields;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.DimensionManager;
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
	@OnlyIn(Dist.CLIENT)
	public synchronized long getTotalWorldTime(int dimension)
	{
		long result;
		try {
			DimensionType lDimensionType = DimensionType.getById(dimension);
			if (lDimensionType == null)
			{
				return -1;
			}
			MinecraftServer lMinecraftServer = ServerLifecycleHooks.getCurrentServer();
			if (lMinecraftServer == null)
			{
				return -1;
			}
			World world = DimensionManager.getWorld(lMinecraftServer, lDimensionType, false, false);
			if (world == null) // World for dimension is not loaded, but check entityworld
			{
				world = Minecraft.getInstance().world;
				if (world != null) {
					if (world.dimension.getType().getId() == dimension) {
						result = world.getGameTime();
					} else {
						result = -1;
						//LaunchMods.error("Dimension " + dimension + " is not loaded!");
						// This dimension is not loaded
					}
				} else {
					result = -1;
					//LaunchMods.error("Client World Doesn't Exist!");
					// world cannot be loaded
				}
			} else {
				result = world.getGameTime();
			}
		}
		catch (Throwable e) {
			result = -1;
			FrontEnd.error("Something went wrong with getting the totalworldtime!");
			e.printStackTrace();
		}

		return result;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public int getDimPlayer()
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
		for (DimensionType dimT:
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
