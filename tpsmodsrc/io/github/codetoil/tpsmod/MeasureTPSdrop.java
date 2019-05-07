package io.github.codetoil.tpsmod;

import io.github.codetoil.litlaunch.launchcommon.LaunchCommon;
import io.github.codetoil.litlaunch.launchcommon.LaunchMods;
import io.github.codetoil.litlaunch.launchcommon.LitEventHandler;
import io.github.codetoil.litlaunch.launchcommon.events.LitEvent;

public class MeasureTPSdrop implements LitEventHandler.EventListener {
	private long totalWorldTimePrev;
	private double previousMeasureTime = 0.0; // In seconds
	private long totalWorldTime;
	private double timeNow;
	private final int dimension;
	public final static double maxTimeWait = 1.0;
	public final CalculateTPS calculateTPS;

	public MeasureTPSdrop(int dimension)
	{
		this.dimension = dimension;
		LitEventHandler.COMMON.addListener(this);
		calculateTPS = new CalculateTPS(dimension);
	}

	public void seeIfTWTHasDropped()
	{
		LaunchMods.getINSTANCE().getLOGGER().trace("checking if total world time has dropped");
		totalWorldTime = LaunchCommon.getGetFields().getTotalWorldTime(dimension);
		timeNow = LaunchCommon.getTimeInSeconds();
		if (timeNow - previousMeasureTime > maxTimeWait || totalWorldTime < totalWorldTimePrev)
		{
			LaunchMods.getINSTANCE().getLOGGER().trace(timeNow);
			LaunchMods.getINSTANCE().getLOGGER().trace(timeNow-previousMeasureTime);
			LaunchMods.getINSTANCE().getLOGGER().trace(totalWorldTime);
			LaunchMods.getINSTANCE().getLOGGER().trace(totalWorldTime-totalWorldTimePrev);
			LitEventHandler.COMMON.post(new LitEvent(this, "updateTPS", totalWorldTime, timeNow, dimension));
			previousMeasureTime = timeNow;
		}
		totalWorldTimePrev = totalWorldTime;
	}

	@Override
	public void RecievedEvent(LitEvent event) {
		LaunchMods.getINSTANCE().getLOGGER().trace(event.getType());
		if (event.getType().equals("onTick")) {
			seeIfTWTHasDropped();
		}
	}

	// There is no main instance for this so I just return "this" so that it gets the instance
	@Override
	public LitEventHandler.EventListener getMainInstance() {
		return this;
	}
}
