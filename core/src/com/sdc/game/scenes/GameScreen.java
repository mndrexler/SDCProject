package com.sdc.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sdc.game.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;

public class GameScreen implements Screen {
    private Main game;
    private Physics physics;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera cam;
    private Viewport view;
    private HUD hud;

    private Texture background = new Texture(Gdx.files.internal("gamescreen.jpg"));
    private Player player;
    private Player testPlayer;
    private Asteroid[] asteroids;
    private Map map;

    public GameScreen(Main g, String playerName){
        this.game = g;
        physics = new Physics();
        debugRenderer = new Box2DDebugRenderer();
        cam = new OrthographicCamera();
        this.cam.setToOrtho(false, game.camWidth, game.camHeight);
        view = new FitViewport(game.camWidth, game.camHeight, cam);
        map = new Map(game.batch);

        player = new Player(game, playerName, physics.world, 10, 10);
        hud = new HUD(game.batch,player);
        testPlayer = new Player(game, "test", physics.world, 20, 10);
        asteroids = new Asteroid[25];
        for(int i = 0; i < asteroids.length; i++) {
            asteroids[i] = new Asteroid(game, physics.world, map);
        }

        physics.world.setContactListener(new ContactListener() {
            @Override public void beginContact(Contact contact) {
                /*
                call collisions method here
                OR
                call collisions method in object class
                 */
                Object aObject = contact.getFixtureA().getBody().getUserData();
                Object bObject = contact.getFixtureB().getBody().getUserData();
                if (aObject instanceof Projectile) ((Projectile) aObject).onCollision(bObject);
                if (bObject instanceof Projectile) ((Projectile) bObject).onCollision(aObject);
            }

            @Override public void endContact(Contact contact) {

            }

            @Override public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    @Override
    public void show() {

    }

    private void keepBounds() {
        int mapLeft = 0;
        int mapRight = map.getWidth();
        int mapBottom = 0;
        int mapTop = map.getHeight();

        float cameraHalfWidth = cam.viewportWidth * .5f;
        float cameraHalfHeight = cam.viewportHeight * .5f;

        float cameraLeft = cam.position.x - cameraHalfWidth;
        float cameraRight = cam.position.x + cameraHalfWidth;
        float cameraBottom = cam.position.y - cameraHalfHeight;
        float cameraTop = cam.position.y + cameraHalfHeight;

        // horizontal axis
        if (map.getWidth() < cam.viewportWidth) {
            cam.position.x = mapRight / 2;
        } else if (cameraLeft <= mapLeft) {
            cam.position.x = mapLeft + cameraHalfWidth;
        } else if (cameraRight >= mapRight) {
            cam.position.x = mapRight - cameraHalfWidth;
        }

        // vertical axis
        if (map.getHeight() < cam.viewportHeight) {
            cam.position.y = mapTop / 2;
        } else if (cameraBottom <= mapBottom) {
            cam.position.y = mapBottom + cameraHalfHeight;
        } else if (cameraTop >= mapTop) {
            cam.position.y = mapTop - cameraHalfHeight;
        }
    }

    public Clip playAudio(String wav, boolean isMusic) {
        Clip clip = null;

        try {
            File file = new File(wav);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);

            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();

            if (isMusic) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return clip;
    }


    @Override
    public void render(float delta) {
        physics.logicStep(delta);
        Vector3 position = new Vector3();
        position.x = player.getBody().getPosition().x * game.PIXELS_PER_METER;
        position.y = player.getBody().getPosition().y * game.PIXELS_PER_METER;
        //cam.position.set(position);
        keepBounds();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();
        map.render(delta);

        player.update();
        testPlayer.update();
        if (testPlayer.toBeDeleted()) {
            //physics.world.destroyBody(testPlayer.getBody());
        }
        Array<Projectile> bullets = player.getBullets();
        for(int i = 0; i < bullets.size; i++) {
            if (bullets.get(i).toBeDeleted()){
                physics.world.destroyBody(bullets.get(i).getBody());
                bullets.removeIndex(i);
            }
        }
        for (Asteroid asteroid : asteroids) {
            asteroid.update();
        }
        game.batch.end();

        game.batch.setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.update(delta);
        debugRenderer.render(physics.world, cam.combined.scl(game.PIXELS_PER_METER));
    }

    @Override
    public void resize(int width, int height) {
        view.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        for (Projectile b : player.getBullets()) b.dispose();
        player.dispose();
        physics.world.dispose();
        debugRenderer.dispose();
        for(Asteroid as: asteroids) as.dispose();
        hud.dispose();
    }
}

