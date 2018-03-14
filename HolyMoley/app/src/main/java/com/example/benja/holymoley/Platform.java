package com.example.benja.holymoley;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * Created by benja on 13/03/2018.
 */

public class Platform extends GameObject
{
    private Sprite[] sprites;

    public Platform()
    {
        // Empty Constructor
    }

    public Platform(PointF position, PointF speed, int size)
    {
        super(null, position, new PointF(0,0), speed, 0, 0, 0);

        this.name = "Platform";
        this.gravity = 0f;

        sprites = new Sprite[]
                {
                        // 2 wide
                        new Sprite(
                                new float[][]
                                        {
                                                {0, 0.714f, 0.428f, 0.285f, 0.142f}
                                        },
                                0f
                        ),
                        // 3 wide
                        new Sprite(
                                new float[][]
                                        {
                                                {0, 0.0f, 0.285f, 0.428f, 0.142f}
                                        },
                                0f
                        ),
                        // 4 wide
                        new Sprite(
                                new float[][]
                                        {
                                                {0, 0.428f, 0.285f, 0.571f, 0.142f}
                                        },
                                0f
                        ),
                        // 5 wide
                        new Sprite(
                                new float[][]
                                        {
                                                {0, 0.0f, 0.428f, 0.714f, 0.142f}
                                        },
                                0f
                        )
                };

        InitiateSize(size);
        this.collisionRect = new RectF(position.x, position.y, position.x + this.size.x, position.y + this.size.y);
    }

    private void SetSize(Sprite sprite, PointF size)
    {
        this.sprite = sprite;
        this.size = size;
    }

    private void InitiateSize(int size)
    {
        float sizeX, sizeY;
        sizeX = 500f;
        sizeY = 500f;
        switch (size)
        {
            case 0:
                SetSize(sprites[0], new PointF(sizeX * 2, sizeY));
                //speed.x = 200f;
                break;
            case 1:
                SetSize(sprites[1], new PointF(sizeX * 3, sizeY));
                break;
            case 2:
                SetSize(sprites[2], new PointF(sizeX * 4, sizeY));
                break;
            case 3:
                SetSize(sprites[3], new PointF(sizeX * 5, sizeY));
                break;
            default:
                SetSize(sprites[0], new PointF(sizeX * 2, sizeY));
                break;
        }
    }

    @Override
    public void Destroy() {
        super.Destroy();
        /*for(Sprite spr : sprites)
        {
            spr = null;
        }*/
    }

    @Override
    public void Update(float deltaTime)
    {
        super.Update(deltaTime);
        if(position.y > 4096)
        {
            Toast.makeText(GameWorld.context, "Platform Destroyed", Toast.LENGTH_SHORT).show();
            Destroy();
        }
        //Log.v(TAG, "Position = [" + this.position.x + "," + this.position.y + "]");
    }

    @Override
    public void Draw(GLRenderer glRenderer)
    {
        super.Draw(glRenderer);
    }

    @Override
    public void OnCollision(GameObject other)
    {
        /*while(other.GetCollisionRect().bottom <= this.GetCollisionRect().top)
        {
            other.SetPosition(new PointF(other.GetPosition().x, other.GetPosition().y + 1));
        }*/
        while(other.GetCollisionRect().intersect(this.GetCollisionRect()))
        {
            other.SetPosition(new PointF(other.GetPosition().x, other.GetPosition().y + 1));
            other.velocity = 0f;
        }
    }
}
