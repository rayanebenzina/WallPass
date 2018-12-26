package insa.clutchgames.wallpass.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import insa.clutchgames.wallpass.models.Balle;
import insa.clutchgames.wallpass.models.CollideTools;
import insa.clutchgames.wallpass.models.Mur;
import insa.clutchgames.wallpass.threads.GameLoopThread;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameLoopThread gameThread;
    private Balle balle;
    private Mur mur, mur2;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThread=new GameLoopThread(this);

        balle = new Balle(100,100,5 , 5,60,300,300);
        mur = new Mur(300,300,87,200);
        mur2 = new Mur(600,300,10,350, 70);

    }

    public void update()
    {
        balle.moveWithCollisionDetection();

        if(CollideTools.Circle_OBB(balle,mur,mur.getAngle()))
        {
            mur.setColor(Color.argb(50,155,15,0));
        }
        else
            mur.setColor(Color.argb(255,255,115,0));

        if (CollideTools.Circle_OBB(balle,mur2,mur2.getAngle()))
        {
            mur2.setColor(Color.argb(50,155,15,0));
        }
        else
            mur2.setColor(Color.argb(255,255,115,0));
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