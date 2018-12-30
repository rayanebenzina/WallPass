package insa.clutchgames.wallpass.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

public abstract class GameObject{

    Body body;
    Paint paint = new Paint();
    GameObject(GameWorld world, BodyType bodyType, Vec2 pos, Vec2 v,float angle, int type, int color)
    {
        paint.setColor(color);
        paint.setAntiAlias(true);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position = pos;
        bodyDef.linearVelocity = v;
        bodyDef.userData = type;
        bodyDef.angle = (float) Math.toRadians(angle);
        body = world.createBody(bodyDef);
    }
    public abstract void draw(Canvas canvas);

    public Vec2 getPosition()
    {
        return body.getPosition();
    }
}
