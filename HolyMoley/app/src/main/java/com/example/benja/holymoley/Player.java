package com.example.benja.holymoley;

import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * Created by benja on 12/03/2018.
 */

public class Player extends GameObject
{
    public static final int LEFT = -1;
    public static final int RIGHT = 1;
    public static final int IDLE = 0;

    private boolean digging;

    private Sprite debugCheckboxRect;

    private Sprite[] sprites;

    public Player()
    {
        // Empty Constructor
    }

    public Player(PointF position, PointF size, PointF speed)
    {
        super(null, position, size, speed, 0, 10f, 0);

        //this.CollisionRect = new RectF(position.x, position.y, position.x + size.x, position.y + size.y);

        this.name = "Player";
        this.gravity = 25f;
        this.digging = false;

        this.sprites = new Sprite[]
                {
                        // Idle Right
                        new Sprite(
                                new float[][]
                                        {
                                                {0, 0.571f, 0.143f, 0.142f, 0.142f}
                                        },
                                animationSpeed
                        ),
                        // Idle Left
                        new Sprite(
                                new float[][]
                                        {
                                                {0, 0.285f, 0.143f, 0.142f, 0.142f}
                                        },
                                animationSpeed
                        ),
                        // Walking Left
                        new Sprite(
                                new float[][]
                                        {
                                                {0, 0.0f, 0.143f, 0.142f, 0.142f},
                                                {0, 0.142f, 0.143f, 0.142f, 0.142f}
                                        },
                                animationSpeed
                        ),
                        // Walking Right
                        new Sprite(
                                new float[][]
                                        {
                                                {0, 0.714f, 0.143f, 0.142f, 0.142f},
                                                {0, 0.857f, 0.143f, 0.142f, 0.142f}
                                        },
                                animationSpeed
                        ),
                        // Digging
                        new Sprite(
                                new float[][]
                                        {
                                                {0, 0.0f, 0.0f, 0.142f, 0.142f},
                                                {0, 0.142f, 0.0f, 0.142f, 0.142f},
                                                {0, 0.285f, 0.0f, 0.142f, 0.142f},
                                                {0, 0.428f, 0.0f, 0.142f, 0.142f},
                                                {0, 0.571f, 0.0f, 0.142f, 0.142f},
                                                {0, 0.714f, 0.0f, 0.142f, 0.142f},
                                                {0, 0.857f, 0.0f, 0.142f, 0.142f}
                                        },
                                animationSpeed
                        ),
                        // Falling
                        new Sprite(
                                new float[][]
                                        {
                                                {0, 0.428f, 0.142f, 0.142f, 0.142f}
                                        },
                                animationSpeed
                        )

                };
        this.sprite = sprites[2];
        this.collisionRect = new RectF(position.x, position.y, position.x + size.x, position.y + size.y);
    }

    public boolean IsDigging()
    {
        return this.digging;
    }

    public void ChangeDirection(int direction, float speed, int spriteIndex)
    {
        if(!IsDigging())
        {
            this.speed.x = speed * (float)direction;
            this.sprite = sprites[spriteIndex];
        }
    }

    public void Dig()
    {
        ChangeDirection(IDLE, 0, 4);
        this.digging = true;
    }

    @Override
    protected void Update(float deltaTime)
    {
        super.Update(deltaTime);
        if(IsDigging())
        {
            if(sprite.GetCurrentFrameIndex() == sprite.GetSprites().length-1)
            {
                this.position.y -= size.y * 2f;
                this.sprite = sprites[0];
                digging = false;
            }
        }

        if(this.position.x + size.x > 2048 && speed.x > 0)
        {
            speed.x = 0;
        }

        if(this.position.x < 0 && speed.x < 0)
        {
            speed.x = 0;
        }
    }

    @Override
    protected void Draw(GLRenderer glRenderer)
    {
        super.Draw(glRenderer);
    }

    @Override
    public void OnCollision(GameObject other) {
        super.OnCollision(other);
    }
}
