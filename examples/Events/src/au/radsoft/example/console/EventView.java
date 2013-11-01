// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.example.console;

import au.radsoft.console.CharKey;
import au.radsoft.console.Console;
import au.radsoft.console.ConsoleUtils;
import au.radsoft.console.Event;

public class EventView {
    public static void main(String[] args) throws Exception {
        Console console = ConsoleUtils.create("Events", 80, 25, true);
        console.clear();
        console.enableMouse(true);
        
        boolean exit = false;
        while (!exit)
        {
            Event event = console.getEvent();
            console.clear();
            console.write(1, 1, event.toString());
            if (event instanceof Event.Key)
            {
                Event.Key kevent = (Event.Key) event;
                if (kevent.key == CharKey.ESCAPE && kevent.state == Event.State.RELEASED)
                    exit = true;
            }
        }
        
        console.close();
    }
}
