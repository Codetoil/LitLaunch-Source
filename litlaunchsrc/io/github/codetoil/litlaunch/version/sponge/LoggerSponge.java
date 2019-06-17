package io.github.codetoil.litlaunch.version.sponge;

import io.github.codetoil.litlaunch.launchcommon.ILogger;
import org.slf4j.Logger;

public class LoggerSponge implements ILogger
{
	private final static ILogger Instance = new LoggerSponge();
	private Logger logger;

	private LoggerSponge()
	{
	}

	public static ILogger getInstance()
	{
		return Instance;
	}

	@Override
	public void info(Object obj)
	{
		logger.info(obj.toString());
	}

	@Override
	public void debug(Object obj)
	{
		logger.debug(obj.toString());
	}

	@Override
	public void error(Object obj)
	{
		logger.error(obj.toString());
	}

	@Override
	public void fatal(Object obj)
	{
		logger.error("[FATAL] " + obj.toString());
	}

	@Override
	public void warn(Object obj)
	{
		logger.warn(obj.toString());
	}

	public Object getInternalLogger()
	{
		return logger;
	}

	public void setInternalLogger(Object logger)
	{
		if (this.logger == null) {
			this.logger = (Logger) logger;
		}
	}
}
