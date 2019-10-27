package io.github.codetoil.tpsmod;

import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.core.event.LitEvent;
import io.github.codetoil.litlaunch.core.event.LitEventHandler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.Instant;

public class EventPrinter implements LitEventHandler.EventListener
{
	private BufferedOutputStream outputStream;

	public EventPrinter() throws IOException
	{
		new File("events.log").createNewFile();
		FileOutputStream fileOutputStream = new FileOutputStream("events.log");
		outputStream = new BufferedOutputStream(fileOutputStream);
		LitEventHandler.COMMON.addListener(this);
		TPSMod.EVENTS.addListener(this);
	}

	@Override
	public void ReceivedEvent(LitEvent event)
	{
		ByteBuffer buffer = ByteBuffer.allocate(1028);
		String lineToPut = Instant.now() + ": " + event + "\n";
		FrontEnd.info(lineToPut);
		char[] line = lineToPut.toCharArray();
		for (char c : line)
		{
			buffer.put((byte) c);
		}
		try
		{
			outputStream.write(buffer.array(), 0, buffer.position());
			outputStream.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public LitEventHandler.EventListener getListener()
	{
		return this;
	}
}
