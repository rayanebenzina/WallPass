package insa.clutchgames.wallpass.models;

import android.graphics.RectF;

import java.util.Vector;

public class CollideTools{


    public  static Vector2D calculerVecteurV2(Vector2D v, Vector2D N) //Vecteurs: V vitesse de la balle, N: la normale au point d'impact
    {
        Vector2D D = Vector2D.sub( v,Vector2D.mul( N,2*Vector2D.dot(v,N) ));
        return D;
    }

    public static Vector2D /* Le point I de collision */ projectionI(Vector2D A,Vector2D B,Vector2D C)
    {
        Vector2D u = Vector2D.sub(B,A);
        Vector2D AC = Vector2D.sub(C,A);
        float ti = Vector2D.dot(AC,u)/u.getNorm2();
        Vector2D I = Vector2D.sum(A,Vector2D.mul(u,ti));
        return I;
    }

    public static RectF createBox(Vector2D p, float radius){
        return createBox(p.x, p.y, radius);
    }

    public static RectF createBox(float x, float y, float radius){
        return new RectF(x-radius, y-radius, x+radius, y+radius);
    }
    public static Vector2D rotate(Vector2D center, Vector2D point, float angle)
    {
        Vector2D tmp = Vector2D.sub(point,center);
        tmp.rotate(angle);
        return  Vector2D.sum(tmp,center);
    }
    public static Vector2D rotate(float cx, float cy, float px, float py, float angle)
    {
        return rotate(new Vector2D(cx,cy),new Vector2D(px,py),angle);
    }
    public static boolean Circle_OBB(Circle C, RectF R, float angle)
    {
        C = C.copy();
        C.setPos(rotate(R.centerX(),R.centerY(),C.p.x,C.p.y, -angle));
        return Circle_AABB(C,R);
    }
    public static boolean Circle_AABB(Circle C, RectF R)
    {
        Vector2D nP = nearestPoint(C,R);
        Vector2D d = Vector2D.sub(C.p,nP);
        return d.getNorm2() < (C.radius * C.radius);
    }
    public static Vector2D nearestPoint(Circle C, RectF R)
    {
        return new Vector2D(Math.max(R.left, Math.min(C.p.x, R.right)),Math.max(R.top, Math.min(C.p.y, R.bottom)));
    }

    public static boolean collideOBB_PointF(Vector<Vector2D> tab, Vector2D P)
    {
        int i;
        for(i=0;i<tab.size();i++)
        {
            Vector2D A = tab.get(i);
            Vector2D B;
            if (i==tab.size()-1)  // si c'est le dernier point, on relie au premier
                B = tab.get(0);
            else           // sinon on relie au suivant.
                B = tab.get(i+1);

            Vector2D D = Vector2D.sub(B,A);
            Vector2D T = Vector2D.sub(P,A);
            D.x = B.x - A.x;
            D.y = B.y - A.y;
            T.x = P.x - A.x;
            T.y = P.y - A.y;
            float d = D.x*T.y - D.y*T.x;
            if (d<0)
                return false;  // un point à droite et on arrête tout.
        }
        return true;  // si on sort du for, c'est qu'aucun point n'est à gauche, donc c'est bon.
    }

}
