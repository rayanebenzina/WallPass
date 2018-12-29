package insa.clutchgames.wallpass.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;


public class Balle
{
    private Body body;
    public Paint paint;
    private float radius;
    private GameWorld w;
    public Fixture f;

    public Balle(GameWorld world,float x, float y, float vx, float vy, float radius, int  categoryBits, int maskBits)
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
        fixtureDef.filter.categoryBits = categoryBits;
        fixtureDef.filter.maskBits = maskBits;
        f = body.createFixture(fixtureDef);
        body.setLinearVelocity(new Vec2(vx,vy));
        body.setUserData(2);
        paint = new Paint();
        paint.setColor(Color.argb(255,255,115,35));
        paint.setAntiAlias(true);
    }

    public void draw(Canvas canvas, double interpolation)
    {
        Vec2 n = w.worldToScreen(body.getPosition());
        Vec2 v = body.getLinearVelocity();
        interpolation = interpolation *0.09;
//        System.out.println("vecteur = [" + v + "], interpolation = [" + interpolation + "]");
        canvas.drawCircle((float) (n.x + interpolation * v.x), (float) (n.y + interpolation * v.y),w.radiusToScreen(radius),paint);
//        canvas.drawCircle(n.x,n.y,w.radiusToScreen(radius),paint);
    }
}