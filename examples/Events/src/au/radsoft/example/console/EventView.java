// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.example.console;

import au.radsoft.console.Console;
import au.radsoft.console.ConsoleUtils;
import au.radsoft.console.Event;

public class EventView {
    public static void main(String[] args) throws Exception {
        Console console = ConsoleUtils.create("Char View", 80, 25, true);
        console.cls();
        console.mouse(true);
        
        boolean exit = false;
        while (!exit)
        {
            Event ev = console.getevent();
            console.cls();
            console.write(1, 1, ev.toString());
        }
        
        console.close();
    }
}
