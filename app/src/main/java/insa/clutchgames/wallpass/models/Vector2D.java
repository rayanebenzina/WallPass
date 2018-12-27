package insa.clutchgames.wallpass.models;

import android.graphics.PointF;

public class Vector2D extends PointF {

    public Vector2D(float x,float y)
    {
        this.x = x;
        this.y = y;
    }
    public  Vector2D()
    {
        this(0,0);
    }
    public Vector2D(Vector2D v)
    {
        this(v.x,v.y);
    }
    public static Vector2D sum(Vector2D A, Vector2D B)
    {
        return new Vector2D(A.x + B.x, A.y + B.y );
    }
    public void add(Vector2D B)
    {
        this.add(B.x,B.y);
    }
    public void add(float x, float y)
    {
        this.x += x;
        this.y += y;
    }
    public void sub(Vector2D B)
    {
        this.sub(B.x,B.y);
    }
    public void sub(float x, float y)
    {
        this.x -= x;
        this.y -= y;
    }
    public static Vector2D sub(Vector2D A, Vector2D B)
    {
        return new Vector2D(A.x - B.x, A.y - B.y);
    }
    public void mul(float lambda)
    {
        this.x *= lambda;
        this.y *= lambda;
    }
    public static  Vector2D mul(Vector2D v, float lambda)
    {
        return new Vector2D(lambda*v.x,lambda*v.y);
    }
    public void div(float lambda)
    {
        this.x /= lambda;
        this.y /= lambda;
    }
    public float getNorm()
    {
        return (float) Math.sqrt(x*x + y*y);
    }
    public float getNorm2()
    {
        return (float) x*x + y*y;
    }
    public Vector2D getNormal()
    {
        return new Vector2D(y,-x);
    }
    public void normalize()
    {
        this.div(this.getNorm());
    }
    public static float dot(Vector2D A, Vector2D B)
    {
        return A.x * B.x + A.y * B.y;
    }
    public void rotate(float angle)
    {
        double theta = angle * Math.PI / 180;
        double rx = x * Math.cos(theta) - y * Math.sin(theta);
        double ry = x * Math.sin(theta) + y * Math.cos(theta);
        x = ( float ) rx;
        y = ( float ) ry;
    }
}
