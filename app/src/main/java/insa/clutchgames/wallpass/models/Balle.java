package insa.clutchgames.wallpass.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

public class Balle extends Circle
{
    public float vx, vy;
    private float h, w;
    private Paint paint;
    private boolean transparent;
    private boolean colliding;
    private PointF oldPos;

    public Balle(float x, float y, float vx, float vy, float radius, int h, int w)
    {
        super(x,y,radius);
        oldPos = new PointF(x,y);
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

        oldPos.x = x;
        oldPos.y = y;
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


    public boolean collidedFromLeft(RectF box, RectF oldBox, RectF otherObj)
    {

        return oldBox.right < otherObj.left && // was not colliding
                box.right >= otherObj.left;
    }

    public boolean collidedFromRight(RectF box, RectF oldBox, RectF otherObj)
    {
        return oldBox.left >= otherObj.right && // was not colliding
                box.left < otherObj.right;
    }

    public boolean collidedFromTop(RectF box, RectF oldBox, RectF otherObj)
    {
        return oldBox.bottom < otherObj.top && // was not colliding
                box.bottom >= otherObj.top;
    }

    public boolean collidedFromBottom(RectF box, RectF oldBox, RectF otherObj)
    {
        return oldBox.top >= otherObj.bottom && // was not colliding
                box.top < otherObj.bottom;
    }

    public RectF getRotatedBox(float cx, float cy, float angle){
        PointF p = CollideTools.rotate(cx,cy,x,y,-angle);
        return CollideTools.createBox(p, radius);
    }

    public RectF getRotatedOldBox(float cx, float cy, float angle){
        PointF p = CollideTools.rotate(cx,cy,oldPos.x,oldPos.y,-angle);
        return CollideTools.createBox(p, radius);
    }
}