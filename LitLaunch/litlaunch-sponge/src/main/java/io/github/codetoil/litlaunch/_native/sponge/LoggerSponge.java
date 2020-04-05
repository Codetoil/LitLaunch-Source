package io.github.codetoil.litlaunch._native.sponge;

import io.github.codetoil.litlaunch.core.ILogger;
import io.github.codetoil.litlaunch.core.LaunchCommon;
import org.slf4j.Logger;

public class LoggerSponge implements ILogger {

    private final static ILogger Instance = new io.github.codetoil.litlaunch._native.sponge.LoggerSponge();
    private Logger LOGGER;

    private LoggerSponge() {
    }

    public static ILogger getInstance() {
        return Instance;
    }

    @Override
    public void debug(Object obj) {
        this.LOGGER.debug(obj.toString());
    }

    @Override
    public void info(Object obj) {
        this.LOGGER.info(obj.toString());
    }

    @Override
    public void warn(Object obj) {
        this.LOGGER.warn(obj.toString());
    }

    @Override
    public void error(Object obj) {
        this.LOGGER.error(obj.toString());
    }

    @Override
    public void fatal(Object obj) {
        this.LOGGER.error("Fatal> " + obj);
    }

    @Override
    public void trace(Object obj) {
        this.LOGGER.debug("Trace> " + obj);
    }

    @Override
    public void verbose(Object obj) {
        if (LaunchCommon.isVerbose()) {
            this.LOGGER.debug("Verbose> " + obj);
        }
    }

    @Override
    public Object getInternalLogger() {
        return LOGGER;
    }

    public void setInternalLogger(Object logger) {
        if (this.LOGGER == null) {
            this.LOGGER = (Logger) logger;
        }
    }
}
