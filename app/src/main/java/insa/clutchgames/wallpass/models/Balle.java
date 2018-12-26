package insa.clutchgames.wallpass.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import java.util.Vector;

public class Balle
{
    private int x,y, vx, vy, h, w;
    private float rayon;
    private Paint paint;

    public Balle(int x, int y, int vx, int vy, int rayon, int h, int w)
    {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.rayon = rayon;
        this.h = h;
        this.w = w;
        paint = new Paint();
        paint.setColor(Color.argb(255,255,115,35));
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public void setPosition(int x,int y)
    {
        this.x = x;
        this.y = y;
    }
    public float getRayon()
    {
        return rayon;
    }
    public void moveWithCollisionDetection()
    {
        x+=vx;
        y+=vy;

        if(x+rayon > w) {vx=-vx;}
        if(y+rayon > h) {vy=-vy;}
        if(x - rayon <0) {vx=-vx;}
        if(y - rayon <0) {vy=-vy;}
    }
    public void resize(int w, int h)
    {
        this.w = w;
        this.h = h;
    }

    private PointF rotatePoint(PointF center, PointF point, float angle){
        //translate
        float tempX = point.x - center.x;
        float tempY = point.y - center.y;

        // apply rotation
        double theta = angle * Math.PI / 180;
        double rotatedX = tempX*Math.cos(theta) - tempY*Math.sin(theta);
        double rotatedY = tempX*Math.sin(theta) + tempY*Math.cos(theta);

        // translate back
        return  new PointF( (float)(rotatedX + center.x), (float) (rotatedY + center.y));
    }

    public boolean collideWall(Mur m){
        Vector<PointF> wallCorners = new Vector<>();
        wallCorners.add( rotatePoint(
                                        new PointF(m.getX(), m.getY()),
                                        m.getTopLeftCorner(),
                                        m.getAngle()
                                    )
        );
        wallCorners.add( rotatePoint(
                                        new PointF(m.getX(), m.getY()),
                                        m.getBottomLeftCorner(),
                                        m.getAngle()
                                    )
        );
        wallCorners.add( rotatePoint(
                                        new PointF(m.getX(), m.getY()),
                                        m.getBottomRightCorner(),
                                        m.getAngle()
                                    )
        );
        wallCorners.add( rotatePoint(
                                        new PointF(m.getX(), m.getY()),
                                        m.getTopRightCorner(),
                                        m.getAngle()
                                    )
        );
        return (CollideTools.collideOBB_PointF(wallCorners,4, new PointF(x, y)) ||
        CollideTools.collisionSegment(m.getTopLeftCorner(), m.getBottomLeftCorner(), new PointF(x, y), rayon) ||
        CollideTools.collisionSegment(m.getBottomLeftCorner(), m.getBottomRightCorner(), new PointF(x, y), rayon) ||
        CollideTools.collisionSegment(m.getBottomRightCorner(), m.getTopRightCorner(), new PointF(x, y), rayon) ||
        CollideTools.collisionSegment(m.getTopRightCorner(), m.getTopLeftCorner(), new PointF(x, y), rayon));

    }

    public void draw(Canvas canvas)
    {
        canvas.drawCircle(x,y,rayon,paint);
    }
}