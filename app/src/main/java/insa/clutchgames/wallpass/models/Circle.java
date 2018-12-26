package insa.clutchgames.wallpass.models;

import android.graphics.PointF;

class Circle{

    public float radius,x,y;

    public Circle(float x, float y, float radius)
    {
        this.x =x;
        this.y = y;
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
    public void setPos(PointF pos)
    {
        setPos(pos.x, pos.y);
    }
    public void setPos(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
    public PointF getPos()
    {
        return new PointF(x,y);
    }

    public Circle copy()
    {
        return new Circle(x,y,radius);
    }
}
