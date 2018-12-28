package insa.clutchgames.wallpass.models;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Mur
{
    private float angle,width,height;
    private Vec2 p;
    private  GameWorld world;
    private Paint paint;
    private Body body;

    public Mur(GameWorld w, float x, float y,float angle, float width)
    {
        this(w,x,y,angle,width,1);
    }

    public Mur(GameWorld w,float x, float y, float angle, float width, float height)
    {
        this.world = w;
        this.angle = angle;
        this.width = width;
        this.height = height;
        this.paint = new Paint();
        this.p = new Vec2(x/w.ratio,y);
        this.paint.setColor(Color.argb(255,155,15,0));
        BodyDef bd = new BodyDef();
        bd.type = BodyType.STATIC;
        body = world.createBody(bd);
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(width/2,height/2,p,(float) (angle/180*Math.PI));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ps;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 1;
        body.createFixture(fixtureDef);
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
        //canvas.drawCircle( sP.x,sP.y+sS.y/2,sS.y/2,paint);
        //canvas.drawCircle(sP.x+sS.x,sP.y+sS.y/2,sS.y/2,paint);
        canvas.drawRect(sP.x - sS.x/2,sP.y-sS.y/2,sP.x+sS.x/2,sP.y+ sS.y/2, paint);
        canvas.restore();
    }
}