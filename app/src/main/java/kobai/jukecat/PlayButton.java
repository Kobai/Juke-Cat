package kobai.jukecat;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by vkobay on 6/20/2016.
 */
public class PlayButton extends GameObject
{
    // variables that the player object class will use
    private Bitmap image;
    private int score;
    private boolean playing;
    private long startTime;
    boolean position;

    //constructor method to initialize variables
    public PlayButton(Bitmap res)
    {
        image = res;
        x = 230;
        y = 300;
    }

    //Update method
    public void update()
    {

    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image,x,y,null);
    }



}
