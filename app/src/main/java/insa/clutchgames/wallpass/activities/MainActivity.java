package insa.clutchgames.wallpass.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import insa.clutchgames.wallpass.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //image button
    private ImageButton buttonPlay;
    private LinearLayout menu;
    private ViewGroup vg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting the button

        buttonPlay = findViewById(R.id.buttonPlay);
        ImageButton level = findViewById(R.id.buttonlevelmode);
        ImageButton infinite = findViewById(R.id.buttoninfinitemode);
        menu = findViewById(R.id.choixmode);
        vg = findViewById(R.id.buttons);

        //adding a click listener
        buttonPlay.setOnClickListener(this);
        level.setOnClickListener(this);
        infinite.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.buttonPlay:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(vg);
                }
                buttonPlay.setVisibility(View.INVISIBLE);
                menu.setVisibility(View.VISIBLE);
                break;
            case R.id.buttonlevelmode:
                Intent i1 = new Intent(this,GameActivity.class);
                i1.putExtra("GAME_MODE",2);
                startActivity(i1);
                break;
            case R.id.buttoninfinitemode:
                Intent i2 = new Intent(this,GameActivity.class);
                i2.putExtra("GAME_MODE",1);
                startActivity(i2);
                break;
            default:
                break;

        }


        //starting game activity

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}