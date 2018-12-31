package insa.clutchgames.wallpass.models;


import android.graphics.Canvas;
import android.graphics.Color;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

public class SimpleRoundedWall extends GameObject
{
    private Rectangle r;
    private Circle c1,c2;

    public SimpleRoundedWall(GameWorld world, Viewport viewport, float x, float y, float angle, float width, float height)
    {
        super(world,BodyType.STATIC,new Vec2(x,y*world.ratio),new Vec2(),angle,0x0004,Color.argb(255,255,115,35));

        r = new Rectangle(body,viewport,width-height,height);
        c1 = new Circle(body,viewport,-width/2 + height/2,0,height/2);
        c2 = new Circle(body,viewport,width/2-height/2,0,height/2);
        c1.setFilter(0x0004, 0x0001);
        c2.setFilter(0x0004, 0x0001);
        r.setFilter(0x0004,0x0001);
    }
    public void draw(Canvas canvas)
    {
        r.draw(canvas,paint);
        c1.draw(canvas,paint);
        c2.draw(canvas,paint);
    }
}