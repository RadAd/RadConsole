// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.console;

public interface Event
{
    public enum State { NONE, PRESSED, RELEASED };
    
    void handle(Handler h);
    
    public interface Handler
    {
        void handle(Key ke);
        void handle(MouseButton mbe);
        void handle(MouseMoved mme);
        // TODO This isnt easily extendable
    }
    
    public static class Key implements Event
    {
        public Key(CharKey key, State state)
        {
            this.key = key;
            this.state = state;
        }
        
        @Override
        public void handle(Handler h)
        {
            h.handle(this);
        }
        
        @Override
        public String toString()
        {
<<<<<<< HEAD
            return super.toString() + "(" + key + ", " + state + ")";
=======
            StringBuilder result = new StringBuilder();
            result.append(super.toString());
            result.append("(key=" + key);
            result.append(", state=" + state);
            result.append(')');
            return result.toString();
>>>>>>> ac60ce4bdc64de658a4c40cd616696abbcee0a4c
        }
        
        public final CharKey key;
        public final State state;
    }

    public static class MouseButton implements Event
    {
        public MouseButton(CharKey key, State state, int mx, int my)
        {
            this.key = key;
            this.state = state;
            this.mx = mx;
            this.my = my;
        }
        
        @Override
        public void handle(Handler h)
        {
            h.handle(this);
        }
        
        @Override
        public String toString()
        {
<<<<<<< HEAD
            return super.toString() + "(" + key + ", " + state + ", " + mx + ", " + my + ")";
=======
            StringBuilder result = new StringBuilder();
            result.append(super.toString());
            result.append("(key=" + key);
            result.append(", state=" + state);
            result.append(", mx=" + mx);
            result.append(", my=" + my);
            result.append(')');
            return result.toString();
>>>>>>> ac60ce4bdc64de658a4c40cd616696abbcee0a4c
        }
        
        public final CharKey key;
        public final State state;
        public final int mx;
        public final int my;
    }

    public static class MouseMoved implements Event
    {
        public MouseMoved(int mx, int my)
        {
            this.mx = mx;
            this.my = my;
        }
        
        @Override
        public void handle(Handler h)
        {
            h.handle(this);
        }
        
        @Override
        public String toString()
        {
<<<<<<< HEAD
            return super.toString() + "(" + mx + ", " + my + ")";
=======
            StringBuilder result = new StringBuilder();
            result.append(super.toString());
            result.append("(mx=" + mx);
            result.append(", my=" + my);
            result.append(')');
            return result.toString();
>>>>>>> ac60ce4bdc64de658a4c40cd616696abbcee0a4c
        }
        
        public final int mx;
        public final int my;
    }
}
