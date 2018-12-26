package insa.clutchgames.wallpass.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Balle extends Circle
{
    private float vx, vy, h, w;
    private Paint paint;
    private boolean transparent;
    private boolean colliding;

    public Balle(float x, float y, float vx, float vy, float radius, int h, int w)
    {
        super(x,y,radius);
        this.transparent = false;
        this.colliding = false;
        this.vx = vx;
        this.vy = vy;
        this.h = h;
        this.w = w;
        paint = new Paint();
        paint.setColor(Color.argb(255,255,115,35));
    }

    public void moveWithCollisionDetection()
    {
        x += vx;
        y += vy;

        if(x+radius > w) {vx=-vx;}
        if(y+radius > h) {vy=-vy;}
        if(x - radius <0) {vx=-vx;}
        if(y - radius <0) {vy=-vy;}
    }
    public void resize(int w, int h)
    {
        this.w = w;
        this.h = h;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawCircle(x,y,radius,paint);
    }

    public boolean isTransparent() {
        return transparent;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;

        if(transparent)
            paint.setColor(Color.argb(55,255,115,35));
        else
            paint.setColor(Color.argb(255,255,115,35));

    }


    public boolean isColliding() {
        return colliding;
    }

    public void setColliding(boolean colliding) {
        this.colliding = colliding;
    }
}