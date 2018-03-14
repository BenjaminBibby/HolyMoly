package com.example.benja.holymoley;

import android.graphics.PointF;
import android.graphics.RectF;
import android.nfc.Tag;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by benja on 11/03/2018.
 */

public class GameObject
{
    public String name;

    protected Sprite sprite;
    protected PointF position;
    protected PointF size;
    protected PointF speed;
    protected RectF collisionRect;

    protected float gravity;
    protected float velocity;
    protected float maxVelocity;
    protected float animationSpeed;
    protected float angle;
    protected float rotationSpeed;

    protected Sprite debugCollisionRect;

    public GameObject()
    {
        // Empty Constructor
    }

    public GameObject(Sprite sprite, PointF position, PointF size, PointF speed, float angle, float animationSpeed, float rotationSpeed)
    {
        this.sprite = sprite;
        this.position = position;
        this.size = size;
        this.speed = speed;
        this.velocity = 0f;
        this.maxVelocity = 300f;
        this.angle = 0;
        this.animationSpeed = animationSpeed;
        this.rotationSpeed = rotationSpeed;

        this.debugCollisionRect = new Sprite(new float[][]{{0, 0.0f, 0.571f, 0.142f, 0.142f}}, 0);

        GameWorld.gameObjects.add(this);
    }

    public PointF GetPosition()
    {
        return this.position;
    }

    public void SetPosition(PointF position)
    {
        this.position = position;
    }

    public PointF GetSize()
    {
        return this.size;
    }

    public PointF GetSpeed()
    {
        return this.speed;
    }

    public Sprite GetSprite()
    {
        return this.sprite;
    }

    protected void Update(float deltaTime)
    {
        if(!GameWorld.isGameRunning)
        {
            return;
        }
        // Update
        if(PlaceFree_x(speed.x * deltaTime))
        {
            this.position.x += speed.x * deltaTime;
        }

        if(velocity < maxVelocity)
        {
            velocity += gravity * deltaTime;
        }

        this.position.y += speed.y * deltaTime - velocity;

        CheckCollision();

        this.angle += DegreeToRad(rotationSpeed) * deltaTime;

        this.sprite.UpdateAnimation(deltaTime);
    }

    protected void Draw(GLRenderer glRenderer)
    {
        // Draw/Render
        if(GameWorld.debug == true)
        {

            glRenderer.RenderObject(
                    debugCollisionRect.GetVertices(
                            new PointF(GetCollisionRect().left, GetCollisionRect().top),
                            new PointF(GetCollisionRect().width(), GetCollisionRect().height()),
                            0
                    ),
                    debugCollisionRect.GetSprite());

        }

        glRenderer.RenderObject(sprite.GetVertices(this.position, this.size, this.angle), sprite.GetSprite());
    }

    public RectF GetCollisionRect()
    {
        return new RectF(position.x, position.y, position.x + collisionRect.width(), position.y + collisionRect.height());
    }

    public boolean IsCollidingWith(GameObject other)
    {
        return this.GetCollisionRect().intersect(other.GetCollisionRect());
    }

    public void CheckCollision()
    {
        for(GameObject gameObject : GameWorld.gameObjectsTmp)
        {
            if(gameObject != this)
            {
                if(this.IsCollidingWith(gameObject))
                {
                    OnCollision(gameObject);
                }
            }
        }
    }

    public void OnCollision(GameObject other)
    {
        // Override this
        return;
    }

    public boolean PlaceFree_x(float x)
    {
        RectF checkbox = new RectF(position.x, position.y, position.x + GetCollisionRect().width(), position.y + GetCollisionRect().height());

        if(x > 0)
        {
            checkbox.left = position.x + GetCollisionRect().width();//position.x + GetCollisionRect().width();
            checkbox.right = position.x + GetCollisionRect().width() + x;
        }
        else
        {
            checkbox.left = position.x + x;
            checkbox.right = position.x;
        }

        for (GameObject gameObject : GameWorld.gameObjectsTmp)
        {
            if(gameObject != this)//gameObject != this)
            {
                if (checkbox.intersect(gameObject.GetCollisionRect()))
                {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean PlaceFree_y(float y)
    {
        RectF checkbox = new RectF(position.x, position.y, position.x + GetCollisionRect().width(), position.y + GetCollisionRect().height());

        if(y > 0)
        {
            checkbox.top = position.y + GetCollisionRect().height();
            checkbox.bottom = position.y + GetCollisionRect().height() + y;
        }
        else
        {
            checkbox.top = position.y + y;
            checkbox.bottom = position.y;
        }

        for (GameObject gameObject : GameWorld.gameObjectsTmp)
        {
            if(gameObject != this)
            {
                if (checkbox.intersect(gameObject.GetCollisionRect()))
                {
                    return false;
                }
            }
        }
        return true;
    }

    public void Destroy()
    {
        Destroy(this);
    }

    public void Destroy(GameObject gameObject)
    {
        GameWorld.gameObjects.remove(gameObject);
        /*sprite = null;
        debugCollisionRect = null;*/
    }

    public static float DegreeToRad(float d)
    {
        return d * ((float)Math.PI/180f);
    }

    public static float RadiantToDeg(float r)
    {
        return r * (180f / (float)Math.PI);
    }
}
