package au.radsoft.geom;

public final class Rect2d
{
    public final int x;
    public final int y;
    public final int w;
    public final int h;
    
    public Rect2d(int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public boolean contains(int vx, int vy)
    {
        return inRange(x, vx, x + w) & inRange(y, vy, y + h);
    }
    
    public boolean contains(Rect2d o)
    {
        return contains(o.x, o.y) && contains(o.x + o.w - 1, o.y + o.h - 1);
    }
    
    public boolean intersects(Rect2d o)
    {
        int nx = Math.max(x, o.x);
        int ny = Math.max(y, o.y);
        int nw = Math.min(x + w, o.x + o.w) - nx;
        int nh = Math.min(y + h, o.y + o.h) - ny;
        return nw > 0 && nh > 0;
    }
    
    public boolean adjoins(Rect2d o)
    {
        return (x + w) == o.x || (o.x + o.w) == x || (y + h) == o.y || (o.y + o.h) == y;
    }
    
    public boolean intersectsOrAdjoins(Rect2d o)
    {
        int nx = Math.max(x, o.x);
        int ny = Math.max(y, o.y);
        int nw = Math.min(x + w, o.x + o.w) - nx;
        int nh = Math.min(y + h, o.y + o.h) - ny;
        return nw >= 0 && nh >= 0;
    }
    
    public Rect2d union(Rect2d o)
    {
        int nx = Math.min(x, o.x);
        int ny = Math.min(y, o.y);
        return new Rect2d(nx, ny, Math.max(x + w, o.x + o.w) - nx, Math.max(y + h, o.y + o.h) - ny);
    }
    
    public Rect2d intersection(Rect2d o)
    {
        int nx = Math.max(x, o.x);
        int ny = Math.max(y, o.y);
        int nw = Math.min(x + w, o.x + o.w) - nx;
        int nh = Math.min(y + h, o.y + o.h) - ny;
        if (nw >= 0 && nh >= 0)
            return new Rect2d(nx, ny, nw, nh);
        else
            return null;
    }
    
    @Override
    public String toString()
    {
        return getClass().getSimpleName() + " [" + x + ", " + y + ", " + w + ", " + h + "]";
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Rect2d)) return false;
        Rect2d r = (Rect2d) o;
        return x == r.x && y == r.y && w == r.w && h == r.h;
    }
    
    // l <= v < g
    private static boolean inRange(int l, int v, int g)
    {
        return l <= v && v < g;
    }
}
