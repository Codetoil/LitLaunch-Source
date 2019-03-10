package io.github.littoil.litlaunch.launchcommon;

import io.github.littoil.litlaunch.launchcommon.events.LitEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class LitEventHandler {
    private List<EventListener> _listeners = new ArrayList<EventListener>();
    public final static LitEventHandler INSTANCE = new LitEventHandler();

    public LitEventHandler()
    {

    }

    public interface EventListener {
        public void RecievedEvent(LitEvent event);
    }

    public void addListener(EventListener e)
    {
        _listeners.add(e);
    }

    public void removeListener(EventListener e)
    {
        _listeners.remove(e);
    }

    public void post(LitEvent event)
    {
        LaunchTPSMOD.INSTANCE.LOGGER.info("Sent event \"" + event.getType() + "\"");
        this._listeners.forEach((listener) -> {
            listener.RecievedEvent(event);
        });
    }
}
