package insa.clutchgames.wallpass.threads;

import android.graphics.Canvas;
import android.os.Handler;


import insa.clutchgames.wallpass.views.GameView;

public class GameLoopThread extends Thread
{
  //  private final static int FRAMES_PER_SECOND = 60;
  //  public final static int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;

    private final GameView view;
    private boolean running = false;

    double interpolation = 0;
    final int TICKS_PER_SECOND = 60;
    final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
    final int MAX_FRAMESKIP = 5;

    public GameLoopThread(GameView view)
    {
        this.view = view;
    }

    public void setRunning(boolean run) {
        running = run;
    }




    @Override
    public void run() {
        double next_game_tick = System.currentTimeMillis();
        int loops;

        while (true) {
            loops = 0;
            while (System.currentTimeMillis() > next_game_tick
                    && loops < MAX_FRAMESKIP) {

                synchronized (view.getHolder()) {
                    view.update();
                }

                next_game_tick += SKIP_TICKS;
                loops++;
            }

            interpolation = (System.currentTimeMillis() + SKIP_TICKS - next_game_tick)
                    / (double) SKIP_TICKS;

            Canvas c = null;
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.doDraw(c, interpolation);
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
        }
    }

}