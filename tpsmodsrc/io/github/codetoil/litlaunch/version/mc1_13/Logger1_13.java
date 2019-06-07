package io.github.codetoil.litlaunch.version.mc1_13;

import org.apache.logging.log4j.Logger;

import io.github.codetoil.litlaunch.launchcommon.ILogger;

public class Logger1_13 implements ILogger {

	private Logger1_13()
	{
	}

	private Logger LOGGER;
	private final static ILogger Instance = new Logger1_13();

	@Override
	public void info(Object obj) {
		this.LOGGER.info(obj);
	}

	@Override
	public void warn(Object obj) {
		this.LOGGER.warn(obj);
	}

	@Override
	public void error(Object obj) {
		this.LOGGER.error(obj);
	}

	@Override
	public void fatal(Object obj) {
		this.LOGGER.fatal(obj);
	}

	@Override
	public void debug(Object obj) {
		this.LOGGER.debug(obj);
	}

	public static ILogger getInstance() {
		return Instance;
	}

	@Override
	public Object getInternalLogger() {
		return LOGGER;
	}

	public void setInternalLogger(Object logger) {
		if (this.LOGGER == null)
		{
			this.LOGGER = (Logger) logger;
		}
	}
}
