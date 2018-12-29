package insa.clutchgames.wallpass.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import java.util.Vector;

public class GameWorld extends World {

    private Balle balle;
    public int nb = 0,nb2 =0;
    public final int FBALLE = 0x0001, FLIMITES = 0x0002, FMURS = 0x0004;
    double t;
    private float dt = 1f/60f;
    public float ratio;
    private Vector<Mur> murs = new Vector<>();
    public Vec2 screen;
    private  final Handler handler = new Handler();
    private  boolean ishandleractive = false;
    private Runnable r = new Runnable() {
        @Override
        public void run() {
            if(nb == 0){
                balle.f.setSensor(false);
                balle.paint.setColor(Color.argb(255,255,115,35));
            }

        }
    };


    public GameWorld()
    {
        super(new Vec2(0,0),true);
    }
    public void init(float width, float height)
    {
        screen = new Vec2(width,height);
        ratio = height/width;
        balle = new Balle(this, 50,90,0,-40,3,FBALLE,FLIMITES | FMURS);

        murs.add(new Mur(this,50,99.5f,0,99,FLIMITES,FBALLE));
        murs.add(new Mur(this,50,0.5f,0,99,FLIMITES,FBALLE));
        murs.add(new Mur(this,0.5f,50,90,99,FLIMITES,FBALLE));
        murs.add(new Mur(this,99.5f,22f,90,44,FLIMITES,FBALLE));
        murs.add(new Mur(this,99.5f,78f,90,44,FLIMITES,FBALLE));

        murs.add(new Mur(this,50,55,45,30,3));
        murs.add(new Mur(this,50,20,0,30,3));
        this.setContactListener(new MyContactListener());

    }
    public void step()
    {
        if(screen != null)
            super.step(dt,6,3);

    }

    public void draw(Canvas c, double interpolation)
    {
        if(screen!=null)
        {
            c.drawColor(Color.argb(255,253,224,195));
            balle.draw(c, interpolation);
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

    public class MyContactListener implements ContactListener
    {

        @Override
        public void preSolve(Contact contact, Manifold manifold) {

        }
        @Override
        public void postSolve(Contact contact, ContactImpulse contactImpulse) {

        }

        @Override
        public void beginContact(Contact contact) {
            if(contact.getFixtureA().getBody().getUserData().equals(1) && contact.getFixtureB().getBody().getUserData().equals(2))
            {
                if(contact.getFixtureA().getFilterData().categoryBits == FLIMITES)
                {
                    nb2++;
                    contact.getFixtureB().setSensor(false);

                    balle.paint.setColor(Color.argb(255,255,115,35));
                    contact.update(this);
                }
                if(contact.getFixtureA().getFilterData().categoryBits == FMURS)
                {
                    nb++;
                }
            }
            if(contact.getFixtureA().getBody().getUserData().equals(2) && contact.getFixtureB().getBody().getUserData().equals(1))
            {
                if(contact.getFixtureB().getFilterData().categoryBits == FLIMITES)
                {
                    nb2++;
                    contact.getFixtureA().setSensor(false);

                    balle.paint.setColor(Color.argb(255,255,115,35));
                    contact.update(this);
                }
                if(contact.getFixtureB().getFilterData().categoryBits == FMURS)
                {
                    nb++;
                }
            }

        }
        @Override
        public void endContact(Contact contact) {
            if(contact.getFixtureA().getBody().getUserData().equals(2) && contact.getFixtureB().getBody().getUserData().equals(1))
            {
                if(contact.getFixtureB().getFilterData().categoryBits == FLIMITES)
                {
                    nb2--;
                }
                if(contact.getFixtureB().getFilterData().categoryBits == FMURS)
                {
                    nb--;
                    if(nb==0)
                    {
                        contact.getFixtureA().setSensor(false);
                        balle.paint.setColor(Color.argb(255,255,115,35));
                    }

                }
            }
            if(contact.getFixtureA().getBody().getUserData().equals(1) && contact.getFixtureB().getBody().getUserData().equals(2))
            {
                if(contact.getFixtureA().getFilterData().categoryBits == FLIMITES)
                {
                    nb2--;
                }
                if(contact.getFixtureA().getFilterData().categoryBits == FMURS)
                {
                    nb--;
                    if(nb==0)
                    {
                        contact.getFixtureB().setSensor(false);
                        balle.paint.setColor(Color.argb(255, 255, 115, 35));
                    }
                }
            }

        }
    }

    public void onClick()
    {
        if(balle.f.isSensor() && nb == 0)
        {
            balle.f.setSensor(false);
            balle.paint.setColor(Color.argb(255,255,115,35));
        }
        else
        {
            if(nb2==0) balle.f.setSensor(true);
            handler.removeCallbacks(r);
            handler.postDelayed(r,300);
            balle.paint.setColor(Color.argb(155,255,115,35));
        }
    }
}
