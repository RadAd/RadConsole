package au.radsoft.tetris;

import au.radsoft.console.CharInfo;
import au.radsoft.console.CharKey;
import au.radsoft.console.Color;
import au.radsoft.console.Console;
import au.radsoft.console.ConsoleUtils;
import au.radsoft.console.Buffer;

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
            console.showCursor(false);
        
            tetris(console);
            tetris(console);
        }
        finally
        {
            console.clear();
            console.close();
        }
    }
    
    static boolean hit(Buffer b, int x, int y)
    {
        return x < 0 || x >= b.getWidth()
            || y < 0 || y >= b.getHeight()
            || b.get(x, y).c != ' ';
    }
    
    static boolean hit(Buffer b, Point2d pb)
    {
        return hit(b, pb.x, pb.y);
    }
    
    static boolean hit(Buffer b, Point2d[] n)
    {
        for (Point2d pb : n)
        {
            if (hit(b, pb))
                return true;
        }
        return false;
    }
    
    static boolean hit(Buffer b, Point2d[] n, Point2d o)
    {
        for (Point2d pb : n)
        {
            if (hit(b, pb.offset(o)))
                return true;
        }
        return false;
    }
    
    static void tetris(Console console)
    {
        boolean exit = false;
        console.clear();
        
        java.util.Random r = new java.util.Random();
        Buffer b = new Buffer(console.getWidth() - 5, console.getHeight());
        Buffer db = new Buffer(console.getWidth(), console.getHeight());
        
        db.fill(b.getWidth(), 0, db.getWidth() - b.getWidth(), b.getHeight(), ' ', Color.WHITE, Color.LIGHT_GRAY);
        
        Tetronome[] o = {
            new Tetronome(Color.GRAY, Color.LIGHT_GRAY, new Point2d[]{ new Point2d(-1, 0), new Point2d(0, 0), new Point2d(1, 0), new Point2d(2, 0) }, false),
            new Tetronome(Color.RED, Color.DARK_RED, new Point2d[]{ new Point2d(0, 0), new Point2d(0, 1), new Point2d(1, 1), new Point2d(1, 0) }, false),
            new Tetronome(Color.CYAN, Color.TEAL, new Point2d[]{ new Point2d(-1, 0), new Point2d(0, 0), new Point2d(0, 1), new Point2d(1, 1) }, true),
            new Tetronome(Color.LEMON, Color.GREEN, new Point2d[]{ new Point2d(-1, 1), new Point2d(0, 1), new Point2d(0, 0), new Point2d(1, 0) }, true),
            new Tetronome(Color.YELLOW, Color.BROWN, new Point2d[]{ new Point2d(-1, 0), new Point2d(0, 0), new Point2d(1, 0), new Point2d(0, 1) }, true),
            new Tetronome(Color.MAGENTA, Color.PURPLE, new Point2d[]{ new Point2d(-1, 0), new Point2d(0, 0), new Point2d(1, 0), new Point2d(1, 1) }, true),
            new Tetronome(Color.BLUE, Color.DARK_BLUE, new Point2d[]{ new Point2d(-1, 0), new Point2d(0, 0), new Point2d(1, 0), new Point2d(-1, 1) }, true)
        };
        
        int oi = r.nextInt(o.length);
        int or = 0;
        
        int speed = 1000;
        Point2d p = new Point2d(b.getWidth()/2, 1);
        long t = System.currentTimeMillis() + speed;
        
        while (!exit)
        {
            Point2d[] n = o[oi].render(p, or);
            db.write(0, 0, b);
            for (Point2d pb : n)
            {
                db.write(pb.x, pb.y, (char) 254, o[oi].c, o[oi].c2);
            }
            console.write(0, 0, db);
            
            boolean sit = hit(b, n, down);
        
            CharKey key = console.getKeyNoWait();
            
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
                    if (!hit(b, n, right))
                    {
                        p = p.offset(right);
                        sit = hit(b, n, down.offset(right));
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
                    if (!hit(b, n, left))
                    {
                        p = p.offset(left);
                        sit = hit(b, n, down.offset(left));
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
                    int miny = b.getHeight() - 1;
                    int maxy = 0;
                    
                    for (Point2d pb : n)
                    {
                        if (pb.y < miny)
                            miny = pb.y;
                        if (pb.y > maxy)
                            maxy = pb.y;
                        b.write(pb.x, pb.y, (char) 254, o[oi].c, o[oi].c2);
                    }
                    
                    for (int y = miny; y <= maxy; ++y)
                    {
                        boolean full = true;
                        for (int x = 0; x < b.getWidth(); ++x)
                        {
                            if (!hit(b, x, y))
                                full = false;
                        }
                        
                        if (full)
                        {
                            b.fill(0, y, b.getWidth(), 1, Color.WHITE, Color.BLACK);
                        }
                    }
                    
                    console.write(0, 0, b);
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
                        for (int x = 0; x < b.getWidth(); ++x)
                        {
                            if (!hit(b, x, y))
                                full = false;
                        }
                        
                        if (full)
                        {
                            for (int xx = 0; xx < b.getWidth(); ++xx) {
                                for (int yy = y; yy > 0; --yy) {
                                    final CharInfo dstcell = b.get(xx, yy);
                                    final CharInfo srccell = b.get(xx, yy - 1);
                                    dstcell.set(srccell);
                                }
                            }
                        }
                    }
                    
                    oi = r.nextInt(o.length);
                    p = new Point2d(b.getWidth()/2, 1);
                    
                    // TODO Test for hit -> game over
                }
            }
        }
    }
}
