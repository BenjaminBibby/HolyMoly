package com.example.benja.holymoley;

import android.graphics.PointF;

/**
 * Created by benja on 12/03/2018.
 */

public class Sprite
{
    private float[][] spriteSheetInfo;
    private float animationSpeed;

    private float currentFrameIndex;

    public Sprite()
    {
        // Empty Constructor
    }

    public Sprite(float[][] spriteSheetInfo, float animationSpeed)
    {
        SetSprite(spriteSheetInfo, animationSpeed);
    }

    public float[][] GetSprites()
    {
        return spriteSheetInfo;
    }

    public float[] GetSprite()
    {
        return spriteSheetInfo[(int)currentFrameIndex];
    }

    public int GetCurrentFrameIndex()
    {
        return (int)currentFrameIndex;
    }

    public void SetSprite(float[][] spriteSheetInfo, float animationSpeed)
    {
        this.currentFrameIndex = 0.0f;
        this.spriteSheetInfo = spriteSheetInfo;
        this.animationSpeed = animationSpeed;
    }

    public float[] GetVertices(PointF position, float xOffset, float yOffset, float angle, float sizeX, float sizeY)
    {
        PointF[] vertices = new PointF[4];
        float sinValue = (float)Math.sin(angle);
        float cosValue = (float)Math.cos(angle);

        PointF center = new PointF(sizeX * 0.5f, sizeY * 0.5f);

        //(0.0,1.0)
        vertices[0] = new PointF
                (-center.x * cosValue - center.y * sinValue
                ,-center.x * sinValue + center.y * cosValue);
        //(0.0,0.0)
        vertices[1] = new PointF
                (-center.x * cosValue + center.y * sinValue
                ,-center.x * sinValue - center.y * cosValue);
        //(1.0,0.0)
        vertices[2] = new PointF
                (center.x * cosValue + center.y * sinValue
                ,center.x * sinValue - center.y * cosValue);
        //(1.0,1.0)
        vertices[3] = new PointF
                (center.x * cosValue - center.y * sinValue
                ,center.x * sinValue + center.y * cosValue);

        for(int i = 0; i < 4; i++)
        {
            vertices[i].x += position.x + xOffset;
            vertices[i].y += position.y + yOffset;
        }

        return new float[]
                {
                        vertices[0].x, vertices[0].y, 0.0f,
                        vertices[1].x, vertices[1].y, 0.0f,
                        vertices[2].x, vertices[2].y, 0.0f,
                        vertices[3].x, vertices[3].y, 0.0f
                };
    }

    public float[] GetVertices(PointF position, PointF size, float xOffset, float yOffset, float angle)
    {
        return GetVertices(position, xOffset, yOffset, angle, size.x, size.y);
    }

    public float[] GetVertices(PointF position, PointF size, float angle)
    {
        return GetVertices(position, size, size.x/2, size.y/2, angle);
    }

    public void UpdateAnimation(float deltaTime)
    {
        currentFrameIndex += deltaTime * animationSpeed;
        if(currentFrameIndex >= spriteSheetInfo.length)
        {
            currentFrameIndex = 0.0f;
        }
    }
}
