package kobai.jukecat;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by vkobay on 10/30/2015.
 */
public class MainThread extends Thread
{
    //Ideal fps
    private int FPS = 30;
    //double of average FPS
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    //Gamepanel object
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    //Constructor method
    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    //run method
    public void run()
    {
        //variables within MainThread class
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000/FPS;

        while(running) {
            startTime = System.nanoTime();
            canvas = null;

            //lock canvas
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch (Exception e) {
            }
            finally{
               if(canvas!=null)
               {
                   //try to unlock and catch exception if applicable
                   try{
                       surfaceHolder.unlockCanvasAndPost(canvas);
                   }catch(Exception e){
                       e.printStackTrace();
                   }

               }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try{
                this.sleep(waitTime);
            }catch(Exception e){}

            totalTime += System.nanoTime()- startTime;
            frameCount++;
            //if frameCount is brought to FPS, frameCount and totalTime are reset
            if (frameCount == FPS)
            {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
            }
        }
    }
    //mutator method to toggle running variable
    public void setRunning(boolean b)
    {
        running=b;
    }
}
