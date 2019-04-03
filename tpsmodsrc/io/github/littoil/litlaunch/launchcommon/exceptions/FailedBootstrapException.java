package io.github.littoil.litlaunch.launchcommon.exceptions;

public class FailedBootstrapException extends Exception {
	public FailedBootstrapException()
	{
		super();
	}

	public FailedBootstrapException(String messsge)
	{
		super(messsge);
	}

	public FailedBootstrapException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
