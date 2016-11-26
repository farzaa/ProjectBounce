package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.PlayActors.Ball;
import com.mygdx.game.PlayActors.BarBox2D;

import java.util.ArrayList;

/**
 * Created by flynn on 11/16/16.
 */


//I implement input processor here so that focus can change from the menu to the play screen.
public class PlayState extends State implements InputProcessor {
    public static final float GRAVITY = 1f;
    public static final float PIXELS_TO_METERS = 100f;

    public World world;
    Camera camera;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;

    Texture playstateHUD;
    ArrayList<Ball> ballList;
    ArrayList<Ball> destroyBallList;

    BarBox2D bar;
    BarBox2D bar2;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        //Many tutorials say to this step, but things seems to be working fine without it.
        //Box2D.init();
        playstateHUD = new Texture("playstate-hud.png");
        //First actually create World and add basic boundaries.
        initWorld();

        Gdx.input.setInputProcessor(this);
        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        ballList = new ArrayList<Ball>();
        destroyBallList = new ArrayList<Ball>();
        spawnBalls();

        //set up contact litsener for the balls/bars. this way we can do things like check for ball/bar collisions.
        //TO DO: set up bars so they give the balls and extra bump of force to keep things spicy.
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Body bodyA = contact.getFixtureA().getBody();
                Body bodyB = contact.getFixtureB().getBody();

                if((contact.getFixtureA().getShape().getType() == Shape.Type.Polygon)) {
                    Gdx.app.log("debug", "touched bar");

                    for (int i = 0; i < ballList.size(); i++) {
                        if(ballList.get(i).ballBody.equals(bodyB)) {
                            ballList.get(i).bounceCounter++;
                        }
                    }
                }

                else if((contact.getFixtureB().getShape().getType() == Shape.Type.Polygon)) {
                    Gdx.app.log("debug", "touched bar");

                    for (int i = 0; i < ballList.size(); i++) {
                        if(ballList.get(i).ballBody.equals(bodyA)) {
                            ballList.get(i).bounceCounter++;
                        }
                    }
                }

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

    public void initWorld() {
        //Create world, give it gravity.
        world = new World(new Vector2(0, GRAVITY), true);
        float w = Gdx.graphics.getWidth()/PIXELS_TO_METERS;
        float h = Gdx.graphics.getHeight()/PIXELS_TO_METERS - 20/PIXELS_TO_METERS;
        //Now create the boundaries. Pass it the vertexes.
        //top edge
        createEdge(-w/2,h/2,w/2,h/2);
        //left edge.
        //createEdge(-w/2,-7*h,-w/2,h/2);

        //TESTING!

        //TESTING!
        //Create part of left edge
        createEdge(-w/2 + 1, h/2, -w/2 + 1, 20/PIXELS_TO_METERS);

        //Now create the REST of it.
        createEdge(-w/2 + 1, -20/PIXELS_TO_METERS ,-w/2 + 1, -7*h);

        //right edge
        createEdge(w/2,-7*h,w/2,h/2);

        //Now create the bars
        //pass in world and the y coordinates of the bar. The x is not passed since it should all be centered already.
        bar = new BarBox2D(world, 0, 10);

        bar2 = new BarBox2D(world, 400, 0);

    }

    public void createBar() {

    }


    public void createEdge (float vx1, float vy1, float vx2, float vy2){
        BodyDef edgeInitial = new BodyDef();
        //StaticBody because our edges will not move.
        edgeInitial.type = BodyDef.BodyType.StaticBody;
        edgeInitial.position.set(0,0);

        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(vx1, vy1, vx2, vy2);
        FixtureDef leftEdgeFix = new FixtureDef();
        leftEdgeFix.shape = edgeShape;

        Body edgeFinal = world.createBody(edgeInitial);
        edgeFinal.createFixture(leftEdgeFix);

        edgeShape.dispose();
    }

    public void spawnBalls() {
        for(int i = 0; i < 5; i++) {
            ballList.add(new Ball(world));
        }
    }

    @Override
    protected void handleInput() {

    }

