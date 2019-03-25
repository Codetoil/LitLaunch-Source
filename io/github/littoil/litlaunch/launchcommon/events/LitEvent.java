package io.github.littoil.litlaunch.launchcommon.events;

import java.util.EventObject;

public class LitEvent extends EventObject {
    private String type;
    private Object[] data;

    public LitEvent(Object source, String type, Object... data) {
        super(source);
        this.type = type;
        this.data = data;
    }

    public String getType()
    {
        return this.type;
    }

    public Object[] getData() {
        return data;
    }
}
