/*
 * Copyright Codetoil (c) 2019
 */

package io.github.codetoil.tpsmod;

import io.github.codetoil.litlaunch.FrontEnd;
import io.github.codetoil.litlaunch.api.LaunchMods;
import io.github.codetoil.litlaunch.backend.LaunchCommon;
import io.github.codetoil.litlaunch.event.LitEvent;
import io.github.codetoil.litlaunch.event.LitEventHandler;

public class CalculateTPS implements LitEventHandler.EventListener
{
	private final int Dimension;
	private long previousTotalWorldTime;
	private double previousMeasureTime;
	private double TPS;

	public CalculateTPS(int Dimension)
	{
		this.Dimension = Dimension;
	}

	public void ReceivedEvent(LitEvent event)
	{
		if (event.getType().equals("updateTPS")) {
			if (event.getData()[2].equals(this.Dimension)) {
				updateTPS();
			}
		}
	}

	private void updateTPS()
	{
		LaunchMods.debug("Update TPS for dimension " + Dimension);
		long totalworldtime = FrontEnd.GET_FIELDS().getTotalWorldTime(this.Dimension);
		double MeasureTime = LaunchCommon.getTimeInSeconds();
		if (MeasureTime - previousMeasureTime != 0) {
			TPS = (totalworldtime - previousTotalWorldTime) / (MeasureTime - previousMeasureTime);
			previousMeasureTime = MeasureTime;
			previousTotalWorldTime = totalworldtime;
		}
		LaunchMods.debug(totalworldtime);
		LaunchMods.debug(MeasureTime);
		LaunchMods.debug(TPS);
	}

	@Override
	public LitEventHandler.EventListener getListener()
	{
		return this;
	}

	public double getTPS()
	{
		return TPS;
	}
}
