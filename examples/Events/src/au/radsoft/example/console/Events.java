// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.example.console;

import au.radsoft.console.CharKey;
import au.radsoft.console.Event;
import au.radsoft.console.Console;
import au.radsoft.console.ConsoleUtils;

public class Events {
    public static void main(String[] args) throws Exception {
        Console console = ConsoleUtils.create("Events", 80, 25, true);
        console.clear();
        
        boolean exit = false;
        while (!exit)
        {
            Event ev = console.getEvent();
            console.write(1, 1, ev.toString() + "     ");
            if (ev instanceof Event.Key)
            {
                Event.Key kev = (Event.Key) ev;
                if (kev.key == CharKey.ESCAPE && kev.state == Event.State.RELEASED)
                    exit = true;
            }
        }
        
        console.close();
    }
}
