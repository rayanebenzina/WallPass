package insa.clutchgames.wallpass.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import insa.clutchgames.wallpass.models.Balle;
import insa.clutchgames.wallpass.threads.GameLoopThread;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    volatile boolean running;
    private Balle balle;
    private GameLoopThread gameThread = null;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    public GameView(Context context) {
        super(context);
        balle = new Balle(15, 13, 0 , 0, 5, this.getHeight(),this.getWidth());

        surfaceHolder = getHolder();
        paint = new Paint();

    }


    public void update() {
        balle.moveWithCollisionDetection();
    }

    public void doDraw(Canvas c) {

            c.drawColor(Color.argb(255,255,128,0));
            c.drawCircle(balle.getX(),balle.getY(), balle.getRayon(),paint);
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
        if(gameThread.getState()==Thread.State.TERMINATED) {
            gameThread=new GameLoopThread(this);
        }
        gameThread.setRunning(true);
        gameThread.start();
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
        balle.resize(w,h); // on définit la taille de la balle selon la taille de l'écran
    }
}