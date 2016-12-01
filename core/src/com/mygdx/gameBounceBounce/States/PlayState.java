package com.mygdx.gameBounceBounce.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.gameBounceBounce.PlayActors.Ball;

import java.util.ArrayList;

/**
 * Created by flynn on 11/16/16.
 */


//I implement input processor here so that focus can change from the menu to the play screen.
public class PlayState extends State implements InputProcessor {
    public static final float GRAVITY = 1f;
    public static final float PIXELS_TO_METERS = 100f;

    public World world;
    com.mygdx.gameBounceBounce.States.GameStateManager gsm;
    Camera camera;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;
    BitmapFont font;

    Texture playstateHUD;
    Texture livesTexture;

    Texture bombPower;
    Rectangle bombPowerRegion;
    boolean hasBombPower = false;

    Texture bonusPower;
    Rectangle bonusPowerRegion;
    boolean hasBonusPower = false;
    int scoreOffset = 0;
    int bonusCounter = 0;


    ArrayList<Ball> ballList;
    ArrayList<Ball> destroyBallList;

    ArrayList<Texture> ballFrames;

    com.mygdx.gameBounceBounce.PlayActors.BarBox2D bar;
    com.mygdx.gameBounceBounce.PlayActors.BarBox2D bar2;
    com.mygdx.gameBounceBounce.PlayActors.BarBox2D bar3;

    int liveCount = 3;
    int score = 0;

    Music gameMusic;
    Music bounceSound;
    Music explosionSound;
    Music touchdownSound;
    Music nextWave;
    Music bombsound;


    //we want to increase the number of balls that come down every wave, this keeps track of that number;
    int ballCount = 5;

    ArrayList<Object> powerUpList;

    public PlayServices playservices;

