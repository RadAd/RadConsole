// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.console;

public interface Event
{
    public enum State { NONE, PRESSED, RELEASED };
    
    public static class Key implements Event
    {
        public Key(CharKey key, State state)
        {
            this.key = key;
            this.state = state;
        }
        
        public final CharKey key;
        public final State state;
    }

    public static class MouseButton implements Event
    {
        public MouseButton(CharKey key, State state)
        {
            this.key = key;
            this.state = state;
        }
        
        public final CharKey key;
        public final State state;
    }

    public static class MouseMoved implements Event
    {
        public MouseMoved()
        {
        }
    }
}
