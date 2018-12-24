package insa.clutchgames.wallpass.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Mur
{
    private float x,y, lenght, angle, strenght;
    private Paint paint;
    public Mur(float x, float y,float angle, float lenght)
    {
        this.x = x;
        this.y = y;
        this.lenght = lenght;
        this.angle = angle;
        strenght = 30;
        paint = new Paint();
        paint.setColor(Color.argb(255,255,115,35));
    }
    public void draw(Canvas canvas)
    {
        canvas.save();
        canvas.rotate(angle,x,y);
        canvas.drawRoundRect(x-lenght/2, y-strenght/2, x+lenght/2, y+strenght/2, strenght/2,strenght/2,paint);
        //canvas.drawRect(25, 50, 100, 100, paint);
        canvas.restore();
    }
}