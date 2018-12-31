package insa.clutchgames.wallpass.views;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import insa.clutchgames.wallpass.models.GameWorld;
import insa.clutchgames.wallpass.models.InfiniteGameWorld;
import insa.clutchgames.wallpass.models.LevelGameWorld;
import insa.clutchgames.wallpass.threads.GameLoopThread;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameLoopThread gameThread;
    private GameWorld w;

    public GameView(Context context,int mode) {
        super(context);
        getHolder().addCallback(this);
        gameThread=new GameLoopThread(this);
        if(mode==1) w = new InfiniteGameWorld();
        else if(mode==2) w = new LevelGameWorld(context);
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
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            w.onClick();
        }
        return super.onTouchEvent(event);
    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        w.init(getWidth(),getHeight());
        gameThread.setRunning(true);
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(gameThread, 0, GameLoopThread.SKIP_TICKS, TimeUnit.MILLISECONDS);
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