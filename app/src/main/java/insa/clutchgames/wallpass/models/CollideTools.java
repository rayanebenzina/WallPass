package insa.clutchgames.wallpass.models;

import android.graphics.PointF;
import android.graphics.RectF;

import java.util.Vector;

public class CollideTools{


    public  static PointF/*Vecteur*/ calculerVecteurV2(PointF v, PointF N) //Vecteurs: V vitesse de la balle, N: la normale au point d'impact
    {
        PointF v2 = new PointF();
        float pscal = (v.x*N.x +  v.y*N.y);
        v2.x = v.x -2*pscal*N.x;
        v2.y = v.y -2*pscal*N.y;
        return v2;
    }

    public static PointF /* Le point I de collision */ projectionI(PointF A,PointF B,PointF C)
    {
        PointF u = new PointF(); //vecteur
        PointF AC = new PointF(); //vecteur
        u.x = B.x - A.x;
        u.y = B.y - A.y;
        AC.x = C.x - A.x;
        AC.y = C.y - A.y;
        float ti = (u.x*AC.x + u.y*AC.y)/(u.x*u.x + u.y*u.y);
        PointF I = new PointF();
        I.x = A.x + ti*u.x;
        I.y = A.y + ti*u.y;
        return I;
    }

    public static PointF /*Vecteur N la normale*/ getNormale(PointF A, PointF B, PointF C)
    {
        PointF AC = new PointF(); //vecteur
        PointF u = new PointF(); //vecteur
        PointF N = new PointF(); //vecteur
        u.x = B.x - A.x;
        u.y = B.y - A.y;
        AC.x = C.x - A.x;
        AC.y = C.y - A.y;
        float parenthesis = u.x*AC.y-u.y*AC.x;  // calcul une fois pour les deux
        N.x = -u.y*(parenthesis);
        N.y = u.x*(parenthesis);
        // normalisons
        float norme = (float)Math.sqrt(N.x*N.x + N.y*N.y);
        N.x/=norme;
        N.y/=norme;
        return N;
    }

    public static RectF createBox(PointF p, float radius){
        return createBox(p.x, p.y, radius);
    }

    public static RectF createBox(float x, float y, float radius){
        return new RectF(x-radius, y-radius, x+radius, y+radius);
    }
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

    public static boolean collideOBB_PointF(Vector<PointF> tab, PointF P)
    {
        int i;
        for(i=0;i<tab.size();i++)
        {
            PointF A = tab.get(i);
            PointF B;
            if (i==tab.size()-1)  // si c'est le dernier point, on relie au premier
                B = tab.get(0);
            else           // sinon on relie au suivant.
                B = tab.get(i+1);
            PointF D = new PointF(0, 0);
            PointF T = new PointF(0,0);
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
