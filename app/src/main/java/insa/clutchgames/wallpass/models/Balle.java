package insa.clutchgames.wallpass.models;

import android.graphics.Canvas;
import android.graphics.Color;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;


public class Balle extends GameObject {
    private Circle c;

    Balle(GameWorld world, Viewport viewport, float x, float y, float vx, float vy, float radius) {
        super(world, BodyType.DYNAMIC, new Vec2(x, y), new Vec2(vx, vy), 0, 0x0001, Color.argb(255, 255, 115, 35));
        c = new Circle(body, viewport, 0, 0, radius);
        c.setFilter(0x0001, 0x0002 | 0x0004);
    }

    public void draw(Canvas canvas)
    {
        c.draw(canvas,paint);
    }
    void setSensor(boolean sensor)
    {
        body.getFixtureList().setSensor(sensor);
        paint.setAlpha(sensor?155:255);
    }

    boolean isSensor()
    {
        return c.f.isSensor();
    }
}