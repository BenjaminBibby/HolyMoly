package com.example.benja.holymoley;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.List;
import java.util.Vector;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.content.ContentValues.TAG;

/**
 * Created by benja on 11/03/2018.
 */

public class GameWorld implements GLSurfaceView.Renderer
{
    public static Context context;

    private GLRenderer glRenderer;

    public static List<GameObject> gameObjects;
    public static List<GameObject> gameObjectsTmp;
    public static List<GameObject> gameObjectsDisabled;

    public static GestureDetector gestureDetector;

    public static Player player;
    public static LevelManager levelManager;

    public static boolean debug = true;
    public static boolean isGameRunning = false;
    public static int count = 0;

    public long lastStartTime;

    public GameWorld(Context context)
    {
        this.context = context;
        this.glRenderer = new GLRenderer(context, new int[]{R.drawable.texture});

        this.gameObjects = new Vector<GameObject>();
        this.gameObjectsTmp = new Vector<GameObject>();
        this.gameObjectsDisabled = new Vector<GameObject>();

        this.levelManager = new LevelManager(context);
        this.player = new Player(new PointF(1024, 2000), new PointF(180*3, 180*3), new PointF(0, 0));

        this.gestureDetector = new GestureDetector(context,
                new GestureController(context, player)
                {
                    @Override
                    public boolean onSwipe(Direction direction)
                    {
                        if(!player.IsDigging())
                        {
                            float speed = 500f;
                            String toastText = "";

                            switch (direction)
                            {
                                case left:
                                    // Walk left
                                    player.ChangeDirection(player.LEFT, speed, 2);
                                    toastText = "Left";
                                    break;
                                case right:
                                    // Walk right
                                    player.ChangeDirection(player.RIGHT, speed, 3);
                                    toastText = "Right";
                                    break;
                                case up:
                                    // Idle
                                    player.ChangeDirection(player.IDLE, 0, 0);
                                    toastText = "Idle";
                                    break;
                                case down:
                                    if(GameWorld.isGameRunning)
                                    {
                                        if(!player.PlaceFree_y(-3))
                                        {
                                            player.Dig();
                                            toastText = "Digging...";
                                        }
                                        // Dig
                                    }
                                    else
                                    {
                                        GameWorld.Resume();
                                        toastText = "Resume Game";
                                    }


                                    break;

                            }
                            if(toastText != "")
                            {
                                Toast.makeText(GameWorld.context, toastText, Toast.LENGTH_SHORT).show();
                            }
                        }
                        return super.onSwipe(direction);
                    }
                }
        );
    }


    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig)
    {
        glRenderer.CreateSurface();
        lastStartTime = SystemClock.elapsedRealtime();
    }


    public void onSurfaceChanged(GL10 gl10, int width, int height)
    {
        glRenderer.ChangeSurface(width, height);
    }


    public void onDrawFrame(GL10 gl10)
    {
        long startTime = System.currentTimeMillis();
        long deltaTime = startTime-lastStartTime;
        lastStartTime = startTime;
        if(debug == true)
        {
            if(count < 2)
            {
                Log.v(TAG, "DeltaTime: " + deltaTime + "Iteration[" + ++count + "]");
            }
        }
        //Log.v(TAG, "DeltaTime: " + (float)deltaTime/100f);

        if(gameObjectsTmp != gameObjects)
        {
            gameObjectsTmp.clear();
            for(int i = 0; i < gameObjects.size(); i++)
            {
                gameObjectsTmp.add(gameObjects.get(i));
            }
        }

        levelManager.Update(deltaTime);

        for(GameObject gameObject : gameObjectsTmp)
        {
            gameObject.Update((float)deltaTime/1000f);
        }

        glRenderer.StartRender();
        for(GameObject gameObject : gameObjectsTmp)
        {
            gameObject.Draw(glRenderer);
        }
    }

    public static void Pause()
    {
        // Pause Game
        if(isGameRunning)
        {
            isGameRunning = false;
        }
    }

    public static void Resume()
    {
        // Resume Game after Pause
        if(!isGameRunning)
        {
            isGameRunning = true;
        }
    }

    public void TouchEvent(MotionEvent event)
    {
        gestureDetector.onTouchEvent(event);
    }
}
