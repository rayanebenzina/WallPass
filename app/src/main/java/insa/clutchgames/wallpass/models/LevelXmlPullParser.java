package insa.clutchgames.wallpass.models;

import android.content.Context;
import android.content.res.XmlResourceParser;
import org.jbox2d.common.Vec2;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import insa.clutchgames.wallpass.R;

public class LevelXmlPullParser{

    private XmlResourceParser parser;
    private GameWorld world;
    private Viewport viewport;
    public boolean noMoreLevels = false;

    public LevelXmlPullParser(Context context, GameWorld world, Viewport viewport)
    {
        parser = context.getResources().getXml(R.xml.levels);
        this.world = world;
        this.viewport = viewport;
    }
    public void getNextLevel(Level level) throws XmlPullParserException, IOException
    {
        level.destroy(world);
        float longueur = 20,largeur = 3;
        boolean levelcharged = false;
        int eventType = parser.getEventType();
        while (!levelcharged && !noMoreLevels) {
            String name;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();

                    if (name.equals("level")) {
                        level.level = parser.getAttributeIntValue(0,4);
                        level.posx = parser.getAttributeIntValue(1,0);
                        level.posy = parser.getAttributeIntValue(2,0);
                        level.center.x = 100*level.posx;
                        level.center.y = 100*level.posy;
                        level.calculs(world.ratio);

                    } else if (level.empty) {
                        if (name.equals("balle")) {
                            Vec2 p = new Vec2(level.center.x + parser.getAttributeFloatValue(2,0f),level.center.y+parser.getAttributeFloatValue(3,0));
                            Vec2 v = new Vec2(parser.getAttributeFloatValue(0,0),parser.getAttributeFloatValue(1,0));
                            level.balle = new Balle(world,viewport,p.x,p.y,v.x,v.y,5);
                        } else if (name.equals("murs")) {
                            longueur = parser.getAttributeFloatValue(1,20);
                            largeur = parser.getAttributeFloatValue(0,3);
                        } else if (name.equals("mur")) {
                            Vec2 p = new Vec2(level.center.x +parser.getAttributeFloatValue(1,0),level.center.y/world.ratio+parser.getAttributeFloatValue(2,0));
                            float angle =parser.getAttributeFloatValue(0,0);
                            level.murs.add(new SimpleRoundedWall(world,viewport,p.x,p.y,angle,longueur,largeur));
                        }
                        else if( name.equals("sortie"))
                        {
                            level.numSortie = parser.getAttributeIntValue(0,1);
                            level.border = new LevelScreenBorderWall(world,viewport,level.center,level.numSortie);
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if(name.equals("levelS")) noMoreLevels = true;
                    else if(name.equals("level"))levelcharged=true;
                    break;

            }
            eventType = parser.next();
        }
        level.empty=!levelcharged;
    }

}
