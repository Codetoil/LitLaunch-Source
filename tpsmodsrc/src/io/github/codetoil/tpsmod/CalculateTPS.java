/*
 * Copyright Codetoil (c) 2019
 */

package io.github.codetoil.tpsmod;

import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.core.LaunchCommon;
import io.github.codetoil.litlaunch.core.event.LitEvent;
import io.github.codetoil.litlaunch.core.event.LitEventHandler;

public class CalculateTPS implements LitEventHandler.EventListener
{
	private final int dimension;
	private long previousTotalWorldTime;
	private double previousMeasureTime;
	private double TPS;

	public CalculateTPS(int dimension)
	{
		this.dimension = dimension;
	}

	public void ReceivedEvent(LitEvent event)
	{
		if (event.getType() == TPSMod.updateTPS)
		{
			if (event.getData()[2].equals(this.dimension))
			{
				updateTPS();
			}
		}
	}

	private void updateTPS()
	{
		FrontEnd.debug("Update TPS for dimension " + dimension);
		long totalworldtime = FrontEnd.GET_FIELDS().getTotalWorldTime(this.dimension);
		double MeasureTime = LaunchCommon.getTimeInSeconds();
		if (MeasureTime - previousMeasureTime != 0)
		{
			TPS = (totalworldtime - previousTotalWorldTime) / (MeasureTime - previousMeasureTime);
			previousMeasureTime = MeasureTime;
			previousTotalWorldTime = totalworldtime;
		}
		FrontEnd.debug(totalworldtime);
		FrontEnd.debug(MeasureTime);
		FrontEnd.debug(TPS);
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
