package com.mygdx.gameBounceBounce.PlayActors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by flynn on 11/19/16.
 */


//Still working on the ball class. In my mind the ball class will have a Sprite and Body attached to it.
//This way we can track both things using the same reference and update using the same object. I might change this later.
public class Ball {

    public Sprite ballSprite;
    public Body ballBody;
    public World world;
    public boolean destroyBool;
    public AtomicBoolean destroyBoolAtomic;

    public int bounceCounter;

    public String powerUpType;

    Random random;

    //This is probably super AIDS, but I'm low on time.
    public Sprite powerUpText;

    public Ball(final World world) {
        this.world = world;
        random = new Random();
        destroyBoolAtomic = new AtomicBoolean(false);

        ballSprite = new Sprite(new Texture("circle-image.png"));

        ballSprite.setScale(0.23f, 0.23f);


        //TO DO: We want to randomize the position AND initial X velocity of the balls when they spawn.

        ballSprite.setPosition((random.nextInt(300) - 300), random.nextInt(4000) - 5500);
        System.out.println(ballSprite.getX() + " Y "  + ballSprite.getY());

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((ballSprite.getX() + ballSprite.getWidth()/2) / com.mygdx.gameBounceBounce.States.PlayState.PIXELS_TO_METERS, (ballSprite.getY() + ballSprite.getHeight()/2) / com.mygdx.gameBounceBounce.States.PlayState.PIXELS_TO_METERS);
        ballBody = world.createBody(bodyDef);
        ballBody.setLinearVelocity(new Vector2(random.nextInt(10) -5, 0));

        CircleShape shape = new CircleShape();
        shape.setRadius(0.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.restitution = 0.8f;

        ballBody.createFixture(fixtureDef);
        shape.dispose();

        //power-up logic

        float chance = random.nextFloat() * 100;

        if(chance < 50) { //0 - 50 percent chance
            powerUpText = null;
            powerUpType = "none";
        }

        else if(chance < 65) { //75 - 85
            powerUpText = new Sprite(new Texture("powerups/heart-image.png"));
            powerUpText.setScale(0.5f,0.5f);
            powerUpType = "heart";
        }

        else if(chance < 85) {
            powerUpText = new Sprite(new Texture("powerups/bonus.png"));
            //powerUpText.setScale(0.f, 0.75f);
            powerUpType = "bonus";
        }

        else {
            powerUpText = new Sprite(new Texture("powerups/cherrybomb.png"));
            powerUpText.setScale(0.5f,0.5f);
            powerUpType = "bomb";
        }


//        //10% chance of a heart power up
//        if (chance <= 0.10f) {
//            powerUpText = new Sprite(new Texture("powerups/heart-image.png"));
//            powerUpText.setScale(0.5f,0.5f);
//            powerUpType = "heart";
//        }

//        TO DO
//        if(chance <= 0.20f) {
//            powerUpText = new Sprite(new Texture("powerups/cherrybomb.png"));
//            powerUpText.setScale(0.5f,0.5f);
//            powerUpType = "bomb";
//        }
//
//        else {
//            powerUpText = null;
//            powerUpType = "none";
//        }







    }

}