    public void checkBalls() {

        for(int i = 0; i < ballList.size(); i++) {

            if(ballList.get(i).ballBody.getPosition().y > 8) {
                ballList.get(i).destroyBoolAtomic.set(true);
            }

            if (ballList.get(i).destroyBoolAtomic.get() == true) {
                destroyBallList.add(ballList.get(i));
                Gdx.app.log("debug", "added to destroy list.");
            }
        }

        //remove balls from our list.
        if(!world.isLocked()) {
            for (int i = 0; i < destroyBallList.size(); i++) {
                world.destroyBody(destroyBallList.get(i).ballBody);
                ballList.remove(destroyBallList.get(i));
                Gdx.app.log("debug", "destroying");
            }
        }

        //re-init destroy ball list.
        destroyBallList =  new ArrayList<Ball>();
    }


    @Override
    protected void update(float dt) {

    }

    @Override
    protected void render(SpriteBatch sb) {
        camera.update();

        // Step the physics simulation forward at a rate of 60hz
        checkBalls();
        world.step(1f/60f, 6, 2);

        //logic for setting up ball draw coordinates
        for(int i = 0; i < ballList.size(); i++) {
            Sprite currSprite = ballList.get(i).ballSprite;
            Body currBody = ballList.get(i).ballBody;
            currSprite.setPosition((currBody.getPosition().x * PIXELS_TO_METERS) - currSprite.getWidth()/2 , (currBody.getPosition().y * PIXELS_TO_METERS) -currSprite.getHeight()/2 );
            currSprite.setRotation((float)Math.toDegrees(currBody.getAngle()));
        }

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.setProjectionMatrix(camera.combined);
        debugMatrix = sb.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,
                PIXELS_TO_METERS, 0);


        sb.begin();
        sb.draw(playstateHUD, -540, -960);
        //logic for actually drawing the balls.
        for(int i = 0; i < ballList.size(); i++) {
            Sprite sprite = ballList.get(i).ballSprite;
            //Gdx.app.log("debug", sprite.getX() + " " + sprite.getY());
            sb.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(),
                    sprite.getOriginY(),
                    sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.
                            getScaleY(), sprite.getRotation());
        }

        //drawing bars
        sb.draw(bar.barSprite, bar.barSprite.getX(),bar.barSprite.getY());
        sb.draw(bar2.barSprite, bar2.barSprite.getX(),bar2.barSprite.getY());

        sb.end();
        debugRenderer.render(world, debugMatrix);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //spawnBalls();
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //Check if bar is being touched!
        Gdx.app.log("debug", "Drag: " + screenX + " " + screenY);
        Gdx.app.log("debug", "BarPos : " + bar.barBodyLeft.getPosition().x + " " + 0);
        Gdx.app.log("debug", "BarSpriteLoc: " + bar.barSprite.getX() + " " + bar.barSprite.getY());

        if(bar.boundingRectangle.contains(screenX - 540, screenY - 960)) {
            bar.barBodyLeft.setTransform(screenX/PIXELS_TO_METERS - 540/PIXELS_TO_METERS - 2270/PIXELS_TO_METERS, bar.barBodyLeft.getPosition().y, 0);
            bar.barBodyRight.setTransform(screenX/PIXELS_TO_METERS - 540/PIXELS_TO_METERS + 2270/PIXELS_TO_METERS, bar.barBodyRight.getPosition().y, 0);
            bar.barSprite.setPosition(screenX - 1080 - 540, 0);
        }

        if(bar2.boundingRectangle.contains(screenX - 540, screenY - 960)) {
            bar2.barBodyLeft.setTransform(screenX/PIXELS_TO_METERS - 540/PIXELS_TO_METERS - 2270/PIXELS_TO_METERS, bar.barBodyLeft.getPosition().y, 0);
            bar2.barBodyRight.setTransform(screenX/PIXELS_TO_METERS - 540/PIXELS_TO_METERS + 2270/PIXELS_TO_METERS, bar.barBodyRight.getPosition().y, 0);
            bar2.barSprite.setPosition(screenX - 1080 - 540, 0);
        }


        //bar.barBody.setLinearVelocity(1,0);
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
