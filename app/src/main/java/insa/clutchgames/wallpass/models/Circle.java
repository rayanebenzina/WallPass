package insa.clutchgames.wallpass.models;

import android.graphics.PointF;

class Circle{

    public float radius;
    public Vector2D p;

    public Circle(float x, float y, float radius)
    {
        p = new Vector2D(x,y);
        this.radius = radius;
    }

    public void setPos(PointF pos)
    {
        p.set(pos);
    }
    public void setPos(float x, float y)
    {
        p.set(x,y);
    }

    public Circle copy()
    {
        return new Circle(p.x,p.y,radius);
    }
}
