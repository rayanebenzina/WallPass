package insa.clutchgames.wallpass.models;

import android.content.Context;

public class Balle
{
    private int x,y, vx, vy,rayon;
    public Balle(int x, int y, int vx, int vy, int rayon)
    {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.rayon = rayon;
    }
    public void moveWithCollisionDetection()
    {
        x+=vx;
        y+=vy;

        // si x dépasse la largeur de l'écran, on inverse le déplacement
        if(x+rayon > wEcran) {speedX=-INCREMENT;}

        // si y dépasse la hauteur l'écran, on inverse le déplacement
        if(y+rayon > hEcran) {speedY=-INCREMENT;}

        // si x passe à gauche de l'écran, on inverse le déplacement
        if(x<0) {speedX=INCREMENT;}

        // si y passe à dessus de l'écran, on inverse le déplacement
        if(y<0) {speedY=INCREMENT;}
    }
}