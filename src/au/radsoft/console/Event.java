// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.console;

public class Event
{
    public enum State { NONE, PRESSED, RELEASED };
    
    public Event(CharKey key, State state)
    {
        this.key = key;
        this.state = state;
    }
    
    public final CharKey key;
    public final State state;
}
