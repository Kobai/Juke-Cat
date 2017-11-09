package kobai.jukecat;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by vkobay on 11/1/2015.
 */
public class Wall extends GameObject
{
    //variables that the wall object will use
    private Bitmap image;
    private long startTime;
    int side;

    //constructor to set image and the walls initial y position
    public Wall(Bitmap res)
    {
        image = res;
        y= GamePanel.HEIGHT;
    }


    //update method for wall
    public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>200)
        {

            startTime = System.nanoTime();
        }
        //brings the wall down
        y+= 30;

        //brings the wall back to the top when it hits the bottom
        if(y>=GamePanel.HEIGHT)
        {
            y=-70;
            //randomly picks which of the 2 lanes the wall will respawn
            side = (int)(2*Math.random());
            if(side ==0)
                x=270;
            else
                x=550;
        }
    }
    //method that draws the image attached to the object and its x and y position
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image,x,y,null);
    }
    //resets the y coordinate of the wall at the end of the game
    public void reset()
    {
        y=GamePanel.HEIGHT+1000;
    }

}
