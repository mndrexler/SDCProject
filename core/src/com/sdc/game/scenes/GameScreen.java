package com.sdc.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sdc.game.Asteroid;
import com.sdc.game.Main;
import com.sdc.game.Physics;
import com.sdc.game.Player;
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
    private Asteroid[] asteroids;

    public GameScreen(Main g, String playerName){
        this.game = g;
        physics = new Physics();
        debugRenderer = new Box2DDebugRenderer();
        cam = new OrthographicCamera();
        view = new FitViewport(game.camWidth, game.camHeight,cam);
        this.cam.setToOrtho(false, game.camWidth, game.camHeight);

        player = new Player(game, playerName, physics.world);
        asteroids = new Asteroid[10];
        for(int i = 0; i < asteroids.length; i++){
            asteroids[i] = new Asteroid(game,(int)(game.camWidth * Math.random()),(int)(game.camHeight * Math.random()));
        }

    }

    @Override
    public void show() {

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

        player.update(delta);
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
        debugRenderer.dispose();
        for(Asteroid as: asteroids){
            as.dispose();
        }
    }
}
