package kobai.jukecat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



/**
 * Created by vkobay on 10/30/2015.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{

    public static final int WIDTH = 960;
    public static final int HEIGHT = 1600;
    //timer used to indicate spawning periods of walls
    private long wallStartTimer;
    //creates a MainThread object
    private MainThread thread;
    //creates a BackGround object
    private Background bg;
    //creates a Player object
    private Player player;
    //creates an array of 5 Wall objects
    private Wall wall[] = new Wall[5];
    //creates button
    private PlayButton button;
    //create a difficulty object
    private Difficulty difficulty;
    //keeps track of number of walls active
    private int wallCounter = 0;
    //triggers which walls will be active.
    private boolean wallActive[] = new boolean[5];
    //used so that player must tap twice before the game restarts, to eliminate what would appear like a glitch
    boolean live = true;
    //placeholder for highscore
    private static int HighScore=0;
    //shraed preferences will be used to save a high score into the internal storage
    private static SharedPreferences prefs;
    private String saveScore = "Highscore";





    public GamePanel(Context context)
    {
        super(context);
        //add callback to surface holder to intercept events
        //initializes prefs so it can store high scores
        prefs = context.getSharedPreferences("kobai.jukecat",context.MODE_PRIVATE);
        String spackage = "kobai.jukecat";
        HighScore = prefs.getInt(saveScore,0);

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable
        //setFocusable(true);
    }

    @Override
    //changes the surface
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    //Destroys the surface
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        int counter = 0;
        while(retry&&counter<1000)
        {
            counter++;
            //try to stop the thread  and catch any possible exception
            try
            {
                thread.setRunning(false);
                thread.join();
                retry = false;
            }catch(InterruptedException e){e.printStackTrace();}

        }
    }

    @Override
    //creates the surface
    public void surfaceCreated(SurfaceHolder Holder)
    {
        //creates a Background, player and 5 walls on the surface
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.bg));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.player));
        difficulty = new Difficulty(BitmapFactory.decodeResource(getResources(), R.drawable.diff));
        button = new PlayButton(BitmapFactory.decodeResource(getResources(), R.drawable.playbutton));
        for(int i = 0; i<5; i++) {
            wall[i] = new Wall(BitmapFactory.decodeResource(getResources(), R.drawable.wall));
        }


        //start game loop
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {   //finds x coordinate of the screen tap
        int tapX = (int)event.getX();
        int tapY = (int)event.getY();

       if(player.getPlaying()==false)
       {
          if(event.getAction()==MotionEvent.ACTION_DOWN&&tapX>=200&&tapX<=800&&tapY>=300&&tapY<=500)
          {
              for(int i = 0; i<5; i++) {
                  wall[i].reset();
                  wallActive[i]=false;
              }
              player.reset();
              difficulty.reset();
              player.setPlaying(true);
          }
       }
       else
       {
           //If the player taps on the left side of the screen
           if(event.getAction()==MotionEvent.ACTION_DOWN&&tapX<540)
               player.position = true;
               //If the right side of the screen is tapped
           else if(event.getAction()==MotionEvent.ACTION_DOWN&&tapX>540)
               player.position = false;
       }
        return super.onTouchEvent(event);
    }

    //update method
    public void update()
    {
        //Only runs of game is in operation
        if(player.getPlaying())
        {
           //background, player and difficulty update methods are run
            bg.update();
            player.update();
            difficulty.update();
            //when certain amount of time elapses, a wall spawns.
            long elapsed = (System.nanoTime()-wallStartTimer)/1000000;
            if(elapsed>400)
            {
                wallStartTimer = System.nanoTime();
                wallActive[wallCounter]=true;
                wallCounter++;
                if(wallCounter>=5)
                    wallCounter=0;
            }
            //All active walls are updated through their own update method
            for(int i = 0; i<5; i++)
            {
                if (wallActive[i])
                {
                    wall[i].update();
                    //If wall comes in contact with the player
                    if(wall[i].getX()<player.getX() && wall[i].getX()+260>player.getX())
                    {
                        if(wall[i].getY()+25>player.getY()&& wall[i].getY()<player.getY()+125)
                        {

                            //game ends with the set playing turning false
                            player.setPlaying(false);
                            live= false;
                            //sets high score to current score if current score>highscore
                            if(player.getScore()>HighScore)
                            {
                                HighScore = player.getScore();
                                //saves new highscore into internal storage
                                prefs.edit().putInt(saveScore,HighScore).commit();
                            }

                        }
                        else if(wall[i].getY()+40>player.getY()&& wall[i].getY()<player.getY()+150)
                        {
                            //when player gets close to wall before moving, juke method used to give points
                            player.juke();
                        }
                    }
                }

            }

        }

    }

    @Override
    public void draw(Canvas canvas)
    {
        //if canvas exists
        if(canvas!=null)
        {
            //background, player and difficulty are drawn
            bg.draw(canvas);
            player.draw(canvas);
            difficulty.draw(canvas);
            //all walls are drawn
            for(int i =0; i<5; i++)
                wall[i].draw(canvas);
            //setting up text font
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(80);
            //prints Don't Hit the Wall at the bottom of the screen
            canvas.drawText("Juke Cat", 380, 1670, paint);
            //changes text size
            paint.setTextSize(70);

            //If the game is not playing
            if(!player.getPlaying())
            {
                //Tap to play cue and Highscore are printed
                    canvas.drawText("Highscore: " + String.valueOf(HighScore), 340, 100, paint);
                    button.draw(canvas);
            }
            else
            {
                //if the game is being played, the score is printed
                paint.setTextSize(100);
                canvas.drawText(String.valueOf(player.getScore()), 470, 110, paint);
            }




        }

    }

}
