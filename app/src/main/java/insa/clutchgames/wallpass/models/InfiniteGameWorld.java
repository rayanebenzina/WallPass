package insa.clutchgames.wallpass.models;

import android.graphics.Canvas;
import android.graphics.Color;
import org.jbox2d.common.Mat22;
import org.jbox2d.common.Vec2;

import java.util.Vector;

public class InfiniteGameWorld extends GameWorld {

    Viewport viewport;
    protected Balle balle;
    private InfiniteScreenBorderWall mur;
    private Vector<SimpleRoundedWall> murs = new Vector();
    private double highscore = 0, score=0;
    private MyContactListener contactListener;

    public InfiniteGameWorld()
    {
        super();
    }
    @Override
    public void init(float width, float height)
    {
        screen = new Vec2(width,height);
        ratio = height/width;
        viewport = new Viewport();
        viewport.setExtents(width/2,height/2);
        viewport.setCenter(0,0);
        viewport.setTransform(new Mat22(width/100,0,0,width/100));
        balle = new Balle(this, viewport,-30,30,0,-50,5);
        mur = new InfiniteScreenBorderWall(this,viewport);
        murs.add(new SimpleRoundedWall(this,viewport,-30,-100,-45,20,3));
        murs.add(new SimpleRoundedWall(this,viewport,30,-85,-45,20,3));
        murs.add(new SimpleRoundedWall(this,viewport,-30,-285,45,20,3));
        murs.add(new SimpleRoundedWall(this,viewport,30,-300,45,20,3));
        murs.add(new SimpleRoundedWall(this,viewport,-30,-500,-45,20,3));
    }
    public void step()
    {
        if(screen != null)
            super.step(dt,6,3);
        score = - balle.getPosition().y;
        if(score > highscore)
        {
            highscore = score;
            viewport.setCenter(0,(float)-highscore);
        }

    }

    public void draw(Canvas c)
    {
        if(screen!=null)
        {
            c.drawColor(Color.argb(255,253,224,195));
            balle.draw(c);
            mur.draw(c);
            for(SimpleRoundedWall mur: murs)mur.draw(c);
        }

    }

    @Override
    public void onClick() {
    }
}
