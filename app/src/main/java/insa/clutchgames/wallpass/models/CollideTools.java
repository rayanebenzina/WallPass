package insa.clutchgames.wallpass.models;

import android.graphics.PointF;

import java.util.Vector;

public class CollideTools{


    public static boolean collidePointCircle(PointF point, PointF cercle, double rayon)
    {
        double d2 = (point.x - cercle.x) * (point.x - cercle.x) + (point.y - cercle.y) * (point.y-cercle.y);
        if (d2 > rayon * rayon)
            return false;
        else
            return true;
    }

    public static boolean collideLine(PointF A, PointF B, PointF C, double rayon)
    {
        PointF u = new PointF();
        u.x = B.x - A.x;
        u.y = B.y - A.y;
        PointF AC = new PointF();
        AC.x = C.x - A.x;
        AC.y = C.y - A.y;
        double numerateur = u.x*AC.y - u.y*AC.x;   // norme du vecteur v
        if (numerateur <0)
            numerateur = - numerateur ;   // valeur absolue ; si c'est négatif, on prend l'opposé.
        double denominateur = Math.sqrt(u.x*u.x + u.y*u.y);  // norme de u
        double CI = numerateur / denominateur;
        if (CI< rayon)
            return true;
        else
            return false;
    }

    public static boolean collideSegment(PointF A, PointF B, PointF C, double rayon)
    {
        if (!collideLine(A,B,C,rayon))
            return false;  // si on ne touche pas la droite, on ne touchera jamais le segment
        PointF AB = new PointF();
        PointF AC = new PointF();
        PointF BC = new PointF();
        AB.x = B.x - A.x;
        AB.y = B.y - A.y;
        AC.x = C.x - A.x;
        AC.y = C.y - A.y;
        BC.x = C.x - B.x;
        BC.y = C.y - B.y;
        double pscal1 = AB.x*AC.x + AB.y*AC.y;  // produit scalaire
        double pscal2 = (-AB.x) * BC.x + (-AB.y) * BC.y;  // produit scalaire
        if ( pscal1>=0 && pscal2>=0 )
            return true;   // I entre A et B, ok.
        // dernière possibilité, A ou B dans le cercle
        if (collidePointCircle(A, C, rayon))
            return true;
        if (collidePointCircle(B, C, rayon))
            return true;
        return false;
    }

    public static boolean collideOBB_PointF(Vector<PointF> tab, int nbp, PointF P)
    {
        int i;
        for(i=0;i<nbp;i++)
        {
            PointF A = tab.get(i);
            PointF B;
            if (i==nbp-1)  // si c'est le dernier point, on relie au premier
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
