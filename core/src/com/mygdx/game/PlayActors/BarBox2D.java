package com.mygdx.game.PlayActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
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
import com.mygdx.game.States.PlayState;

/**
 * Created by flynn on 11/23/16.
 */

public class BarBox2D {

    public Sprite barSprite;
    public Body barBodyLeft;
    public Body barBodyRight;
    public World world;
    public Rectangle boundingRectangle;
    public static final float PIXELS_TO_METERS = 100f;

    public BarBox2D(final World world, float y) {
        this.world = world;
        barSprite = new Sprite(new Texture("menu-black-bar-stripes.png"));
        float w = Gdx.graphics.getWidth()/PIXELS_TO_METERS;
        //float h = Gdx.graphics.getHeight()/PIXELS_TO_METERS - 20/PIXELS_TO_METERS;

        //first create the left bar.
        BodyDef barBodyInitial = new BodyDef();
        //StaticBody because our edges will not move.
        barBodyInitial.type = BodyDef.BodyType.StaticBody;
        barBodyInitial.position.set(-2*w - 110/PIXELS_TO_METERS, y/PIXELS_TO_METERS + 10/PIXELS_TO_METERS);

        PolygonShape box = new PolygonShape();
        box.setAsBox(2*w , 15/PIXELS_TO_METERS);

        FixtureDef barDef = new FixtureDef();
        barDef.shape = box;

        barBodyLeft = world.createBody(barBodyInitial);
        barBodyLeft.createFixture(barDef);

        box.dispose();
        barSprite.setPosition(-barSprite.getWidth()/2, y-barSprite.getHeight()/2);

        //now create the right bar
        BodyDef barBodyInitialRight = new BodyDef();
        barBodyInitialRight.type = BodyDef.BodyType.StaticBody;
        barBodyInitialRight.position.set(2*w + 110/PIXELS_TO_METERS, y/PIXELS_TO_METERS + 10/PIXELS_TO_METERS);

        PolygonShape boxRight = new PolygonShape();
        boxRight.setAsBox(2*w , 15/PIXELS_TO_METERS);

        FixtureDef barDefRight = new FixtureDef();
        barDefRight.shape = box;

        barBodyRight = world.createBody(barBodyInitialRight);
        barBodyRight.createFixture(barDefRight);

        box.dispose();
        barSprite.setPosition(-barSprite.getWidth()/2, y-barSprite.getHeight()/2);

        //now create the rectangle that will bound the entire bar. This way, the bar ONLY moves when the user actually touches it.
        boundingRectangle =  new Rectangle(0, 0, Gdx.graphics.getWidth(), 40);


    }
}
