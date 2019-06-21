/*
 * Copyright Codetoil (c) 2019
 */

package io.github.codetoil.litlaunch.version.mc1_8;

import io.github.codetoil.litlaunch.api.*;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GetFields implements IGetFields
{
	public static final IGetFields INSTANCE = new io.github.codetoil.litlaunch.version.mc1_8.GetFields();

	private GetFields()
	{
	}

	@Override
	@SideOnly(Side.CLIENT)
	public synchronized long getTotalWorldTime(int dimension)
	{
		long result;
		try {
			World world = DimensionManager.getWorld(dimension);
			if (world == null) // World for dimension is not loaded, but check entityworld
			{
				world = Minecraft.getMinecraft().theWorld;
				if (world != null) {
					WorldProvider provider = world.provider;
					if (provider != null) {
						if (world.provider.getDimensionId() == dimension) {
							result = world.getTotalWorldTime();
						} else {
							result = -1;
							//LaunchMods.error("Dimension " + dimension + " is not loaded!");
							// This dimension is not loaded
						}
					} else {
						result = -1;
						LaunchMods.error("World provider for Client world could not be loaded!");
						// world provider cannot be loaded
					}
				} else {
					result = -1;
					//LaunchMods.error("Client World Doesn't Exist!");
					// world cannot be loaded
				}
			} else {
				result = world.getTotalWorldTime();
			}
		}
		catch (Throwable e) {
			result = -1;
			LaunchMods.error("Something went wrong!");
			e.printStackTrace();
		}

		return result;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getDimPlayer()
	{
		return net.minecraft.client.Minecraft.getMinecraft().thePlayer.dimension;
	}

	@Override
	public int[] getDimsAvailable()
	{
		Integer[] dims = DimensionManager.getStaticDimensionIDs();
		int[] dimIDs = new int[dims.length];
		int j = 0;
		for (Integer dim : dims) {
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
