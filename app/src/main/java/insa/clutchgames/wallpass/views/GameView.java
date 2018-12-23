package insa.clutchgames.wallpass.views;

import android.content.Context;
import android.view.SurfaceView;


public class GameView extends SurfaceView implements Runnable {

    volatile boolean running;

    private Thread gameThread = null;

    public GameView(Context context) {
        super(context);

    }

    @Override
    public void run() {
        while (running) {
            update();
            draw();
            control();
        }
    }


    private void update() {

    }

    private void draw() {

    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        //when the game is resumed
        //starting the thread again
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}