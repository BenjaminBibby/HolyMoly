package com.example.benja.holymoley;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by benja on 11/03/2018.
 */

public class MyGLSurfaceView extends GLSurfaceView
{
    private final GameWorld gameWorld;

    public MyGLSurfaceView(Context context)
    {
        super(context);

        setEGLContextClientVersion(2);

        gameWorld = new GameWorld(context);

        setRenderer(gameWorld);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        gameWorld.TouchEvent(event);
        return true;
    }
}
