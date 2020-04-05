/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.core.event;

import io.github.codetoil.litlaunch.api.FrontEnd;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

public class LitEvent extends EventObject {
    private TYPE type;
    private Map<String, Object> data;

    //Used for invoking with strings
    public LitEvent(Object source) {
        super(source);
    }

    public LitEvent(Object source, TYPE type, Map<String, Object> data) {
        super(source);
        this.type = type;
        this.data = data;
    }

    @Deprecated
    public LitEvent(Object source, TYPE type, Object... data) {
        super(source);
        this.type = type;
        this.data = new HashMap<>();
        for (int i = 0; i < data.length; i++) {
            this.data.put("element " + i, data);
        }
    }

    public TYPE getType() {
        return this.type;
    }

    public void setType(TYPE type) {
        if (this.type == null) {
            this.type = type;
        } else {
            FrontEnd.warn("Cannot set type of LitEvent again");
        }
    }

    public Map<String, Object> getDataMap() {
        return data;
    }

    @Deprecated
    public Object[] getData() {
        return data.values().toArray();
    }

    public void setData(Map<String, Object> data) {
        if (this.data == null) {
            this.data = data;
        } else {
            FrontEnd.warn("Cannot set data of LitEvent again");
        }
    }

    @Override
    public String toString() {
        return "LitEvent{type=\"" + type + ",data=\"" + data + ",source=\"" + source + "\"}";
    }

    public static class TYPE {
        private static Map<String, TYPE> types = new HashMap<>();
        public static final TYPE CONSTRUCTION = getEnumFromString("Contruction");
        public static final TYPE PREINIT = getEnumFromString("PreInit");
        public static final TYPE INIT = getEnumFromString("Init");
        public static final TYPE POSTINIT = getEnumFromString("PostInit");
        public static final TYPE TESTSPAM = getEnumFromString("TestSpam");
        public static final TYPE SERVERLOAD = getEnumFromString("ServerLoad");
        public static final TYPE SERVERCONNECT = getEnumFromString("ServerConnect");
        public static final TYPE CLIENTTICK = getEnumFromString("clientTick");
        public static final TYPE SERVERTICK = getEnumFromString("serverTick");
        public static final TYPE ONPACKET = getEnumFromString("onPacket");
        private final String name;

        private TYPE(String name) {
            this.name = name;
        }

        public static TYPE getEnumFromString(String name) {
            if (types == null) {
                types = new HashMap<>();
            }
            TYPE type = types.get(name);
            if (type == null) {
                type = new TYPE(name);
                types.put(name, type);
            }
            return type;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "enum TYPE{name=" + name + "}";
        }
    }
}
