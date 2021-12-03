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

import javax.swing.*;

public class GameScreen implements Screen {
    private Main game;
    private Physics physics;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera cam;
    private Viewport view;

    private Texture background = new Texture(Gdx.files.internal("gamescreen.jpg"));
    private Player player;
    private Player testPlayer;
    private Asteroid[] asteroids;

    public GameScreen(Main g, String playerName){
        this.game = g;
        physics = new Physics();
        debugRenderer = new Box2DDebugRenderer();
        cam = new OrthographicCamera();
        view = new FitViewport(game.camWidth, game.camHeight,cam);
        this.cam.setToOrtho(false, game.camWidth, game.camHeight);

        player = new Player(game, playerName, physics.world, 10, 10);
        testPlayer = new Player(game, "test", physics.world, 20, 10);
        asteroids = new Asteroid[10];
        for(int i = 0; i < asteroids.length; i++){
            asteroids[i] = new Asteroid(game, physics.world, (int)(game.camWidth * Math.random()),(int)(game.camHeight * Math.random()));
        }

        physics.world.setContactListener(new ContactListener() {
            @Override public void beginContact(Contact contact) {
                /*
                call collisions method in game
                OR
                directly call collisions method in projectiles class
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

    public void collisions(Fixture a, Fixture b) {

    }

    @Override
    public void render(float delta) {
        physics.logicStep(delta);
        //        Vector3 position = cam.position;
        //        position.x = player.getBody().getPosition().x * game.PIXELS_PER_METER;
        //        position.y = player.getBody().getPosition().y * game.PIXELS_PER_METER;
        //        cam.position.set(position);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();
        game.batch.draw(background, 0,0,game.camWidth,game.camHeight);

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
            asteroid.update(delta);
        }
        game.batch.end();
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
        player.dispose();
        physics.world.dispose();
        debugRenderer.dispose();
        for(Asteroid as: asteroids){
            as.dispose();
        }
    }
}

