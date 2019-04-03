package io.github.littoil.litlaunch.version.mc1_7;

import cpw.mods.fml.common.FMLCommonHandler;
import io.github.littoil.litlaunch.launchcommon.IGetFields;
import net.minecraft.client.Minecraft;

public class GetFields implements IGetFields {
	public static final IGetFields INSTANCE = new GetFields();

	@Override
	public long getTotalWorldTime() {
		switch (FMLCommonHandler.instance().getSide()) {
			case SERVER:
				return FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getTotalWorldTime();
			case CLIENT:
				return Minecraft.getMinecraft().theWorld.getTotalWorldTime();
			default:
				return -1;
		}
	}
}
