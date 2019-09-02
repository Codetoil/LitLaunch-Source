/*
 * Copyright Codetoil (c) 2019
 */

package io.github.codetoil.tpsmod;

import io.github.codetoil.litlaunch.api.ChainableMap;
import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.core.LaunchCommon;
import io.github.codetoil.litlaunch.core.event.LitEvent;
import io.github.codetoil.litlaunch.core.event.LitEventHandler;

import java.util.HashMap;

public class MeasureTPSdrop implements LitEventHandler.EventListener
{
	private static double maxTimeWait = 5.0;
	public final int dimension;
	public final CalculateTPS calculateTPS;
	private long totalWorldTimePrevTick;
	private double previousMeasureTime = 0.0; // In seconds

	public MeasureTPSdrop(int dimension)
	{
		this.dimension = dimension;
		calculateTPS = new CalculateTPS(dimension);
		LitEventHandler.COMMON.addListener(this);
		TPSMod.EVENTS.addListener(calculateTPS);
	}

	public static double getMaxTimeWait()
	{
		return maxTimeWait;
	}

	static void setMaxTimeWait(double pMaxTimeWait)
	{
		if (pMaxTimeWait >= 0.0)
		{
			maxTimeWait = pMaxTimeWait;
		}
	}

	@Override
	public void ReceivedEvent(LitEvent event)
	{
		//LaunchMods.trace(event.getType());
		if (event.getType() == LitEvent.TYPE.CLIENTTICK || event.getType() == LitEvent.TYPE.SERVERTICK) {
			seeIfTWTHasDropped();
		}
	}

	public void seeIfTWTHasDropped()
	{
		//LaunchMods.trace("checking if total world time has dropped");
		long totalWorldTime = LaunchCommon.getGetFields().getTotalWorldTime(dimension);
		double timeNow = LaunchCommon.getTimeInSeconds();
		long DeltaTick = totalWorldTime - totalWorldTimePrevTick;
		if (DeltaTick != 0)
		{
			FrontEnd.verbose("Delta Tick: " + DeltaTick);
		}
		if (DeltaTick < 0 || timeNow - previousMeasureTime > maxTimeWait) {
			TPSMod.EVENTS.post(new LitEvent(this, TPSMod.updateTPS, ChainableMap.newMap().putChain("totalWorldTime", totalWorldTime).putChain("timeNow", timeNow).put("dimension", dimension))), true);
			previousMeasureTime = timeNow;
		}
		totalWorldTimePrevTick = totalWorldTime;
	}

	// This class is the listener!
	@Override
	public LitEventHandler.EventListener getListener()
	{
		return this;
	}
}
