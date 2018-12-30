package insa.clutchgames.wallpass.models;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Filter;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

public class Rectangle implements DrawableShape,CollidableShape{
    private Body body;
    private Viewport viewport;
    private Vec2 pos = new Vec2();
    private Vec2 halfBoxSize;
    private float angle;
    private RectF screenRect = new RectF();
    private Vec2 tmp = new Vec2();
    public Fixture f;

    public Rectangle(Body body, Viewport viewport, Vec2 size, Vec2 pos, float angle)
    {
        this.body = body;
        this.viewport = viewport;
        this.halfBoxSize = new Vec2(size.x/2,size.y/2);
        this.pos.set(pos.x,pos.y); // Relative to body position
        this.angle = angle;//relative to body position


        initPhysics();
        initGraphics();
    }
    public Rectangle(Body body, Viewport viewport,float width,float height)
    {
        this(body,viewport,new Vec2(width,height),new Vec2(),0);
    }
    public void initPhysics()
    {
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(halfBoxSize.x-halfBoxSize.y,halfBoxSize.y,pos,(float)Math.toRadians(angle));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ps;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 1;
        fixtureDef.userData = body.getUserData();
        f = body.createFixture(fixtureDef);
    }

    @Override
    public void setFilter(int type, int mask) {
        Filter filter = new Filter();
        filter.categoryBits = type;
        filter.maskBits = mask;
        f.setFilterData(filter);
    }

    public void initGraphics()
    {
        Vec2 tmp = new Vec2();
        viewport.getWorldVectorToScreen(halfBoxSize,tmp);
        this.screenRect.set(-tmp.x,-tmp.y, tmp.x, tmp.y);
        synchronize();
    }

    public void synchronize()
    {
        viewport.getWorldToScreen(body.getPosition().add(pos),tmp);
    }
    public void draw(Canvas canvas, Paint paint)
    {
        synchronize();
        canvas.save();
        canvas.translate(tmp.x,tmp.y);
        canvas.rotate((float)Math.toDegrees(body.getAngle())+angle,0,0);

        canvas.drawRect(screenRect,paint);
        canvas.restore();
    }
}
