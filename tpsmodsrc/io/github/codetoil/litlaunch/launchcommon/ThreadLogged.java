package io.github.codetoil.litlaunch.launchcommon;

public abstract class ThreadLogged extends Thread {

	/**
	 * The name of the thread
	 */
	private final String name;

	/**
	 * Initializes a Logged Thread
	 * @param name The name of the thread
	 */
	public ThreadLogged(String name)
	{
		super(name + " thread");
		this.name = name;
		this.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				if (!(e instanceof ThreadDeath)) {
					printExitThreadDueToExceptionMessage(e);
					exitThread();
				}
			}
		});
	}

	/**
	 * Starts the logged thread. This starts by logging that a thread is starting and then starts the thread as normal.
	 */
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
	 * Exits thread. Will most likely cause the {@link ThreadDeath} exception.
	 * */
	@SuppressWarnings("deprecation")
	private void exitThread()
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
	 * Creates a exit thread message due to a throwable being thrown. Does not exit the thread.
	 * @param t the throwable that was thrown
	 * */
	private void printExitThreadDueToExceptionMessage(Throwable t)
    {
		LaunchMods.getINSTANCE().getLOGGER().error("Exiting the " + this.getName() + " Thread due to Throwable " + t.toString());
    	t.printStackTrace();
    }

	/**
	 * The code that is run after the thread starts.
	 */
	@Override
    public abstract void run();
}
