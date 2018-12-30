package insa.clutchgames.wallpass.models;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface DrawableShape {
    void initGraphics();
    void synchronize();
    void draw(Canvas canvas, Paint paint);
}
