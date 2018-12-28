package insa.clutchgames.wallpass.views;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import insa.clutchgames.wallpass.models.GameWorld;
import insa.clutchgames.wallpass.threads.GameLoopThread;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameLoopThread gameThread;
    private GameWorld w;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThread=new GameLoopThread(this);
        w = new GameWorld();
    }

    public void update()
    {
        w.step();
    }



    public void doDraw(Canvas c)
    {
        w.draw(c);

    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        w.init(getWidth(),getHeight());
        gameThread.setRunning(true);
        gameThread.start();


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        w.screen = null;
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
        this.w.screen.set(w,h);
    }
}