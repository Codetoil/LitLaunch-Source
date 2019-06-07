package io.github.ianthisawesome.tpsmod;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class GetTPS {

	protected static final Logger LOGGER = LogManager.getLogger();
	private long previousTotalWorldTime = -1;
	private double previousMeasureTime = -1.0;
	public static double tps = -1.0;
	public static String tpsSTR = "";
	public static final double nanotimediv = 1000000000.0;//1,000,000,000.0
	public boolean freepass = false;
	
	public GetTPS()
	{
		LOGGER.debug("GetTPS");
	}
	
	public void resetVals()
	{
		this.previousTotalWorldTime = -1;
		this.previousMeasureTime = -1.0;
		tps = -1.0;
	}
	
	public String getMostRecentChatMessage()
	{
		return (String) Minecraft.getMinecraft().ingameGUI.getChatGUI().getSentMessages().get(Minecraft.getMinecraft().ingameGUI.getChatGUI().getSentMessages().size() - 1);
	}
	
	public void doCommandStuffCommandTPS(ICommandSender sender, String[] argsString)
	{
		if (argsString.length == 1)
		{
			if (argsString[0].equals("help") || argsString[0].equals("?"))
			{
				sender.addChatMessage(new ChatComponentTranslation("message.tpsmod.tps.help"));
				return;
			}
		}
		boolean freepass = false;
		if (this.previousTotalWorldTime == -1) {
			updatePreviousTotalWorldTime(Minecraft.getMinecraft().theWorld);
			freepass = true;
		}
		if (freepass || ((getTimeInSeconds() - this.previousMeasureTime) >= 3.0))
		{
			LOGGER.debug("Passing...");
		}else 
		{
			sender.addChatMessage(new ChatComponentTranslation("message.tpsmod.fail").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			return;
		}
		updateTPS(Minecraft.getMinecraft().theWorld);
		
		sender.addChatMessage(new ChatComponentTranslation("message.tpsmod.tps", new Object[] {GetTPS.tpsSTR}).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
	}
	
	public void doCommandStuffCommandALLTPS(ICommandSender sender, String[] argsString)
	{
		if (argsString.length == 1)
		{
			if (argsString[0].equals("help") || argsString[0].equals("?"))
			{
				sender.addChatMessage(new ChatComponentTranslation("message.tpsmod.alltps.help"));
				return;
			}
		}
		if (this.previousTotalWorldTime == -1) {
			updatePreviousTotalWorldTime(Minecraft.getMinecraft().theWorld);
		}
		if (freepass || ((getTimeInSeconds() - this.previousMeasureTime) >= 3.0))
		{
			LOGGER.debug("Passing...");
		}else 
		{
			sender.addChatMessage(new ChatComponentTranslation("message.tpsmod.fail").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			return;
		}
		updateTPS(Minecraft.getMinecraft().theWorld);
		Minecraft.getMinecraft().thePlayer.sendChatMessage("Littoil's TPS Mod measured a tps of " + GetTPS.tpsSTR + " server ticks per second");
	}
	
	/**
	 * Sets the TotalWorldTime to whatever it is at the moment, effectively updating it
	 * @param worldIn
	 */
	public void updatePreviousTotalWorldTime(World worldIn)
	{
		//LOGGER.info("Updating PTWT");
		try {
			if (worldIn.getTotalWorldTime() == 0) 
			{
				Thread.sleep(500);
				if (worldIn.getTotalWorldTime() == 0)
				{
					Thread.sleep(1000);
					if (worldIn.getTotalWorldTime() == 0)
					{
						LOGGER.warn("The TotalWorldTime is 0 after waiting for the TotalWorldTime"); 
						LOGGER.warn("Please tweet me to @littoil on twitter or email me at ianthisawesomep@gmail.com and tell me about this!");
					}
				}
			}
			this.previousTotalWorldTime = worldIn.getTotalWorldTime();
			this.previousMeasureTime = getTimeInSeconds();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Updates the TPS variable
	 * @param worldIn
	 */
	public void updateTPS(World worldIn)
	{
		//LOGGER.info("Updating TPS");
		//LOGGER.info(System.nanoTime() / nanotimediv);
		if (getTimeInSeconds() - this.previousMeasureTime < 3.0)
		{
			return;
		}
		GetTPS.tps = ((double) (worldIn.getTotalWorldTime() - this.previousTotalWorldTime)) / (getTimeInSeconds() - this.previousMeasureTime);
		updatePreviousTotalWorldTime(worldIn);
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		GetTPS.tpsSTR = df.format(GetTPS.tps);
	}
	
	public static double getTimeInSeconds()
    {
    	return ((double) System.nanoTime()) / nanotimediv;
    }
}
