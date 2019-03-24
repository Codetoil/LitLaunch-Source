package io.github.littoil.litlaunch.version.mc1_8;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.littoil.litlaunch.launchcommon.ILogger;

public class Logger1_8 implements ILogger {

	private Logger1_8()
	{
	}

	private Logger LOGGER;
	private final static ILogger Instance = new Logger1_8();

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
