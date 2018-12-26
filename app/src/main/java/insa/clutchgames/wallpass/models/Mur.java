package insa.clutchgames.wallpass.models;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

public class Mur extends RectF
{
    private float angle;
    private Paint paint;
    private PointF center;

    public Mur(float x, float y,float angle, float width)
    {
        this(x,y,angle,width,30);
    }

    public Mur(float x, float y,float angle, float width, float height)
    {
        this.paint = new Paint();
        this.paint.setColor(Color.argb(255,155,15,0));
        this.angle = angle;

        this.left = x-width/2;
        this.right = x+width/2;
        this.top = y-height/2;
        this.bottom = y+height/2;

        this.center = new PointF(x,y);
    }
    public void setColor(int color)
    {
        paint.setColor(color);
    }

    public float getAngle() {
        return angle;
    }
    public PointF getCenter()
    {
        return center;
    }
    public void draw(Canvas canvas)
    {
        canvas.save();
        canvas.rotate(angle,this.centerX(),this.centerY());
        canvas.drawRect(this,paint);
        canvas.restore();
    }
}