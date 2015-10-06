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
    
    @Override
    public boolean equals(Object o)
    {
        if (o == null) return false;
        if (o == this) return true;
        if (o instanceof CharInfo)
            return equals((CharInfo) o);
        else
            return false;
        
    }
    
    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        result.append(super.toString());
        result.append("(c=" + (int)c);
        result.append(", fg=" + fg);
        result.append(", bg=" + bg);
        result.append(')');
        return result.toString();
    }
    
    // TODO Hash
    
    public char c;
    public Color fg;
    public Color bg;
};
