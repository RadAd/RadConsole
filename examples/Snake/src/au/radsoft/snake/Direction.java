package au.radsoft.snake;

import au.radsoft.geom.Point2d;

public enum Direction
{
    None(new Point2d(0, 0)),
    North(new Point2d(0, -1)),
    East(new Point2d(1, 0)),
    South(new Point2d(0, 1)),
    West(new Point2d(-1, 0));
    
    Direction(Point2d p)
    {
        offset = p;
    }
    
    public final Point2d offset;
}
