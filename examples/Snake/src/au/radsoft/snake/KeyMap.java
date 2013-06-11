package au.radsoft.snake;

import au.radsoft.console.CharKey;
import au.radsoft.console.Console;

public class KeyMap
{
    public void getKeys(Console console, long d)
    {
        time_ += d;
        java.util.Arrays.fill(keys_, false);
        while (time_ > System.currentTimeMillis())
        {
            CharKey key = console.getKeyNoWait();
            if (key != null)
                keys_[key.ordinal()] = true;
                else
            {
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                }
            }
        }
    }
    
    public boolean isSet(CharKey key)
    {
        return keys_[key.ordinal()];
    }
    
    private long time_ = System.currentTimeMillis();
    private boolean[] keys_ = new boolean[CharKey.values().length];
}
