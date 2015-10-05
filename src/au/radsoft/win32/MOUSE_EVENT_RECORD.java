// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.win32;

import com.sun.jna.Structure;

// typedef struct _MOUSE_EVENT_RECORD {
//   COORD dwMousePosition;
//   DWORD dwButtonState;
//   DWORD dwControlKeyState;
//   DWORD dwEventFlags;
// } MOUSE_EVENT_RECORD;
public class MOUSE_EVENT_RECORD extends Structure {
    public static final short MOUSE_MOVED = 0x0001;
    public static final short DOUBLE_CLICK = 0x0002;
    public static final short MOUSE_WHEELED = 0x0004;
    public static final short MOUSE_HWHEELED = 0x0008;
    
    public static final short FROM_LEFT_1ST_BUTTON_PRESSED = 0x0001;
    public static final short RIGHTMOST_BUTTON_PRESSED = 0x0002;
    public static final short FROM_LEFT_2ND_BUTTON_PRESSED = 0x0004;
    public static final short FROM_LEFT_3RD_BUTTON_PRESSED = 0x0008;
    public static final short FROM_LEFT_4TH_BUTTON_PRESSED = 0x0010;
    
    public COORD dwMousePosition;
    public int dwButtonState;
    public int dwControlKeyState;
    public int dwEventFlags;
    
    private static String[] fieldOrder = { "dwMousePosition", "dwButtonState", "dwControlKeyState", "dwEventFlags" };

    @Override
    protected java.util.List<String> getFieldOrder() {
        return java.util.Arrays.asList(fieldOrder);
    }
}
