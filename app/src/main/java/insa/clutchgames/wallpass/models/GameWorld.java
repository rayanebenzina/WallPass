package insa.clutchgames.wallpass.models;

import android.graphics.Canvas;
import android.os.Handler;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Mat22;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;


public abstract class GameWorld extends World {

    protected Viewport viewport;
    public int nbMursTouches = 0;
    protected float dt = 1f/60f;
    public float ratio;
    public Vec2 screen;
    private  final Handler handler = new Handler();
    private Runnable r ;
    protected Balle balle;
    boolean continuer = true;
    protected long counter = 0;

    public GameWorld()
    {
        super(new Vec2(0,0),true);
    }
    public void init(float width, float height)
    {
        screen = new Vec2(width,height);
        ratio = height/width;
        viewport = new Viewport();
        viewport.setExtents(width/2,height/2);
        viewport.setCenter(0,0);
        viewport.setTransform(new Mat22(width/100,0,0,width/100));
        balle = new Balle(this,viewport, 50,90,0,-40,3);
        r = new Runnable() {@Override public void run() {if(nbMursTouches == 0){balle.setSensor(false);}}};
    }

    public abstract void step();

    public abstract void draw(Canvas c);

    public class MyContactListener implements ContactListener
    {
        private final int BALLE = 0x0001, LIMITE = 0x0002, MUR = 0x0004;

        @Override
        public void preSolve(Contact contact, Manifold manifold) {
        }
        @Override
        public void postSolve(Contact contact, ContactImpulse contactImpulse) {
        }

        @Override
        public void beginContact(Contact contact) {
            int A = (int) contact.getFixtureA().getUserData();
            int B = (int) contact.getFixtureB().getUserData();

            if((A == LIMITE && B == BALLE)|| (A==BALLE && B== LIMITE))
            {
                if(counter>20)
                    continuer=false;
                System.out.println(counter);
            }
            if((A == MUR && B == BALLE) || ( A == BALLE && B == MUR))
            {
                nbMursTouches++;
            }

        }
        @Override
        public void endContact(Contact contact) {
            int A = (int) contact.getFixtureA().getUserData();
            int B = (int) contact.getFixtureB().getUserData();
            if( (A == LIMITE && B == BALLE) || (A==BALLE && B== LIMITE))
            {

            }
            else if((A == MUR && B == BALLE) || ( A == BALLE && B == MUR))
            {
                nbMursTouches--;
                if(nbMursTouches==0) balle.setSensor(false);
            }
        }
    }

    public void onClick()
    {
        if(balle.isSensor())
        {
            if(nbMursTouches==0)balle.setSensor(false);
        }
        else
        {
            balle.setSensor(true);
            handler.removeCallbacks(r);
            handler.postDelayed(r,600);
        }
    }
}
