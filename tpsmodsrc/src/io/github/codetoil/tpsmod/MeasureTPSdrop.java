/*
 * Copyright Codetoil (c) 2019
 */

package io.github.codetoil.tpsmod;

import io.github.codetoil.litlaunch.backend.LaunchCommon;
import io.github.codetoil.litlaunch.event.LitEvent;
import io.github.codetoil.litlaunch.event.LitEventHandler;

public class MeasureTPSdrop implements LitEventHandler.EventListener
{
	public final static double maxTimeWait = 1.0;
	public final int dimension;
	public final CalculateTPS calculateTPS;
	private long totalWorldTimePrev;
	private double previousMeasureTime = 0.0; // In seconds

	public MeasureTPSdrop(int dimension)
	{
		this.dimension = dimension;
		calculateTPS = new CalculateTPS(dimension);
		LitEventHandler.COMMON.addListener(this);
		LitEventHandler.COMMON.addListener(calculateTPS);
	}

	@Override
	public void ReceivedEvent(LitEvent event)
	{
		//LaunchMods.trace(event.getType());
		if (event.getType().equals("onTick")) {
			seeIfTWTHasDropped();
		}
	}

	public void seeIfTWTHasDropped()
	{
		//LaunchMods.trace("checking if total world time has dropped");
		long totalWorldTime = LaunchCommon.getGetFields().getTotalWorldTime(dimension);
		double timeNow = LaunchCommon.getTimeInSeconds();
		if (timeNow - previousMeasureTime > maxTimeWait || totalWorldTime < totalWorldTimePrev) {
			//LaunchMods.trace(timeNow);
			//LaunchMods.trace(timeNow-previousMeasureTime);
			//LaunchMods.trace(totalWorldTime);
			//LaunchMods.trace(totalWorldTime-totalWorldTimePrev);
			LitEventHandler.COMMON.post(new LitEvent(this, "updateTPS", totalWorldTime, timeNow, dimension), true);
			previousMeasureTime = timeNow;
		}
		totalWorldTimePrev = totalWorldTime;
	}

	// This class is the listener!
	@Override
	public LitEventHandler.EventListener getListener()
	{
		return this;
	}
}
