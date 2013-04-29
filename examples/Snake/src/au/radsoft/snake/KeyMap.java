package au.radsoft.snake;

import au.radsoft.console.CharKey;
import au.radsoft.console.Console;

public class KeyMap
{
    public void getkeys(Console console, long d)
    {
        t += d;
        java.util.Arrays.fill(k, false);
        while (t > System.currentTimeMillis())
        {
            CharKey key = console.getkeynowait();
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
    
    public boolean isset(CharKey key)
    {
        return k[key.ordinal()];
    }
    
    private long t = System.currentTimeMillis();
    private boolean[] k = new boolean[CharKey.values().length];
}
