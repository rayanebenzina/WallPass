package insa.clutchgames.wallpass.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Vector;

import insa.clutchgames.wallpass.models.Balle;
import insa.clutchgames.wallpass.models.CollideTools;
import insa.clutchgames.wallpass.models.Mur;
import insa.clutchgames.wallpass.threads.GameLoopThread;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameLoopThread gameThread;
    private Balle balle;
    private Mur mur, mur2;
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

        balle = new Balle(100,100,15 , 15,60,300,300);
        mur = new Mur(300,300,87,200);
        mur2 = new Mur(600,300,10,350, 70);

        murs = new Vector<>();
        murs.add(mur);
        murs.add(mur2);

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

        for (Mur m :murs) {
            if(CollideTools.Circle_OBB(balle,m,m.getAngle()))
            {
                balle.setColliding(true);
                if(!balle.isTransparent()){
                    m.setColor(Color.argb(50,155,15,0));
                    PointF A = new PointF();
                    PointF B = new PointF();
                    PointF C =  new PointF(balle.x,balle.y);
                    C = CollideTools.rotate(m.getCenter(),C,-m.getAngle());
                    RectF box = balle.getRotatedBox(m.centerX(), m.centerY(), m.getAngle());
                    RectF oldBox = balle.getRotatedOldBox(m.centerX(), m.centerY(), m.getAngle());
                    boolean getNewVect = false;
                    if(balle.collidedFromLeft(box, oldBox,m)){
                        A.y = m.bottom;
                        B.y = m.top;
                        A.x = B.x = m.left;
                        getNewVect = true;
                        System.out.println("LEFT");
                    }
                    else if(balle.collidedFromTop(box, oldBox, m)){
                        A.x = m.left;
                        B.x = m.right;
                        A.y = B.y = m.top;
                        System.out.println("TOP");
                        getNewVect = true;
                    }
                    else if(balle.collidedFromRight(box, oldBox, m)){
                        A.y = m.top;
                        B.y = m.bottom;
                        A.x = B.x = m.right;
                        System.out.println("RIGHT");
                        getNewVect = true;
                    }
                    else if(balle.collidedFromBottom(box, oldBox, m)){
                        A.x = m.right;
                        B.x = m.left;
                        A.y = B.y = m.bottom;
                        System.out.println("BOTTOM");
                        getNewVect = true;
                    }
                    else {
                        /**
                         *
                         *
                         * NORMALEMENT ON NE DOIT JAMAIS ENTRER DANS CE ELSE
                         *
                         *
                         * MAIS CA ARRIVE :-(
                         * quand ca arrive je fais exist
                         */
                        System.out.println("Aucun côte");
                        System.exit(-1);
                    }

                    if(getNewVect) {
                        A = CollideTools.rotate(m.getCenter(), A, m.getAngle());
                        B = CollideTools.rotate(m.getCenter(), B, m.getAngle());
                        C = CollideTools.rotate(m.getCenter(), C, m.getAngle());
                        PointF normale = CollideTools.getNormale(A, B, C);
                        System.out.println("Normale [" + normale + "]");
                        PointF v2 = CollideTools.calculerVecteurV2(new PointF(balle.vx, balle.vy), normale);
                        System.out.println("V = ( x = [" + balle.vx + "] y= [" + balle.vy + "] )");
                        balle.vx = v2.x;
                        balle.vy = v2.y;
                        System.out.println("V2 = ( x = [" + v2.x + "] y= [" + v2.y + "] )");
                        System.out.println("New direction");
                    }
                }
            }
            else {
                balle.setColliding(false);
                if (!balle.isTransparent()){
                    m.setColor(Color.argb(255, 255, 115, 0));
                }
            }
        }
    }

    public void doDraw(Canvas c)
    {
        c.drawColor(Color.argb(255,255,230,204));
        balle.draw(c);
        mur.draw(c);
        mur2.draw(c);
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
            catch (InterruptedException e) {}
        }
    }
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h)
    {
        balle.resize(w, h);
    }
}