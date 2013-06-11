// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.console;

public interface Console {
    boolean isValid();

    int getWidth();

    int getHeight();
    
    void enableMouse(boolean enable);
    
    int getMouseX();

    int getMouseY();

    void clear();

    void showCursor(boolean show);

    void setCursor(int x, int y);

    void fill(int x, int y, int w, int h, char c, Color fg, Color bg);

    void fill(int x, int y, int w, int h, char c);

    void fill(int x, int y, int w, int h, Color fg, Color bg);

    void write(int x, int y, char ch);
    
    void write(int x, int y, char ch, Color fg, Color bg);

    void write(int x, int y, String s);

    void write(int x, int y, String s, Color fg, Color bg);
    
    void write(int x, int y, Buffer b);

    void read(int x, int y, Buffer b);
    
    CharKey getKey();

    CharKey getKeyNoWait();

    Event getEvent();

    Event getEventNoWait();

    boolean getKeyDown(CharKey key);
    
    void close();
}
