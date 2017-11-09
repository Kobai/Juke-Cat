package kobai.jukecat;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by vkobay on 11/11/2015.
 */

//The difficulty object is a large black square that slowly falls, lowering the player's field of view
//it will cap at a certain distance, leaving a small anticipation
public class Difficulty extends GameObject {
    //variables that difficulty object uses
    private Bitmap image;
    private long startTime;

    //constructor that sets difficulty
    public Difficulty(Bitmap res)
    {
        image = res;
        y=-850;
    }

    //update method of the difficulty object
    public void update() {
        //keeps track of time elapsed and slowly brings the difficulty object down
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if (elapsed >= 10 && y <= 100) {
            y++;
            startTime = System.nanoTime();
        }


    }
    //draw method that draws the object
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image,x,y,null);
    }
    //reset method that brings difficulty back to its original position
    public void reset()
    {
        y = -850;
    }
}
