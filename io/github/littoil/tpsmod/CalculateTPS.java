package io.github.littoil.tpsmod;

import io.github.littoil.litlaunch.launchcommon.ThreadLogged;

public class CalculateTPS extends ThreadLogged {
	private long totalWorldTimePrev;
	private long previousMeasureTime; // In seconds

	public CalculateTPS()
	{
		super("calculateTPS");
	}

	@Override
	public void run() {
		while (true)
		{
			try {
				sleep(100);
			} catch (Exception e)
			{
				e.printStackTrace(System.err);
			}
		}
	}
}
