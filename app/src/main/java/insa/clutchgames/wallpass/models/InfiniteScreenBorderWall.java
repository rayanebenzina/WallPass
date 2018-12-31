package insa.clutchgames.wallpass.models;

import android.graphics.Canvas;
import android.graphics.Color;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

public class InfiniteScreenBorderWall extends GameObject {
    private Rectangle r1,r2;
    InfiniteScreenBorderWall(GameWorld w,Viewport viewport)
    {
        super(w,BodyType.STATIC,new Vec2(),new Vec2(),0,2,Color.BLUE);
        r1 = new Rectangle(body,viewport,new Vec2(100*w.ratio*1000,5),new Vec2(-47.5f,0),90);
        r2 = new Rectangle(body,viewport,new Vec2(100*w.ratio*1000,5),new Vec2(47.5f,0),90);
        r1.setFilter(0x0002,0x0001);
        r2.setFilter(0x0002,0x0001);
    }
    @Override
    public void draw(Canvas canvas) {
        r1.draw(canvas,paint);
        r2.draw(canvas,paint);

    }
}
