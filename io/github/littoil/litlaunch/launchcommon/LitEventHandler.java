package io.github.littoil.litlaunch.launchcommon;

import io.github.littoil.litlaunch.launchcommon.events.LitEvent;

import java.util.ArrayList;
import java.util.List;

public class LitEventHandler {
    private List<EventListener> _listeners = new ArrayList<EventListener>();
    public final static LitEventHandler COMMON = new LitEventHandler();
    public final static LitEventHandler CLIENT = new LitEventHandler();
    public final static LitEventHandler SERVER = new LitEventHandler();

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
        LaunchMods.getINSTANCE().getLOGGER().info("Sent event \"" + event.getType() + "\"");
        this._listeners.forEach((listener) -> {
            listener.RecievedEvent(event);
        });
    }
}
