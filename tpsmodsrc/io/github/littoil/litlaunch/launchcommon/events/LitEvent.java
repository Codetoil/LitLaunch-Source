package io.github.littoil.litlaunch.launchcommon.events;

import io.github.littoil.litlaunch.launchcommon.LaunchMods;

import java.util.Arrays;
import java.util.EventObject;

public class LitEvent extends EventObject {
    private String type;
    private Object[] data;

    public LitEvent(Object source)
    {
        super(source);
    }

    public LitEvent(Object source, String type, Object... data) {
        super(source);
        this.type = type;
        this.data = data;
    }

    public void setData(Object[] data) {
        if (this.data == null)
        {
            this.data = data;
        }
        else
        {
            LaunchMods.getINSTANCE().getLOGGER().warn("Cannot set data of LitEvent again");
        }
    }

    public void setType(String type) {
        if (this.type == null)
        {
            this.type = type;
        }
        else
        {
            LaunchMods.getINSTANCE().getLOGGER().warn("Cannot set type of LitEvent again");
        }
    }

    public String getType()
    {
        return this.type;
    }

    public Object[] getData() {
        return data;
    }

	@Override
	public String toString() {
		return "LitEvent: \"" + type + "\" with data " + Arrays.toString(data) + " from source \"" + source + "\"";
	}
}
