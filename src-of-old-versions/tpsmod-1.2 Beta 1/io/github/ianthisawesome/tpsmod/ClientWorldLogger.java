package io.github.ianthisawesome.tpsmod;

import net.minecraft.client.Minecraft;

public class ClientWorldLogger extends ThreadLogged {
	
	public static String worldName;
	public static String worldNameOld;
	public static int delay = 10;
	public static boolean onWorld = false;
	//public Ads ads;
	
	
	public ClientWorldLogger(String name)
	{
		super(name);
		LOGGER.debug("World Logger");
	}
	
	@Override
	public void runOn() {
		while (true)
		{
			try {
				worldName = Minecraft.getMinecraft().theWorld.getWorldInfo().getWorldName();
				//LOGGER.info(Minecraft.getMinecraft().theWorld.getWorldInfo().getWorldTotalTime());
				if (!worldName.isEmpty() && !onWorld) {
					joinWorld();
				}
				onWorld = true;
				
			} catch (NullPointerException e) {
				//LOGGER.error("Oops! Null Pointer");
				//e.printStackTrace();
				if (onWorld) {
					exitWorld();
				}
				onWorld = false;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			
			try {
				worldNameOld = worldName;
			} catch (NullPointerException e) { }
			
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		}
	}
	
	public void joinWorld() {
		LOGGER.debug("Joined World " + worldName);
		//tpsMeasurer = new TPSMeasurer("TPS Counter");
		//tpsMeasurer.start();
    	//ads = new Ads("Reminders");
    	//ads.start();
	}
	
	public void exitWorld() {
		LOGGER.debug("Left World " + worldNameOld);
		//tpsMeasurer.stop();
		//ads.stop();
	}
}
