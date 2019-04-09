package io.github.littoil.tpsmod;

import io.github.littoil.litlaunch.launchcommon.LaunchCommon;
import io.github.littoil.litlaunch.launchcommon.LaunchMods;
import io.github.littoil.litlaunch.launchcommon.LitEventHandler;
import io.github.littoil.litlaunch.launchcommon.events.LitEvent;

public class MeasureTPSdrop implements LitEventHandler.EventListener {
	private long totalWorldTimePrev;
	private double previousMeasureTime; // In seconds
	private long totalWorldTime;
	private double timeNow;
	public final static double maxTimeWait = 1.0;
	public static final MeasureTPSdrop INSTNACE = new MeasureTPSdrop();
	public final CalculateTPS calculateTPS = new CalculateTPS();

	private MeasureTPSdrop()
	{
	}

	public void seeIfTWTHasDropped(int dimension)
	{
		totalWorldTime = LaunchCommon.getGetFields().getTotalWorldTime(dimension);
		timeNow = LaunchCommon.getTimeInSeconds();
		if (timeNow - previousMeasureTime > maxTimeWait || totalWorldTime < totalWorldTimePrev)
		{
			LitEventHandler.COMMON.post(new LitEvent(this, "updateTPS", totalWorldTime, timeNow));
			previousMeasureTime = timeNow;
		}
		totalWorldTimePrev = totalWorldTime;
	}

	@Override
	public void RecievedEvent(LitEvent event) {
		LaunchMods.getINSTANCE().getLOGGER().info(event.getType());
		if (event.getType().equals("checkIFTWTDrop"))
		{
			seeIfTWTHasDropped((Integer) event.getData()[0]);
		}
	}

	@Override
	public LitEventHandler.EventListener getMainInstance() {
		return INSTNACE;
	}
}
