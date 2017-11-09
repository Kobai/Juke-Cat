package kobai.jukecat;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by vkobay on 10/30/2015.
 */
public class Background {
    //variables that background object uses
    private Bitmap image;
    private int x, y;

    public Background(Bitmap res)
    {
        image = res;
    }
    public void update() {}
    //draw method that draws background
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x,y,null);
    }
}
