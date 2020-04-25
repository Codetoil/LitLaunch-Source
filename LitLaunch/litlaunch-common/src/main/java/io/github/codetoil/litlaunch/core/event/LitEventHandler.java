/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.core.event;

import io.github.codetoil.litlaunch.api.FrontEnd;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class LitEventHandler {
    public final String NAME;
    public final static LitEventHandler COMMON = new LitEventHandler("Common");
    public final static LitEventHandler CLIENT = new LitEventHandler("Client");
    public final static LitEventHandler SERVER = new LitEventHandler("Server");

    public final Queue<EventListener> eventListenersToAdd = new ArrayDeque<>();
    public final Queue<EventListener> eventListenersToRemove = new ArrayDeque<>();
    private List<EventListener> _listeners = new ArrayList<>();

    @Override
    public String toString() {
        return NAME + "{"+eventListenersToAdd+","+eventListenersToRemove+","+_listeners+"}";
    }

    public LitEventHandler(String name) {
        this.NAME = name;
        updateListenersFromQueue();
    }

    private synchronized void updateListenersFromQueue() {
        while (!eventListenersToAdd.isEmpty()) {
            EventListener listener = eventListenersToAdd.poll();
            FrontEnd.info("Adding: " + listener.toString());
            _listeners.add(listener);
        }
        while (!eventListenersToRemove.isEmpty()) {
            EventListener listener = eventListenersToRemove.poll();
            FrontEnd.info("Removing: " + listener.toString());
            _listeners.remove(listener);
        }
    }

    public synchronized void addListener(EventListener e) {
        eventListenersToAdd.offer(e);
        //_listeners.add(e);
    }

    public void removeListener(EventListener e) {
        eventListenersToRemove.offer(e);
    }

    public synchronized void post(LitEvent pEvent) {
        post(pEvent, false);
    }

    public synchronized void post(LitEvent event, boolean pIsSPAMMY) {
        updateListenersFromQueue();
        logEVENT(event, pIsSPAMMY);
        for (EventListener listener : this._listeners) {
            FrontEnd.verbose("Sending event to: " + listener);
            listener.ReceivedEvent(event, this);
        }
    }

    private void logEVENT(LitEvent event, boolean isSpammy) {
        if (!isSpammy) {
            FrontEnd.trace("Sent Event: " + event.toString());
        } else {
            FrontEnd.verbose("Sent Event (Verbose): " + event.toString());
        }
    }

    public interface EventListener {
        void ReceivedEvent(LitEvent event, LitEventHandler from);

        EventListener getListener();
    }
}
