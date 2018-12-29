package insa.clutchgames.wallpass.threads;

import android.graphics.Canvas;
import android.os.Handler;


import insa.clutchgames.wallpass.views.GameView;

public class GameLoopThread extends Thread
{
    private final static int FRAMES_PER_SECOND = 60;
    private final static int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;

    private final GameView view;
    private boolean running = false;


    public GameLoopThread(GameView view)
    {
        this.view = view;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run()
    {
        long startTime;
        long sleepTime;



        while (running)
        {
            startTime = System.currentTimeMillis();
            synchronized (view.getHolder()) {view.update();}

            Canvas c = null;
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {view.doDraw(c);}
            }
            finally
            {
                if (c != null) {view.getHolder().unlockCanvasAndPost(c);}
            }

            sleepTime = SKIP_TICKS-(System.currentTimeMillis() - startTime);
            try {
                if (sleepTime >= 0) {sleep(sleepTime);}
            }
            catch (Exception ignored) {}
        }
    }

}