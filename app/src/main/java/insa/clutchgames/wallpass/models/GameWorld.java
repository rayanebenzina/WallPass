package insa.clutchgames.wallpass.models;

import android.graphics.Canvas;
import android.graphics.Color;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.Vector;

public class GameWorld extends World {

    private Balle balle;
    private float dt = 1f/60f;
    public float ratio;
    private Vector<Mur> murs = new Vector<>();
    public Vec2 screen;

    public GameWorld()
    {
        super(new Vec2(0,0),true);
    }
    public void init(float width, float height)
    {
        screen = new Vec2(width,height);
        ratio = height/width;
        balle = new Balle(this, 55,90,0,-40,3);

        murs.add(new Mur(this,50,99.5f,0,99));
        murs.add(new Mur(this,50,0.5f,0,99));
        murs.add(new Mur(this,0.5f,50,90,100));
        murs.add(new Mur(this,99.5f,50,90,100));

        murs.add(new Mur(this,75,25,90,30,3));
        murs.add(new Mur(this,50,50,90,30,3));
        murs.add(new Mur(this,25,75,90,30,3));
        murs.add(new Mur(this,25,25,90,30,3));
        murs.add(new Mur(this,75,75,90,30,3));
    }
    public void step()
    {
        if(screen != null)
            super.step(dt,6,3);
    }
    public void setScreen(Vec2 screen)
    {
        this.screen = screen;
    }
    public void draw(Canvas c)
    {
        if(screen!=null)
        {
            c.drawColor(Color.argb(255,253,224,195));
            balle.draw(c);
            for(Mur mur : murs) mur.draw(c);
        }

    }
    public Vec2 worldToScreen(Vec2 a)
    {
        return new Vec2(screen.y * a.x / 100, screen.y * a.y / 100);
    }

    public float radiusToScreen(float radius) {
        return screen.y * radius / 100;
    }
}
