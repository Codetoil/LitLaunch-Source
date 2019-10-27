package io.github.ianthisawesome.tpsmod;

import net.minecraft.client.Minecraft;

public class WorldLogger extends ThreadLogged
{
	public static String worldName;
	public static String worldNameOld;
	public static int delay;
	public static boolean onWorld = false;
	public GetTPS getChatMessage;

	public WorldLogger(String name)
	{
		super(name);
	}

	public void runOn()
	{
		while (true)
		{
			try
			{
				worldName = Minecraft.func_71410_x().field_71441_e.func_72912_H().func_76065_j();
				if (!worldName.isEmpty() && !onWorld)
				{
					this.joinWorld();
				}

				onWorld = true;
			}
			catch (NullPointerException var4)
			{
				if (onWorld)
				{
					this.exitWorld();
				}

				onWorld = false;
			}

			try
			{
				worldNameOld = worldName;
			}
			catch (NullPointerException var3)
			{
				;
			}

			try
			{
				Thread.sleep((long) delay);
			}
			catch (InterruptedException var2)
			{
				var2.printStackTrace();
			}
		}
	}

	public void joinWorld()
	{
		LOGGER.info("Joined World " + worldName);
		this.getChatMessage = new GetTPS("Get TPS");
		this.getChatMessage.updateTPS(Minecraft.func_71410_x().field_71441_e);
	}

	public void exitWorld()
	{
		LOGGER.info("Left World " + worldNameOld);
	}
}
