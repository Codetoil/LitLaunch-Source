package io.github.ianthisawesome.tpsmod;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.TimerTask;

public class TPSCounter extends TimerTask
{
	private static final Logger LOGGER = LogManager.getLogger();
	public static double tps;
	private long totalWorldTime;

	public TPSCounter()
	{
		LOGGER.info("Starting TPS Counter");
	}

	public void run()
	{
		try
		{
			if (Minecraft.func_71410_x().field_71441_e != null)
			{
				try
				{
					tps = (double) (Minecraft.func_71410_x().field_71441_e.func_72912_H().func_82573_f() - this.totalWorldTime) * Math.pow(TpsMod.timeToMeasure, -1.0D);
					this.totalWorldTime = Minecraft.func_71410_x().field_71441_e.func_72912_H().func_82573_f();
					LOGGER.info(tps + " tps");
				}
				catch (NullPointerException var2)
				{
					this.totalWorldTime = Minecraft.func_71410_x().field_71441_e.func_72912_H().func_82573_f();
				}
				catch (ArithmeticException var3)
				{
					LOGGER.warn("The Math Broke... an ArithmeticException.");
				}
				catch (Exception var4)
				{
					var4.printStackTrace();
				}
			}
		}
		catch (NullPointerException var5)
		{
			;
		}
		catch (Exception var6)
		{
			var6.printStackTrace();
		}

	}
}
