// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.win32;

import com.sun.jna.Structure;
import com.sun.jna.Union;

// typedef struct _INPUT_RECORD {
//   WORD  EventType;
//   union {
//     KEY_EVENT_RECORD          KeyEvent;
//     MOUSE_EVENT_RECORD        MouseEvent;
//     WINDOW_BUFFER_SIZE_RECORD WindowBufferSizeEvent;
//     MENU_EVENT_RECORD         MenuEvent;
//     FOCUS_EVENT_RECORD        FocusEvent;
//   } Event;
// } INPUT_RECORD;
public class INPUT_RECORD extends Structure {
    public static final short FOCUS_EVENT = 0x0010;
    public static final short KEY_EVENT = 0x0001;
    public static final short MENU_EVENT = 0x0008;
    public static final short MOUSE_EVENT = 0x0002;
    public static final short WINDOW_BUFFER_SIZE_EVENT = 0x0004;

    public short EventType;
    public EventUnion Event;

    public static class EventUnion extends Union {
        public KEY_EVENT_RECORD KeyEvent;
        public MOUSE_EVENT_RECORD MouseEvent;
        public WINDOW_BUFFER_SIZE_RECORD WindowBufferSizeEvent;
        // MENU_EVENT_RECORD MenuEvent;
        // FOCUS_EVENT_RECORD FocusEvent;
    }

    @Override
    public void read() {
        readField("EventType");
        switch (EventType) {
        case KEY_EVENT:
            Event.setType(KEY_EVENT_RECORD.class);
            break;
        case MOUSE_EVENT:
            Event.setType(MOUSE_EVENT_RECORD.class);
            break;
        case WINDOW_BUFFER_SIZE_EVENT:
            Event.setType(WINDOW_BUFFER_SIZE_RECORD.class);
            break;
        }
        super.read();
    }
    
    private static String[] fieldOrder = { "EventType", "Event" };
    
    @Override
    protected java.util.List<String> getFieldOrder() {
        return java.util.Arrays.asList(fieldOrder);
    }
}
