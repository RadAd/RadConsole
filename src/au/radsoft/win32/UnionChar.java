// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.win32;

import com.sun.jna.Union;

public class UnionChar extends Union {
    public UnionChar() {
    }

    public UnionChar(char c) {
        setType(char.class);
        UnicodeChar = c;
    }

    public UnionChar(byte c) {
        setType(byte.class);
        AsciiChar = c;
    }

    public void set(char c) {
        setType(char.class);
        UnicodeChar = c;
    }

    public void set(byte c) {
        setType(byte.class);
        AsciiChar = c;
    }

    public char UnicodeChar;
    public byte AsciiChar;
}
