package kobai.jukecat;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;



//Creates a splash screen for the game

/**
 * Created by vkobay on 12/20/2015.
 */
public class Splash extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        //fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        //thread creates the splash screen
        Thread myThread = new Thread()
        {
            @Override
            public void run(){
                try {
                    //sleep thread cause splash screen to appear for limited time
                    sleep(2100);
                    //switches over to main activity
                    Intent startMainScreen = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(startMainScreen);
                    //ends the thread
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //starts thread
        myThread.start();
    }






}
