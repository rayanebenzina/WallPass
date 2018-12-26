package insa.clutchgames.wallpass.models;

import android.graphics.PointF;
import android.graphics.RectF;

public class CollideTools{



    public static PointF rotate(PointF center, PointF point, float angle)
    {
        return  rotate(center.x, center.y, point.x, point.y, angle);
    }
    public static PointF rotate(float cx, float cy, float px, float py, float angle)
    {
        //translate
        float tmpX = px - cx;
        float tmpY = py - cy;
        // apply rotation
        double theta = angle * Math.PI / 180;
        double rotatedX = tmpX * Math.cos(theta) - tmpY * Math.sin(theta);
        double rotatedY = tmpX * Math.sin(theta) + tmpY * Math.cos(theta);

        // translate back
        return  new PointF( (float)(rotatedX + cx), (float) (rotatedY + cy));
    }
    public static boolean Circle_OBB(Circle C, RectF R, float angle)
    {
        C = C.copy();
        C.setPos(rotate(R.centerX(),R.centerY(),C.x,C.y, -angle));
        return Circle_AABB(C,R);
    }
    public static boolean Circle_AABB(Circle C, RectF R)
    {
        float dx = C.x - Math.max(R.left, Math.min(C.x, R.right));
        float dy = C.y - Math.max(R.top, Math.min(C.y, R.bottom));
        return ( dx*dx + dy*dy ) < (C.radius * C.radius);
    }

}
