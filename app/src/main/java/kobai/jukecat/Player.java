package kobai.jukecat;


import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Player extends GameObject{
    // variables that the player object class will use
    private Bitmap image;
    private int score;
    private boolean playing;
    private long startTime;
    boolean position;

    //constructor method to initialize variables
    public Player(Bitmap res)
    {
        image = res;
        x=(GamePanel.WIDTH/2)-10;
        y=GamePanel.HEIGHT/100 * 85;
        score =0;
    }

    //Update method
    public void update()
    {
        //This section of code finds the time elapsed and increments the score accordingly
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>400)
        {
            score++;
            //sets start time to the system time to restart the elapsed cycle
            startTime = System.nanoTime();
        }

        //Controls which of the 2 lanes the player is in
        //if position is true, it is in the left lane, else it is in the right lane
        if(position)
        {
            x = 320;
        }
        else
        {
            x = 600;
        }
    }
    //juke method is used to give bonus points when player waits until wall gets extremely close to them before moving
    public void juke()
    {
        score+=5;
    }
    //draw method to draw the image at the current coordinates
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image,x,y,null);
    }
    //accessor method to get score
    public int getScore()
    {
        return score;
    }
    //accessor method to get if the game is being played
    public boolean getPlaying()
    {
        return playing;
    }
    //mutator method to change the playing variable
    public void setPlaying(boolean b)
    {
        playing = b;
    }

    //mutator method that resets score and player's position
    public void reset()
    {
        score = 0;
        x=(GamePanel.WIDTH/2)-10;
    }

}
