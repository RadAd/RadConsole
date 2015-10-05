// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.win32;

import com.sun.jna.Structure;

// typedef struct _SMALL_RECT {
//    SHORT Left;
//    SHORT Top;
//    SHORT Right;
//    SHORT Bottom;
//  } SMALL_RECT;
public class SMALL_RECT extends Structure {
    public SMALL_RECT() {
    }

    public SMALL_RECT(short Top, short Left, short Bottom, short Right) {
        this.Top = Top;
        this.Left = Left;
        this.Bottom = Bottom;
        this.Right = Right;
    }

    public short Left;
    public short Top;
    public short Right;
    public short Bottom;
    
    private static String[] fieldOrder = { "Left", "Top", "Right", "Bottom" };
    
    @Override
    protected java.util.List<String> getFieldOrder() {
        return java.util.Arrays.asList(fieldOrder);
    }
}
