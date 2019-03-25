package io.github.littoil.litlaunch.version.mc1_12;

import io.github.littoil.litlaunch.launchcommon.IGetFields;
import io.github.littoil.litlaunch.launchcommon.ThreadLogged;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class GetFields implements IGetFields {
	public static final IGetFields INSTANCE = new GetFields();

	@Override
	public long getTotalWorldTime() {
		switch (FMLCommonHandler.instance().getSide()) {
			case SERVER:
				return FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getTotalWorldTime();
			case CLIENT:
				return Minecraft.getMinecraft().world.getTotalWorldTime();
			default:
				return -1;
		}
	}
}
