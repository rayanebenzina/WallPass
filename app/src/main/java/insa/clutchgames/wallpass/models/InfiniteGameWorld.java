package insa.clutchgames.wallpass.models;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.Vector;

public class InfiniteGameWorld extends GameWorld {

    private InfiniteScreenBorderWall mur;
    private Vector<SimpleRoundedWall> murs;
    private double highscore = 0, score;

    public InfiniteGameWorld()
    {
    }
    @Override
    public void init(float width, float height)
    {
        score = 0;
        murs = new Vector<>();
        super.init(width,height);
        balle = new Balle(this, viewport,-30,30,0,-50,5);
        mur = new InfiniteScreenBorderWall(this,viewport);
        murs.add(new SimpleRoundedWall(this,viewport,-30,-100,-45,20,3));
        murs.add(new SimpleRoundedWall(this,viewport,30,-85,-45,20,3));
        murs.add(new SimpleRoundedWall(this,viewport,-30,-285,45,20,3));
        murs.add(new SimpleRoundedWall(this,viewport,30,-300,45,20,3));
        murs.add(new SimpleRoundedWall(this,viewport,-30,-500,-45,20,3));
        MyContactListener contactListener = new MyContactListener();
        this.setContactListener(contactListener);
    }
    public void step()
    {
        if (screen != null && continuer)
        {
            super.step(dt, 6, 3);
            counter++;
        }
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

        if(counter == 0)continuer=true;
        else super.onClick();
    }
}
