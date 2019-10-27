/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.core;

import io.github.codetoil.litlaunch.api.FrontEnd;

public abstract class ThreadLogged extends Thread
{

	/**
	 * The name of the thread
	 */
	private final String name;

	/**
	 * Initializes a Logged Thread
	 *
	 * @param name The name of the thread
	 */
	public ThreadLogged(String name)
	{
		super(name + " thread");
		this.name = name;
		this.setUncaughtExceptionHandler(new UncaughtExceptionHandler()
		{
			@Override
			public void uncaughtException(Thread t, Throwable e)
			{
				if (e instanceof ThreadInterrupedException)
				{
					printExitThreadMessage();
				}
				else
				{
					printExitThreadDueToExceptionMessage(e);
					ThreadInterrupedException lThreadInterrupedException = new ThreadInterrupedException(e.getLocalizedMessage());
					lThreadInterrupedException.addSuppressed(e);
					throw lThreadInterrupedException;
				}
			}
		});
	}

	private void printExitThreadMessage()
	{
		FrontEnd.debug("Exiting the " + this.name + " Thread");
	}

	/**
	 * Creates a exit thread message due to a throwable being thrown. Does not exit the thread.
	 *
	 * @param t the throwable that was thrown
	 */
	private void printExitThreadDueToExceptionMessage(Throwable t)
	{
		FrontEnd.error("Exiting the " + this.getName() + " Thread due to Throwable " + t.toString());
		t.printStackTrace();
	}

	/**
	 * Starts the logged thread. This starts by logging that a thread is starting and then starts the thread as normal.
	 */
	@Override
	public final synchronized void start()
	{
		logThread();
		super.start();
	}

	/**
	 * Creates the Starting Thread Message.
	 */
	public void logThread()
	{
		FrontEnd.debug("Starting the " + this.name + " Thread");
	}

	/**
	 * Runs the code and then, after that, prints the exit thread message. The code is stored in runCode().
	 */
	@Override
	public final void run()
	{
		try
		{
			runCode();
		}
		catch (ThreadInterrupedException e)
		{
			FrontEnd.debug("Interruped, Exiting Thread");
		}
		catch (Throwable t)
		{
			printExitThreadDueToExceptionMessage(t);
		}
		finally
		{
			printExitThreadMessage();
		}
	}

	public abstract void runCode() throws ThreadInterrupedException;

	/**
	 * Exits the thread
	 */
	private void exitThread() throws ThreadInterrupedException
	{
		throw new ThreadInterrupedException("Exiting thread");
	}

	public class ThreadInterrupedException extends RuntimeException
	{
		public ThreadInterrupedException()
		{
			this("Interruped Thread");
		}

		public ThreadInterrupedException(String string)
		{
			super(string);
		}
	}
}
