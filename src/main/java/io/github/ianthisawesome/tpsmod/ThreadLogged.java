/*
 * Copyright Codetoil (c) 2019
 */

package io.github.ianthisawesome.tpsmod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ThreadLogged extends Thread {
	
	protected static final Logger LOGGER = LogManager.getLogger();
	private final String name;
	public ThreadLogged(String name)
	{
		super(name + " thread");
		LOGGER.info("Thread");
		this.name = name;
		logThread();
	}
	
	/**
	 * Creates the Starting Thread Message.
	 * @param thread
	 */
	public void logThread()
    {
    	LOGGER.debug("Starting the " + this.name + " Thread");
    }
	
	/**
	 * Creates the Exit Thread Message. Would not not call this directely I guess.
	 * @param thread
	 * */
	@SuppressWarnings("deprecation")
	public void exitThread()
    {
    	LOGGER.debug("Exiting the " + this.name + " Thread");
    	this.stop();
    }
	
	/**
	 * Creates the Exit Thread Message Base If an Exception is called. Do not call this directely I guess.
	 * @param thread
	 * */
	public void exitThreadDueToException(Exception e)
    {
    	LOGGER.error("Exiting the " + this.getName() + " Thread due to Exception " + e.toString());
    	e.printStackTrace();
    	this.exitThread();
    }
	
	@Override
	@Deprecated
	/**
	 * DON'T USE THIS unless you want the Exit Thread Message to vanish. <br>
	 * Please use <code>runOn()</code> instead!
	 */
	public void run()
	{
		try {
			runOn();
		}
		catch (ThreadDeath e)
		{
			exitThread();
		}
		catch (Exception e)
		{
			exitThreadDueToException(e);
		}
	}
	
	/**
	 * Please don't use <code>run()</code> as it has code in it relating to the <code>exitThread()</code> function and it's calling.<br>
	 * Please Use this instead!
	 */
	public abstract void runOn();
}
