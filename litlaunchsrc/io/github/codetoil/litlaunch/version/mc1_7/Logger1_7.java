/*
 * Copyright Codetoil (c) 2019
 */

package io.github.codetoil.litlaunch.version.mc1_7;

import io.github.codetoil.litlaunch.backend.ILogger;
import org.apache.logging.log4j.Logger;

public class Logger1_7 implements ILogger
{

	private final static ILogger Instance = new Logger1_7();
	private Logger LOGGER;

	private Logger1_7()
	{
	}

	public static ILogger getInstance()
	{
		return Instance;
	}

	@Override
	public void debug(Object obj)
	{
		this.LOGGER.debug(obj);
	}

	@Override
	public void info(Object obj)
	{
		this.LOGGER.info(obj);
	}

	@Override
	public void warn(Object obj)
	{
		this.LOGGER.warn(obj);
	}

	@Override
	public void error(Object obj)
	{
		this.LOGGER.error(obj);
	}

	@Override
	public void fatal(Object obj)
	{
		this.LOGGER.fatal(obj);
	}

	@Override
	public void trace(Object obj)
	{
		this.LOGGER.trace(obj);
	}

	public Object getInternalLogger()
	{
		return LOGGER;
	}

	public void setInternalLogger(Object logger)
	{
		if (this.LOGGER == null) {
			this.LOGGER = (Logger) logger;
		}
	}

}
