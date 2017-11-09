package kobai.jukecat;


import android.graphics.Rect;
//This class creates a template for all objects in the game
public abstract class GameObject
{
    //creates the variables each object that extends this class will contain
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    //accessor methods to access object variables
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }

}
