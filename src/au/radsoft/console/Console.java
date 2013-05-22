// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.console;

public interface Console {
    boolean isvalid();

    int width();

    int height();
    
    void mouse(boolean enable);
    
    int mousex();

    int mousey();

    void cls();

    void showcursor(boolean show);

    void setcursor(int x, int y);

    void fill(int x, int y, int w, int h, char c, Color fg, Color bg);

    void fill(int x, int y, int w, int h, char c);

    void fill(int x, int y, int w, int h, Color fg, Color bg);

    void write(int x, int y, char ch);
    
    void write(int x, int y, char ch, Color fg, Color bg);

    void write(int x, int y, String s, Color fg, Color bg);

    void write(int x, int y, Buffer b);

    void read(int x, int y, Buffer b);
    
    CharKey getkey();

    CharKey getkeynowait();

    Event getevent();

    Event geteventnowait();

    boolean getkeydown(CharKey key);
    
    void close();
}
