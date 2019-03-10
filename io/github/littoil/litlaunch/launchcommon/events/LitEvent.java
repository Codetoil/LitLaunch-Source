package io.github.littoil.litlaunch.launchcommon.events;

import java.util.EventObject;

public class LitEvent extends EventObject {
    private String type;

    public LitEvent(Object source, String type) {
        super(source);
        this.type = type;
    }

    public String getType()
    {
        return this.type;
    }
}
