package io.github.littoil.litlaunch.version.mc1_13;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.littoil.litlaunch.launchcommon.ILogger;

public class Logger1_13 implements ILogger {
	
	private final Logger LOGGER = LogManager.getLogger();

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

}
