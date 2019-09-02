/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.api;

public interface IGetFields
{
	long getTotalWorldTime(int dimension);

	int getDimRunning();

	int[] getDimsAvailable();

	String getVERSION();
}
