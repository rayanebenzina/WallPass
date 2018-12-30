package insa.clutchgames.wallpass.models;

import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Vec2;

public class Viewport extends OBBViewportTransform {
    private Vec2 screenCenter;
    public Viewport()
    {
        super();
        screenCenter = new Vec2(0,0);
    }

    @Override
    public void setCenter(float x, float y) {
        setCenter(new Vec2(x,y));
    }

    @Override
    public void setCenter(Vec2 argPos) {
        super.setCenter(argPos);
        getWorldToScreen(argPos,screenCenter);
    }

    public Vec2 getScreenCenter() {
        return screenCenter;
    }
}
