package com.example.benja.holymoley;

import android.content.Context;
import android.graphics.PointF;
import android.widget.Toast;

/**
 * Created by benja on 14/03/2018.
 */

public class LevelManager
{
    private Context context;

    private int difficultyCount;
    private int state;
    /*
    * State 0:
    *   Small Platforms
    * State 1:
    *   Medium Platforms
    *   Increase speed
    * State 2:
    *   Large Platforms
    *   Boulders
    * State 3:
    *   Full size platforms
    * */
    private float currentPlatformTimer;
    private int platformSpawnInterval;

    public LevelManager(Context context)
    {
        this.context = context;
        this.difficultyCount = 0;
        this.state = 0;
        this.currentPlatformTimer = 0.0f;
        this.platformSpawnInterval = 3500;

        new Platform(new PointF(0, 500), new PointF(0, 400), state);
    }

    public void Update(float deltaTime)
    {
        if(!GameWorld.isGameRunning)
        {
            return;
        }
        currentPlatformTimer += deltaTime;
        if(currentPlatformTimer >= platformSpawnInterval)
        {
            currentPlatformTimer = 0.0f;
            difficultyCount++;
            if(difficultyCount < 10)
            {
                state = 0;
            }
            else if(difficultyCount > 10 && difficultyCount < 20)
            {
                state = 1;
            }
            else if(difficultyCount > 20)
            {
                state = 2;
            }
            new Platform(new PointF(0, 500), new PointF(0, 400), state);
        }
    }
}
