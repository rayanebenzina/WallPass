package insa.clutchgames.wallpass.models;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Mat22;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

public class Mur
{
    private float angle,width,height;
    private Vec2 p;
    private  GameWorld world;
    public Paint paint;
    private Body body;
    public int mask;

    public Mur(GameWorld w, float x, float y,float angle, float width)
    {
        this(w,x,y,angle,width,w.FMURS,w.FBALLE,3);
    }
    public Mur(GameWorld w, float x, float y,float angle, float width,float height)
    {
        this(w,x,y,angle,width,w.FMURS,w.FBALLE,height);
    }
    public Mur(GameWorld w, float x, float y,float angle, float width, int  categoryBits, int maskBits)
    {
        this(w,x,y,angle,width,categoryBits,maskBits,3);
    }
    public Mur(GameWorld w,float x, float y, float angle, float width, int  categoryBits, int maskBits, float height)
    {
        this.world = w;
        this.angle = angle;
        this.width = width;
        this.height = height;
        this.paint = new Paint();
        this.mask = categoryBits;
        this.p = new Vec2(x/w.ratio,y);
        this.paint.setColor(Color.argb(255,255,115,35));


        BodyDef bd = new BodyDef();
        bd.type = BodyType.STATIC;
        body = world.createBody(bd);
        body.setUserData(1);
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(width/2 - height/2,height/2,p,(float) (angle/180*Math.PI));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ps;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 1;
        fixtureDef.filter.categoryBits = categoryBits;
        fixtureDef.filter.maskBits = maskBits;
        body.createFixture(fixtureDef);


        Vec2 v = new Vec2(width/2 - height/2,0);
        rotate(v,angle);

        CircleShape c1 = new CircleShape();
        c1.m_radius=height/2;
        c1.m_p.x = x/w.ratio - v.x;
        c1.m_p.y = y - v.y;
        FixtureDef fd1 = new FixtureDef();
        fd1.shape = c1;
        fd1.friction = 0;
        fd1.restitution = 1;
        fd1.filter.categoryBits = categoryBits;
        fd1.filter.maskBits = maskBits;
        body.createFixture(fd1);

        c1 = new CircleShape();
        c1.m_radius=height/2;
        c1.m_p.x = x/w.ratio + v.x;
        c1.m_p.y = y + v.y;
        fd1 = new FixtureDef();
        fd1.shape = c1;
        fd1.friction = 0;
        fd1.restitution = 1;
        fd1.filter.categoryBits = categoryBits;
        fd1.filter.maskBits = maskBits;
        body.createFixture(fd1);
    }
    public void setColor(int color)
    {
        paint.setColor(color);
    }
    public void draw(Canvas canvas)
    {

        Vec2 sP = world.worldToScreen(p), sS = world.worldToScreen(new Vec2(width,height));
        canvas.save();
        canvas.rotate(angle,sP.x,sP.y);
        canvas.drawCircle( sP.x - sS.x/2 + sS.y/2,sP.y,sS.y/2,paint);
        canvas.drawCircle(sP.x+sS.x/2 - sS.y/2,sP.y,sS.y/2,paint);
        canvas.drawRect(sP.x - sS.x/2 + sS.y/2,sP.y-sS.y/2,sP.x+sS.x/2 - sS.y/2,sP.y+ sS.y/2, paint);
        canvas.restore();
    }
    public static Vec2 rotate(Vec2 center, Vec2 point, float angle)
    {
        Vec2 tmp = new Vec2();
        tmp.x = point.x - center.x;
        tmp.y = point.y - center.y;
        rotate(tmp,angle);
        tmp.add(center);
        return  tmp;
    }
    public static void rotate(Vec2 v, float angle)
    {
        double theta = angle * Math.PI / 180;
        double rx = v.x * Math.cos(theta) - v.y * Math.sin(theta);
        double ry = v.x * Math.sin(theta) + v.y * Math.cos(theta);
        v.x = ( float ) rx;
        v.y = ( float ) ry;
    }
}