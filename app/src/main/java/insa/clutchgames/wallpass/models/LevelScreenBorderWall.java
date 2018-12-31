package insa.clutchgames.wallpass.models;

import android.graphics.Canvas;
import android.graphics.Color;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import java.util.Vector;

public class LevelScreenBorderWall extends GameObject {
    private Vector<Rectangle> r = new Vector<>();
    private Viewport viewport;
    private final float STRENGHT = 5;

    LevelScreenBorderWall(GameWorld w,Viewport viewport,Vec2 posCenter,int numSortie)
    {
        super(w,BodyType.STATIC,new Vec2(),new Vec2(),0,0x0002,Color.BLUE);
        this.viewport = viewport;

        // 1 = HAUT
        // 2 = BAS
        // 3 = GAUCHE
        // 4 = DROITE

        Vec2 size = new Vec2();
        Vec2 pos = new Vec2();
        float angle;
        for(int i = 1; i<=4; i++)
        {

            size.set(100 * (i > 2 ? w.ratio : 1), STRENGHT);
            pos.set((i > 2 ? 47.5f : 0) * (i == 3 ? -1 : 1),
                    (i < 3 ? 48.6f : 0) * (i == 1 ? -1 : 1)*w.ratio);
            angle = i > 2 ? 90 : 0;
            if(i != numSortie)
            {
                addRectangle(size.x,pos.add(posCenter),angle);
            }
            else
            {

                float LENGHT_HOLE = 30;
                float sizetmp = size.x / 2 - LENGHT_HOLE /2;
                Vec2 tmpos = new Vec2();
                tmpos.x = (pos.x==0)? - size.x/2 + sizetmp /2:pos.x;
                tmpos.y = (pos.y==0)? - size.x/2 + sizetmp /2:pos.y;
                addRectangle(sizetmp,tmpos.add(posCenter),angle);
                tmpos.x = (pos.x==0)? + size.x/2 - sizetmp /2:pos.x;
                tmpos.y = (pos.y==0)? + size.x/2 - sizetmp /2:pos.y;
                addRectangle(sizetmp,tmpos.add(posCenter),angle);
            }
            r.get(i-1).setFilter(0x0002,0x0001);
        }
        r.get(4).setFilter(0x0002,0x0001);
    }
    private void addRectangle(float size, Vec2 pos, float angle)
    {
        r.add(new Rectangle(body,viewport,new Vec2(size,STRENGHT),pos,angle));
    }
    @Override
    public void draw(Canvas canvas)
    {
        for (Rectangle rect: r)
        {
            rect.draw(canvas,paint);
        }
    }
}
