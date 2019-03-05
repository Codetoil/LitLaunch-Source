package io.github.littoil.litlaunch.launchcommon;

public interface ILogger {
	public void debug(Object obj);
	public void info(Object obj);
	public void warn(Object obj);
	public void error(Object obj);
	public void fatal(Object obj);
}
