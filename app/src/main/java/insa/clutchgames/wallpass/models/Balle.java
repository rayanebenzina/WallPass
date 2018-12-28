package insa.clutchgames.wallpass.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class Balle
{
    private Body body;
    private Paint paint;
    private float radius;
    private GameWorld w;

    public Balle(GameWorld world,float x, float y, float vx, float vy, float radius)
    {
        w = world;
        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(x/w.ratio,y);
        body = world.createBody(bd);
        CircleShape circle = new CircleShape();
        circle.m_radius=radius;
        this.radius= radius;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 1;
        body.createFixture(fixtureDef);
        body.setLinearVelocity(new Vec2(vx,vy));
        paint = new Paint();
        paint.setColor(Color.argb(255,255,115,35));
    }

    public void draw(Canvas canvas)
    {
        Vec2 n = w.worldToScreen(body.getPosition());
        canvas.drawCircle(n.x,n.y,w.radiusToScreen(radius),paint);
    }
}