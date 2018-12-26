package insa.clutchgames.wallpass.models;

import android.graphics.*;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class Mur
{
    private float x,y, width, angle, height;
    private Paint paint;
    public Mur(float x, float y,float angle, float width)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.angle = angle;
        height = 30;
        paint = new Paint();
        paint.setColor(Color.argb(255,155,15,0));
    }
    public Mur(float x, float y,float angle, float width, float height)
    {
        this(x, y, angle, width);
        this.height = height;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void draw(Canvas canvas)
    {
        canvas.save();
        canvas.rotate(angle,x,y);
        canvas.drawRoundRect(x-width/2, y-height/2, x+width/2, y+height/2, height/2,height/2,paint);
//        canvas.drawRect(25, 50, 100, 100, paint);
        canvas.restore();
    }


    public PointF getTopLeftCorner(){
        return new PointF(x-width/2, y-height/2);
    }

    public PointF getBottomLeftCorner(){
        return new PointF(x-width/2, y+height/2);
    }


    public PointF getTopRightCorner(){
        return new PointF(x+width/2, y-height/2);
    }

    public PointF getBottomRightCorner(){
        return new PointF(x+width/2, y+height/2);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getAngle() {
        return angle;
    }

    public float getHeight() {
        return height;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Paint getPaint() {
        return paint;
    }
}