package insa.clutchgames.wallpass.models;

import android.graphics.Canvas;
import android.graphics.Color;
import java.util.Vector;

public class LevelGameWorld extends GameWorld {

    private LevelScreenBorderWall mur;
    private Vector<SimpleRoundedWall> murs = new Vector();

    public LevelGameWorld()
    {
        super();
    }
    @Override
    public void init(float width, float height)
    {
        super.init(width,height);
        balle = new Balle(this, viewport,0,30,0,-50,5);
        mur = new LevelScreenBorderWall(this,viewport,2);
        murs.add( new SimpleRoundedWall(this,viewport,33.3f,33.3f*ratio,-45,20,3) );
        murs.add( new SimpleRoundedWall(this,viewport,-33.3f,33.3f*ratio,45,20,3) );


        murs.add( new SimpleRoundedWall(this,viewport,0,0,45,20,3) );
        murs.add( new SimpleRoundedWall(this,viewport,33.3f,0,45,20,3) );
        murs.add( new SimpleRoundedWall(this,viewport,-33.3f,0,-45,20,3) );

        murs.add( new SimpleRoundedWall(this,viewport,0,-33.3f*ratio,-45,20,3) );
        murs.add( new SimpleRoundedWall(this,viewport,-33.3f,-33.3f*ratio,45,20,3) );
        murs.add( new SimpleRoundedWall(this,viewport,33.3f,-33.3f*ratio,45,20,3) );
        MyContactListener contactListener = new MyContactListener();
        this.setContactListener(contactListener);
        continuer = true;
    }
    public void step()
    {
        if(screen != null && continuer)
        {
            super.step(dt,6,3);
            counter++;
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
        super.onClick();
    }
}
