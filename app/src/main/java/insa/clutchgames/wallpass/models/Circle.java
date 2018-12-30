package insa.clutchgames.wallpass.models;

import android.graphics.Canvas;
import android.graphics.Paint;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Filter;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;


public class Circle implements CollidableShape, DrawableShape{

    private Body body;
    private Viewport viewport;
    private Vec2 pos;
    private Vec2 tmp=new Vec2();
    private Vec2 tmp1 = new Vec2();
    private float radius,screenRadius;
    public Fixture f;

    public Circle(Body body, Viewport viewport, Vec2 pos, float radius)
    {
        this.body = body;
        this.viewport = viewport;
        this.radius = radius;
        this.pos = pos; // Relative to body position
        initPhysics();
        initGraphics();
    }
    public Circle(Body body, Viewport viewport, float x, float y, float radius)
    {
        this(body,viewport,new Vec2(x,y),radius);
    }
    public void initPhysics()
    {
        CircleShape cs = new CircleShape();
        cs.m_p.x = pos.x;
        cs.m_p.y = pos.y;
        cs.m_radius = radius;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = cs;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 1;
        fixtureDef.userData = body.getUserData();
        f = body.createFixture(fixtureDef);
    }
    public void initGraphics()
    {
        viewport.getWorldVectorToScreen(new Vec2(radius,radius),tmp);
        screenRadius = tmp.x;
        synchronize();

    }
    @Override
    public void setFilter(int type, int mask) {
        Filter filter = new Filter();
        filter.categoryBits = type;
        filter.maskBits = mask;
        f.setFilterData(filter);
    }
    public void synchronize()
    {
        viewport.getWorldToScreen(body.getPosition(),tmp);
        viewport.getWorldVectorToScreen(pos,tmp1);
    }
    public void draw(Canvas canvas, Paint paint)
    {
        synchronize();
        canvas.save();
        canvas.translate(tmp.x,tmp.y);
        canvas.rotate((float)Math.toDegrees(body.getAngle()),0,0);
        canvas.translate(tmp1.x,tmp1.y);
        canvas.drawCircle(0,0,screenRadius,paint);
        canvas.restore();
    }
}