    public PlayState(PlayServices playservices, com.mygdx.gameBounceBounce.States.GameStateManager gsm) {
        super(gsm);
        this.gsm = gsm;
        this.playservices = playservices;

        //playservices.unlockAchievement();
        //playservices.submitScore(20);
        //playservices.showScore();

        //Many tutorials say to this step, but things seems to be working fine without it.
        //Box2D.init();
        playstateHUD = new Texture("playstate-hud.png");
        livesTexture = new Texture("heart-image.png");

        //init power ups to black and white.
        bombPower = new Texture("powerups/cherrybomb-bw.png");
        bonusPower = new Texture("powerups/bonus-bw.png");

        font = new BitmapFont();
        font.getData().setScale(5, 5);
        //First actually create World and add basic boundaries.
        initWorld();

        Gdx.input.setInputProcessor(this);
        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        ballList = new ArrayList<Ball>();
        destroyBallList = new ArrayList<Ball>();

        liveCount = 3;
        ballFrames = new ArrayList<Texture>();

        for(int i = 0; i <= 10; i++){
            ballFrames.add(new Texture("ballFrames/circle-image-" + i + ".png"));
        }

        //set/init up all the sounds here
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/play-state-audio.mp3"));
        gameMusic.play();
        gameMusic.setVolume(0.3f);
        gameMusic.setLooping(true);

        //i load all the sounds here since we'll be reusing them a lot.
        bounceSound = Gdx.audio.newMusic(Gdx.files.internal("audio/bump.wav"));
        bounceSound.setVolume(0.5f);

        explosionSound = Gdx.audio.newMusic(Gdx.files.internal("audio/explosion.wav"));
        explosionSound.setVolume(0.5f);

        touchdownSound = Gdx.audio.newMusic(Gdx.files.internal("audio/endzone.wav"));
        touchdownSound.setVolume(0.5f);

        bombsound = Gdx.audio.newMusic(Gdx.files.internal("audio/bombsound.wav"));
        bombsound.setVolume(0.5f);

        nextWave = Gdx.audio.newMusic(Gdx.files.internal("audio/nextwave.wav"));
        nextWave.setVolume(0.5f);

        spawnBalls();

        powerUpList = new ArrayList<Object>();

        bombPowerRegion = new Rectangle(-500, -900, 200, 200);
        bonusPowerRegion =  new Rectangle(0, -900, 200, 200);


        //this contains all the logic for contact within the game.
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Body bodyA = contact.getFixtureA().getBody();
                Body bodyB = contact.getFixtureB().getBody();

                //if a ball touches a bar
                if((contact.getFixtureA().getShape().getType() == Shape.Type.Polygon)) {
                    Gdx.app.log("debug", "touched bar");
                    bounceSound.play();

                    for (int i = 0; i < ballList.size(); i++) {
                        //We want to find the ball that touched the bar.
                        if(ballList.get(i).ballBody.equals(bodyB)) {
                            ballList.get(i).bounceCounter++;
                            //Keep on adding force to the ball as long as it touches a bar so things stay moving.
                            ballList.get(i).ballBody.applyForceToCenter(new Vector2(0, 7), true);
                        }
                    }
                }

                //if a ball touches a bar but if the roles are switched, we do the exact same thing as above.
                else if((contact.getFixtureB().getShape().getType() == Shape.Type.Polygon)) {
                    Gdx.app.log("debug", "touched bar");
                    bounceSound.play();

                    for (int i = 0; i < ballList.size(); i++) {
                        if(ballList.get(i).ballBody.equals(bodyA)) {
                            ballList.get(i).bounceCounter++;
                            ballList.get(i).ballBody.applyForceToCenter(new Vector2(0, 7), true);
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


    //logic for touching powerups.
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Gdx.app.log("debug", "TOUCH: " + screenX + " " + screenY);

        if(bombPowerRegion.contains(screenX - 540, screenY - 2700) && hasBombPower == true ) {
            bombPowerUp();
            hasBombPower = false;
            bombPower = new Texture("powerups/cherrybomb-bw.png");
        }

        else if(bonusPowerRegion.contains(screenX - 540, screenY - 2700) && hasBonusPower == true ) {
            hasBonusPower = false;
            bonusPower = new Texture("powerups/bonus-bw.png");
            bonusCounter = 2;
        }
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

        //logic for middle bar
        if(bar.boundingRectangle.contains(screenX - 540, screenY - 960)) {
            bar.barBodyLeft.setTransform(screenX/PIXELS_TO_METERS - 540/PIXELS_TO_METERS - 2270/PIXELS_TO_METERS, bar.barBodyLeft.getPosition().y, 0);
            bar.barBodyRight.setTransform(screenX/PIXELS_TO_METERS - 540/PIXELS_TO_METERS + 2270/PIXELS_TO_METERS, bar.barBodyRight.getPosition().y, 0);
            bar.barSprite.setPosition(screenX - 1080 - 540, bar.barSprite.getY());
        }

        //logic for top bar
        if(bar2.boundingRectangle.contains(screenX - 540, screenY - 140)) {
            bar2.barBodyLeft.setTransform(screenX/PIXELS_TO_METERS - 540/PIXELS_TO_METERS - 2270/PIXELS_TO_METERS, bar2.barBodyLeft.getPosition().y, 0);
            bar2.barBodyRight.setTransform(screenX/PIXELS_TO_METERS - 540/PIXELS_TO_METERS + 2270/PIXELS_TO_METERS, bar2.barBodyRight.getPosition().y, 0);
            bar2.barSprite.setPosition(screenX - 1080 - 540, bar2.barSprite.getY());
        }

        //logic for bottom bar
        if(bar3.boundingRectangle.contains(screenX - 540, screenY - 1800)) {
            bar3.barBodyLeft.setTransform(screenX/PIXELS_TO_METERS - 540/PIXELS_TO_METERS - 2270/PIXELS_TO_METERS, bar3.barBodyLeft.getPosition().y, 0);
            bar3.barBodyRight.setTransform(screenX/PIXELS_TO_METERS - 540/PIXELS_TO_METERS + 2270/PIXELS_TO_METERS, bar3.barBodyRight.getPosition().y, 0);
            bar3.barSprite.setPosition(screenX - 1080 - 540, bar3.barSprite.getY());
        }

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

    public void initWorld() {
        //Create world, give it gravity.
        world = new World(new Vector2(0, GRAVITY), true);
        float w = Gdx.graphics.getWidth()/PIXELS_TO_METERS;
        float h = Gdx.graphics.getHeight()/PIXELS_TO_METERS - 20/PIXELS_TO_METERS;
        //Now create the boundaries. Pass it the vertexes.
        //top edge
        createEdge(-w/2,h/2,w/2,h/2);
        //left edge.
        createEdge(-w/2, 960/PIXELS_TO_METERS, -w/2, 430/PIXELS_TO_METERS);
        createEdge(-w/2, 375/PIXELS_TO_METERS, -w/2, 40/PIXELS_TO_METERS);

        //Now create the REST of it.
        createEdge(-w/2, -20/PIXELS_TO_METERS ,-w/2, -7*h);

        //right edge
        createEdge(w/2,-7*h,w/2,h/2);

        //Now create the bars
        //pass in world and the y coordinates of the bar. The x is not passed since it should all be centered already.
        bar = new com.mygdx.gameBounceBounce.PlayActors.BarBox2D(world, 0, 10);

        bar2 = new com.mygdx.gameBounceBounce.PlayActors.BarBox2D(world, 400, 0);

        bar3 = new com.mygdx.gameBounceBounce.PlayActors.BarBox2D(world, -400, 0);

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
        for(int i = 0; i < ballCount; i++) {
            nextWave.play();
            ballList.add(new Ball(world));
        }
    }

    @Override
    protected void handleInput() {

    }

    //this checks for balls on our destroy list and destroys them as necessary.
    public void checkBalls() {

        for(int i = 0; i < ballList.size(); i++) {

            //If the balls reach the end zone, we want to remove them and add to our score.
            if(ballList.get(i).ballBody.getPosition().y > 8) {
                ballList.get(i).destroyBoolAtomic.set(true);
                touchdownSound.play();

                //power up logic
                if(ballList.get(i).powerUpText != null) {

                    //heart is pretty simple, since it doesn't have any button control.
                    if(ballList.get(i).powerUpType.equals("heart")) {
                        if(liveCount < 3)
                            liveCount++;
                    }

                    if(ballList.get(i).powerUpType.equals("bomb")) {
                        if(hasBombPower == false) {
                            bombPower = new Texture("powerups/cherrybomb.png");
                            hasBombPower = true;
                        }
                    }

                    if(ballList.get(i).powerUpType.equals("bonus")) {
                        if(hasBonusPower ==  false) {
                            bonusPower = new Texture("powerups/bonus.png");
                            hasBonusPower = true;
                        }
                    }
                }

                if(bonusCounter != 0) {
                    bonusCounter--;
                    score = score + 2;
                }

                else {
                    score++;
                }

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

    //if the ball touches the bar more than 10 times, it will be added to destroy list.
    public void checkBounceCount() {
        for(int i = 0; i < ballList.size(); i++) {
            //Gdx.app.log("debug", "BallCount ... " + ballList.get(i).bounceCounter);
            if(ballList.get(i).bounceCounter > 10) {
                //we want to subtract from the life count upon a ball explosion.
                explosionSound.play();
                liveCount--;
                destroyBallList.add(ballList.get(i));
            }
        }
    }

    public void checkLiveCount() {
        if(liveCount == 0) {
            dispose();
            playservices.submitScore(score);
            playservices.showScore();
            gsm.set(new MenuState(playservices, gsm));
        }
    }

    public void changeBallSprites(){
        for(int i = 0; i < ballList.size(); i++) {
            int frame = ballList.get(i).bounceCounter;

            //TO DO: SUPER IMPORTANT: How do I dispose the old textures when setting the new ones? AH.
            ballList.get(i).ballSprite.setTexture(ballFrames.get(frame));
        }
    }

    public void checkBallCount() {
        if(ballList.isEmpty()) {
            ballCount = ballCount + 3;
            spawnBalls();
        }
    }

    //logic for bar power up
    public void bombPowerUp() {
        bombsound.play();
        for(int i = 0; i < ballList.size()/2; i++) {
            score++;
            destroyBallList.add(ballList.get(i));
        }
    }

    public void checkScoreCount() {
        if (score == 1) {
            playservices.unlockAchievement();
        }
    }


    @Override
    protected void update(float dt) {

    }

    @Override
    protected void render(SpriteBatch sb) {
        camera.update();

        checkBounceCount();
        checkLiveCount();
        checkBalls();
        changeBallSprites();
        checkBallCount();
        checkScoreCount();

        // Step the physics simulation forward at a rate of 60hz
        world.step(1f/60f, 6, 2);

        //logic for setting up ball draw coordinates
        for(int i = 0; i < ballList.size(); i++) {

            Sprite currSprite = ballList.get(i).ballSprite;
            Body currBody = ballList.get(i).ballBody;

            currSprite.setPosition((currBody.getPosition().x * PIXELS_TO_METERS) - currSprite.getWidth()/2 , (currBody.getPosition().y * PIXELS_TO_METERS) -currSprite.getHeight()/2 );
            currSprite.setRotation((float)Math.toDegrees(currBody.getAngle()));

            if(ballList.get(i).powerUpText != null) {
                ballList.get(i).powerUpText.setPosition(currSprite.getX() + currSprite.getWidth() / 2 - 50, currSprite.getY() + currSprite.getHeight() / 2 - 50);
                ballList.get(i).powerUpText.setRotation((float)Math.toDegrees(currBody.getAngle()));
            }
        }

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.setProjectionMatrix(camera.combined);
        debugMatrix = sb.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS, PIXELS_TO_METERS, 0);


        sb.begin();
        sb.draw(playstateHUD, -540, -960);
        //logic for actually drawing the balls.
        for(int i = 0; i < ballList.size(); i++) {
            Sprite sprite = ballList.get(i).ballSprite;
            Sprite powerSprite =  ballList.get(i).powerUpText;
            //Gdx.app.log("debug", sprite.getX() + " " + sprite.getY());
            sb.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
            if(ballList.get(i).powerUpText != null) {
                sb.draw(powerSprite, powerSprite.getX(), powerSprite.getY(), powerSprite.getOriginX(), powerSprite.getOriginY(), powerSprite.getWidth(), powerSprite.getHeight(), powerSprite.getScaleX(), powerSprite.getScaleY(), powerSprite.getRotation());
            }
        }

        //drawing bars
        sb.draw(bar.barSprite, bar.barSprite.getX(),bar.barSprite.getY());
        sb.draw(bar2.barSprite, bar2.barSprite.getX(),bar2.barSprite.getY());
        sb.draw(bar3.barSprite, bar3.barSprite.getX(), bar3.barSprite.getY());


        //draw live count
        for(int i = 0; i < liveCount; i++) {
            sb.draw(livesTexture, 225 + i*100 , 800);
        }

        //draw score
        font.setColor(Color.BLACK);
        font.draw(sb, Integer.toString(score), -475, 875);

        //draw powerups
        sb.draw(bombPower, -500, -940, 150, 150);
        sb.draw(bonusPower, -100, -965, 200, 200);

        sb.end();
        debugRenderer.render(world, debugMatrix);
    }

    public void dispose() {
        gameMusic.dispose();
    }
}
