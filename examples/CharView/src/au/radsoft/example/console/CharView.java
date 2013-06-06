// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.example.console;

import au.radsoft.console.Console;
import au.radsoft.console.ConsoleUtils;

public class CharView {
    public static void main(String[] args) throws Exception {
        Console console = ConsoleUtils.create("Char View", 80, 25, true);
        console.cls();
        
        for (int y = 0; y < 16; ++y)
        {
            for (int x = 0; x < 16; ++x)
            {
                char c = (char) (y * 16 + x);
                console.write(x + 1, y + 1, c);
            }
        }
        
        console.getkey();
        console.close();
    }
}
