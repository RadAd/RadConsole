package au.radsoft.tetris;

import au.radsoft.console.CharInfo;
import au.radsoft.console.CharKey;
import au.radsoft.console.Color;
import au.radsoft.console.Console;
import au.radsoft.console.ConsoleUtils;
import au.radsoft.console.Window;

import au.radsoft.geom.Point2d;

class Tetris
{
    static final Point2d up    = new Point2d(0, -1);
    static final Point2d down  = new Point2d(0, 1);
    static final Point2d left  = new Point2d(-1, 0);
    static final Point2d right = new Point2d(1, 0);
    
    public static void main(String s[])
        throws java.io.IOException
    {
        Console console = ConsoleUtils.create("Tetris", 15, 15, true);
        try
        {
            console.showcursor(false);
        
            tetris(console);
        }
        finally
        {
            console.cls();
            console.close();
        }
    }
    
    static boolean hit(Window w, int x, int y)
    {
        return x < 0 || x >= w.width()
            || y < 0 || y >= w.height()
            || w.get(x, y).c != ' ';
    }
    
    static boolean hit(Window w, Point2d b)
    {
        return hit(w, b.x, b.y);
    }
    
    static boolean hit(Window w, Point2d[] n)
    {
        for (Point2d b : n)
        {
            if (hit(w, b))
                return true;
        }
        return false;
    }
    
    static boolean hit(Window w, Point2d[] n, Point2d o)
    {
        for (Point2d b : n)
        {
            if (hit(w, b.offset(o)))
                return true;
        }
        return false;
    }
    
    static void tetris(Console console)
    {
        boolean exit = false;
        console.cls();
        
        java.util.Random r = new java.util.Random();
        Window w = new Window(console.width() - 5, console.height());
        Window db = new Window(console.width(), console.height());
        
        db.fill(w.width(), 0, db.width() - w.width(), w.height(), ' ', Color.WHITE, Color.LIGHT_GRAY);
        
        Tetronome[] o = {
            new Tetronome(Color.GRAY, new Point2d[]{ new Point2d(-1, 0), new Point2d(0, 0), new Point2d(1, 0), new Point2d(2, 0) }, false),
            new Tetronome(Color.RED, new Point2d[]{ new Point2d(0, 0), new Point2d(0, 1), new Point2d(1, 1), new Point2d(1, 0) }, false),
            new Tetronome(Color.CYAN, new Point2d[]{ new Point2d(-1, 0), new Point2d(0, 0), new Point2d(0, 1), new Point2d(1, 1) }, true),
            new Tetronome(Color.LEMON, new Point2d[]{ new Point2d(-1, 1), new Point2d(0, 1), new Point2d(0, 0), new Point2d(1, 0) }, true),
            new Tetronome(Color.YELLOW, new Point2d[]{ new Point2d(-1, 0), new Point2d(0, 0), new Point2d(1, 0), new Point2d(0, 1) }, true),
            new Tetronome(Color.MAGENTA, new Point2d[]{ new Point2d(-1, 0), new Point2d(0, 0), new Point2d(1, 0), new Point2d(1, 1) }, true),
            new Tetronome(Color.BLUE, new Point2d[]{ new Point2d(-1, 0), new Point2d(0, 0), new Point2d(1, 0), new Point2d(-1, 1) }, true)
        };
        
        int oi = r.nextInt(o.length);
        int or = 0;
        
        int speed = 1000;
        Point2d p = new Point2d(w.width()/2, 1);
        long t = System.currentTimeMillis() + speed;
        
        while (!exit)
        {
            Point2d[] n = o[oi].render(p, or);
            db.write(0, 0, w);
            for (Point2d b : n)
            {
                db.write(b.x, b.y, (char) 254, o[oi].c, Color.BLACK);
            }
            console.write(0, 0, db);
            
            boolean sit = hit(w, n, down);
        
            CharKey key = console.getkeynowait();
            
            if (key != null)
            {
                switch (key)
                {
                case COMMA:
                    --or;
                    if (or < 0)
                        or = 3;
                    break;
                    
                case PERIOD:
                    ++or;
                    if (or > 3)
                        or = 0;
                    break;
                    
                case D:
                case RIGHT:
                    if (!hit(w, n, right))
                    {
                        p = p.offset(right);
                        sit = hit(w, n, down.offset(right));
                    }
                    break;

                case W:
                case UP:
                    ++or;
                    if (or > 3)
                        or = 0;
                    // TODO Test if it can safely be placed here
                    // TODO Bump of edges if not enough room
                    break;
                    
                case S:
                case DOWN:
                    t = 0;
                    break;
                    
                case A:
                case LEFT:
                    if (!hit(w, n, left))
                    {
                        p = p.offset(left);
                        sit = hit(w, n, down.offset(left));
                    }
                    break;
                    
                case Q:
                case ESCAPE:
                    exit = true;
                    break;
                }
            }
            
            long s = System.currentTimeMillis();
            if (t < s)
            {
                t = s + speed;
                p = p.offset(0, 1);
                
                if (sit)
                {
                    int miny = w.height() - 1;
                    int maxy = 0;
                    
                    for (Point2d b : n)
                    {
                        if (b.y < miny)
                            miny = b.y;
                        if (b.y > maxy)
                            maxy = b.y;
                        w.write(b.x, b.y, (char) 254, o[oi].c, Color.BLACK);
                    }
                    
                    for (int y = miny; y <= maxy; ++y)
                    {
                        boolean full = true;
                        for (int x = 0; x < w.width(); ++x)
                        {
                            if (!hit(w, x, y))
                                full = false;
                        }
                        
                        if (full)
                        {
                            w.fill(0, y, w.width(), 1, Color.WHITE, Color.BLACK);
                        }
                    }
                    
                    console.write(0, 0, w);
                    try
                    {
                        Thread.sleep(200);
                    }
                    catch (InterruptedException e)
                    {
                    }
                    
                    for (int y = miny; y <= maxy; ++y)
                    {
                        boolean full = true;
                        for (int x = 0; x < w.width(); ++x)
                        {
                            if (!hit(w, x, y))
                                full = false;
                        }
                        
                        if (full)
                        {
                            for (int xx = 0; xx < w.width(); ++xx) {
                                for (int yy = y; yy > 0; --yy) {
                                    final CharInfo dstcell = w.get(xx, yy);
                                    final CharInfo srccell = w.get(xx, yy - 1);
                                    dstcell.set(srccell);
                                }
                            }
                        }
                    }
                    
                    oi = r.nextInt(o.length);
                    p = new Point2d(w.width()/2, 1);
                    
                    // TODO Test for hit -> game over
                }
            }
        }
    }
}
