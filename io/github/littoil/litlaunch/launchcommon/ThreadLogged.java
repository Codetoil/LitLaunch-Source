package io.github.littoil.litlaunch.launchcommon;

public abstract class ThreadLogged extends Thread {
	
	ILogger LOGGER;
	
	private final String name;
	public ThreadLogged(String name, ILogger LOGGER)
	{
		super(name + " thread");
		this.name = name;
		this.LOGGER = LOGGER;
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
    	try {
    		this.stop();
    	}
    	catch (ThreadDeath e)
    	{
    		
    	}
    	
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
	
	/**
	 * Creates the Exit Thread Message Base If an Exception is called. Do not call this directely I guess.
	 * @param thread
	 * */
	public void exitThreadDueToAbstact(AbstractMethodError e)
    {
    	LOGGER.error("Exiting the " + this.getName() + " Thread due to AbstractMethodError " + e.toString());
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
		logThread();
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
		catch (AbstractMethodError e)
		{
			exitThreadDueToAbstact(e);
		}
	}
	
	/**
	 * Please don't use <code>run()</code> as it has code in it relating to the <code>exitThread()</code> function and it's calling.<br>
	 * Please Use this instead!
	 */
	public abstract void runOn();
}
