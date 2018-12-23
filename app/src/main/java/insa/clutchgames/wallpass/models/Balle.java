package insa.clutchgames.wallpass.models;

import android.content.Context;
import android.view.Display;

public class Balle
{
    private int x,y, vx, vy, h, w;
    private float rayon;

    public Balle(int x, int y, int vx, int vy, int rayon, int h, int w)
    {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.rayon = rayon;
        this.h = h;
        this.w = w;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public float getRayon()
    {
        return rayon;
    }
    public void moveWithCollisionDetection()
    {
        x+=vx;
        y+=vy;

        if(x+rayon > w) {vx=-vx;}
        if(y+rayon > h) {vy=-vy;}
        if(x<0) {vx=-vx;}
        if(y<0) {vy=-vy;}
    }
    public void resize(int w, int h)
    {
        this.w = w;
        this.h = h;
    }
}