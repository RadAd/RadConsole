package au.radsoft.geom;

public final class Point2d
{
    public final int x;
    public final int y;
    
    public Point2d(int ix, int iy)
    {
        x = ix;
        y = iy;
    }
    
    public Point2d offset(int ix, int iy)
    {
        return new Point2d(x + ix, y + iy);
    }
    
    public Point2d offset(Point2d p)
    {
        return new Point2d(x + p.x, y + p.y);
    }
    
    public boolean equals(Point2d p)
    {
        return p.x == x && p.y == y;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o == null) return false;
        if (o == this) return true;
        if (o instanceof Point2d)
            return equals((Point2d) o);
        else
            return false;
    }
    
    @Override
    public int hashCode()
    {
        return x<<7-x+y;//x*prime+y
    }
    
    @Override
    public String toString()
    {
        return "(" + x + "," + y + ")";
    }
}
