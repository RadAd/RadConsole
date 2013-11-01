// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.example.console;

import au.radsoft.console.Buffer;
import au.radsoft.console.Console;
import au.radsoft.console.ConsoleUtils;

public class CharView {
    public static void main(String[] args) throws Exception {
        //ConsoleUtils.realloc();
        Console console = ConsoleUtils.create("Char View", 80, 25, true);
        console.clear();
        
        for (int y = 0; y < 16; ++y)
        {
            for (int x = 0; x < 16; ++x)
            {
                char c = (char) (y * 16 + x);
                console.write(x + 1, y + 1, c);
            }
        }
        
        Buffer b = new Buffer(16, 16);
        
        for (int y = 0; y < 16; ++y)
        {
            for (int x = 0; x < 16; ++x)
            {
                char c = (char) (y * 16 + x);
                b.write(x, y, c);
            }
        }
        
        console.write(19, 1, b);
        
        while (console.getKey() == null)
            ;
        console.close();
    }
}
