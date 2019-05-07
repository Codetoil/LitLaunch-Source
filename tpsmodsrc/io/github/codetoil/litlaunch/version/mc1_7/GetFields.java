package io.github.littoil.litlaunch.version.mc1_7;

import cpw.mods.fml.common.FMLCommonHandler;
import io.github.littoil.litlaunch.launchcommon.IGetFields;
import io.github.littoil.litlaunch.launchcommon.LaunchMods;
import net.minecraft.server.MinecraftServer;

public class GetFields implements IGetFields {
	public static final IGetFields INSTANCE = new GetFields();

	@Override
	public long getTotalWorldTime(int dimension) {
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		LaunchMods.getINSTANCE().getLOGGER().debug(server);
		LaunchMods.getINSTANCE().getLOGGER().debug(server.getEntityWorld());
		LaunchMods.getINSTANCE().getLOGGER().debug(server.getEntityWorld().isRemote);
		if(server.getEntityWorld().isRemote)  {
			return server.worldServerForDimension(dimension).getTotalWorldTime();
		}
		else
		{
			return server.getEntityWorld().getTotalWorldTime();
		}
	}
}
