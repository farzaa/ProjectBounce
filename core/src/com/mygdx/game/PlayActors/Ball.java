package com.mygdx.game.PlayActors;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameDemo;
import com.mygdx.game.States.PlayState;

import java.util.Random;

/**
 * Created by flynn on 11/19/16.
 */


//Still working on the ball class. In my mind the ball class will have a Sprite and Body attached to it.
//This way we can track both things using the same reference and update using the same object. I might change this later.
public class Ball extends Actor{

    public Sprite ballSprite;
    public Body ballBody;
    public World world;
    Random random;
    public Ball(final World world) {
        this.world = world;
        random = new Random();
        ballSprite = new Sprite(new Texture("circle-image.png"));

        ballSprite.setScale(0.2f, 0.2f);

        //TO DO: We want to randomize the position AND initial X velocity of the balls when they spawn.

        ballSprite.setPosition(-(random.nextInt(800) - 400), random.nextInt(5000) - 6000);
        System.out.println(ballSprite.getX() + " Y "  + ballSprite.getY());


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((ballSprite.getX() + ballSprite.getWidth()/2) / PlayState.PIXELS_TO_METERS, (ballSprite.getY() + ballSprite.getHeight()/2) / PlayState.PIXELS_TO_METERS);
        ballBody = world.createBody(bodyDef);
        ballBody.setLinearVelocity(new Vector2(random.nextInt(10) -5, 0));

        CircleShape shape = new CircleShape();
        shape.setRadius(0.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.restitution = 0.5f;

        ballBody.createFixture(fixtureDef);
        shape.dispose();

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                //if(contact.getFixtureB().get) {
                    //world.destroyBody(ballBody);
                    System.out.println("Contact");
                //}

            }
            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    public void update() {
        //if()
        //world.destroyBody();

    }

    @Override
    public void draw (Batch sb, float parentAlpha) {
        update();
    }
}
