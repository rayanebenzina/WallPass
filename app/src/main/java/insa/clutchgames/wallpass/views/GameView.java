package insa.clutchgames.wallpass.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import android.widget.Toast;
import insa.clutchgames.wallpass.models.Balle;
import insa.clutchgames.wallpass.models.Mur;
import insa.clutchgames.wallpass.threads.GameLoopThread;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameLoopThread gameThread;
    private Balle balle;
    private Mur mur, mur2;
    private int pos = 0;
    private int pos2 = 0;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThread=new GameLoopThread(this);
        balle = new Balle(100,100,10 , 10,60,300,300);
        mur = new Mur(300,300,87,200);
        mur2 = new Mur(600,300,10,350, 70);

    }

    public void update() {
        balle.moveWithCollisionDetection();
        if(balle.collideWall(mur)){
            pos++;
            mur.getPaint().setColor(Color.argb(50,155,15,0));
        }
        else
            mur.getPaint().setColor(Color.argb(255,255,115,0));

        if (balle.collideWall(mur2)){

            pos2++;
            mur2.getPaint().setColor(Color.argb(50,155,15,0));
        }
        else
            mur2.getPaint().setColor(Color.argb(255,255,115,0));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void doDraw(Canvas c) {
        c.drawColor(Color.argb(255,255,230,204));
        balle.draw(c);
        mur.draw(c);
        mur2.draw(c);
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        gameThread.setRunning(true);
        gameThread.start();
        balle.setPosition(getWidth()/2, getHeight()/2);
        balle.resize(getWidth(),getHeight());
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
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {
    }
}