package io.github.littoil.litlaunch.launchcommon;

import io.github.littoil.litlaunch.launchcommon.events.LitEvent;

import java.util.*;

public class LitEventHandler {
    private List<EventListener> _listeners = new ArrayList<EventListener>();
    public final static LitEventHandler COMMON = new LitEventHandler();
    public final static LitEventHandler CLIENT = new LitEventHandler();
    public final static LitEventHandler SERVER = new LitEventHandler();
    public final static Queue<EventListener> eventListenersToAdd = new ArrayDeque<EventListener>();
    public final static Queue<EventListener> eventListenersToRemove = new ArrayDeque<EventListener>();

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
            _listeners.add(eventListenersToAdd.poll());
        }
        while (!eventListenersToRemove.isEmpty())
        {
            _listeners.remove(eventListenersToRemove.poll());
        }
    }

    public synchronized void post(LitEvent event)
    {
        updateListenersFromQueue();
        LaunchMods.getINSTANCE().getLOGGER().debug("Sent event \"" + event.getType() + "\"");
        for(EventListener listener :this._listeners )
        {
            listener.RecievedEvent(event);
        }
    }
}
