// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.example.console;

import au.radsoft.console.CharKey;
import au.radsoft.console.Color;
import au.radsoft.console.Console;
import au.radsoft.console.ConsoleUtils;
import au.radsoft.console.Buffer;

import java.util.Random;

public class Test {
    public static void main(String[] args) throws Exception {
        Console console = ConsoleUtils.create("Test", 80, 25, true);
        test4(console);
        console.close();
    }
    
    static void test4(Console console) {
        console.showCursor(false);
        console.clear();
        
        java.util.Random r = new java.util.Random();
        
        int w = 10;
        int h = 10;
        
        for (int y = 0; y < h; ++y)
        {
            for (int x = 0; x < w; ++x)
            {
                console.write(x, y, (char) r.nextInt(256), Color.values()[r.nextInt(15)], Color.values()[r.nextInt(15)]);
            }
        }
        
        Buffer b = new Buffer(w, h);
        
        console.read(0, 0, b);
        console.write(w + 2, 0, b);
        
        console.getKey();
    }

    static void test3(Console console) {
        console.showCursor(false);
        console.clear();
        
        for (int y = 0; y < 16; ++y) {
            for (int x = 0; x < 16; ++x) {
                console.write(x, y, (char) (y * 16 + x), Color.WHITE, Color.BLACK);
            }
        }
        
        console.getKey();
    }
    
    static void test2(Console console) {
        console.showCursor(false);
        console.clear();

        Buffer b = new Buffer(console.getWidth(), console.getHeight());
        b.clear();
        Buffer ship = new Buffer(3, 2);
        ship.clear();
        ship.write(1, 0, (char) 30);
        ship.write(1, 1, (char) (5 * 32 + 17));
        ship.write(0, 1, '<');
        ship.write(2, 1, '>');

        int shipx = (console.getWidth() - ship.getWidth()) / 2;

        Random r = new Random();

        boolean exit = false;
        while (!exit && console.isValid()) {
            int row = 0;// w.getHeight() - 1;
            ConsoleUtils.scrollDown(b);
            b.fill(0, row, b.getWidth(), 1, ' ');
            b.write(r.nextInt(b.getWidth()), row, '.');

            console.write(0, 0, b);
            console.write(shipx, b.getHeight() - ship.getHeight(), ship);

            CharKey c;
            if ((c = console.getKeyNoWait()) != null) {
                switch (c) {
                case ESCAPE:
                    exit = true;
                    break;
                    
                case LEFT:
                case A:
                    if (shipx > 0)
                        shipx--;
                    break;
                    
                case RIGHT:
                case D:
                    if (shipx < console.getWidth() - 1)
                        shipx++;
                    break;
                    
                default:
                    break;
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    static void test1(Console console) {
        console.clear();
        console.setCursor(3, 1);
        console.enableMouse(true);
        console.showCursor(false);
        console.write(1, 1, "Press esc to exit.", Color.TEAL, Color.BLACK);

        int x = console.getWidth() / 2;
        int y = console.getHeight() / 2;

        boolean exit = false;
        while (!exit && console.isValid()) {
            console.write(1, 3, "Mouse: " + console.getMouseX() + "," + console.getMouseY() + "         ", Color.TEAL, Color.BLACK);
            console.write(x, y, '@', Color.WHITE, Color.BLACK);

            CharKey c = console.getKey();
            //System.err.println("c: " + c);

            console.write(x, y, '+', Color.WHITE, Color.BLACK);

            switch (c) {
            case ESCAPE:
                exit = true;
                break;
                
            case LEFT:
            case A:
                if (x > 0)
                    x--;
                break;
                
            case UP:
            case W:
                if (y > 0)
                    y--;
                break;
                
            case RIGHT:
            case D:
                if (x < console.getWidth() - 1)
                    x++;
                break;
                
            case DOWN:
            case S:
                if (y < console.getHeight() - 1)
                    y++;
                break;
                
            case MOUSE_BUTTON1:
                console.write(console.getMouseX(), console.getMouseY(), '*', Color.WHITE, Color.BLACK);
                break;
                
            case MOUSE_BUTTONR:
                console.write(console.getMouseX(), console.getMouseY(), '=', Color.WHITE, Color.BLACK);
                break;
                
            case MOUSE_BUTTON2:
                console.write(console.getMouseX(), console.getMouseY(), '%', Color.WHITE, Color.BLACK);
                break;
                
            default:
                break;
            }
        }
    }
}
