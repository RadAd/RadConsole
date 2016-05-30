// RadConsole  Copyright (C) 2016  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.win32;

import com.sun.jna.Structure;

// typedef struct _WINDOW_BUFFER_SIZE_RECORD {
//   COORD dwSize;
// } WINDOW_BUFFER_SIZE_RECORD;
public class WINDOW_BUFFER_SIZE_RECORD extends Structure {
    public COORD dwSize;
    
    private static String[] fieldOrder = { "dwSize" };

    @Override
    protected java.util.List<String> getFieldOrder() {
        return java.util.Arrays.asList(fieldOrder);
    }
}
