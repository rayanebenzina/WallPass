package insa.clutchgames.wallpass.models;

import android.graphics.Canvas;
import android.graphics.RectF;
import org.jbox2d.common.Vec2;
import java.util.Vector;

public class Level {
    public Vec2 center = new Vec2();
    int level;
    int numSortie;
    int posx,posy;
    private RectF AABB;
    Balle balle;
    LevelScreenBorderWall border;
    boolean empty = true;
    Vector<SimpleRoundedWall> murs = new Vector<>();

    /*public void setActive(boolean flag)
    {
        for (SimpleRoundedWall mur : murs) {
            mur.body.setActive(flag);
        }
        balle.body.setActive(flag);
    }*/
    boolean isOutOfBound()
    {
        return !AABB.contains(balle.getPosition().x,balle.getPosition().y);
    }
    void calculs(float ratio)
    {
        AABB = new RectF(center.x-50,center.y-50*ratio,center.x+50,center.y+50*ratio);
    }
    void destroy(GameWorld w)
    {
        if(!empty)
        {
            for (SimpleRoundedWall mur : murs) {
                mur.destroy(w);
            }
            border.destroy(w);
            murs.clear();
            balle.destroy(w);
            empty = true;
        }
    }
    void drawLevel(Canvas c)
    {
        balle.draw(c);
        border.draw(c);
        for(SimpleRoundedWall mur: murs)mur.draw(c);
    }
}
