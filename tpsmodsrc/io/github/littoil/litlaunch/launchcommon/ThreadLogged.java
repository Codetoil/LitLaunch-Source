package io.github.littoil.litlaunch.launchcommon;

public abstract class ThreadLogged extends Thread {
	
	private final String name;
	public ThreadLogged(String name)
	{
		super(name + " thread");
		this.name = name;

		this.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				if (e instanceof ThreadDeath)
				{
					exitThread();
				} else if (e instanceof AbstractMethodError)
				{
					exitThreadDueToAbstact((AbstractMethodError) e);
				} else if (e instanceof Exception)
				{
					exitThreadDueToException((Exception) e);
				}
			}
		});
	}

	@Override
	public synchronized void start() {
		logThread();
		super.start();
	}

	/**
	 * Creates the Starting Thread Message.
	 */
	public void logThread()
    {
    	LaunchMods.getINSTANCE().getLOGGER().debug("Starting the " + this.name + " Thread");
    }
	
	/**
	 * Creates the Exit Thread Message. Would not not call this directely I guess.
	 * */
	@SuppressWarnings("deprecation")
	public void exitThread()
    {
		LaunchMods.getINSTANCE().getLOGGER().debug("Exiting the " + this.name + " Thread");
    	try {
    		this.stop();
    	}
    	catch (ThreadDeath e)
    	{
			LaunchMods.getINSTANCE().getLOGGER().debug("Thread" + this.name + "is ending.");
    	}
    	
    }
	
	/**
	 * Creates the Exit Thread Message Base If an Exception is called. Do not call this directely I guess.
	 * */
	public void exitThreadDueToException(Exception e)
    {
		LaunchMods.getINSTANCE().getLOGGER().error("Exiting the " + this.getName() + " Thread due to Exception " + e.toString());
    	e.printStackTrace();
    	this.exitThread();
    }
	
	/**
	 * Creates the Exit Thread Message Base If an Exception is called. Do not call this directely I guess.
	 * */
	public void exitThreadDueToAbstact(AbstractMethodError e)
    {
		LaunchMods.getINSTANCE().getLOGGER().error("Exiting the " + this.getName() + " Thread due to AbstractMethodError " + e.toString());
    	e.printStackTrace();
    	this.exitThread();
    }

    @Override
    public abstract void run();
}
