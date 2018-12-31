package insa.clutchgames.wallpass.models;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

public class LevelGameWorld extends GameWorld {

    private Context context;
    private LevelGameManager lm;

    public LevelGameWorld(Context context)
    {
        super();
        this.context=context;

    }
    @Override
    public void init(float width, float height)
    {
        super.init(width,height);
        lm = new LevelGameManager(context,this,viewport);
        lm.startGame();
        balle=lm.actualLevel.balle;
        MyContactListener contactListener = new MyContactListener();
        this.setContactListener(contactListener);
        continuer = true;


    }
    public void step()
    {
        if(lm.levelFinished())
        {
            lm.changeLevel();
            balle = lm.actualLevel.balle;
            viewport.setCenter(lm.actualLevel.center);
            continuer=true;
        }
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
            lm.draw(c);
        }

    }

    @Override
    public void onClick() {
        super.onClick();
    }

}
