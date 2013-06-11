package au.radsoft.snake;

import au.radsoft.console.CharKey;
import au.radsoft.console.Console;

public class KeyMap
{
    public void getKeys(Console console, long d)
    {
        t += d;
        java.util.Arrays.fill(k, false);
        while (t > System.currentTimeMillis())
        {
            CharKey key = console.getKeyNoWait();
            if (key != null)
                k[key.ordinal()] = true;
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
        return k[key.ordinal()];
    }
    
    private long t = System.currentTimeMillis();
    private boolean[] k = new boolean[CharKey.values().length];
}
