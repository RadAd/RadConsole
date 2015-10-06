package au.radsoft.tetris;

import au.radsoft.geom.Rect2d;

class DirtyRectangles
{
    private java.util.List<Rect2d> dirty_ = new java.util.ArrayList<Rect2d>();
    
    java.util.List<Rect2d> get()
    {
        return java.util.Collections.unmodifiableList(dirty_);
    }
    
    void add(Rect2d r)
    {
        if (r == null)
            return;
            
        for (Rect2d er : dirty_)
        {
            if (er.contains(r))
                return;
        }
        
        boolean anotherPass = true;
        while (anotherPass)
        {
            anotherPass = false;
            for (java.util.Iterator<Rect2d> iter = dirty_.listIterator(); iter.hasNext(); )
            {
                Rect2d er = iter.next();
                if (r.contains(er))
                {
                    iter.remove();
                }
                //else if (r.intersects(er) || r.adjoins(er))
                else if (r.intersectsOrAdjoins(er))
                {
                    r = r.union(er);
                    iter.remove();
                    anotherPass = true;
                }
                // TODO Join abutting rects
            }
        }
        
        dirty_.add(r);
    }
}
