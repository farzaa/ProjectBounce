package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Physics2 extends ApplicationAdapter implements InputProcessor {
    SpriteBatch batch;
    Sprite sprite;

    Sprite sprite2;
    Texture img;
    Texture playstateHUD;
    World world;
    Body body;
    Body body2;
    HashMap<Body, Sprite> bodyHash;
    Body bodyEdgeScreen;
    Body bodyEdgeScreen3;
    Body bodyEdgeScreen4;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;
    OrthographicCamera camera;
    BitmapFont font;


    float torque = 0.0f;
    boolean drawSprite = true;

    final float PIXELS_TO_METERS = 100f;

    @Override
    public void create() {

        batch = new SpriteBatch();
        img = new Texture("circle-image.png");
        playstateHUD = new Texture("playstate-hud.png");
        sprite = new Sprite(img);
        sprite.setScale(0.5f, 0.5f);

        sprite.setPosition(-sprite.getWidth()/2,800);

        world = new World(new Vector2(0, -5f),true);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((sprite.getX() + sprite.getWidth()/2) /
                        PIXELS_TO_METERS,
                (sprite.getY() + sprite.getHeight()/2) / PIXELS_TO_METERS);

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(1f);
        //shape.setAsBox(sprite.getWidth()/2 / PIXELS_TO_METERS, sprite.getHeight()/2 / PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.restitution = 0.5f;

        body.createFixture(fixtureDef);
        shape.dispose();

        sprite2 = new Sprite(new Texture("circle-image.png"));
        sprite2.setScale(0.5f, 0.5f);

        sprite2.setPosition(-sprite2.getWidth()/2,400);

        BodyDef bodyDef5 = new BodyDef();
        bodyDef5.type = BodyDef.BodyType.DynamicBody;
        bodyDef5.position.set((sprite2.getX() + sprite2.getWidth()/2) /
                        PIXELS_TO_METERS,
                (sprite2.getY() + sprite2.getHeight()/2) / PIXELS_TO_METERS);

        body2 = world.createBody(bodyDef5);
        body2.setLinearVelocity(new Vector2(15, 0));


        CircleShape shape5 = new CircleShape();
        shape5.setRadius(1f);
        //shape.setAsBox(sprite.getWidth()/2 / PIXELS_TO_METERS, sprite.getHeight()/2 / PIXELS_TO_METERS);

        FixtureDef fixtureDef5 = new FixtureDef();
        fixtureDef5.shape = shape5;
        fixtureDef5.density = 0.1f;
        fixtureDef5.restitution = 0.5f;

        body2.createFixture(fixtureDef5);
        shape5.dispose();

        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody;
        float w = Gdx.graphics.getWidth()/PIXELS_TO_METERS;
        // Set the height to just 50 pixels above the bottom of the screen so we can see the edge in the
        // debug renderer
        float h = Gdx.graphics.getHeight()/PIXELS_TO_METERS- 50/PIXELS_TO_METERS;
        //bodyDef2.position.set(0,
//                h-10/PIXELS_TO_METERS);
        bodyDef2.position.set(0,0);
        FixtureDef fixtureDef2 = new FixtureDef();

        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(-w/2,-h/2,w/2,-h/2);
        fixtureDef2.shape = edgeShape;

        bodyEdgeScreen = world.createBody(bodyDef2);
        bodyEdgeScreen.createFixture(fixtureDef2);
        edgeShape.dispose();

        BodyDef bodyDef3 = new BodyDef();
        bodyDef3.type = BodyDef.BodyType.StaticBody;
        float w3 = Gdx.graphics.getWidth()/PIXELS_TO_METERS;
        // Set the height to just 50 pixels above the bottom of the screen so we can see the edge in the
        // debug renderer
        float h3 = Gdx.graphics.getHeight()/PIXELS_TO_METERS- 50/PIXELS_TO_METERS;
        //bodyDef2.position.set(0,
//                h-10/PIXELS_TO_METERS);
        bodyDef3.position.set(0,0);
        FixtureDef fixtureDef3 = new FixtureDef();

        EdgeShape edgeShape3 = new EdgeShape();
        edgeShape3.set(-w/2,-h/2,-w/2,h/2);
        fixtureDef3.shape = edgeShape3;

        bodyEdgeScreen3 = world.createBody(bodyDef3);
        bodyEdgeScreen3.createFixture(fixtureDef3);
        edgeShape3.dispose();

        BodyDef bodyDef4 = new BodyDef();
        bodyDef4.type = BodyDef.BodyType.StaticBody;
        float w4 = Gdx.graphics.getWidth()/PIXELS_TO_METERS;
        // Set the height to just 50 pixels above the bottom of the screen so we can see the edge in the
        // debug renderer
        float h4 = Gdx.graphics.getHeight()/PIXELS_TO_METERS- 50/PIXELS_TO_METERS;
        //bodyDef2.position.set(0,
//                h-10/PIXELS_TO_METERS);
        bodyDef4.position.set(0,0);
        FixtureDef fixtureDef4 = new FixtureDef();

        EdgeShape edgeShape4 = new EdgeShape();
        edgeShape4.set(w/2,-h/2,w/2,h/2);
        fixtureDef4.shape = edgeShape4;

        bodyEdgeScreen4 = world.createBody(bodyDef4);
        bodyEdgeScreen4.createFixture(fixtureDef4);
        edgeShape4.dispose();

        Gdx.input.setInputProcessor(this);

        debugRenderer = new Box2DDebugRenderer();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.
                getHeight());
    }

    private float elapsed = 0;
    @Override
    public void render() {
        camera.update();
        // Step the physics simulation forward at a rate of 60hz
        world.step(1f/60f, 6, 2);

        body.applyTorque(torque,true);

        sprite.setPosition((body.getPosition().x * PIXELS_TO_METERS) - sprite.
                        getWidth()/2 ,
                (body.getPosition().y * PIXELS_TO_METERS) -sprite.getHeight()/2 )
        ;
        sprite.setRotation((float)Math.toDegrees(body.getAngle()));

        sprite2.setPosition((body2.getPosition().x * PIXELS_TO_METERS) - sprite2.
                        getWidth()/2 ,
                (body2.getPosition().y * PIXELS_TO_METERS) -sprite2.getHeight()/2 )
        ;
        sprite2.setRotation((float)Math.toDegrees(body2.getAngle()));

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,
                PIXELS_TO_METERS, 0);
        batch.begin();
        batch.draw(playstateHUD, -540,-960);
        if(drawSprite) {
            batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(),
                    sprite.getOriginY(),
                    sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.
                            getScaleY(), sprite.getRotation());
            batch.draw(sprite2, sprite2.getX(), sprite2.getY(),sprite2.getOriginX(),
                    sprite2.getOriginY(),
                    sprite2.getWidth(),sprite2.getHeight(),sprite2.getScaleX(),sprite2.
                            getScaleY(),sprite2.getRotation());
        }

        font.draw(batch,
                "Restitution: " + body.getFixtureList().first().getRestitution(),
                -Gdx.graphics.getWidth()/2,
                Gdx.graphics.getHeight()/2 );
        batch.end();

        debugRenderer.render(world, debugMatrix);
    }

    @Override
    public void dispose() {
        img.dispose();
        world.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {


        if(keycode == Input.Keys.RIGHT)
            body.setLinearVelocity(1f, 0f);
        if(keycode == Input.Keys.LEFT)
            body.setLinearVelocity(-1f,0f);

        if(keycode == Input.Keys.UP)
            body.applyForceToCenter(0f,10f,true);
        if(keycode == Input.Keys.DOWN)
            body.applyForceToCenter(0f, -10f, true);

        // On brackets ( [ ] ) apply torque, either clock or counterclockwise
        if(keycode == Input.Keys.RIGHT_BRACKET)
            torque += 0.1f;
        if(keycode == Input.Keys.LEFT_BRACKET)
            torque -= 0.1f;

        // Remove the torque using backslash /
        if(keycode == Input.Keys.BACKSLASH)
            torque = 0.0f;

        // If user hits spacebar, reset everything back to normal
        if(keycode == Input.Keys.SPACE|| keycode == Input.Keys.NUM_2) {
            body.setLinearVelocity(0f, 0f);
            body.setAngularVelocity(0f);
            torque = 0f;
            sprite.setPosition(0f,0f);
            body.setTransform(0f,0f,0f);
        }

        if(keycode == Input.Keys.COMMA) {
            body.getFixtureList().first().setRestitution(body.getFixtureList().first().getRestitution()-0.1f);
        }
        if(keycode == Input.Keys.PERIOD) {
            body.getFixtureList().first().setRestitution(body.getFixtureList().first().getRestitution()+0.1f);
        }
        if(keycode == Input.Keys.ESCAPE || keycode == Input.Keys.NUM_1)
            drawSprite = !drawSprite;

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }


    // On touch we apply force from the direction of the users touch.
    // This could result in the object "spinning"
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        body.applyForce(1f,1f,screenX,screenY,true);
        //body.applyTorque(0.4f,true);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}