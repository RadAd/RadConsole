// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.win32;

import com.sun.jna.Structure;

// typedef struct _CHAR_INFO {
//   union {
//     WCHAR UnicodeChar;
//     CHAR  AsciiChar;
//   } Char;
//   WORD  Attributes;
// } CHAR_INFO, *PCHAR_INFO;
public class CHAR_INFO extends Structure {
    public CHAR_INFO() {
    }

    public CHAR_INFO(char c, short attr) {
        uChar = new UnionChar(c);
        Attributes = attr;
    }

    public CHAR_INFO(byte c, short attr) {
        uChar = new UnionChar(c);
        Attributes = attr;
    }
    
    public UnionChar uChar;
    public short Attributes;

    public static CHAR_INFO[] createArray(int size) {
        return (CHAR_INFO[]) new CHAR_INFO().toArray(size);
    }
}
