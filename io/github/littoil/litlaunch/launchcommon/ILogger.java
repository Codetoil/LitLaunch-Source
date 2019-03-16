package io.github.littoil.litlaunch.launchcommon;

public interface ILogger {
	void debug(Object obj);
	void info(Object obj);
	void warn(Object obj);
	void error(Object obj);
	void fatal(Object obj);

	Object getInternalLogger();
	void setInternalLogger(Object logger);
}
