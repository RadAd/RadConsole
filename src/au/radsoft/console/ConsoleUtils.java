// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.console;

import au.radsoft.console.javase.ConsoleCanvas;
import au.radsoft.console.win32.Win32Console;

public class ConsoleUtils {
    public static Console create(String title, int w, int h, boolean usenative)
            throws java.io.IOException {
        Console c = usenative ? Win32Console.create(title, w, h) : null;
        if (c == null)
            c = ConsoleCanvas.create(title, w, h);
        return c;
    }
    
    public static void realloc()
    {
        Win32Console.realloc();
    }

    public static void scrollup(Buffer b) {
        for (int xx = 0; xx < b.width(); ++xx) {
            for (int yy = 0; yy < b.height(); ++yy) {
                final CharInfo dstcell = b.get(xx, yy);
                if (dstcell != null) {
                    final CharInfo srccell = b.get(xx, yy + 1);
                    if (srccell != null) {
                        dstcell.set(srccell);
                    }
                }
            }
        }
    }

    public static void scrolldown(Buffer b) {
        for (int xx = 0; xx < b.width(); ++xx) {
            for (int yy = b.height(); yy > 0; --yy) {
                final CharInfo dstcell = b.get(xx, yy - 1);
                if (dstcell != null) {
                    final CharInfo srccell = b.get(xx, yy - 2);
                    if (srccell != null) {
                        dstcell.set(srccell);
                    }
                }
            }
        }
    }
}
