package io.github.codetoil.litlaunch.version.mc1_12;

import org.apache.logging.log4j.Logger;

import io.github.codetoil.litlaunch.launchcommon.ILogger;

public class Logger1_12 implements ILogger {

	private Logger1_12()
	{
	}

	private Logger LOGGER;
	private final static ILogger Instance = new Logger1_12();

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

	@Override
	public void trace(Object obj) {
		this.LOGGER.trace(obj);
	}

	public static ILogger getInstance() {
		return Instance;
	}
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
