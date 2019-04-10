package io.github.littoil.litlaunch.version.mc1_12;

import io.github.littoil.litlaunch.launchcommon.IGetFields;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class GetFields implements IGetFields {
	public static final IGetFields INSTANCE = new GetFields();

	private GetFields(){}

	@Override
	public long getTotalWorldTime(int dimension) {
		return FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(dimension).getTotalWorldTime();
	}
}
