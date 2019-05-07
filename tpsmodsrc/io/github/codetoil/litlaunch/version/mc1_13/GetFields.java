package io.github.littoil.litlaunch.version.mc1_13;

import io.github.littoil.litlaunch.launchcommon.IGetFields;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class GetFields implements IGetFields {
	public static final IGetFields INSTANCE = new GetFields();

	private GetFields(){}

	@Override
	public long getTotalWorldTime(int dimension) {
		return ServerLifecycleHooks.getCurrentServer().getWorld(DimensionType.getById(dimension)).getGameTime();
	}
}
