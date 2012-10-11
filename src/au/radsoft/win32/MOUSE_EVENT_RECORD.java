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
    public COORD dwMousePosition;
    public int dwButtonState;
    public int dwControlKeyState;
    public int dwEventFlags;
}
