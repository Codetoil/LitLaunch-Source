package io.github.littoil.litlaunch.version.mc1_8;

import io.github.littoil.litlaunch.launchcommon.IGetFields;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class GetFields implements IGetFields {
	public static final IGetFields INSTANCE = new GetFields();
	private GetFields(){}
	@Override
	public long getTotalWorldTime() {
		World world;
		switch (FMLCommonHandler.instance().getSide()) {
			case SERVER:
				world = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();
				break;
			case CLIENT:
				world = Minecraft.getMinecraft().theWorld;
				break;
			default:
				world = null;
				break;
		}
		if (world != null)
		{
			return world.getTotalWorldTime();
		}
		else
		{
			return -1;
		}
	}
}
