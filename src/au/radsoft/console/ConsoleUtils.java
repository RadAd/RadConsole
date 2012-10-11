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

    public static void scrollup(Window w) {
        for (int xx = 0; xx < w.width(); ++xx) {
            for (int yy = 0; yy < w.height(); ++yy) {
                final CharInfo dstcell = w.get(xx, yy);
                if (dstcell != null) {
                    final CharInfo srccell = w.get(xx, yy + 1);
                    if (srccell != null) {
                        dstcell.set(srccell);
                    }
                }
            }
        }
    }

    public static void scrolldown(Window w) {
        for (int xx = 0; xx < w.width(); ++xx) {
            for (int yy = w.height(); yy > 0; --yy) {
                final CharInfo dstcell = w.get(xx, yy - 1);
                if (dstcell != null) {
                    final CharInfo srccell = w.get(xx, yy - 2);
                    if (srccell != null) {
                        dstcell.set(srccell);
                    }
                }
            }
        }
    }
}
