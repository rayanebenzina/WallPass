package insa.clutchgames.wallpass.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import java.util.Vector;

public class Balle extends Circle
{
    public Vector2D v,screen;
    private Paint paint;
    private boolean transparent;
    private boolean colliding;
    public Vector2D oldPos;

    public Balle(float x, float y, float vx, float vy, float radius, int h, int w)
    {
        super(x,y,radius);
        v = new Vector2D(vx,vy);
        oldPos = new Vector2D(x,y);
        this.colliding = false;

        screen = new Vector2D(w,h);
        this.transparent = false;
        paint = new Paint();
        paint.setColor(Color.argb(255,255,115,35));
    }

    public void moveWithCollisionDetection()
    {
        oldPos.set(p);
        p.add(v);
        if(p.x+radius > screen.x) {p.x=screen.x-radius; v.x=-v.x;}
        if(p.y+radius > screen.y) {p.y=screen.y-radius; v.y=-v.y;}
        if(p.x - radius <0) {p.x=radius; v.x=-v.x;}
        if(p.y - radius <0) {p.y=radius; v.y=-v.y;}
    }
    /*public Vector2D projectionI(Mur M,Balle C)
    {
        Vector2D vM,vMC;
        vM = M.getVitesse();
        vMC = Vector2D.sub(C.getPosition(), M.getPosition());
        float ti = (vM.x*vMC.x + vM.y*vMC.y)/(vM.x*vM.x + vM.y*vM.y);
        Vector2D I;
        I.x = M.getPosition().x + ti*vM.x;
        I.y = M.getPosition().y + ti*vM.y;
        return I;
    }*/
    public void resize(int w, int h)
    {
        screen.set(w,h);
    }
    public void draw(Canvas canvas)
    {
        canvas.drawCircle(p.x,p.y,radius,paint);
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
        Vector2D d = CollideTools.rotate(cx,cy,p.x,p.y,-angle);
        return CollideTools.createBox(d, radius);
    }

    public RectF getRotatedOldBox(float cx, float cy, float angle){
        Vector2D d = CollideTools.rotate(cx,cy,oldPos.x,oldPos.y,-angle);
        return CollideTools.createBox(d, radius);
    }
}