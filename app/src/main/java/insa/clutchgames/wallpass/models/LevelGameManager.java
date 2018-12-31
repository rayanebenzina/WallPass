package insa.clutchgames.wallpass.models;

import android.content.Context;
import android.graphics.Canvas;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class LevelGameManager {
    private LevelXmlPullParser parser;
    Level actualLevel = new Level();
    private boolean finished = false;

    LevelGameManager(Context context,GameWorld world, Viewport viewport)
    {
        parser = new LevelXmlPullParser(context,world,viewport);
    }

   boolean levelFinished() {
        finished = actualLevel.isOutOfBound();
        return finished;
    }
    void startGame()
    {
        //finished = false;
        getNextLevel(actualLevel);
        //getNextLevel(nextLevel);
        //nextLevel.setActive(false);
    }
    void changeLevel()
    {
        if(!parser.noMoreLevels)
        {
            getNextLevel(actualLevel);
            //actualLevel = nextLevel;
            //actualLevel.setActive(true);
            //getNextLevel(nextLevel);
            finished=false;
        }

    }
    public void draw(Canvas c)
    {
        actualLevel.drawLevel(c);
        //if(finished) nextLevel.drawLevel(c);
    }

    private void getNextLevel(Level level)
    {
        try {
            parser.getNextLevel(level);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
