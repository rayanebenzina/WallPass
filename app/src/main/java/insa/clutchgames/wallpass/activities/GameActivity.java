package insa.clutchgames.wallpass.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import insa.clutchgames.wallpass.views.GameView;

public class GameActivity extends AppCompatActivity
{
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);

        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}
