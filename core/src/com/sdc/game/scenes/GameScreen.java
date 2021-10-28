package com.sdc.game.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.sdc.game.Main;
import com.sdc.game.Physics;

public class GameScreen implements Screen {

    private Main game;
    private Physics physics;

    public GameScreen(Main g){
        this.game = g;
        physics = new Physics();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

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

    }
}
