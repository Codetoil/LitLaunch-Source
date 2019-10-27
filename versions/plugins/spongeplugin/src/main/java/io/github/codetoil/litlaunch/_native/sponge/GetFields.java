/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch._native.sponge;

import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.api.IGetFields;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.DimensionType;
import org.spongepowered.api.world.DimensionTypes;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class GetFields implements IGetFields
{
	public static final IGetFields INSTANCE = new io.github.codetoil.litlaunch._native.sponge.GetFields();

	private GetFields()
	{
	}

	@Override
	public synchronized long getTotalWorldTime(int dimension)
	{
		long result = -1;
		try
		{
			if (!Sponge.isServerAvailable())
			{
				return -1;
			}
			Optional<World> worldOptional = Sponge.getServer().getWorld(getDimensionStringFromID(dimension));
			if (!worldOptional.isPresent())
			{
				return -1;
			}
			World world = worldOptional.get();
			return world.getProperties().getTotalTime();
		}
		catch (Throwable e)
		{
			FrontEnd.error("Something went wrong!");
			e.printStackTrace();
		}

		return result;
	}

	private String getDimensionStringFromID(int id)
	{
		DimensionType type;
		switch (id)
		{
			case -1:
				type = DimensionTypes.NETHER;
				break;
			case 0:
				type = DimensionTypes.OVERWORLD;
				break;
			case 1:
				type = DimensionTypes.THE_END;
				break;
			default:
				return "";
		}
		return type.getId();
	}

	//Client Thing
	@Override
	public int getDimRunning()
	{
		throw new IllegalStateException("This should not be running on a client!");
	}

	@Override
	public int[] getDimsAvailable()
	{
		return new int[] {-1, 0, 1};
	}

	@Override
	public String getVERSION()
	{
		return Sponge.getGame().getPlatform().getMinecraftVersion().getName();
	}
}
