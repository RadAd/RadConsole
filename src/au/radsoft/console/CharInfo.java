// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.console;

public class CharInfo {
    public CharInfo(char cc, Color fgc, Color bgc) {
        c = cc;
        fg = fgc;
        bg = bgc;
    }

    public CharInfo(CharInfo o) {
        c = o.c;
        fg = o.fg;
        bg = o.bg;
    }

    public void set(CharInfo o) {
        c = o.c;
        fg = o.fg;
        bg = o.bg;
    }

    public boolean equals(CharInfo o)
    {
        return c == o.c && fg == o.fg && bg == o.bg;
    }
    
    public boolean equals(Object o)
    {
        if (o instanceof CharInfo)
            return equals((CharInfo) o);
        else
            return false;
        
    }
    
    public char c;
    public Color fg;
    public Color bg;
};
