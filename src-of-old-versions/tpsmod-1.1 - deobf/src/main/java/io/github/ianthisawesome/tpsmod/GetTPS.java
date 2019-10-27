package io.github.ianthisawesome.tpsmod;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetTPS
{
	public static final double nanotimediv = 1.0E9D;
	protected static final Logger LOGGER = LogManager.getLogger();
	public static double tps = -1.0D;
	private static String mostRecentChatMessage = null;
	private long previousTotalWorldTime = -1L;
	private double previousMeasureTime = -1.0D;

	public GetTPS(String name)
	{
	}

	public String getMostRecentChatMessage()
	{
		return (String) Minecraft.getMinecraft().ingameGUI.getChatGUI().getSentMessages().get(Minecraft.getMinecraft().ingameGUI.getChatGUI().getSentMessages().size() - 1);
	}

	public void doCommandStuffCommandTPS(ICommandSender sender, String[] argsString)
	{
		if (argsString.length != 1 || !argsString[0].equals("help") && !argsString[0].equals("?"))
		{
			if (this.previousTotalWorldTime == -1L)
			{
				this.updatePreviousTotalWorldTime(Minecraft.getMinecraft().theWorld);
			}

			this.updateTPS(Minecraft.getMinecraft().theWorld);
			sender.addChatMessage(new ChatComponentTranslation("message.tpsmod.tps", new Object[] {tps}).setChatStyle((new ChatStyle()).setColor(EnumChatFormatting.YELLOW)));
		}
		else
		{
			sender.addChatMessage(new ChatComponentTranslation("message.tpsmod.tps.help", new Object[0]));
		}
	}

	public void updatePreviousTotalWorldTime(World worldIn)
	{
		try
		{
			if (worldIn.getTotalWorldTime() == 0L)
			{
				Thread.sleep(500L);
				if (worldIn.getTotalWorldTime() == 0L)
				{
					Thread.sleep(1000L);
					if (worldIn.getTotalWorldTime() == 0L)
					{
						LOGGER.warn("The TotalWorldTime is 0 after waiting for the TotalWorldTime");
						LOGGER.warn("Please tweet me to @littoil on twitter or email me at ianthisawesomep@gmail.com and tell me about this!");
					}
				}
			}
		}
		catch (InterruptedException var3)
		{
			var3.printStackTrace();
		}

		this.previousTotalWorldTime = worldIn.getTotalWorldTime();
		this.previousMeasureTime = getTimeInSeconds();
	}

	public void updateTPS(World worldIn)
	{
		if (tps != -1.0D)
		{
			tps = (double) (worldIn.getTotalWorldTime() - this.previousTotalWorldTime) / (getTimeInSeconds() - this.previousMeasureTime);
		}

		this.updatePreviousTotalWorldTime(worldIn);
		if (tps == -1.0D)
		{
			tps = (double) (worldIn.getTotalWorldTime() - this.previousTotalWorldTime) / (getTimeInSeconds() - this.previousMeasureTime);
		}

	}

	public static double getTimeInSeconds()
	{
		return (double) System.nanoTime() / 1.0E9D;
	}

	public void doCommandStuffCommandALLTPS(ICommandSender sender, String[] argsString)
	{
		if (argsString.length != 1 || !argsString[0].equals("help") && !argsString[0].equals("?"))
		{
			if (this.previousTotalWorldTime == -1L)
			{
				this.updatePreviousTotalWorldTime(Minecraft.getMinecraft().theWorld);
			}

			this.updateTPS(Minecraft.getMinecraft().theWorld);
			Minecraft.getMinecraft().thePlayer.sendChatMessage(((EntityPlayer) sender).getDisplayName() + " measured the tps using the TPS Mod by Littoil, and got a tps of " + tps);
		}
		else
		{
			sender.addChatMessage(new ChatComponentTranslation("message.tpsmod.alltps.help", new Object[0]));
		}
	}
}
