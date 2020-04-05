package io.github.ianthisawesome.tpsmod;

import java.util.Random;

public class Ads extends ThreadLogged
{
	private volatile Random rand = new Random();
	private volatile double timeOfLastAd = GetTPS.getTimeInSeconds();

	public Ads(String name)
	{
		super(name);
	}

	public void runOn()
	{
		LOGGER.info(Long.valueOf(this.getRandomNumber(5L, 10L)));

		while (true)
		{
			try
			{
				Thread.sleep(100L);
			}
			catch (InterruptedException var2)
			{
				var2.printStackTrace();
			}
		}
	}

	private long getRandomNumber(long min, long max)
	{
		return (long) this.map(Math.random(), 0.0D, 1.0D, (double) min, (double) max);
	}

	private double map(double value, double oldmin, double oldmax, double newmin, double newmax)
	{
		double m = (newmax - newmin) / (oldmax - oldmin);
		return m * (value - oldmin) + newmin;
	}
}
