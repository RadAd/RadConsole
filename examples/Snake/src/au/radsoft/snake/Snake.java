package au.radsoft.snake;

import au.radsoft.console.CharInfo;
import au.radsoft.console.CharKey;
import au.radsoft.console.Color;
import au.radsoft.console.Console;
import au.radsoft.console.ConsoleUtils;

import au.radsoft.geom.Point2d;

class Snake
{
    public static void main(String s[])
        throws java.io.IOException
    {
        Console console = ConsoleUtils.create("Snake", 30, 15, true);
        try
        {
            console.showCursor(false);
        
            title(console);
        }
        finally
        {
            console.clear();
            console.close();
        }
    }
    
    static void title(Console console)
    {
        boolean exit = false;
        console.clear();
        drawBox(console, Color.WHITE, Color.BLACK);
        
        while (!exit && console.isValid())
        {
            console.fill(0, 0, console.getWidth(), console.getHeight(), Color.GRAY, Color.BLACK);
            
            console.write((console.getWidth() - 5) / 2, console.getHeight() / 2, "SNAKE", Color.WHITE, Color.BLACK);
            
            CharKey key = console.getKey();
            
            switch (key)
            {
            case ENTER:
            case SPACE:
                game(console);
                break;
                
            case Q:
            case ESCAPE:
                exit = true;
                break;
            }
        }
    }
    
    static void drawBox(Console console, Color fg, Color bg)
    {
        char[] c = { 205, 186, 201, 187, 200, 188 };
        
        int x1 = 0;
        int y1 = 0;
        int x2 = console.getWidth() - 1;
        int y2 = console.getHeight() - 1;
        
        for (int x = x1 + 1; x < x2; ++x)
        {
            console.write(x, y1, c[0], fg, bg);
            console.write(x, y2, c[0], fg, bg);
        }
        for (int y = y1 + 1; y < y2; ++y)
        {
            console.write(x1, y, c[1], fg, bg);
            console.write(x2, y, c[1], fg, bg);
        }
        console.write(x1, y1, c[2], fg, bg);
        console.write(x2, y1, c[3], fg, bg);
        console.write(x1, y2, c[4], fg, bg);
        console.write(x2, y2, c[5], fg, bg);
    }
    
    static void drawScore(Console console, int score)
    {
        console.write(0, 0, "Score: " + score + " ", Color.WHITE, Color.BLACK);
    }
    
    static void game(Console console)
    {
        boolean exit = false;
        console.clear();
        
        drawBox(console, Color.WHITE, Color.BLACK);
        
        java.util.Random r = new java.util.Random();
        
        KeyMap km = new KeyMap();
        
        CharInfo head = new CharInfo((char) 2, Color.RED, Color.BLACK);
        CharInfo tail = new CharInfo((char) 254, Color.GREEN, Color.BLACK);
        CharInfo target = new CharInfo((char) 5, Color.LEMON, Color.BLACK);
        
        int score = 0;
        drawScore(console, score);
        
        Point2d p = new Point2d(r.nextInt(console.getWidth() - 2) + 1, r.nextInt(console.getHeight() - 2) + 1);
        Direction d = p.x < (console.getWidth()/2) ? Direction.East : Direction.West;
        java.util.Vector<Point2d> v = new java.util.Vector<Point2d>();
        console.write(p.x, p.y, head.c, head.fg, head.bg);
        v.add(p);
        p = p.offset(d.offset);
        v.add(p);
        p = p.offset(d.offset);
        for (Point2d i : v)
            console.write(i.x, i.y, tail.c, tail.fg, tail.bg);
       
        Point2d q;
        do
        {
            q = new Point2d(r.nextInt(console.getWidth() - 2) + 1, r.nextInt(console.getHeight() - 2) + 1);
        } while (p.equals(q) || v.contains(q));
        console.write(q.x, q.y, target.c, target.fg, target.bg);
        
        while (!exit && console.isValid())
        {
            km.getKeys(console, 300 - (20 * v.size()));
            
            if (km.isSet(CharKey.W) || km.isSet(CharKey.UP))
            {
                d = Direction.North;
            }
            else if (km.isSet(CharKey.D) || km.isSet(CharKey.RIGHT))
            {
                d = Direction.East;
            }
            else if (km.isSet(CharKey.S) || km.isSet(CharKey.DOWN))
            {
                d = Direction.South;
            }
            else if (km.isSet(CharKey.A) || km.isSet(CharKey.LEFT))
            {
                d = Direction.West;
            }
            
            if (km.isSet(CharKey.Q) || km.isSet(CharKey.ESCAPE))
            {
                exit = true;
            }
            
            console.write(p.x, p.y, tail.c, tail.fg, tail.bg);

            v.add(p);
            p = p.offset(d.offset);
            
            console.write(p.x, p.y, head.c, head.fg, head.bg);
            
            if (p.equals(q))
            {
                score += 10 * v.size();
                drawScore(console, score);
                
                do
                {
                    q = new Point2d(r.nextInt(console.getWidth() - 2) + 1, r.nextInt(console.getHeight() - 2) + 1);
                } while (p.equals(q) || v.contains(q));
                console.write(q.x, q.y, target.c, target.fg, target.bg);
            }
            else
            {
                Point2d i = v.remove(0);
                console.write(i.x, i.y, ' ', Color.WHITE, Color.BLACK);
                if (score > 0)
                {
                    --score;
                    drawScore(console, score);
                }
            }
            
            if (v.contains(p))
                exit = true;
            
            if (p.x <= 0 || p.y <= 0 || p.x >= console.getWidth() - 1 || p.y >= console.getHeight() - 1)
                exit = true;
        }
    }
}
