package io.github.codetoil.litlaunch._native.mc1_14;

import io.github.codetoil.litlaunch.core.ILogger;
import io.github.codetoil.litlaunch.core.LaunchCommon;
import org.apache.logging.log4j.Logger;

public class Logger1_14 implements ILogger
{

	private final static ILogger Instance = new io.github.codetoil.litlaunch._native.mc1_14.Logger1_14();
	private Logger LOGGER;

	private Logger1_14()
	{
	}

	public static ILogger getInstance()
	{
		return Instance;
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
	public void debug(Object obj)
	{
		this.LOGGER.debug(obj);
	}

	@Override
	public void trace(Object obj)
	{
		this.LOGGER.debug("Trace> " + obj);
	}

	@Override
	public void verbose(Object obj)
	{
		if (LaunchCommon.isVerbose())
		{
			this.LOGGER.debug("Verbose> " + obj);
		}
	}

	@Override
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
