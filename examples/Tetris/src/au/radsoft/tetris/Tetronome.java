package au.radsoft.tetris;

import au.radsoft.console.Color;

import au.radsoft.geom.Point2d;

public class Tetronome
{
    public Tetronome(Color c, Color c2, Point2d[] o, boolean rotate)
    {
        this.c = c;
        this.c2 = c2;
        this.o = o;
        this.rotate = rotate;
    }
    
    public final Color c;
    public final Color c2;
    private final Point2d[] o;
    private final boolean rotate;
    
    private static Point2d rotate(Point2d p, int r)
    {
        switch (r)
        {
        default:
        case 0:
            return p;
            
        case 1:
            return new Point2d(-p.y, p.x);
            
        case 2:
            return new Point2d(-p.x, -p.y);
            
        case 3:
            return new Point2d(p.y, -p.x);
        }
    }
    
    private static Point2d flip(Point2d p, int r)
    {
        if (r%2 == 0)
            return p;
        else
            return new Point2d(p.y, p.x);
    }
    
    public Point2d[] render(Point2d p, int r)
    {
        Point2d[] n = new Point2d[o.length];
        for (int i = 0; i < o.length; ++i)
        {
            if (rotate)
                n[i] = p.offset(rotate(o[i], r));
            else
                n[i] = p.offset(flip(o[i], r));
        }
        return n;
    }
}
