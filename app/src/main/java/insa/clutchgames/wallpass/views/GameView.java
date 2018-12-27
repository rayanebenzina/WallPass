package insa.clutchgames.wallpass.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.Vector;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import insa.clutchgames.wallpass.models.Balle;
import insa.clutchgames.wallpass.models.CollideTools;
import insa.clutchgames.wallpass.models.Mur;
import insa.clutchgames.wallpass.models.Vector2D;
import insa.clutchgames.wallpass.threads.GameLoopThread;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameLoopThread gameThread;
    private Balle balle;
    private Vector<Mur> murs;
    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        public void run() {
            if(!balle.isColliding())
                balle.setTransparent(false);
            else {
                handler.postDelayed(this, 50);
            }
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThread=new GameLoopThread(this);
        murs = new Vector<Mur>();
        balle = new Balle(100,100,15 , 15,60,300,300);
        murs.add(new Mur(300,300,0,200));
        murs.add(new Mur(600,600,0,350));

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    if (!balle.isTransparent()){
                        handler.postDelayed(runnable, 400);
                        balle.setTransparent(true);
                    }
                }
                return false;
            }

        });

    }

    public void update()
    {
        balle.moveWithCollisionDetection();

        for (Mur m :murs)
        {
            Vector2D nP = CollideTools.Circle_OBB(balle, m, m.getAngle());

            if (nP != null)
            {
                balle.setColliding(true);
                if (!balle.isTransparent()) {
                    Vector2D N = Vector2D.sub(balle.p, nP);
                    N.normalize();
                    Vector2D v2 = CollideTools.calculerVecteurV2(balle.v, N);
                    balle.v.set(v2);
                }
            }
            else
            {
                balle.setColliding(false);
                if (!balle.isTransparent())
                {
                    m.setColor(Color.argb(255, 255, 115, 0));
                }
            }
        }
    }

    public void doDraw(Canvas c)
    {
        c.drawColor(Color.argb(255,255,230,204));
        balle.draw(c);
        for(Mur mur : murs ) mur.draw(c);
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        gameThread.setRunning(true);
        gameThread.start();
        balle.resize(getWidth(), getHeight());
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        gameThread.setRunning(false);
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            }
            catch (InterruptedException ignored) {}
        }
    }
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h)
    {
        balle.resize(w, h);
    }
}