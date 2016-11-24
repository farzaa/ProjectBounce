package com.mygdx.game.PlayActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by flynn on 11/23/16.
 */

public class BarBox2D {

    public Sprite barSprite;
    public Body barBody;
    public World world;
    public static final float PIXELS_TO_METERS = 100f;

    public BarBox2D(final World world) {
        this.world = world;
        barSprite = new Sprite(new Texture("menu-black-bar-stripes.png"));

        float w = Gdx.graphics.getWidth()/PIXELS_TO_METERS;
        float h = Gdx.graphics.getHeight()/PIXELS_TO_METERS - 20/PIXELS_TO_METERS;

        BodyDef barBodyInitial = new BodyDef();
        //StaticBody because our edges will not move.
        barBodyInitial.type = BodyDef.BodyType.StaticBody;
        barBodyInitial.position.set(0,0);

        PolygonShape box = new PolygonShape();
        box.setAsBox(w/4, 15/PIXELS_TO_METERS);

        FixtureDef barDef = new FixtureDef();
        barDef.shape = box;

        barBody = world.createBody(barBodyInitial);
        barBody.createFixture(barDef);

        box.dispose();
        barSprite.setPosition(-barSprite.getWidth()/2, -barSprite.getHeight()/2);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                //Gdx.app.log("debug", "Bar in contact");

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
}
