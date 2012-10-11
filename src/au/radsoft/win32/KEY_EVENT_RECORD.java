// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.win32;

import com.sun.jna.Structure;

// typedef struct _KEY_EVENT_RECORD {
//   BOOL  bKeyDown;
//   WORD  wRepeatCount;
//   WORD  wVirtualKeyCode;
//   WORD  wVirtualScanCode;
//   union {
//     WCHAR UnicodeChar;
//     CHAR  AsciiChar;
//   } uChar;
//   DWORD dwControlKeyState;
// } KEY_EVENT_RECORD;
public class KEY_EVENT_RECORD extends Structure {
    public boolean bKeyDown;
    public short wRepeatCount;
    public short wVirtualKeyCode;
    public short wVirtualScanCode;
    public UnionChar uChar;
    public int dwControlKeyState;
}
