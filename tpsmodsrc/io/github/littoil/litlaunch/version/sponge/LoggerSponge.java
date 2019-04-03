package io.github.littoil.litlaunch.version.sponge;

import com.google.inject.Inject;
import io.github.littoil.litlaunch.launchcommon.ILogger;
import org.slf4j.Logger;

public class LoggerSponge implements ILogger {
	private Logger logger;
	private final static ILogger Instance = new LoggerSponge();

	private LoggerSponge()
	{
	}

	@Override
	public void info(Object obj) {
		logger.info(obj.toString());
	}

	@Override
	public void debug(Object obj) {
		logger.debug(obj.toString());
	}

	@Override
	public void error(Object obj) {
		logger.error(obj.toString());
	}

	@Override
	public void fatal(Object obj) {
		logger.error("[FATAL] " + obj.toString());
	}

	@Override
	public void warn(Object obj) {
		logger.warn(obj.toString());
	}


	public static ILogger getInstance() {
		return Instance;
	}
	public Object getInternalLogger() {
		return logger;
	}

	public void setInternalLogger(Object logger) {
		if (this.logger == null)
		{
			this.logger = (Logger) logger;
		}
	}
}
