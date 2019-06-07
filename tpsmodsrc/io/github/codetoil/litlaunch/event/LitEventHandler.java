/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.event;

import io.github.codetoil.litlaunch.FrontEnd;
import io.github.codetoil.litlaunch.api.LaunchMods;
import io.github.codetoil.litlaunch.backend.LaunchCommon;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class LitEventHandler
{
	public final static LitEventHandler COMMON = new LitEventHandler();
	public final static LitEventHandler CLIENT = new LitEventHandler();
	public final static LitEventHandler SERVER = new LitEventHandler();

	public final Queue<EventListener> eventListenersToAdd = new ArrayDeque<>();
	public final Queue<EventListener> eventListenersToRemove = new ArrayDeque<>();
	private List<EventListener> _listeners = new ArrayList<EventListener>();

	public LitEventHandler()
	{
		updateListenersFromQueue();
	}

	public synchronized void addListener(EventListener e)
	{
		eventListenersToAdd.offer(e);
		//_listeners.add(e);
	}

	public void removeListener(EventListener e)
	{
		eventListenersToRemove.offer(e);
	}

	public synchronized void post(LitEvent pEvent)
	{
		post(pEvent, false);
	}

	public synchronized void post(LitEvent event, boolean pIsSPAMMY)
	{
		updateListenersFromQueue();
		logEVENT(event, pIsSPAMMY);
		for (EventListener listener : this._listeners) {
			listener.ReceivedEvent(event);
		}
	}

	private synchronized void updateListenersFromQueue()
	{
		while (!eventListenersToAdd.isEmpty()) {
			EventListener listener = eventListenersToAdd.poll();
			LaunchMods.info("Adding: " + listener.toString());
			_listeners.add(listener);
		}
		while (!eventListenersToRemove.isEmpty()) {
			EventListener listener = eventListenersToRemove.poll();
			LaunchMods.info("Removing: " + listener.toString());
			_listeners.remove(listener);
		}
	}

	public interface EventListener
	{
		void ReceivedEvent(LitEvent event);
		EventListener getListener();
	}
	private void logEVENT(LitEvent event, boolean isSpammy)
	{
		if (!isSpammy)
		{
			FrontEnd.trace("Sent Event: " + event.toString());
		}
		else if (FrontEnd.IS_LITLAUNCH_VERBOSE())
		{
			FrontEnd.trace("Sent Event (Verbose): " + event.toString());
		}
	}
}
