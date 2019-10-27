package io.github.ianthisawesome.tpsmod;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class GetTPS extends ThreadLogged
{
	public static final double nanotimediv = 1.0E9D;
	public static double tps = -1.0D;
	private static String mostRecentChatMessage = null;
	private long previousTotalWorldTime = -1L;
	private double previousMeasureTime = -1.0D;

	public GetTPS(String name)
	{
		super(name);
	}

	public void runOn()
	{
		while (true)
		{
			try
			{
				if ((new String("/tps")).equals(this.getMostRecentChatMessage()))
				{
					this.updateTPS(Minecraft.func_71410_x().field_71441_e);
					this.doCommandStuff(Minecraft.func_71410_x().field_71439_g, new String[0]);
					Minecraft.func_71410_x().field_71456_v.func_146158_b().func_146239_a(".tps");
				}
			}
			catch (ArrayIndexOutOfBoundsException var3)
			{
				;
			}
			catch (Exception var4)
			{
				var4.printStackTrace();
			}

			try
			{
				Thread.sleep(300L);
			}
			catch (InterruptedException var2)
			{
				var2.printStackTrace();
			}
		}
	}

	public String getMostRecentChatMessage()
	{
		return (String) Minecraft.func_71410_x().field_71456_v.func_146158_b().func_146238_c().get(Minecraft.func_71410_x().field_71456_v.func_146158_b().func_146238_c().size() - 1);
	}

	public void updateTPS(World worldIn)
	{
		if (tps != -1.0D)
		{
			tps = (double) (worldIn.func_82737_E() - this.previousTotalWorldTime) / (getTimeInSeconds() - this.previousMeasureTime);
		}

		this.updatePreviousTotalWorldTime(worldIn);
		if (tps == -1.0D)
		{
			tps = (double) (worldIn.func_82737_E() - this.previousTotalWorldTime) / (getTimeInSeconds() - this.previousMeasureTime);
		}

	}

	public void doCommandStuff(ICommandSender sender, String[] argsString)
	{
		World world = sender.func_130014_f_();
		sender.func_145747_a((new ChatComponentTranslation("message.tpsmod.tps", new Object[] {tps})).func_150255_a((new ChatStyle()).func_150238_a(EnumChatFormatting.YELLOW)));
	}

	public static double getTimeInSeconds()
	{
		return (double) System.nanoTime() / 1.0E9D;
	}

	public void updatePreviousTotalWorldTime(World worldIn)
	{
		try
		{
			if (worldIn.func_82737_E() == 0L)
			{
				Thread.sleep(500L);
				if (worldIn.func_82737_E() == 0L)
				{
					Thread.sleep(1000L);
					if (worldIn.func_82737_E() == 0L)
					{
						LOGGER.warn("The Total WorldTime is 0 after trying to fix it to no avail, this means the tps will be (even more maybe) inaccurate! Sorry!");
						LOGGER.warn("Please tweet me to @littoil on twitter or email me at ianthisawesomep@gmail.com and tell me about this!");
					}
				}
			}
		}
		catch (InterruptedException var3)
		{
			var3.printStackTrace();
		}

		this.previousTotalWorldTime = worldIn.func_82737_E();
		this.previousMeasureTime = getTimeInSeconds();
	}
}
