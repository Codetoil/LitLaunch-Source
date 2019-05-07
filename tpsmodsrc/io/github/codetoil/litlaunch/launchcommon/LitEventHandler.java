package io.github.codetoil.litlaunch.launchcommon;

import io.github.codetoil.litlaunch.launchcommon.events.LitEvent;

import java.util.*;

public class LitEventHandler {
    private List<EventListener> _listeners = new ArrayList<EventListener>();
    public final static LitEventHandler COMMON = new LitEventHandler();
    public final static LitEventHandler CLIENT = new LitEventHandler();
    public final static LitEventHandler SERVER = new LitEventHandler();
    public final static Queue<EventListener> eventListenersToAdd = new ArrayDeque<>();
    public final static Queue<EventListener> eventListenersToRemove = new ArrayDeque<>();

    public LitEventHandler()
    {

    }

    public interface EventListener {
        void RecievedEvent(LitEvent event);
        EventListener getMainInstance();
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

    private synchronized void updateListenersFromQueue()
    {
        while (!eventListenersToAdd.isEmpty())
        {
            EventListener listener = eventListenersToAdd.poll();
            LaunchMods.getINSTANCE().getLOGGER().info("Adding: " + listener.toString());
            _listeners.add(listener);
        }
        while (!eventListenersToRemove.isEmpty())
        {
            EventListener listener = eventListenersToRemove.poll();
	        LaunchMods.getINSTANCE().getLOGGER().info("Removing: " + listener.toString());
            _listeners.remove(listener);
        }
    }

    public synchronized void post(LitEvent event)
    {
        updateListenersFromQueue();
        LaunchMods.getINSTANCE().getLOGGER().trace("Sent event \"" + event.getType() + "\"");
        for(EventListener listener :this._listeners )
        {
	        LaunchMods.getINSTANCE().getLOGGER().trace("Send event to: " + listener.toString());
            listener.RecievedEvent(event);
        }
    }
}
