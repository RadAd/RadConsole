// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.win32;

import com.sun.jna.Structure;

// typedef struct _COORD {
//    SHORT X;
//    SHORT Y;
//  } COORD, *PCOORD;
public class COORD extends Structure implements Structure.ByValue {
    public COORD() {
    }

    public COORD(short X, short Y) {
        this.X = X;
        this.Y = Y;
    }

    public short X;
    public short Y;
    
    private static String[] fieldOrder = { "X", "Y" };
    
    @Override
    protected java.util.List<String> getFieldOrder() {
        return java.util.Arrays.asList(fieldOrder);
    }
}
