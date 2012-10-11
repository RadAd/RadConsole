// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.win32;

import com.sun.jna.Structure;

// typedef struct _CONSOLE_CURSOR_INFO {
//   DWORD dwSize;
//   BOOL  bVisible;
// } CONSOLE_CURSOR_INFO, *PCONSOLE_CURSOR_INFO;
public class CONSOLE_CURSOR_INFO extends Structure {
    public int dwSize;
    public boolean bVisible;

    public static class ByReference extends CONSOLE_CURSOR_INFO implements
            Structure.ByReference {
    }
}
